package com.example.bvarg.ubicacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    Button login;
    Button signup;
    EditText email;
    EditText password;
    SignInButton google;
    RelativeLayout loadingPanel;
    String token;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        token = sharedPreferences.getString("token", "");
        loadingPanel = findViewById(R.id.loadingPanel);
        validartoken(token);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPreferences = getSharedPreferences("com.example.bvarg.ubicacion", Context.MODE_PRIVATE);
        setTitle("SocialiTec");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.button_registro);
        signup = findViewById(R.id.button_signup);
        google = findViewById(R.id.signInButton);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_contrasena);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarusuario(email.getText().toString().toLowerCase(), password.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void validartoken(final String token){
        loadingPanel.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://socialitec.herokuapp.com/api/private";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Intent intent = new Intent(getApplicationContext(), Menu_Nav.class);
                        startActivity(intent);
                        loadingPanel.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loadingPanel.setVisibility(View.INVISIBLE);
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("authorization", token);
                return params;
            }
        };
        queue.add(postRequest);
    }


    public void validarusuario(final String email, final String password){
        loadingPanel.setVisibility(View.VISIBLE);
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/signin";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("respuesta",response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            sharedPreferences.edit().putString("token", obj.getString("token")).apply();
                            sharedPreferences.edit().putString("id", obj.getString("id")).apply();
                            Intent intent = new Intent(getApplicationContext(), Menu_Nav.class);
                            startActivity(intent);
                            loadingPanel.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        loadingPanel.setVisibility(View.INVISIBLE);
                        Log.d("Error.Response", "fallo");
                        Toast.makeText(getApplicationContext(), "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(postRequest);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        }
        return true;
    }
}
