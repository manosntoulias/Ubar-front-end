package gr.uoa.di.ecommerce.ubar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
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

    //fields
    EditText usrname;
    EditText pass;
    EditText con_pass;
    EditText surname;
    EditText name;
    EditText email;
    EditText address;
    EditText phone;
    Button signup;

    //errors
    TextView er_usrname;
    TextView er_pass;
    TextView er_con_pass;
    TextView er_surname;
    TextView er_name;
    TextView er_email;
    TextView er_address;
    TextView er_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();

        get_fields();

        check_availability(email, er_email, Def.CHECK_MAIL_PATH);
        check_availability(usrname, er_usrname, Def.CHECK_USR_PATH);

        onUnFocus_check_password(pass);
        onUnFocus_check_password(con_pass);

        register(signup, Def.passenger);
       // register(driver_signup, Def.driver);
    }


    protected void check_availability(final EditText edText, final TextView error, final String path) {
            edText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)  {
                final String check_text = edText.getText().toString();


                if (!hasFocus && !"".equals(check_text))
                {
                    //username exclusive
                    if (edText == usrname)
                    {
                        if (check_text.length() < 5) {
                            error.setText("Must be at least 5 characters");
                            return;
                        }
                        else
                            error.setText("");
                    }

                    String url = Def.SERVER_URL + path;

                    // Request a string response from the provided URL.
                    Request stringRequest =  RequestFactory.createRequest(url, edText, error);

                    //edText.setText("SUCCESS");
                    requestQueue.add((StringRequest)stringRequest);
                }
                else
                    error.setText("");
            }
        });
    }

    protected void register(Button RegButtion, final String type) {

        RegButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User(usrname.getText().toString(),
                        pass.getText().toString(),
                        surname.getText().toString(),
                        name.getText().toString(),
                        email.getText().toString(),
                        address.getText().toString(),
                        phone.getText().toString());


                String url = Def.SERVER_URL + Def.REGISTER_PATH + "/" + type;
                JSONObject jobj = new JSONObject();
                if (user.empty())
                {
                    er_address.setText("Please fill all empty fields to continue");
                    return;
                }
                else if (errors()) {
                    er_address.setText("Please resolve all errors to continue");
                    return;
                }
                else {
                    er_address.setText("");
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


            }
        });
    }

    protected void onUnFocus_check_password(EditText a_password) {

        a_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)  {
                String check_pass = pass.getText().toString();
                String check_confirm  = con_pass.getText().toString();
                if (!hasFocus && !"".equals(check_pass) && !"".equals(check_confirm) && !check_confirm.equals(check_pass))
                {
                    er_con_pass.setText("Passwords don't match");
                }
                else
                    er_con_pass.setText("");

                if (check_pass.length() < 5 && check_pass.length() > 0)
                    er_pass.setText("Must be at least 5 characters");
                else
                    er_pass.setText("");
            }
        });

    }

    protected boolean errors()
    {
        if ("".equals(er_usrname.getText().toString()) &&
        "".equals(er_pass.getText().toString()) &&
        "".equals(er_con_pass.getText().toString()) &&
        "".equals(er_email.getText().toString()))
            return false;
        return true;
    }



    protected void get_fields() {

        usrname = (EditText) findViewById(R.id.edituser);
        pass = (EditText) findViewById(R.id.editpass);
        con_pass = (EditText) findViewById(R.id.editcon_pass);
        surname = (EditText) findViewById(R.id.editsurname);
        name = (EditText) findViewById(R.id.editname);
        email = (EditText) findViewById(R.id.editemail);
        address= (EditText) findViewById(R.id.editaddress);
        phone = (EditText) findViewById(R.id.editphone);
        signup = (Button) findViewById(R.id.signup);

        //errors
        er_usrname = (TextView) findViewById(R.id.erroruser);
        er_pass = (TextView) findViewById(R.id.errorpass);
        er_con_pass = (TextView) findViewById(R.id.errorcon_pass);
        er_surname = (TextView) findViewById(R.id.errorsurname);
        er_name = (TextView) findViewById(R.id.errorname);
        er_email = (TextView) findViewById(R.id.erroremail);
        er_address= (TextView) findViewById(R.id.erroraddress);
        er_phone = (TextView) findViewById(R.id.errorphone);

    }

}
