package gr.uoa.di.ecommerce.ubar;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import android.app.Application;

public class GlobalState extends Application {

    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() { return requestQueue;}

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);

    }
}
