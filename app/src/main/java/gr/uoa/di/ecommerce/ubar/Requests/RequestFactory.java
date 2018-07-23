package gr.uoa.di.ecommerce.ubar.Requests;

import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class RequestFactory {


    public static Request createRequest(int method, String url, final EditText edText) {
        if (method == Request.Method.POST) {
            return new EmailCHRequest(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            edText.setText(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, edText.getText().toString());
        }
        else if (method == Request.Method.GET)
        {
            return new UserCHRequest(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            edText.setText(response);
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
