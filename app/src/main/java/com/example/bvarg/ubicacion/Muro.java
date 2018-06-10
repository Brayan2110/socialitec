package com.example.bvarg.ubicacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Muro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Muro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Muro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String nombre;
    String apellido;
    String foto;
    List<Publicacion> publicaciones = new ArrayList<>();

    View vista;

    private OnFragmentInteractionListener mListener;

    public Muro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Muro.
     */
    // TODO: Rename and change types and number of parameters
    public static Muro newInstance(String param1, String param2) {
        Muro fragment = new Muro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       vista = inflater.inflate(R.layout.fragment_muro, container, false);
       ((Menu_Nav) getActivity()).setActionBarTitle("Muro");
       publicaciones = new ArrayList<>();
       buscarpublicaciones(login.sharedPreferences.getString("token", ""));
       //agregarpublicaciones();
       //llenarlista();

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void llenarlista() {
        ArrayAdapter<Publicacion> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Publicacion>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itempublicaciones, publicaciones);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;

            if(!publicaciones.get(publicaciones.size()-1-position).getImagen().equals("")) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itempublicaciones, parent, false);
            }
            else{
                itemView = getLayoutInflater().inflate(R.layout.activity_itempublicaciones2, parent, false);
            }

            Log.d("numero", String.valueOf(position));
            Publicacion Currentpublicacion = publicaciones.get(publicaciones.size()-1-position);

            if(!Currentpublicacion.getImagen().equals("")){
                TextView NombreText = itemView.findViewById(R.id.textViewnombre);
                TextView FechaText = itemView.findViewById(R.id.textViewfecha);
                TextView Texto = itemView.findViewById(R.id.textViewtexto);
                CircleImageView Foto = itemView.findViewById(R.id.fotoperfil);
                ImageView Imagen = itemView.findViewById(R.id.imageViewimagen);

                NombreText.setText(Currentpublicacion.getNombre());
                FechaText.setText(Currentpublicacion.getFecha());
                Texto.setText(Currentpublicacion.getMensaje());
                if(!Currentpublicacion.getFoto().equals("")){
                    Glide.with(getContext())
                            .load(Uri.parse(Currentpublicacion.getFoto()))
                            .into(Foto);
                }
                if(!Currentpublicacion.getImagen().equals("")){
                    Glide.with(getContext())
                            .load(Uri.parse(Currentpublicacion.getImagen()))
                            .into(Imagen);
                }
            }
            else{
                TextView NombreText = itemView.findViewById(R.id.textViewnombre);
                TextView FechaText = itemView.findViewById(R.id.textViewfecha);
                TextView Texto = itemView.findViewById(R.id.textViewtexto);
                CircleImageView Foto = itemView.findViewById(R.id.fotoperfil);

                NombreText.setText(Currentpublicacion.getNombre());
                FechaText.setText(Currentpublicacion.getFecha());
                Texto.setText(Currentpublicacion.getMensaje());
                if(!Currentpublicacion.getFoto().equals("")){
                    Glide.with(getContext())
                            .load(Uri.parse(Currentpublicacion.getFoto()))
                            .into(Foto);
                }
            }
            return itemView;
        }
    }

    public void buscarpublicaciones(final String token){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://socialitec.herokuapp.com/api/publication";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            Iterator<String> keys = obj.keys();
                            while (keys.hasNext())
                            {
                                // obtiene el nombre del objeto.
                                String key = keys.next();
                                Log.i("Parser", "objeto : " + key);
                                JSONArray jsonArray = obj.getJSONArray(key);
                                for(int i= 0; i<obj.getJSONArray(key).length(); i++){
                                    JSONObject mainObject = new JSONObject(jsonArray.getString(i));

                                    //obtiene valores dentro del objeto.
                                    String anho = mainObject.getString("publicationDate").substring(0,4);
                                    String mes = mainObject.getString("publicationDate").substring(5,7);
                                    String dia = mainObject.getString("publicationDate").substring(8,10);
                                    //Log.i("fecha",dia+"/"+mes+"/"+anho);
                                    Log.d("usuario", mainObject.getString("usuario"));
                                    extraernombre(mainObject.getString("usuario"),i);
                                    publicaciones.add(new Publicacion("",nombre+" "+apellido, mainObject.getString("texto"),dia+"/"+mes+"/"+anho,""));
                                    if(mainObject.has("imagen")){
                                        publicaciones.get(publicaciones.size()-1).setImagen(mainObject.getString("imagen"));
                                    }
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
                params.put("authorization", token);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void extraernombre(final String id, final int i){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://socialitec.herokuapp.com/api/usuario";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Iterator<String> keys = obj.keys();
                            while (keys.hasNext()) {
                                // obtiene el nombre del objeto.
                                String key = keys.next();
                                try {
                                    JSONObject obj2 = obj.getJSONObject(key);
                                    Log.d("nombre",obj2.getString("nombre"));
                                    nombre = obj2.getString("nombre");
                                    apellido = obj2.getString("apellidos");
                                    publicaciones.get(i).setNombre(obj2.getString("nombre")+ " "+obj2.getString("apellidos"));
                                    publicaciones.get(i).setFoto(obj2.getString("foto"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                llenarlista();
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
