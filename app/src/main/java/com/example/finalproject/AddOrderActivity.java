package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Product;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class AddOrderActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    EditText e;
    ProductChooserAdapter adapter;
    Context c;
    Customer customer;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Intent intent=getIntent();
         id =intent.getIntExtra("id",0);
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();

        int result = availability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (!availability.isUserResolvableError(result)) {
                Toast.makeText(this, "Error in the on create", Toast.LENGTH_LONG).show();
            }
        }

        c = this;


        loadingItemsToOrder();


    }

    public void loadingItemsToOrder() {
        String url = "http://10.0.2.2/androidProject/q.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(c));
                        String f = "";
                        try {
                            JSONArray array = response.getJSONArray("kit");
                            Product[]products = new Product[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                Product p = new Product(obj.getString("productName"), Double.parseDouble(obj.getString("productPirce")), Integer.parseInt(obj.getString("productId")), obj.getString("productType"));
                                products[i] = p;
                                f += p.getProductName() + "\n";
                            }
                            adapter = new ProductChooserAdapter(c, products);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        error.printStackTrace();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

    public void orderOnClick(View view) {
        // Check if we have permission to access high accuracy fine location.
        int permission = ActivityCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION);

        // If permission is granted, fetch the last location.
        if (permission == PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient;
            fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                    == PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                System.err.println("in");

                                Geocoder geocoder = new Geocoder(c);
                                try{
                                    List<Address> address = geocoder.getFromLocation(location.getLatitude(),
                                            location.getLongitude(), 1);
                                    String finaladdress=address.get(0).getAddressLine(0);
                                    System.err.println("final address"+finaladdress);

                                    connectingWithDb(finaladdress);
                                }catch(Exception e){

                                }


                            }
                        });

            }
        } else {
            // If permission has not been granted, request permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }












    }


    public void connectingWithDb(String finaladdress){
        Product products[]=adapter.products;
        String pIds = "";
        double price=0;
        int counter=0;
        for (int i=0;i<adapter.checkBoxesStatus.length;i++) {

            if (adapter.checkBoxesStatus[i]) {
                counter++;
                price+=products[i].getPrice();



                if (pIds.isEmpty()) {
                    pIds+=products[i].getId();
                }else{
                    pIds+=","+products[i].getId();
                }
            }
        }

        if(finaladdress.isEmpty()){
            //Toast.makeText(c, "address is empty", Toast.LENGTH_SHORT).show();
        }
        System.err.println("address in php code"+ finaladdress);
        String url = "http://10.0.2.2/androidProject/addOrder.php?cId="+id + /*customer.getId() +*/ "&pIds=" +pIds+"&price="+price+"&address="+finaladdress;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


//                        error.printStackTrace();
                    }
                });
        Toast.makeText(this, "Order has been placed", Toast.LENGTH_SHORT).show();
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}