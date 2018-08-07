package gr.uoa.di.ecommerce.ubar.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import gr.uoa.di.ecommerce.ubar.Def;
import gr.uoa.di.ecommerce.ubar.GlobalState;
import gr.uoa.di.ecommerce.ubar.R;
import gr.uoa.di.ecommerce.ubar.Requests.PostStringRequest;

public class ProfileActivity extends AppCompatActivity {

    protected GlobalState state;
    protected RequestQueue requestQueue;

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView avatar;
    private Button avatarBtn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            final Uri selectedImage = data.getData();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
//                avatar.setImageURI(selectedImage);
//                Bitmap bmap =  ((BitmapDrawable) avatar.getDrawable()).getBitmap();
                Bitmap bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                bmap.compress(Bitmap.CompressFormat.PNG, 70, stream);

                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP + Base64.DEFAULT);
                Log.d(Def.LOG_TAG, encoded);
                bmap.recycle();
                JSONObject json = new JSONObject();
                json.put("id", 1);
                json.put("avatar", encoded);
                json.put("type", ".png");
                String requestBody = json.toString();

                String url = Def.SERVER_URL + Def.UPLOAD_IMAGE_PATH;
                StringRequest request = new PostStringRequest(url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                boolean status = Boolean.parseBoolean(response);
                                if (status) {
                                    Picasso.get().load(selectedImage).into(avatar);
                                } else {
//                                    TODO - show error message
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(Def.LOG_TAG, error.toString());
                            }
                        }, requestBody, Def.APP_JSON);
                requestQueue.add(request);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        state = ((GlobalState) getApplicationContext());
        requestQueue = state.getRequestQueue();

        final String url = Def.SERVER_URL + Def.PROFILE_PATH + "1";
        final TextView username = (TextView) findViewById(R.id.username);
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView surname = (TextView) findViewById(R.id.surname);
        final TextView email = (TextView) findViewById(R.id.email);
        final TextView address = (TextView) findViewById(R.id.address);
        avatar = (ImageView) findViewById(R.id.avatar);
        avatarBtn = (Button) findViewById(R.id.avatarBtn);

        JsonObjectRequest getProfile = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            username.setText(response.getString("username"));
                            email.setText(response.getString("email"));
                            name.setText(response.getString("name"));
                            surname.setText(response.getString("surname"));
                            address.setText(response.getString("address"));
                            Picasso.get().load(Def.SERVER_URL + response.getString("avatar")).placeholder(R.drawable.porgress_animation)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(avatar);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Def.LOG_TAG, error.toString());
                    }
                }
        );
        requestQueue.add(getProfile);

        avatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

    }
}
