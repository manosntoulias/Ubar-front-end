package gr.uoa.di.ecommerce.ubar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Fragments.FirstLoginFragment;
import gr.uoa.di.ecommerce.ubar.Fragments.SecondLoginFragment;

public class MainActivity extends AppCompatActivity implements FirstLoginFragment.OnFragmentInteractionListener, SecondLoginFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        init_fragments();

    }

    protected void init_fragments()
    {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        FirstLoginFragment flFragment = new FirstLoginFragment();
        SecondLoginFragment slFragment = new SecondLoginFragment();

        // dynamic frgaments don't have IDs
        // need to define tags ("1", "2", ...) in order to get them later
        ft.add(R.id.fragment_container, flFragment, "1");
        ft.add(R.id.fragment_container, slFragment, "2");
        ft.hide(slFragment);

        ft.commit();
    }

    @Override
    public void use_second_fragment(String type)
    {   // called when one of the two images clicked

        FragmentManager fm = getSupportFragmentManager();
        FirstLoginFragment flFragment = (FirstLoginFragment) fm.findFragmentByTag("1");
        SecondLoginFragment slFragment = (SecondLoginFragment) fm.findFragmentByTag("2");

        FragmentTransaction ft = fm.beginTransaction();

        // erase all info inside edittexts from previous usage and
        // set the login type (driver or passenger)
        TextView typeTxt = (TextView)findViewById(R.id.type);
        typeTxt.setText(type);
        ((TextView)findViewById(R.id.editUser)).setText("");
        ((TextView)findViewById(R.id.editPass)).setText("");
        ((TextView)findViewById(R.id.error)).setText("");


        ft.hide(flFragment);
        ft.show(slFragment);

        ft.commit();
    }

    @Override
    public void use_first_fragment()
    {
        //called when back button is pressed in second fragment

        FragmentManager fm = getSupportFragmentManager();
        FirstLoginFragment flFragment = (FirstLoginFragment) fm.findFragmentByTag("1");
        SecondLoginFragment slFragment = (SecondLoginFragment) fm.findFragmentByTag("2");

        FragmentTransaction ft = fm.beginTransaction();

        ft.hide(slFragment);
        ft.show(flFragment);

        ft.commit();
    }



}
