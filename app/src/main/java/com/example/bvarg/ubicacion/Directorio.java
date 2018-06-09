package com.example.bvarg.ubicacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Directorio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Directorio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Directorio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Contactos> contactos = new ArrayList<>();

    View vista;

    private OnFragmentInteractionListener mListener;

    public Directorio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Directorio.
     */
    // TODO: Rename and change types and number of parameters
    public static Directorio newInstance(String param1, String param2) {
        Directorio fragment = new Directorio();
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

        vista = inflater.inflate(R.layout.fragment_directorio, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Directorio");

        agregarcontactos();
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

    public void agregarcontactos(){
        contactos.add(new Contactos("Administracion","2550 9064"));
        contactos.add(new Contactos("Agricola","2550 2303"));
        contactos.add(new Contactos("Agronegocios","2550 2287"));
        contactos.add(new Contactos("Ati","2550 2254"));
        contactos.add(new Contactos("Ambiental","2550 9139"));
        contactos.add(new Contactos("Biologia","2550 9028"));
        contactos.add(new Contactos("Escuela Computacion","2550 2254"));
    }

    public void llenarlista() {
        ArrayAdapter<Contactos> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<Contactos>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemcontacto, contactos);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView==null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itemcontacto, parent, false);
            }
            Contactos CurrentContactos = contactos.get(position);

            TextView NombreText = itemView.findViewById(R.id.textView_nombre);
            NombreText.setText(CurrentContactos.getNombre());

            TextView TelefonoText = itemView.findViewById(R.id.textView_telefono);
            TelefonoText.setText(CurrentContactos.getTelefono());

            return itemView;
        }
    }
}
