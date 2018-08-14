package gr.uoa.di.ecommerce.ubar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.support.constraint.ConstraintLayout;

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
import gr.uoa.di.ecommerce.ubar.Utilities.Vehicle;
import gr.uoa.di.ecommerce.ubar.Utilities.Hash;


import java.lang.Boolean;

public class SignUp extends AppCompatActivity {

    protected GlobalState state;
    protected RequestQueue requestQueue;

    protected User user;
    protected Vehicle vehicle;

    boolean isDriver;
    ConstraintLayout DriverView;
    String type;
    String vehicle_type;

    //buttons
    Spinner type_spinner;
    Button signup;
    RadioGroup usergroup;

    //user fields
    EditText usrname, pass, con_pass, surname, name, email, address, phone;

    //vehicle fields
    EditText model, manufacturer, year, plate, color;

    //errors
    TextView er_usrname, er_pass, er_con_pass, er_surname, er_name, er_email, er_address, er_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        get_fields();

        check_availability(email, er_email, Def.CHECK_MAIL_PATH);
        check_availability(usrname, er_usrname, Def.CHECK_USR_PATH);

        onUnFocus_check_password(pass);
        onUnFocus_check_password(con_pass);

        onUserTypeSelected();
        register(signup);
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

    protected void register(Button RegButtion) {

        RegButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.set(usrname.getText().toString(),
                        pass.getText().toString(),
                        surname.getText().toString(),
                        name.getText().toString(),
                        email.getText().toString(),
                        address.getText().toString(),
                        phone.getText().toString());

                if (isDriver)
                    vehicle.set(model.getText().toString(),
                            manufacturer.getText().toString(),
                            year.getText().toString(),
                            plate.getText().toString(),
                            color.getText().toString(),
                            vehicle_type);


                String url = Def.SERVER_URL + Def.REGISTER_PATH + "/" + type;
                JSONObject jobj;
                if (user.empty() || "".equals(con_pass.getText().toString())
                        || (isDriver && vehicle.empty()) )
                {
                    er_address.setText("Please fill all empty fields to continue");
                    return;
                }
                else if (errors()) {
                    er_address.setText("Please resolve all errors to continue");
                    return;
                }
                else {
                    user.setPassword(Hash.getSHA512(user.getPassword()));
                    jobj = new JSONObject();
                    er_address.setText("");
                    try {

                        if (isDriver)
                            vehicle.mapJSON(jobj);
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

    protected void onUserTypeSelected() {
        usergroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isDriver = !isDriver;
                if (isDriver) {
                    DriverView.setVisibility(View.VISIBLE);
                    type = Def.driver;
                } else {
                    DriverView.setVisibility(View.GONE);
                    type = Def.passenger;
                }
            }

        });
    }

    protected void VehicleTypeSelected() {

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 1)
                    vehicle_type = "car";
                else
                    vehicle_type = "motorcycle";
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
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

        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();
        isDriver = false;
        type = Def.passenger;
        vehicle_type = "car";

        //set up dropdown menu for vehicle type
        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);

        user = new User();
        vehicle = new Vehicle();

        usrname = (EditText) findViewById(R.id.edituser);
        pass = (EditText) findViewById(R.id.editpass);
        con_pass = (EditText) findViewById(R.id.editcon_pass);
        surname = (EditText) findViewById(R.id.editsurname);
        name = (EditText) findViewById(R.id.editname);
        email = (EditText) findViewById(R.id.editemail);
        address= (EditText) findViewById(R.id.editaddress);
        phone = (EditText) findViewById(R.id.editphone);
        signup = (Button) findViewById(R.id.signup);

        //vehicle
        model = (EditText) findViewById(R.id.editmodel);
        manufacturer = (EditText) findViewById(R.id.editmanufacturer);
        year = (EditText) findViewById(R.id.edityear);
        plate = (EditText) findViewById(R.id.editplate);
        color = (EditText) findViewById(R.id.editcolor);

        usergroup = (RadioGroup) findViewById(R.id.typeRadio);
        DriverView = (ConstraintLayout) findViewById(R.id.vehicle);
        DriverView.setVisibility(View.GONE);

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
