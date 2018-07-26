package gr.uoa.di.ecommerce.ubar.Fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

import gr.uoa.di.ecommerce.ubar.Activities.MainActivity;
import gr.uoa.di.ecommerce.ubar.Activities.MapActivity;
import gr.uoa.di.ecommerce.ubar.GlobalState;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Def;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondLoginFragment extends Fragment {

    Button back;
    Button login;
    TextView error;
    TextView type;
    EditText UserTxt;
    EditText PassTxt;

    protected GlobalState state;
    protected RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SecondLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondLoginFragment newInstance(String param1, String param2) {
        SecondLoginFragment fragment = new SecondLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void onClickBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((MainActivity)getActivity()).use_first_fragment();
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

                String url = Def.SERVER_URL + Def.LOGIN_PATH;
                //String url = "http://httpbin.org/get?param1=hello";
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("username", username);
                    jobj.put("password", password);
                    jobj.put("type", typeStr);
                } catch (JSONException e) {
                    //TODO handle error
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                boolean success = false;
                                Intent intent = new Intent(getActivity(), MapActivity.class);
                                try {
                                    if ((Integer) response.get(Def.id) != Def.not_found) {
                                        error.setText("SUCCESS");
                                        success = true;
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
