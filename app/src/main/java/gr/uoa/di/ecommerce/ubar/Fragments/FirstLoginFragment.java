package gr.uoa.di.ecommerce.ubar.Fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import gr.uoa.di.ecommerce.ubar.Activities.SignUp;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.Activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstLoginFragment extends Fragment {

    private TextView passengerTxt;
    private TextView driverTxt;
    private ImageButton passengerImg;
    private ImageButton driverImg;
    private Button SignUpbutton;




    public FirstLoginFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_login, container, false);



        passengerTxt = (TextView) view.findViewById(R.id.passengerTXT);
        driverTxt = (TextView) view.findViewById(R.id.driverTXT);
        passengerImg = (ImageButton) view.findViewById(R.id.passengerImg);
        driverImg = (ImageButton) view.findViewById(R.id.driverImg);
        SignUpbutton = (Button) view.findViewById(R.id.signup);

        onClick_selectType(Def.passenger, passengerTxt);
        onClick_selectType(Def.driver, driverTxt);
        onClick_selectType(Def.passenger, passengerImg);
        onClick_selectType(Def.driver, driverImg);

        onClickSignup(SignUpbutton);

        return view;


    }








    private void onClickSignup(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    protected void onClick_selectType(final String type, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).use_second_fragment(type);
            }

        });
    }
}
