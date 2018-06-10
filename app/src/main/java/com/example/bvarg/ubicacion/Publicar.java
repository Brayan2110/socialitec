package com.example.bvarg.ubicacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Publicar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Publicar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Publicar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button publicar;
    ImageView foto;
    EditText nombre;
    EditText apellidos;
    EditText publicacion;
    ImageView imagen;
    View vista;

    String fotoelegida = "";
    //Imagen
    StorageReference mStorage;
    Uri img;
    Uri uri;
    ProgressDialog mProgressDialog;
    public static int GALLERY_INTENT = 1;
    static boolean registrado = false;

    private OnFragmentInteractionListener mListener;

    public Publicar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Publicar.
     */
    // TODO: Rename and change types and number of parameters
    public static Publicar newInstance(String param1, String param2) {
        Publicar fragment = new Publicar();
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
        vista = inflater.inflate(R.layout.fragment_publicar, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mStorage = FirebaseStorage.getInstance().getReference();
        nombre = vista.findViewById(R.id.editTextnombre);
        nombre.setEnabled(false);
        apellidos = vista.findViewById(R.id.editTextapellidos);
        apellidos.setEnabled(false);
        publicacion = vista.findViewById(R.id.editText_publicacion);
        foto = vista.findViewById(R.id.imageViewfoto);
        imagen = vista.findViewById(R.id.image_publicar);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        publicar = vista.findViewById(R.id.button_publicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(publicacion.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Se debe escribir algo", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(fotoelegida.equals("")){
                        publicar(login.sharedPreferences.getString("token",""), login.sharedPreferences.getString("id",""), publicacion.getText().toString());
                    }
                    else{
                        publicar2(login.sharedPreferences.getString("token",""), login.sharedPreferences.getString("id",""), publicacion.getText().toString(), fotoelegida);
                    }
                }
            }
        });
        extraernombre(login.sharedPreferences.getString("id",""));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == -1){
            uri = data.getData();

            mProgressDialog.setTitle("Cargando...");
            mProgressDialog.setMessage("Cargando Contenido");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            final int numero = (int) (Math.random() * 100) + 1;
            StorageReference filepath = mStorage.child("fotos").child(uri.getLastPathSegment()+numero);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    //Toast.makeText(getContext(), "Se actualizo correctamente", Toast.LENGTH_SHORT).show();

                    StorageMetadata var1;
                    fotoelegida = taskSnapshot.getDownloadUrl().toString();
                    img = taskSnapshot.getUploadSessionUri();
                    Glide.with(getContext())
                            .load(Uri.parse(fotoelegida))
                            .into(imagen);
                }
            });
        }
    }

    public void extraernombre(final String id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                    nombre.setText(obj2.getString("nombre"));
                                    apellidos.setText(obj2.getString("apellidos"));
                                    Glide.with(getContext())
                                            .load(Uri.parse(obj2.getString("foto")))
                                            .into(foto);
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

    public void publicar(final String token, final String id, final String texto){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/publication";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("respuesta",response);
                        Toast.makeText(getContext(), "Se publico correctamente", Toast.LENGTH_SHORT).show();
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        trans.replace(R.id.content_main, new Perfil());
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "fallo");
                        Toast.makeText(getContext(), "No se pudo completar la publicacion", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", id);
                params.put("texto", texto);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("authorization", token);
                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }

    public void publicar2(final String token, final String id, final String texto, final String imagen){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/publication";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("respuesta",response);
                        Toast.makeText(getContext(), "Se publico correctamente", Toast.LENGTH_SHORT).show();
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        trans.replace(R.id.content_main, new Perfil());
                        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        trans.addToBackStack(null);
                        trans.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "fallo");
                        Toast.makeText(getContext(), "No se pudo completar la publicacion", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", id);
                params.put("texto", texto);
                params.put("imagen", imagen);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("authorization", token);
                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }
}
