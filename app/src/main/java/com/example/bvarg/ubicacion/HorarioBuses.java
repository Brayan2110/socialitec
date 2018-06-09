package com.example.bvarg.ubicacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HorarioBuses.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HorarioBuses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorarioBuses extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText TECCartago;
    EditText CartagoTEC;
    EditText TECCartagoS;
    EditText CartagoTECS;
    EditText TECSanJose;
    EditText SanJoseTEC;

    View vista;

    private OnFragmentInteractionListener mListener;

    public HorarioBuses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HorarioBuses.
     */
    // TODO: Rename and change types and number of parameters
    public static HorarioBuses newInstance(String param1, String param2) {
        HorarioBuses fragment = new HorarioBuses();
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
        vista = inflater.inflate(R.layout.fragment_horario_buses, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Horario de buses");
        TECCartago = vista.findViewById(R.id.editText);
        CartagoTEC = vista.findViewById(R.id.editText2);
        TECCartagoS = vista.findViewById(R.id.editText3);
        CartagoTECS = vista.findViewById(R.id.editText4);
        TECSanJose = vista.findViewById(R.id.editText5);
        SanJoseTEC = vista.findViewById(R.id.editText6);

        TECCartago.setKeyListener(null);
        TECCartago.setText("8:30   15:10   18:30\n"+
                           "9:30   15:40   18:45\n"+
                           "10:30  16:00   19:00\n"+
                           "11:15  16:30   19:35\n"+
                           "11:30  16:40   20:00\n"+
                           "12:00  17:00   20:30\n"+
                           "12:10  17:15   21:00\n"+
                           "12:30  17:30   21:30\n"+
                           "13:10  18:00\n"+
                           "14:10  18:15\n");

        CartagoTEC.setKeyListener(null);
        CartagoTEC.setText("7:00    11:00     18:00\n"+
                           "7:10    11:30     17:15\n"+
                           "7:15    11:40     17:30\n"+
                           "7:20    12:50     17:45\n"+
                           "7:40    13:00     18:00\n"+
                           "7:50    13:50     18:15\n"+
                           "8:00    14:30     18:30\n"+
                           "9:00    15:25     18:45\n"+
                           "9:15    16:10     19:15\n"+
                           "10:00   16:45     19:45\n");

        TECCartagoS.setKeyListener(null);
        TECCartagoS.setText("12:00    16:00\n"+
                            "12:40    16:40\n");

        CartagoTECS.setKeyListener(null);
        CartagoTECS.setText("7:40    8:00     11:45\n");

        TECSanJose.setKeyListener(null);
        TECSanJose.setText("11:35   15:05   18:05\n"+
                           "12:05   16:05   19:05\n"+
                           "13:05   16:55   20:05\n"+
                           "14:05   17:15   21:05\n");

        SanJoseTEC.setKeyListener(null);
        SanJoseTEC.setText("6:10    13:50     16:50\n"+
                           "6:25    14:50     17:50\n"+
                           "12:50   15:50     18:50\n");



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
}
