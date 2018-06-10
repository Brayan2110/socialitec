package com.example.bvarg.ubicacion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Conversacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Conversacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Conversacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button enviarmensaje;
    EditText Textmensaje;
    static String nombre;
    static String idotrapersona;
    static List<Mensaje> mensajes2 = new ArrayList<>();
    static String fotopersona;
    static String fotootrapersona;
    View vista;

    private OnFragmentInteractionListener mListener;

    public Conversacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Conversacion.
     */
    // TODO: Rename and change types and number of parameters
    public static Conversacion newInstance(String param1, String param2) {
        Conversacion fragment = new Conversacion();
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
        vista = inflater.inflate(R.layout.fragment_conversacion, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle(nombre);
        mensajes2 = new ArrayList<>();
        Textmensaje = vista.findViewById(R.id.editTextmensaje);
        enviarmensaje = vista.findViewById(R.id.buttonenviar);
        enviarmensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Textmensaje.getText().equals("")){
                    Enviarmensaje(login.sharedPreferences.getString("id",""),Textmensaje.getText().toString(),idotrapersona,login.sharedPreferences.getString("token",""));
                    mensajes2.add(new Mensaje(login.sharedPreferences.getString("id",""),idotrapersona,Textmensaje.getText().toString()));
                    Textmensaje.setText("");
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    llenarlista();
                }
            }
        });
        llenarlista();

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
        ArrayAdapter<Mensaje> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Mensaje>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemmensaje, mensajes2);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(mensajes2.get(position).getEmisor().equals(login.sharedPreferences.getString("id",""))) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itememisor, parent, false);

                Mensaje CurrentMensaje = mensajes2.get(position);

                TextView TextoText = itemView.findViewById(R.id.textViewtexto);
                CircleImageView fotoperfil = itemView.findViewById(R.id.fotoperfil);
                TextoText.setText(CurrentMensaje.getTexto());
                if(!fotopersona.equals("")){
                    Glide.with(getContext())
                            .load(fotopersona)
                            .into(fotoperfil);
                }
            }
            else{
                itemView = getLayoutInflater().inflate(R.layout.activity_itemreceptor, parent, false);

                Mensaje CurrentMensaje = mensajes2.get(position);

                TextView TextoText = itemView.findViewById(R.id.textViewtexto);
                CircleImageView fotoperfil = itemView.findViewById(R.id.fotoperfil);
                TextoText.setText(CurrentMensaje.getTexto());
                if(!fotootrapersona.equals("")){
                    Glide.with(getContext())
                            .load(fotootrapersona)
                            .into(fotoperfil);
                }
            }

            return itemView;
        }
    }

    public void Enviarmensaje(final String emisor, final String texto, final String receptor, final String token){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/message";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("respuesta",response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "fallo");
                        Toast.makeText(getContext(), "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("emisor", emisor);
                params.put("receptor", receptor);
                params.put("texto", texto);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("authorization", token);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }
}
