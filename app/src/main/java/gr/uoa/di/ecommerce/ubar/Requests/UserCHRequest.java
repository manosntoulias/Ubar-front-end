package gr.uoa.di.ecommerce.ubar.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

//check user availability
public class UserCHRequest extends StringRequest {



    public UserCHRequest (int method, String url, Response.Listener<String> li, Response.ErrorListener Erli, String usrname)
    {
        super(method, url+"/"+usrname, li, Erli);
    }
}
