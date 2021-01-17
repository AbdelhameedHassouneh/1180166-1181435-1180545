package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    EditText nameField,passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       nameField=findViewById(R.id.loginNameField);
       passwordField=findViewById(R.id.loginPasswordField);

    }


    public void toRegisterOnClick(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }
    static boolean isAllowed=false;
    public void logInOnClick(View view) {
        Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
        if(nameField.getText().toString().isEmpty()||passwordField.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            String userName=nameField.getText().toString();
            String password=passwordField.getText().toString();
            if(userName.startsWith("manager")){
                Toast.makeText(this, "starts with manager", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "doesn't start", Toast.LENGTH_SHORT).show();
            }

            if(userName.startsWith("manager")){
                String url ="http://10.0.2.2/androidProject/loginscript.php?username="+userName+"&password=" +password;


                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url,
                                null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String AccessedArray[];
                                try {
                                    JSONArray array = response.getJSONArray("kit");
                                    AccessedArray = new String[array.length()];

                                        JSONObject obj = array.getJSONObject(0);

                                    isAllowed =  obj.getBoolean("isAccessed");
                                    System.err.println("isALowwed"+isAllowed);

                                    if(isAllowed){
                                        Intent intent=new Intent(getBaseContext(),AddProductActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getBaseContext(), "Wrong password or userName", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "bbbbb", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "fkfkfkkfkf", Toast.LENGTH_SHORT).show();

                                error.printStackTrace();
                            }
                        });
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



            }else{


                String url ="http://10.0.2.2/androidProject/loginscriptCustomer.php?username="+userName+"&password=" +password;


                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url,
                                null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String AccessedArray[];
                                try {
                                    JSONArray array = response.getJSONArray("kit");
                                    AccessedArray = new String[array.length()];

                                    JSONObject obj = array.getJSONObject(0);

                                    isAllowed =  obj.getBoolean("isAccessed");
                                    System.err.println("isALowwed"+isAllowed);

                                    if(isAllowed){
                                        int id=obj.getInt("id");

                                        Intent intent=new Intent(getBaseContext(),AddOrderActivity.class);
                                        intent.putExtra("id",id);

                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getBaseContext(), "Wrong password or userName", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "bbbbb", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "fkfkfkkfkf", Toast.LENGTH_SHORT).show();

                                error.printStackTrace();
                            }
                        });
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);






            }

        }
    }
}