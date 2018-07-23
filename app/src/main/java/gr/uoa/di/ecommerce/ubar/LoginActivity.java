package gr.uoa.di.ecommerce.ubar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import java.io.IOException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_USER = "gr.uoa.di.ecommerce.ubar.USER";
    public static final String KEY_TYPE = "gr.uoa.di.ecommerce.ubar.TYPE";

    protected LoginActivity thisActivity;
    protected GlobalState state;
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        thisActivity = this;
        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();

        Button Passengerbutton = (Button) findViewById(R.id.login);
        Button Driverbutton = (Button) findViewById(R.id.Dlogin);
        EditText Usrtxt = (EditText) findViewById(R.id.editUser);
        EditText passtxt = (EditText) findViewById(R.id.editPass);
        TextView error = (TextView) findViewById(R.id.error);
        Button SignUpbutton = (Button) findViewById(R.id.signup);

        attempt_login(Passengerbutton, Usrtxt, passtxt, error, "passenger");
        attempt_login(Driverbutton, Usrtxt, passtxt, error, "driver");
        signup(SignUpbutton);
    }

    private void signup(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void attempt_login(Button b, final EditText Utxt, final EditText Ptxt, final TextView error, final String type) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Utxt.getText().toString();
                String password = Ptxt.getText().toString();

                String url = Def.SERVER_URL + Def.LOGIN_PATH;
                //String url = "http://httpbin.org/get?param1=hello";
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("username", username);
                    jobj.put("password", password);
                    jobj.put("type", type);
                } catch (JSONException e) {
                    //TODO handle error
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                boolean success = false;
                                Intent intent = new Intent(thisActivity, MapActivity.class);
                                try {
                                    if ((Integer) response.get(Def.id) != Def.not_found) {
                                        error.setText("SUCCESS");
                                        success = true;
                                        intent.putExtra(KEY_USER, (String) response.get(Def.username));
                                        intent.putExtra(KEY_TYPE, (String) response.get(Def.type));

                                    } else {
                                        error.setText("Wrong Username or Password");
                                    }
                                } catch (JSONException e) {   //TO handle error

                                }
                                if (success)
                                //finish();
                                    thisActivity.startActivity(intent);


                                //error.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError voLerror) {
                                error.setText("Error: " + voLerror.toString());
                                //TODO write to log instead
                            }
                        });

                requestQueue.add(jsonObjectRequest);



            }
        });

    }


}
