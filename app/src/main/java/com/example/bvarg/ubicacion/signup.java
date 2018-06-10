package com.example.bvarg.ubicacion;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {


    Button registro;
    EditText correo;
    EditText password;
    EditText password2;
    EditText apellidos;
    EditText nombre;
    RelativeLayout loadingPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Registro");
        loadingPanel = findViewById(R.id.loadingPanel);
        correo = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_contrasena);
        password2 = findViewById(R.id.editText_contrasena2);
        apellidos = findViewById(R.id.editText_apellidos);
        nombre = findViewById(R.id.editText_nombre);

        registro = findViewById(R.id.button_registro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correo.getText().toString().equals("") && !password.getText().toString().equals("") && !password2.getText().toString().equals("") && !apellidos.getText().toString().equals("") && !nombre.getText().toString().equals("")){
                    if(password.getText().toString().equals(password2.getText().toString())){
                        loadingPanel.setVisibility(View.VISIBLE);
                        registrar(correo.getText().toString().toLowerCase(), password.getText().toString(),nombre.getText().toString(),apellidos.getText().toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Las dos contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Se debe llenar toda la informacion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registrar(final String email, final String password, final String nombre, final String apellidos){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/signup";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        loadingPanel.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "No se pudo completar el registro", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", "fallo");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(postRequest);
    }
}
