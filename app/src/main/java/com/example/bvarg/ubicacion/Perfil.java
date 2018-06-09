package com.example.bvarg.ubicacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Publicacion> publicaciones = new ArrayList<>();
    Button guardar;
    Button editar;
    Button atras;
    ImageView foto;
    EditText nombree;
    EditText apellidos;
    View vista;

    String fotoelegida;
    //Imagen
    StorageReference mStorage;
    Uri img;
    Uri uri;
    ProgressDialog mProgressDialog;
    public static int GALLERY_INTENT = 1;
    static boolean registrado = false;

    private OnFragmentInteractionListener mListener;

    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Perfil");
        mProgressDialog = new ProgressDialog(getContext());
        mStorage = FirebaseStorage.getInstance().getReference();
        nombree = vista.findViewById(R.id.editTextnombre);
        nombree.setEnabled(false);
        apellidos = vista.findViewById(R.id.editTextapellidos);
        apellidos.setEnabled(false);
        foto = vista.findViewById(R.id.imageViewfoto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        guardar = vista.findViewById(R.id.buttonguardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombree.getText().toString().equals("") || apellidos.getText().toString().equals("")){
                    Toast.makeText(getContext(), "No puede quedar alguno dato vacio", Toast.LENGTH_SHORT).show();
                }
                else{
                    actualizarapellidos(login.sharedPreferences.getString("id",""),apellidos.getText().toString());
                    actualizarnombre(login.sharedPreferences.getString("id",""),nombree.getText().toString());
                    TextView nombre = Menu_Nav.hView.findViewById(R.id.textonombre);
                    nombre.setText("");
                    nombre.setText(nombree.getText().toString()+" "+apellidos.getText().toString());
                    Toast.makeText(getContext(), "Cambio realizado con exito", Toast.LENGTH_SHORT).show();
                }
            }
        });
        atras = vista.findViewById(R.id.buttonatras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.content_main, new Muro());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        editar = vista.findViewById(R.id.buttoneditar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombree.isEnabled()){
                    nombree.setEnabled(false);
                    apellidos.setEnabled(false);
                }
                else{
                    nombree.setEnabled(true);
                    apellidos.setEnabled(true);
                }
            }
        });
        extraernombre(login.sharedPreferences.getString("id",""));
        buscarpublicaciones(login.sharedPreferences.getString("token",""),login.sharedPreferences.getString("id",""));
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
                    login.sharedPreferences.edit().putString("foto", fotoelegida).apply();
                    img = taskSnapshot.getUploadSessionUri();
                    Glide.with(getContext())
                            .load(Uri.parse(fotoelegida))
                            .into(foto);
                    actualizarfoto(login.sharedPreferences.getString("id",""),fotoelegida);
                    ImageView fotoperfil = Menu_Nav.hView.findViewById(R.id.imagenperfil);

                    Glide.with(getContext())
                            .load(Uri.parse(fotoelegida))
                            .into(fotoperfil);
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
                                    nombree.setText(obj2.getString("nombre"));
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

    public void llenarlista() {
        ArrayAdapter<Publicacion> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Publicacion>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemamigo, publicaciones);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;

            if(publicaciones.get(position).getImagen() != 0) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itempublicacionesamigo, parent, false);
            }
            else{
                itemView = getLayoutInflater().inflate(R.layout.activity_itempublicacionesamigo2, parent, false);
            }

            Log.d("numero", String.valueOf(position));
            Publicacion Currentpublicacion = publicaciones.get(position);

            if(Currentpublicacion.getImagen() != 0){
                TextView FechaText = itemView.findViewById(R.id.textViewfecha);
                TextView Texto = itemView.findViewById(R.id.textViewtexto);
                ImageView Imagen = itemView.findViewById(R.id.imageViewimagen);

                FechaText.setText(Currentpublicacion.getFecha());
                Texto.setText(Currentpublicacion.getMensaje());
                Imagen.setImageResource(Currentpublicacion.getImagen());
            }
            else{
                TextView FechaText = itemView.findViewById(R.id.textViewfecha);
                TextView Texto = itemView.findViewById(R.id.textViewtexto);

                FechaText.setText(Currentpublicacion.getFecha());
                Texto.setText(Currentpublicacion.getMensaje());
            }
            return itemView;
        }
    }

    public void buscarpublicaciones(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://socialitec.herokuapp.com/api/userpublication/"+id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("publcaciones", response);
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
                                    publicaciones.add(new Publicacion("","", mainObject.getString("texto"),dia+"/"+mes+"/"+anho));
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

    public void actualizarfoto(final String id, final String foto){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/usuario/foto";
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
                        Toast.makeText(getContext(), "No se pudo actulizar la foto", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("foto", foto);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }
    public void actualizarnombre(final String id, final String nombre){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/usuario/nombre";
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
                        Toast.makeText(getContext(), "No se pudo actulizar el nombre", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("nombre", nombre);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }
    public void actualizarapellidos(final String id, final String apellidos){
        StringRequest postRequest = null;
        String url = "https://socialitec.herokuapp.com/api/usuario/apellido";
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
                        Toast.makeText(getContext(), "No se pudo actulizar el apellido", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("apellidos", apellidos);

                return params;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        MyRequestQueue.add(postRequest);
    }
}
