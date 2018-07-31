package gr.uoa.di.ecommerce.ubar.Fragments;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


import gr.uoa.di.ecommerce.ubar.Activities.HomeActivity;
import gr.uoa.di.ecommerce.ubar.GlobalState;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.Utilities.Hash;


public class SecondLoginFragment extends Fragment {

    Button back;
    Button login;
    TextView error;
    TextView type;
    EditText UserTxt;
    EditText PassTxt;

    protected GlobalState state;
    protected RequestQueue requestQueue;

    private OnFragmentInteractionListener mListener;



    public SecondLoginFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        state = ((GlobalState) getActivity().getApplicationContext());
        requestQueue = state.getRequestQueue();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_login, container, false);

        login = (Button) view.findViewById(R.id.login);
        back = (Button) view.findViewById(R.id.back);
        error = (TextView) view.findViewById(R.id.error);
        type = (TextView) view.findViewById(R.id.type);
        UserTxt = (EditText) view.findViewById(R.id.editUser);
        PassTxt = (EditText) view.findViewById(R.id.editPass);

        onClickBack();
        onClickLogin();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void use_first_fragment();
    }





    protected void onClickBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.use_first_fragment();
            }

        });
    }

    private void onClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserTxt.getText().toString();
                String password = PassTxt.getText().toString();

                String typeStr = type.getText().toString();
                typeStr = typeStr.substring(0,1).toLowerCase() + typeStr.substring(1,typeStr.length());

                String url = Def.SERVER_URL + Def.LOGIN_PATH;
                //String url = "http://httpbin.org/get?param1=hello";
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("username", username);
                    jobj.put("password", Hash.getSHA512(password));
                    jobj.put("type", typeStr);
                } catch (JSONException e) {
                    //TODO handle error
                }

                //error.setText(jobj.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                boolean success = false;
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                try {
                                    if ((Integer) response.get(Def.id) != Def.not_found) {
                                        error.setText("SUCCESS");
                                        success = true;
                                        state.setMyUsername((String) response.get(Def.username));
                                        state.setMyType((String) response.get(Def.type));
                                        state.setMyID(Integer.parseInt((String) response.get(Def.id)));
                                        intent.putExtra(Def.KEY_USERNAME, (String) response.get(Def.username));
                                        intent.putExtra(Def.KEY_TYPE, (String) response.get(Def.type));

                                    } else {
                                        error.setText("Wrong Username or Password");
                                    }
                                } catch (JSONException e) {   //TO handle error

                                }
                                if (success)
                                    //finish();
                                    getActivity().startActivity(intent);


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
