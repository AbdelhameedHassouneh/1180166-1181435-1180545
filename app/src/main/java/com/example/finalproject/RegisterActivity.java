package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText userName,password,rePassword,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName=findViewById(R.id.et_name);
        password=findViewById(R.id.et_password);
        rePassword=findViewById(R.id.et_repassword);
        email=findViewById(R.id.et_email);
        


    }

    public void registerssOnClick(View view) {
        if(userName.getText().toString().isEmpty()||password.getText().toString().isEmpty()||rePassword.getText().toString().isEmpty()||email.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            if(password.getText().toString().equals(rePassword.getText().toString())){
                String url = "http://10.0.2.2/androidProject/register.php?userName="+userName.getText().toString()+"&password="+password.getText().toString();


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url,
                                null, new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(MainActivity.this, "fkfkfkkfkf", Toast.LENGTH_SHORT).show();

//                        error.printStackTrace();
                            }
                        });

                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
                Intent intent=new Intent(this,MainActivity.class);
                Toast.makeText(this, "Registering succefully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                
                
            }else {
                Toast.makeText(this, "Please make sure that the password equals the clone", Toast.LENGTH_SHORT).show();
                
                
            }
            
            
            
        }


    }
}