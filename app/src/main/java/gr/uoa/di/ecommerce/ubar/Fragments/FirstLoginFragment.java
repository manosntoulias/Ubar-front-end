package gr.uoa.di.ecommerce.ubar.Fragments;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import gr.uoa.di.ecommerce.ubar.Activities.SignUp;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Def;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FirstLoginFragment extends Fragment {

    private TextView passengerTxt;
    private TextView driverTxt;
    private ImageButton passengerImg;
    private ImageButton driverImg;


    // interfaces are used to decouple the fragment from activies that it is used
    // note that we dont import any acitivity in this file
    private OnFragmentInteractionListener mListener;



    public FirstLoginFragment() {
        // Required empty public constructor
    }


    //similiar to onCreate of an Activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_login, container, false);


        //Get the views of this fragment
        //store them in fields of this fragment class
        passengerTxt = (TextView) view.findViewById(R.id.passengerTXT);
        driverTxt = (TextView) view.findViewById(R.id.driverTXT);
        passengerImg = (ImageButton) view.findViewById(R.id.passengerImg);
        driverImg = (ImageButton) view.findViewById(R.id.driverImg);


        // passes the type (driver or passenger to the second fragment)
        // 2 images and 2 texts do this job once clicked
        onClick_selectType(Def.passenger, passengerTxt);
        onClick_selectType(Def.driver, driverTxt);
        onClick_selectType(Def.passenger, passengerImg);
        onClick_selectType(Def.driver, driverImg);



        return view;


    }

    // context is super class of activity
    // store an Activity that uses this fragment as an inteface in a field of this class
    // in out case MainActivity is stored
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

    public interface OnFragmentInteractionListener {

        void use_second_fragment(String type);
    }





    protected void onClick_selectType(final String type, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.use_second_fragment(type);
            }

        });
    }
}
