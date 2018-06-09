package com.example.bvarg.ubicacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Menu_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Conversacion.OnFragmentInteractionListener, Muro.OnFragmentInteractionListener, Mensajes.OnFragmentInteractionListener, Noticias.OnFragmentInteractionListener, Informacion.OnFragmentInteractionListener, HorarioBuses.OnFragmentInteractionListener, HorarioTrenes.OnFragmentInteractionListener, Mapa.OnFragmentInteractionListener, Directorio.OnFragmentInteractionListener, Horarios.OnFragmentInteractionListener, Amigos.OnFragmentInteractionListener, MuroAmigo.OnFragmentInteractionListener, Perfil.OnFragmentInteractionListener {

    TextView textonombre;
    ImageView fotoperfil;
    NavigationView navigationView;
    static View hView;
    static int cambio = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__nav);
        setTitle("SocialiTec");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new Muro()).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        if(cambio==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new Noticias()).commit();
        }

        extraernombre(login.sharedPreferences.getString("id",""));
    }

    public void setActionBarTitle(String title){
        setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.configurarperfil) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new Perfil()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentseleccionado = false;

        if (id == R.id.nav_muro) {
            miFragment = new Muro();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_noticias) {
            miFragment = new Noticias();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_informacion) {
            miFragment = new Informacion();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_amigos) {
            miFragment = new Amigos();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_mensajes) {
            miFragment = new Mensajes();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_sesion) {
            login.sharedPreferences.edit().putString("token", "").apply();
            login.sharedPreferences.edit().putString("id", "").apply();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
        }
        if(fragmentseleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void extraernombre(final String id){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://socialitec.herokuapp.com/api/usuario";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        String n = "";
                        String a;
                        try {
                            JSONObject obj = new JSONObject(response);
                            Iterator<String> keys = obj.keys();
                            while (keys.hasNext()) {
                                // obtiene el nombre del objeto.
                                String key = keys.next();
                                try {
                                    JSONObject obj2 = obj.getJSONObject(key);
                                    if(obj2.has("foto")){
                                        login.sharedPreferences.edit().putString("foto", obj2.getString("foto")).apply();
                                    }
                                    else{
                                        login.sharedPreferences.edit().putString("foto", "").apply();
                                    }
                                    hView = navigationView.getHeaderView(0);
                                    TextView nombre = hView.findViewById(R.id.textonombre);
                                    ImageView fotoperfil = hView.findViewById(R.id.imagenperfil);
                                    nombre.setText(String.valueOf(obj2.getString("nombre")+" "+obj2.getString("apellidos")));
                                    Glide.with(getApplicationContext())
                                            .load(Uri.parse(obj2.getString("foto")))
                                            .into(fotoperfil);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("_id", id);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
