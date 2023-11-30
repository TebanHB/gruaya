package com.example.gruaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button register,login;
    TextView texterror;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        register = findViewById(R.id.btn_login_register);
        login = findViewById(R.id.btn_login_login);
        texterror = findViewById(R.id.texterror);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarWs();
            }
        });
    }
    private void EnviarWs(){ //Leer Web Services
        String url = getResources().getString(R.string.url) + "login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Log.i("message", message);
                    if(message.equals("Logged in successfully")){
                        JSONObject jsonUser = jsonObject.getJSONObject("user");
                        String token = jsonUser.getString("token");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("token",token);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.i("message", message);
                    }
                } catch (JSONException e) {
                    System.out.println("esta en el catch");
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    if(statusCode == 403){
                        texterror.setText("Credenciales Inválidas");
                        Log.e("Error", "Credenciales Inválidas");
                    }else{
                    Log.e("Error", "Código de respuesta HTTP: " + statusCode);
                    }
                }else{
                    if(error.getMessage() != null){
                        Log.e("Error", error.getMessage());
                    }else{
                        Log.e("Error", "Error desconocido");
                    }
                }

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("password",password.getText().toString());
                params.put("email",email.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

}