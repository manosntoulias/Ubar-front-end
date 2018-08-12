package gr.uoa.di.ecommerce.ubar.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;

public class PrefSingleton {
    private static PrefSingleton ourInstance;

    private SharedPreferences sharedPref;
    private Context mContext;

    private static final String FILE_NAME = "our_user_preferences";


    //INITIALIZE

    public static PrefSingleton getInstance() {

        if (ourInstance == null) {
            ourInstance = new PrefSingleton();
        }
            return ourInstance;
    }

    public void Initialize(Context ctxt){

        mContext = ctxt;
        sharedPref = mContext.getSharedPreferences(
                FILE_NAME , Context.MODE_PRIVATE);
    }

    private PrefSingleton() {
    }

    //GETTERS AND SETTERS
    public void writeString(String key, String value){
        SharedPreferences.Editor e = sharedPref.edit();
        e.putString(key, value);
        e.commit();
    }

    public void writeInt(String key, int value){
        SharedPreferences.Editor e = sharedPref.edit();
        e.putInt(key, value);
        e.commit();
    }


    // use: PrefSingleton.getInstance().getSharedPref().getInt(String, defaultValue) to get the values
    //                                              or .getString(String, defaultvalue)

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public void clear() {
        SharedPreferences.Editor e = sharedPref.edit();
        e.clear();
        e.commit();
    }
}
