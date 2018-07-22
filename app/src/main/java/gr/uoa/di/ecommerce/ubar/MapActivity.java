package gr.uoa.di.ecommerce.ubar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        TextView view = (TextView) findViewById(R.id.test);
        view.setText(intent.getStringExtra(LoginActivity.KEY_USER) + " " + intent.getStringExtra(LoginActivity.KEY_TYPE));
    }
}
