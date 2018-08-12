package gr.uoa.di.ecommerce.ubar;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import android.app.Application;
import gr.uoa.di.ecommerce.ubar.Utilities.PrefSingleton;

public class GlobalState extends Application {

    private RequestQueue requestQueue;

    private String myUsername;
    private int myID;
    private String myType;

    public RequestQueue getRequestQueue() { return requestQueue;}

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        PrefSingleton.getInstance().Initialize(this);

    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String type) {
        this.myType = type;
    }
}
