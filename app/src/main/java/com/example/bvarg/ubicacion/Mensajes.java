package com.example.bvarg.ubicacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * {@link Mensajes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mensajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mensajes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Persona> personas = new ArrayList<>();
    List<Mensaje> mensajes = new ArrayList<>();
    ListView lista;

    View vista;

    private OnFragmentInteractionListener mListener;

    public Mensajes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mensajes.
     */
    // TODO: Rename and change types and number of parameters
    public static Mensajes newInstance(String param1, String param2) {
        Mensajes fragment = new Mensajes();
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
        vista = inflater.inflate(R.layout.fragment_mensajes, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Mensajes");
        lista = vista.findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idpersona = personas.get(position).getId();
                Conversacion.mensajes2.clear();
                Conversacion.idotrapersona = personas.get(position).getId();
                Conversacion.nombre = personas.get(position).getNombre()+" "+personas.get(position).getApellido();
                Conversacion.fotootrapersona = personas.get(position).getFoto();
                Log.d("direccion",login.sharedPreferences.getString("foto",""));
                Conversacion.fotopersona = login.sharedPreferences.getString("foto","");
                for(int i = 0; i<mensajes.size(); i++){
                    if(mensajes.get(i).getReceptor().equals(idpersona) || mensajes.get(i).getEmisor().equals(idpersona)){
                        Conversacion.mensajes2.add(mensajes.get(i));
                    }
                }
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.content_main, new Conversacion());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        buscarmensajes(login.sharedPreferences.getString("token",""),login.sharedPreferences.getString("id",""));

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
        ArrayAdapter<Persona> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Persona>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemmensaje, personas);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView==null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itemmensaje, parent, false);
            }
            Persona CurrentPersona = personas.get(position);

            TextView NombreText = itemView.findViewById(R.id.textViewnombre);
            CircleImageView fotoperfil = itemView.findViewById(R.id.fotoperfil);
            TextView TextoText = itemView.findViewById(R.id.textViewmensaje);
            NombreText.setText(CurrentPersona.getNombre()+ " "+CurrentPersona.getApellido());
            for (int i = mensajes.size(); i != 0; i--) {
                if (CurrentPersona.getId().equals(mensajes.get(i-1).getEmisor())|| CurrentPersona.getId().equals(mensajes.get(i-1).getReceptor())) {
                    TextoText.setText(mensajes.get(i-1).getTexto());
                    if(!CurrentPersona.getFoto().equals("")){
                        Glide.with(getContext())
                                .load(Uri.parse(CurrentPersona.getFoto()))
                                .into(fotoperfil);
                    }
                    break;
                }
            }
            return itemView;
        }
    }

    public void buscarmensajes(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://socialitec.herokuapp.com/api/message/"+id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("mensajes", response);
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
                                    mensajes.add(new Mensaje(mainObject.getString("emisor"),mainObject.getString("receptor"),mainObject.getString("texto")));
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obtenerconversaciones();
                        //llenarlista();
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

    public void obtenerconversaciones() {
        int index = 0;
        for (int i = mensajes.size(); i != 0; i--) {
            boolean entra = true;
            Log.d("largo", String.valueOf(personas.size()));
            for (int y = 0; y < personas.size(); y++) {
                Log.d("personaid",personas.get(y).getId());
                Log.d("emisorid",mensajes.get(i - 1).getEmisor());
                Log.d("receptorid",mensajes.get(i - 1).getReceptor());
                if (personas.get(y).getId().equals(mensajes.get(i - 1).getEmisor()) || personas.get(y).getId().equals(mensajes.get(i - 1).getReceptor())) {
                    Log.d("probando","mierda");
                    entra = false;
                }
            }
            if (entra) {
                if(mensajes.get(i-1).getReceptor().equals(login.sharedPreferences.getString("id",""))){
                    personas.add(new Persona("", "",mensajes.get(i-1).getEmisor(),""));
                    extraernombre(mensajes.get(i-1).getEmisor(),index);
                    index++;
                }
                else{
                    personas.add(new Persona("", "",mensajes.get(i-1).getReceptor(),""));
                    extraernombre(mensajes.get(i-1).getReceptor(),index);
                    index++;
                }
            }
        }
    }

    public void extraernombre(final String id,final int i){
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
                                    personas.get(i).setNombre(obj2.getString("nombre"));
                                    personas.get(i).setApellido(obj2.getString("apellidos"));
                                    personas.get(i).setId(id);
                                    if(obj2.has("foto")){
                                        personas.get(i).setFoto(obj2.getString("foto"));
                                    }
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
