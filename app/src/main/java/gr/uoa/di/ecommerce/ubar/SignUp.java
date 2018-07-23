package gr.uoa.di.ecommerce.ubar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import gr.uoa.di.ecommerce.ubar.Requests.RequestFactory;

public class SignUp extends AppCompatActivity {

    protected GlobalState state;
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();

        EditText email = (EditText) findViewById(R.id.editemail);
        EditText usrname = (EditText) findViewById(R.id.edituser);

        check_availability(email, Request.Method.POST, Def.CHECK_MAIL_PATH);
        check_availability(usrname, Request.Method.GET, Def.CHECK_USR_PATH);
    }


    protected void check_availability(final EditText edText, final int method, final String path) {
            edText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)  {
                final String check_text = edText.getText().toString();
                if (!hasFocus && !"".equals(check_text))
                {

                    String url = Def.SERVER_URL + path;

                    // Request a string response from the provided URL.
                    Request stringRequest =  RequestFactory.createRequest(method, url, edText);

                    //edText.setText("SUCCESS");
                    requestQueue.add((StringRequest)stringRequest);
                }
            }
        });
    }
}
