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
 * {@link Horarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Horarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Horarios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<HorariosLugares> horarios = new ArrayList<>();

    View vista;

    private OnFragmentInteractionListener mListener;

    public Horarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Horarios.
     */
    // TODO: Rename and change types and number of parameters
    public static Horarios newInstance(String param1, String param2) {
        Horarios fragment = new Horarios();
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

        vista = inflater.inflate(R.layout.fragment_horarios, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Horarios");

        agregarhorarios();
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

    public void agregarhorarios(){
        horarios.add(new HorariosLugares("Financiero","Lunes a Viernes : 7:30 am - 12: 00 pm \n" +
                "13:00 pm - 16:30 pm", R.drawable.financiero));
        horarios.add(new HorariosLugares("Biblioteca","Lunes a Viernes : 7:30 am - 12: 00 pm \n" +
                "13:00 pm - 16:30 pm\n Sabados : 08:00 am  - 16:00 pm ", R.drawable.biblioteca));
        horarios.add(new HorariosLugares("Clinica","Lunes a Viernes : 7:30 am - 16:30 pm", R.drawable.clinica));
        horarios.add(new HorariosLugares("Comedor","Desayuno: 8:15 am - 10:00 am\n" +
                "Almuerzo: 11:00 am - 2:00 pm\n" +
                "Café: 3:00 pm - 4:00 pm\n" +
                "Cena: 5:00 pm - 7:30 pm (lunes - jueves)\n" +
                "           5:00 pm - 6:30 pm (viernes)", R.drawable.comedor));
        horarios.add(new HorariosLugares("Laimi","Lunes a Viernes: 7:30 am - 8:00 pm\n" +
                "Domingos: 8:00 am - 4:00 pm", R.drawable.laimi));
        horarios.add(new HorariosLugares("Admision y Registro","Lunes a Viernes: 7:30 am - 8:00 pm", R.drawable.admision));
        horarios.add(new HorariosLugares("Feitec","Lunes a Viernes: 7:30 am - 4:30 pm", R.drawable.feitec));
    }

    public void llenarlista() {
        ArrayAdapter<HorariosLugares> adapter = new milista();
        ListView list = vista.findViewById(R.id.lista);
        list.setAdapter(adapter);
    }

    public class milista extends ArrayAdapter<HorariosLugares>{
        public milista(){
            super(vista.getContext(), R.layout.activity_itemhorario, horarios);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView==null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_itemhorario, parent, false);
            }
            HorariosLugares CurrentHorarios = horarios.get(position);

            TextView NombreText = itemView.findViewById(R.id.textView_nombre);
            NombreText.setText(CurrentHorarios.getNombre());

            TextView TelefonoText = itemView.findViewById(R.id.textView_telefono);
            TelefonoText.setText(CurrentHorarios.getHorario());

            ImageView Imagenperfil = itemView.findViewById(R.id.imagenperfil);
            Imagenperfil.setImageResource(CurrentHorarios.getImagen());

            return itemView;
        }
    }
}
