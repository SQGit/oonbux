package sqindia.net.oonbux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileActivity extends FragmentActivity implements OnMapReadyCallback {

    LinearLayout phy_layout, prof_layout;
    ImageButton btn_back;
    TextView tv_oonbuxid_txt, tv_oonbux_id, tv_location, tv_phone, tv_fname, tv_lname, tv_phy_adr, tv_prof, tv_pers, tv_ver;
    com.rey.material.widget.TextView tv_header;
    Button btn_logout, btn_finish;
    ImageView iv_profile;
    String get_profile_sts, str_oonbux_id, str_state, str_zip, str_phone, str_fname, str_lname, str_photo, str_session_id;
    Uri uri;
    Bitmap bitmap;
    com.rey.material.widget.LinearLayout lt_back;
    SweetAlertDialog sweetAlertDialog;
    // EditText et_fname,et_lname;

    MaterialEditText et_fname, et_lname;
    SweetAlertDialog psDialog;
    String imagePath = null;


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);

        get_profile_sts = sharedPreferences.getString("profile", "");
        str_fname = sharedPreferences.getString("fname", "");
        str_lname = sharedPreferences.getString("lname", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_photo = sharedPreferences.getString("photourl", "");

        str_session_id = sharedPreferences.getString("sessionid", "");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        phy_layout = (LinearLayout) findViewById(R.id.pda_layout);
        prof_layout = (LinearLayout) findViewById(R.id.pi_layout);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        tv_header = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);
        tv_oonbuxid_txt = (TextView) findViewById(R.id.textview_id_txt);
        tv_oonbux_id = (TextView) findViewById(R.id.textview_id);
        tv_location = (TextView) findViewById(R.id.textview_location);
        tv_phone = (TextView) findViewById(R.id.textview_phone);
        tv_fname = (TextView) findViewById(R.id.textview_fname);
        tv_lname = (TextView) findViewById(R.id.textview_lname);
        tv_phy_adr = (TextView) findViewById(R.id.tv_phy_adr);
        tv_prof = (TextView) findViewById(R.id.tv_prf_in);
        tv_pers = (TextView) findViewById(R.id.tv_pers);
        tv_ver = (TextView) findViewById(R.id.tv_ver);
        btn_logout = (Button) findViewById(R.id.button_logout);
        btn_finish = (Button) findViewById(R.id.button_finish);
        iv_profile = (ImageView) findViewById(R.id.image_profile);
        //  et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        //  et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
//        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);


        tv_oonbux_id.setText(str_oonbux_id);
        tv_location.setText(str_state + "\t" + str_zip);
        tv_phone.setText(str_phone);
        tv_fname.setText(str_fname);
        tv_lname.setText(str_lname);

        uri = Uri.fromFile(new File(str_photo));
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv_profile.setImageBitmap(bitmap);
        //bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);


        if ((get_profile_sts.equals(""))) {
            btn_finish.setVisibility(View.VISIBLE);
        } else {
            btn_finish.setVisibility(View.GONE);
        }


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header.setTypeface(tf1);

        tv_oonbuxid_txt.setTypeface(tf);
        tv_oonbux_id.setTypeface(tf);
        tv_location.setTypeface(tf);
        tv_fname.setTypeface(tf);
        tv_lname.setTypeface(tf);
        tv_phone.setTypeface(tf);

        tv_phy_adr.setTypeface(tf);
        tv_prof.setTypeface(tf);
        tv_pers.setTypeface(tf);
        tv_ver.setTypeface(tf);
        btn_logout.setTypeface(tf);
        btn_finish.setTypeface(tf);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((get_profile_sts.equals(""))) {
                    //Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
                  /*  Intent inte = new Intent(getApplicationContext(), DeliveryAddress.class);
                    startActivity(inte);*/
                    onBackPressed();

                } else {
                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);
                }
            }
        });

        phy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), DeliveryAddress.class);
                startActivity(goPhy);
            }
        });


        prof_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), ProfileInfo.class);
                startActivity(goPhy);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!Config.isNetworkAvailable(ProfileActivity.this)) {

                    new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No network Available!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    // sweetAlertDialog.setCancelable(false);

                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    finish();
                                }
                            })

                            .show();


                } else {
                    profile_update();
                }


            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(29.6396935, -95.6845927);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo( 10.0f ));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));


    }


    public void profile_update() {

        new Profile_Update_Task().execute();
    }


    class Profile_Update_Task extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();

            sweetAlertDialog = new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("firstname", sharedPreferences.getString("fname", ""));
                jsonObject.accumulate("lastname", sharedPreferences.getString("lname", ""));
                jsonObject.accumulate("email", sharedPreferences.getString("mail", ""));
                jsonObject.accumulate("phone", sharedPreferences.getString("phone", ""));
                jsonObject.accumulate("state", sharedPreferences.getString("state", ""));
                jsonObject.accumulate("country", sharedPreferences.getString("country", ""));

                jsonObject.accumulate("loc_addr_line1", sharedPreferences.getString("loc_addr1", ""));
                jsonObject.accumulate("loc_addr_line2", sharedPreferences.getString("loc_addr2", ""));
                jsonObject.accumulate("loc_addr_city", sharedPreferences.getString("loc_city", ""));
                jsonObject.accumulate("loc_addr_state", sharedPreferences.getString("loc_state", ""));
                jsonObject.accumulate("loc_addr_zip", sharedPreferences.getString("loc_zip", ""));
                jsonObject.accumulate("loc_phone", sharedPreferences.getString("loc_phone", ""));
                jsonObject.accumulate("loc_delivery_note", sharedPreferences.getString("loc_note", ""));

                jsonObject.accumulate("int_addr_line1", sharedPreferences.getString("int_addr1", ""));
                jsonObject.accumulate("int_addr_line2", sharedPreferences.getString("int_addr2", ""));
                jsonObject.accumulate("int_addr_city", sharedPreferences.getString("int_city", ""));
                jsonObject.accumulate("int_addr_state", sharedPreferences.getString("int_state", ""));
                jsonObject.accumulate("int_addr_zip", sharedPreferences.getString("int_zip", ""));
                jsonObject.accumulate("int_phone", sharedPreferences.getString("int_phone", ""));
                jsonObject.accumulate("int_delivery_note", sharedPreferences.getString("int_note", ""));

                jsonObject.accumulate("default_loc", sharedPreferences.getString("default_adr", ""));


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.REG_URL + "profileupdate", json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            sweetAlertDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    btn_finish.setVisibility(View.GONE);

                    if (str_photo != null) {
                        new UploadImageToServer(str_photo).execute();
                    } else {

                        Dialog_new cdd = new Dialog_new(ProfileActivity.this, "Profile Updated Successfully", 2);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();
                    }

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "SUCCESS");
                    editor.commit();


                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {

                    btn_finish.setVisibility(View.VISIBLE);

                    Dialog_Msg cdd = new Dialog_Msg(ProfileActivity.this, msg);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private class UploadImageToServer extends AsyncTask<Void, Integer, String> {

        String img_path;

        public UploadImageToServer(String path) {

            this.img_path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            psDialog.setTitleText("Uploading Photo");
            psDialog.setCancelable(false);
            psDialog.show();

        }


        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();

        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://oonbux.sqindia.net/api/profilepic");
            httppost.setHeader("session_id", str_session_id);

            try {
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                File sourceFile = new File(img_path);
                entity.addPart("fileUpload", new FileBody(sourceFile));
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("tag", "Response from server: " + result);
            psDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(result);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    btn_finish.setVisibility(View.GONE);


                    Dialog_new cdd = new Dialog_new(ProfileActivity.this, "Profile Updated Successfully", 2);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "SUCCESS");
                    editor.commit();

                } else if (status.equals("fail"))

                {
                    Dialog_new cdd = new Dialog_new(ProfileActivity.this, "Profile Picture not uploaded", 4);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                } else if (status.equals("fail")) {

                    btn_finish.setVisibility(View.VISIBLE);

                    Dialog_Msg cdd = new Dialog_Msg(ProfileActivity.this, msg);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

/*
            Dialog_new cdd = new Dialog_new(ProfileActivity.this, "Profile Updated Successfully", 2);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();*/
        }

    }


}

