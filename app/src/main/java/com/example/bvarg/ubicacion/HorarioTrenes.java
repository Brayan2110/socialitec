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
 * {@link HorarioTrenes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HorarioTrenes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorarioTrenes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText SanJoseCartago;
    EditText CartagoSanJose;

    View vista;

    private OnFragmentInteractionListener mListener;

    public HorarioTrenes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HorarioTrenes.
     */
    // TODO: Rename and change types and number of parameters
    public static HorarioTrenes newInstance(String param1, String param2) {
        HorarioTrenes fragment = new HorarioTrenes();
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
        vista = inflater.inflate(R.layout.fragment_horario_trenes, container, false);
        ((Menu_Nav) getActivity()).setActionBarTitle("Horario de Trenes");
        SanJoseCartago = vista.findViewById(R.id.editText);
        CartagoSanJose = vista.findViewById(R.id.editText2);

        SanJoseCartago.setKeyListener(null);
        SanJoseCartago.setText("5:20          5:30           6:21\n"+
                               "                  6:00           6:51\n"+
                               "6:20          6:30           7:21\n"+
                               "                  7:00           7:51\n"+
                               "7:30          7:35           8:26\n"+
                               "8:26          8:30           9:21\n"+
                               "                  9:00           9:51\n"+
                               "16:00        16:05         16:56\n"+
                               "                  16:30         17:21\n"+
                               "17:00        17:05         17:56\n"+
                               "                  17:35         18:26\n"+
                               "17:55        18:00         18:51\n"+
                               "                  18:30         19:21\n");

        CartagoSanJose.setKeyListener(null);
        CartagoSanJose.setText("6:30           7:22            7:25\n"+
                               "7:31           8:23            8:26\n"+
                               "7:55           8:47\n"+
                               "15:00         15:52          15:55\n"+
                               "15:30         16:22\n"+
                               "16:00         16:52          16:55\n"+
                               "16:40         17:32\n"+
                               "17:00         17:52          17:55\n"+
                               "17:30         18:22\n"+
                               "18:00         18:52          18:55\n"+
                               "18:30         19:22\n"+
                               "18:55         17:47          19:50\n"+
                               "19:30         20:22\n");


        return  vista;
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
