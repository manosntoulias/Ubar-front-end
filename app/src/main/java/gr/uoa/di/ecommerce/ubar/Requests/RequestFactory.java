package gr.uoa.di.ecommerce.ubar.Requests;

import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import gr.uoa.di.ecommerce.ubar.Def;

public class RequestFactory {
    //SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length
    //    SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length

    public static Request createRequest(String url, final EditText edText, final TextView erText) {
        if ((Def.SERVER_URL + Def.CHECK_MAIL_PATH).equals(url)) {
            return new PostStringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("true"))
                                erText.setText("Email already exists");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, edText.getText().toString(), Def.TEXT_PLAIN);
        }
        else if ((Def.SERVER_URL + Def.CHECK_USR_PATH).equals(url))
        {
            return new UserCHRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("true"))
                                erText.setText("Username already exists");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, edText.getText().toString());
        }
        return null;
    }
}
