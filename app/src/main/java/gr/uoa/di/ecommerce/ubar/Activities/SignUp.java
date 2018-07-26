package gr.uoa.di.ecommerce.ubar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.GlobalState;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Requests.RequestFactory;
import gr.uoa.di.ecommerce.ubar.Requests.PostStringRequest;
import gr.uoa.di.ecommerce.ubar.User;


import java.lang.Boolean;

public class SignUp extends AppCompatActivity {

    protected GlobalState state;
    protected RequestQueue requestQueue;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();

        EditText email = (EditText) findViewById(R.id.editemail);
        EditText usrname = (EditText) findViewById(R.id.edituser);
        Button signup = (Button) findViewById(R.id.signup);
        //Button driver_signup = (Button) findViewById(R.id.Dsignup);

        check_availability(email, Def.CHECK_MAIL_PATH);
        check_availability(usrname, Def.CHECK_USR_PATH);

        register(signup, Def.passenger);
       // register(driver_signup, Def.driver);
    }


    protected void check_availability(final EditText edText, final String path) {
            edText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)  {
                final String check_text = edText.getText().toString();
                if (!hasFocus && !"".equals(check_text))
                {

                    String url = Def.SERVER_URL + path;

                    // Request a string response from the provided URL.
                    Request stringRequest =  RequestFactory.createRequest(url, edText);

                    //edText.setText("SUCCESS");
                    requestQueue.add((StringRequest)stringRequest);
                }
            }
        });
    }

    protected void register(Button RegButtion, final String type) {

        RegButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User(((EditText) findViewById(R.id.edituser)).getText().toString(),
                        ((EditText) findViewById(R.id.editpass)).getText().toString(),
                        ((EditText) findViewById(R.id.editsurname)).getText().toString(),
                        ((EditText) findViewById(R.id.editname)).getText().toString(),
                        ((EditText) findViewById(R.id.editemail)).getText().toString(),
                        ((EditText) findViewById(R.id.editaddress)).getText().toString(),
                        ((EditText) findViewById(R.id.editphone)).getText().toString());


                String url = Def.SERVER_URL + Def.REGISTER_PATH + "/" + type;
                JSONObject jobj = new JSONObject();
                try {

                final String mRequestBody = user.mapJSON(jobj);

                StringRequest stringRequest = new PostStringRequest
                        (url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                boolean success = Boolean.parseBoolean(response);
                                if (success)
                                    finish();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError voLerror) {
                                //error.setText("Error: " + voLerror.toString());
                                //TODO write to log instead
                            }
                        }, mRequestBody, Def.APP_JSON);

                requestQueue.add(stringRequest);

                } catch (JSONException e) {
                    //TODO handle error
                }



            }
        });
    }



}
