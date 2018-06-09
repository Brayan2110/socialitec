package com.example.bvarg.ubicacion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapa extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] items;
    String[] lat;
    String[] lon;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    View vista;

    private OnFragmentInteractionListener mListener;

    public Mapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mapa.
     */
    // TODO: Rename and change types and number of parameters
    public static Mapa newInstance(String param1, String param2) {
        Mapa fragment = new Mapa();
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

        vista = inflater.inflate(R.layout.fragment_mapa, container, false);

        listView = vista.findViewById(R.id.lista);
        editText = vista.findViewById(R.id.busqueda);
        lat = new String[]{"9.856829","9.856574","9.856599","9.855860","9.855936","9.856137",
                "9.856304","9.855680","9.854961","9.855456","9.855479","9.855663","9.855633","9.855557","9.856878","9.856614","9.856497","9.855231","9.855301","9.854972",
                "9.858598","9.849932","9.855108","9.855216","9.855445","9.855661","9.854893"};
        lon = new String[]{"-83.912275","-83.912960","-83.913129","-83.913283","-83.912530","-83.912510",
                "-83.912567","-83.913163","-83.912590","-83.912897","-83.913287","-83.912319","-83.911828","-83.911330","-83.910381","-83.910528","-83.910760","-83.909425","-83.909108","-83.909007",
                "-83.911667","-83.910996","-83.908310","-83.909736","-83.912274","-83.913393","-83.911247"};
        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initList();
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchItem(s.toString());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = listItems.get(position);
                for(int i = 0; i < items.length; i++){
                    if(items[i].toLowerCase().equals(nombre.toLowerCase())){
                        MapsActivity.x = Double.valueOf(lat[i]);
                        MapsActivity.y = Double.valueOf(lon[i]);
                        MapsActivity.nombre = nombre;
                    }
                }
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return vista;
    }
    public void searchItem(String textToSearch){
        for(String item:items){
            if(!item.toLowerCase().contains(textToSearch.toLowerCase())){
                listItems.remove(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
    public void initList(){
        items = new String[]{"Financiero","Vicerrectoría","Admisión y Registro","Cajero BNCR","B1","B2",
                "B3","Feitec","Biblioteca","Comedor","Laimi 1","D1","D2","D3","Piscina","Unidad de Deporte","Gimnasio ASETEC","F1","F2","F3",
                "Residencias","L1","Laimi 2","Clínica","Librería","Cajero BCR","D10"};
        listItems = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
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
