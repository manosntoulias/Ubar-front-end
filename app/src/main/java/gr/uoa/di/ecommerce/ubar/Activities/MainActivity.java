package gr.uoa.di.ecommerce.ubar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.widget.TextView;

import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Fragments.FirstLoginFragment;
import gr.uoa.di.ecommerce.ubar.Fragments.SecondLoginFragment;

public class MainActivity extends AppCompatActivity {

    protected FirstLoginFragment flFragment;
    protected SecondLoginFragment slFragment;
    public static final String KEY_USER = "gr.uoa.di.ecommerce.ubar.USER";
    public static final String KEY_TYPE = "gr.uoa.di.ecommerce.ubar.TYPE";


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
                // Begin the transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        flFragment = new FirstLoginFragment();
        slFragment = new SecondLoginFragment();


        ft.add(R.id.fragment_container, flFragment);
        ft.add(R.id.fragment_container, slFragment);
        ft.hide(slFragment);

        ft.commit();
    }

    public void use_second_fragment(String type)
    {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        TextView typeTxt = (TextView)findViewById(R.id.type);
        ((TextView)findViewById(R.id.editUser)).setText("");
        ((TextView)findViewById(R.id.editPass)).setText("");
        ((TextView)findViewById(R.id.error)).setText("");
        typeTxt.setText(type);

        ft.hide(flFragment);
        ft.show(slFragment);

        ft.commit();
    }

    public void use_first_fragment()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.hide(slFragment);
        ft.show(flFragment);

        ft.commit();
    }
}
