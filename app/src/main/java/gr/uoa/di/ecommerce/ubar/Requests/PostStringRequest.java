package gr.uoa.di.ecommerce.ubar.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


//check user availability or
//register user
public class PostStringRequest extends StringRequest {

    protected String body;
    protected String contentType;

    public PostStringRequest (String url, Response.Listener<String> li, Response.ErrorListener Erli, String text, String Type)
    {
        super(Request.Method.POST, url, li, Erli);
        body = text;
        contentType = Type;
    }



    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {

        return body.getBytes();
    };

    @Override
    public String getBodyContentType()
    {
        return contentType;
    }
}
