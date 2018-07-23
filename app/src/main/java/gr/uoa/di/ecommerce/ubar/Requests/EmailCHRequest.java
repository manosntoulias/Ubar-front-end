package gr.uoa.di.ecommerce.ubar.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

//check email availabilty
public class EmailCHRequest extends StringRequest {

    protected String email;

    public EmailCHRequest (int method, String url, Response.Listener<String> li, Response.ErrorListener Erli, String text)
    {
         super(method, url, li, Erli);
         email = text;
    }



    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {

        return email.getBytes();
    };

    @Override
    public String getBodyContentType()
    {
        return "text/plain";
    }
}
