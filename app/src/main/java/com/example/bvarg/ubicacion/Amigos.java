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
 * {@link Amigos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Amigos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Amigos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Persona> personas = new ArrayList<>();

    ListView list;
    View vista;

    private OnFragmentInteractionListener mListener;

    public Amigos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Amigos.
     */
    // TODO: Rename and change types and number of parameters
    public static Amigos newInstance(String param1, String param2) {
        Amigos fragment = new Amigos();
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
        vista = inflater.inflate(R.layout.fragment_amigos, container, false);
        personas = new ArrayList<>();
        ((Menu_Nav) getActivity()).setActionBarTitle("Personas");
        list = vista.findViewById(R.id.lista);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MuroAmigo.idamigo = personas.get(position).getId();
                MuroAmigo.foto = personas.get(position).getFoto();
                MuroAmigo.nombre = personas.get(position).getNombre()+" "+personas.get(position).getApellido();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.content_main, new MuroAmigo());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idpersona = personas.get(position).getId();
                Conversacion.mensajes2.clear();
                Conversacion.nombre = personas.get(position).getNombre()+" "+personas.get(position).getApellido();
                Conversacion.idotrapersona = personas.get(position).getId();
                Conversacion.fotootrapersona = personas.get(position).getFoto();
                Conversacion.fotopersona = login.sharedPreferences.getString("foto","");
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.content_main, new Conversacion());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();

                return true;
            }
        });


        buscaramigos();
        //agregaramigos();
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
        ArrayAdapter<Persona> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Persona>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemamigo, personas);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView==null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itemamigo, parent, false);
            }
            Persona CurrentPersona = personas.get(position);

            TextView NombreText = itemView.findViewById(R.id.textViewnombre);
            CircleImageView Fotoperfil = itemView.findViewById(R.id.fotoperfil);
            NombreText.setText(CurrentPersona.getNombre()+" "+CurrentPersona.getApellido());
            if(!CurrentPersona.getFoto().equals("")){
                Glide.with(getContext())
                        .load(Uri.parse(CurrentPersona.getFoto()))
                        .into(Fotoperfil);
            }

            return itemView;
        }
    }

    public void buscaramigos(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://socialitec.herokuapp.com/api/user";
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
                                    if(!mainObject.getString("_id").equals(login.sharedPreferences.getString("id", ""))){
                                        //obtiene valores dentro del objeto.
                                        personas.add(new Persona(mainObject.getString("nombre").substring(0,1).toUpperCase()+mainObject.getString("nombre").substring(1), mainObject.getString("apellidos").substring(0,1).toUpperCase()+mainObject.getString("apellidos").substring(1), mainObject.getString("_id"),""));
                                        if(mainObject.has("foto")){
                                            personas.get(personas.size()-1).setFoto(mainObject.getString("foto"));
                                        }

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        llenarlista();
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
        );
        queue.add(postRequest);
    }
}
