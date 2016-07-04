package sqindia.net.oonbux;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
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
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileDashboard extends FragmentActivity implements OnMapReadyCallback {

    LinearLayout phy_layout, prof_layout;
    ImageButton btn_back;
    TextView tv_oonbuxid_txt, tv_oonbux_id, tv_location, tv_phone, tv_fname, tv_lname, tv_phy_adr, tv_prof, tv_pers, tv_ver;
    com.rey.material.widget.TextView tv_header;
    Button btn_logout, btn_finish;
    ImageView iv_profile;
    String get_profile_sts, str_oonbux_id, str_state, str_zip, str_phone, str_fname, str_lname, str_photo, str_session_id, web_photo, vir_adr_sts;
    Uri uri;
    Bitmap bitmap;
    com.rey.material.widget.LinearLayout lt_back;
    SweetAlertDialog sweetAlertDialog;
    // EditText et_fname,et_lname;
    String get_Virtual1, get_Virtual2;
    ProgressDialog progressDialog;
    String[] vir_adr;
    MaterialEditText et_fname, et_lname;
    SweetAlertDialog psDialog;
    String imagePath = null;

    double lat, lon;
    Geocoder geocoder;
    Context context = this;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
        get_profile_sts = sharedPreferences.getString("profile", "");
        str_fname = sharedPreferences.getString("fname", "");
        str_lname = sharedPreferences.getString("lname", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_photo = sharedPreferences.getString("photourl", "");
        web_photo = sharedPreferences.getString("web_photo_url", "");
        str_session_id = sharedPreferences.getString("sessionid", "");
        vir_adr_sts = sharedPreferences.getString("virtul_addr", "");

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
        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        tv_oonbux_id.setText(str_oonbux_id);
        tv_location.setText(str_state + "\t" + str_zip);
        tv_phone.setText(str_phone);
        tv_fname.setText(str_fname);
        tv_lname.setText(str_lname);

        getLatlongtoImage();

        if (web_photo != null) {

            if (web_photo.contains("http://oonsoft.")) {
                Log.d("tag", "inside");
                Log.d("tag", web_photo);
                Picasso.with(context)
                        .load(web_photo)
                        .placeholder(null)
                        .into(iv_profile);

            } else {
                Log.d("tag", "inside");
                Log.d("tag", web_photo);
                Picasso.with(context)
                        .load(new File(web_photo))
                        .placeholder(null)
                        .into(iv_profile);
            }


        }


    /*    if (!(web_photo.isEmpty())) {
            new LoadImage().execute(web_photo);
        } else {
            uri = Uri.fromFile(new File(str_photo));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            iv_profile.setImageBitmap(bitmap);
            //bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        }*/

        if ((get_profile_sts.equals(""))) {
            btn_finish.setVisibility(View.VISIBLE);
        } else {
            btn_finish.setVisibility(View.GONE);
        }


        get_Virtual1 = sharedPreferences.getString("virtual_address1", "");
        get_Virtual2 = sharedPreferences.getString("virtual_address2", "");

        vir_adr = new String[2];

        vir_adr[0] = get_Virtual1;
        vir_adr[1] = get_Virtual2;


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

                new SweetAlertDialog(ProfileDashboard.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to Logout the Application?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login", "");
                                editor.commit();
                                Intent inte = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(inte);
                                finish();
                                sDialog.dismiss();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        })
                        .show();

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((get_profile_sts.equals(""))) {
                    //Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
                  /*  Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
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
                Intent goPhy = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
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


                if (!Config.isNetworkAvailable(ProfileDashboard.this)) {

                    new SweetAlertDialog(ProfileDashboard.this, SweetAlertDialog.WARNING_TYPE)
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

    private void getLatlongtoImage() {


        geocoder = new Geocoder(ProfileDashboard.this);
        try {
            List<Address> addresses = geocoder.getFromLocationName("77015", 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());
                //  Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                lat = address.getLatitude();
                lon = address.getLongitude();


                //  Log.d("tag", "" + address.getLatitude() + "\t" + address.getLongitude() + "\t" + address.getAddressLine(0) + "\t" + address.getLocality());

            } else {

                //Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.d("tag", "" + e);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLatlongtoImage();
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo( 10.0f ));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 9f));
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }


    public void profile_update() {

        if (vir_adr_sts.equals("")) {
            new Virtual_Address_Task().execute();
        } else {
            new Profile_Update_Task().execute();
        }

    }


    class Virtual_Address_Task extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);

        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();


                jsonObject.accumulate("selected_location", get_Virtual1);
                jsonObject.accumulate("selected_location", get_Virtual2);


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "virtualaddr/update", json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

//            sweetAlertDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {


                    Log.d("tag", "<-----Status----->" + msg);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtul_addr", "success");
                    editor.commit();

                    new Profile_Update_Task().execute();


                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                    new Profile_Update_Task().execute();
                } else if (status.equals("fail")) {
                    Log.d("tag", "<-----Status----->" + msg);


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtul_addr", "success");
                    editor.commit();

                    new Profile_Update_Task().execute();

                } else {
                    new Profile_Update_Task().execute();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    class Profile_Update_Task extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);

        protected void onPreExecute() {
            super.onPreExecute();

            sweetAlertDialog = new SweetAlertDialog(ProfileDashboard.this, SweetAlertDialog.PROGRESS_TYPE);
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

                Log.d("tag", "" + sharedPreferences.getString("loc_country", "") + sharedPreferences.getString("int_country", ""));

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
                jsonObject.accumulate("loc_addr_country", sharedPreferences.getString("loc_country", ""));

                jsonObject.accumulate("int_addr_line1", sharedPreferences.getString("int_addr1", ""));
                jsonObject.accumulate("int_addr_line2", sharedPreferences.getString("int_addr2", ""));
                jsonObject.accumulate("int_addr_city", sharedPreferences.getString("int_city", ""));
                jsonObject.accumulate("int_addr_state", sharedPreferences.getString("int_state", ""));
                jsonObject.accumulate("int_addr_zip", sharedPreferences.getString("int_zip", ""));
                jsonObject.accumulate("int_phone", sharedPreferences.getString("int_phone", ""));
                jsonObject.accumulate("int_delivery_note", sharedPreferences.getString("int_note", ""));
                jsonObject.accumulate("int_addr_country", sharedPreferences.getString("int_country", ""));

                jsonObject.accumulate("default_loc", sharedPreferences.getString("default_adr", ""));


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "profileupdate", json, str_session_id);
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

                        Dialog_new cdd = new Dialog_new(ProfileDashboard.this, "Profile Updated Successfully", 2);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();
                    }

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "SUCCESS");
                    editor.commit();


                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {

                    btn_finish.setVisibility(View.VISIBLE);

                    Dialog_Msg cdd = new Dialog_Msg(ProfileDashboard.this, msg);
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
        Bitmap bm;

        public UploadImageToServer(String path) {

            this.img_path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(ProfileDashboard.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            psDialog.setTitleText("Loading");
            psDialog.setCancelable(false);
            psDialog.show();

        }


        @Override
        protected String doInBackground(Void... params) {

            bm = BitmapFactory.decodeFile(img_path);
            return uploadFile();

        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("http://oonsoft.eastus.cloudapp.azure.com/api/profilepic");
            httppost.setHeader("session_id", str_session_id);

            try {


               MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                File sourceFile = new File(img_path);
                Log.e("tag",""+img_path);
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
            Log.e("tag", "Response from server: " + result);
            psDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(result);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.e("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    String url = jo.getString("url");

                    Log.e("tag", url);


                    btn_finish.setVisibility(View.GONE);


                    Dialog_new cdd = new Dialog_new(ProfileDashboard.this, "Profile Updated Successfully", 2);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileDashboard.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "SUCCESS");
                    editor.putString("web_photo_url", url);
                    editor.commit();

                } else if (status.equals("fail"))

                {
                    Dialog_new cdd = new Dialog_new(ProfileDashboard.this, "Profile Picture not uploaded", 4);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                } else if (status.equals("fail")) {

                    btn_finish.setVisibility(View.VISIBLE);

                    Dialog_Msg cdd = new Dialog_Msg(ProfileDashboard.this, msg);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                } else {
                    Dialog_new cdd = new Dialog_new(ProfileDashboard.this, "Profile Picture not uploaded", 2);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

/*
            Dialog_new cdd = new Dialog_new(ProfileDashboard.this, "Profile Updated Successfully", 2);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();*/
        }

    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProfileDashboard.this);
            progressDialog.setMessage("Loading Image ....");
            progressDialog.show();

        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            Log.d("tag", "" + image);
            if (image != null) {
                //img.setImageBitmap(image);
                bitmap = image;
                progressDialog.dismiss();

                iv_profile.setImageBitmap(bitmap);

            } else {

                progressDialog.dismiss();
                Toast.makeText(ProfileDashboard.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}

