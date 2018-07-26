package gr.uoa.di.ecommerce.ubar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Def;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        TextView view = (TextView) findViewById(R.id.test);
        view.setText(intent.getStringExtra(Def.KEY_USERNAME) + " " + intent.getStringExtra(Def.KEY_TYPE));
    }
}
