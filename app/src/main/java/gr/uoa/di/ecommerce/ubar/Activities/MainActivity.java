package gr.uoa.di.ecommerce.ubar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Fragments.FirstLoginFragment;
import gr.uoa.di.ecommerce.ubar.Fragments.SecondLoginFragment;
import gr.uoa.di.ecommerce.ubar.Utilities.PrefSingleton;

public class MainActivity extends AppCompatActivity implements FirstLoginFragment.OnFragmentInteractionListener, SecondLoginFragment.OnFragmentInteractionListener {

    private Button SignUpbutton;
    private MainActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //test
        if (PrefSingleton.getInstance().getSharedPref().contains("username")) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        //end test

        setContentView(R.layout.activity_main);

        // if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }
        SignUpbutton = (Button) findViewById(R.id.signup);
        thisActivity = this;

        init_fragments();
        onClickSignup(SignUpbutton);

    }

    @Override
    protected void onResume () {
        super.onResume();
        use_first_fragment();
    }

    private void onClickSignup(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, SignUp.class);
                startActivity(intent);
            }
        });
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

        String new_type = "";
        if (type.equals(Def.driver))
            new_type = "Driver";
        else
            new_type = "Passenger";
        FragmentManager fm = getSupportFragmentManager();
        FirstLoginFragment flFragment = (FirstLoginFragment) fm.findFragmentByTag("1");
        SecondLoginFragment slFragment = (SecondLoginFragment) fm.findFragmentByTag("2");

        FragmentTransaction ft = fm.beginTransaction();

        // erase all info inside edittexts from previous usage and
        // set the login type (driver or passenger)
        TextView typeTxt = (TextView)findViewById(R.id.type);
        typeTxt.setText(new_type);
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
