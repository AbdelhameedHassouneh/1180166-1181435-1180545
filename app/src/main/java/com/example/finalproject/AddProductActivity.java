package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class AddProductActivity extends AppCompatActivity {
    EditText productNameText,priceText;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        productNameText=findViewById(R.id.productNameTextView);
        priceText=findViewById(R.id.editTextNumber);
        spinner=findViewById(R.id.spinner);




    }

    public void addProductButtonOnClick(View view) {
        if(productNameText.getText().toString().isEmpty()||priceText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            String productName=productNameText.getText().toString();
            double price=Double.parseDouble(priceText.getText().toString());
            String type=spinner.getSelectedItem().toString();

            String url = "http://10.0.2.2/androidProject/addProduct.php?productName="+productName + "&productPrice=" +price+"&productType="+type;


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
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
            Toast.makeText(this, "Product has been added", Toast.LENGTH_SHORT).show();






        }




    }
}