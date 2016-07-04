package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Salman on 4/15/2016.
 */
public class ProfileInfo extends Activity {

    public final static int REQUEST_CODE = 1;
    ImageButton btn_back;
    String get_profile_sts, str_fname, str_lname, str_email, str_phone, str_zip, str_state, str_photo;
    com.rey.material.widget.LinearLayout lt_back;
    LinearLayout bck_lt;
    Button btn_next, btn_edit;
    TextView header;
    PhotoAdapter photoAdapter;
    MaterialEditText et_fname, et_lname, et_email, et_phone, et_zip, et_state;
    ImageView iv_profile_pic, img_edit;
    Bitmap bitmap;
    Uri uri;
    boolean img_frm_gal = false, img_success1, img_frm_web = false;
    String web_photo, str_session_id, photo_path;
    Context context = this;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    SweetAlertDialog sweetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitiy_profile_info);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);

        get_profile_sts = sharedPreferences.getString("profile", "");
        str_fname = sharedPreferences.getString("fname", "");
        str_lname = sharedPreferences.getString("lname", "");
        str_email = sharedPreferences.getString("mail", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_session_id = sharedPreferences.getString("sessionid", "");


        Log.e("tag", "pfs" + get_profile_sts);

        //  photoAdapter = new PhotoAdapter(this, selectedPhotos);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.button_submit);
        btn_edit = (Button) findViewById(R.id.button_edit);
        header = (TextView) findViewById(R.id.tv_hd_txt);
        et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_zip = (MaterialEditText) findViewById(R.id.edittext_zip);
        et_state = (MaterialEditText) findViewById(R.id.edittext_state);
        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        iv_profile_pic = (ImageView) findViewById(R.id.nav_header_propic);
        img_edit = (ImageView) findViewById(R.id.img_change);


        if ((get_profile_sts.equals(""))) {
            btn_next.setVisibility(View.VISIBLE);
            lt_back.setVisibility(View.GONE);
        } else {
            btn_next.setVisibility(View.GONE);
            lt_back.setVisibility(View.VISIBLE);
        }


        et_lname.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    et_phone.requestFocus();
                    int pos = et_phone.getText().length();
                    et_phone.setSelection(pos);

                    return true;
                }
                return false;
            }

        });


        disable();
        et_fname.setText(str_fname);
        et_lname.setText(str_lname);
        et_email.setText(str_email);
        et_phone.setText(str_phone);
        et_zip.setText(str_zip);
        et_state.setText(str_state);


     /*   if (!((sharedPreferences.getString("web_photo_url", "")) == null)) {

            Log.d("tag", "inside0");
            web_photo = sharedPreferences.getString("web_photo_url", "");

            if (web_photo != null) {
                Log.d("tag", "inside1");

                Picasso.with(context)
                        .load(web_photo)
                        .fit()
                        .into(iv_profile_pic);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("photourl", web_photo);
                editor.commit();

                img_frm_web = true;
            }


        } else if (!((sharedPreferences.getString("photourl", "")) == null)) {

            Log.d("tag", "inside11");
            web_photo = sharedPreferences.getString("web_photo_url", "");


            if (web_photo != null) {
                Log.d("tag", "inside1");

                Picasso.with(context)
                        .load(new File(web_photo))
                        .fit()
                        .into(iv_profile_pic);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("photourl", web_photo);
                editor.commit();

                img_frm_gal = true;
            }
        }


        if (sharedPreferences.getString("edit", "").isEmpty()) {

        }*/



       /* if (!(web_photo.equals(""))) {

            uri = Uri.fromFile(new File(web_photo));

            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            iv_profile_pic.setImageBitmap(bitmap);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("photourl", web_photo);
            editor.commit();

        }*/

        Log.e("tag",""+sharedPreferences.getString("photo_path", ""));

        if (sharedPreferences.getString("photo_path", "").equals("")) {

            Log.e("tag","inside");

        }
        else{
            Log.e("tag","outside");
            photo_path = sharedPreferences.getString("photo_path", "");

            img_frm_web = true;
            img_frm_gal =true;

            Picasso.with(context)
                    .load(photo_path)
                    .fit()
                    .into(iv_profile_pic);
        }


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


        header.setTypeface(tf);

        et_fname.setTypeface(tf1);
        et_lname.setTypeface(tf1);
        et_email.setTypeface(tf1);
        et_phone.setTypeface(tf1);
        et_zip.setTypeface(tf1);
        et_state.setTypeface(tf1);
        btn_edit.setTypeface(tf1);
        btn_next.setTypeface(tf1);


        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ProfileInfo.this);
                intent.setPhotoCount(1);
                intent.setColumn(3);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                startActivity(inte);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                img_success1 = sharedPreferences.getBoolean("img_add", false);
                btn_edit.setBackgroundColor(getResources().getColor(R.color.text_color));

                if (img_frm_gal && img_frm_web) {
                    validatedatas();
                } else {
                    //Toast.makeText(getApplicationContext(), "Add Profile Picture", Toast.LENGTH_LONG).show();

                    new SweetAlertDialog(ProfileInfo.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Add Profile Picture!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    PhotoPickerIntent intent = new PhotoPickerIntent(ProfileInfo.this);
                                    intent.setPhotoCount(1);
                                    intent.setColumn(3);
                                    intent.setShowCamera(true);
                                    startActivityForResult(intent, REQUEST_CODE);
                                }
                            })
                            .show();


                }

            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile", "");
                editor.putString("edit", "success");
                editor.commit();
                enable();

                //    btn_edit.setBackgroundColor(getResources().getColor(R.color.hint_floating_dark));
                // btn_edit.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);


            }
        });


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();


        if ((get_profile_sts.equals(""))) {
            new SweetAlertDialog(ProfileInfo.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Do you want to exit the Application?")
                    .setConfirmText("Yes!")
                    .setCancelText("No")

                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent i1 = new Intent(Intent.ACTION_MAIN);
                            i1.setAction(Intent.ACTION_MAIN);
                            i1.addCategory(Intent.CATEGORY_HOME);
                            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i1);
                            finish();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login", "");
                            editor.commit();
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

        } else {
            Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
            startActivity(inte);
            finish();
        }


    }


    public void validatedatas() {


        str_fname = et_fname.getText().toString();
        str_lname = et_lname.getText().toString();
        str_email = et_email.getText().toString();
        str_phone = et_phone.getText().toString();
        str_zip = et_zip.getText().toString();
        str_state = et_state.getText().toString();


        if (!str_fname.isEmpty()) {
            if (!str_lname.isEmpty()) {
                if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
                    if (!(str_phone.isEmpty() || str_phone.length() < 10)) {
                        if (!str_state.isEmpty()) {
                            if (!(str_zip.isEmpty() || str_zip.length() < 3)) {


                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("fname", str_fname);
                                editor.putString("lname", str_lname);
                                editor.putString("mail", str_email);
                                editor.putString("phone", str_phone);
                                editor.putString("state", str_state);
                                editor.putString("zip", str_zip);
                                editor.putString("photourl", photo_path);
                                //editor.putString("photo",selectedPhotos.get(0));
                                editor.commit();

                                disable();

                                Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
                                startActivity(inte);

                            } else {
                                et_zip.setError("Enter zipcode");
                                et_zip.requestFocus();

                            }
                        } else {
                            et_state.setError("Enter State");
                            et_state.requestFocus();
                        }
                    } else {
                        et_phone.setError("Enter valid phone number");
                        et_phone.requestFocus();

                    }
                } else {
                    et_email.setError("Enter a valid email address!");
                    et_email.requestFocus();
                }
            } else {
                et_lname.setError("Enter a Last Name!");
                et_lname.requestFocus();
            }
        } else {
            et_fname.setError("Enter a First Name!");
            et_fname.requestFocus();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;

        if (resultCode != RESULT_CANCELED) {
            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                }
                selectedPhotos.clear();
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                // photoAdapter.notifyDataSetChanged();

                web_photo = selectedPhotos.get(0);

                web_photo = selectedPhotos.get(0);

                img_frm_gal = true;


                new UploadImageToServer(web_photo).execute();




                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("img_add", true);
                editor.putString("photourl", web_photo);
                editor.commit();

            }
        }
    }


    public void enable() {

        et_fname.setEnabled(true);
        et_fname.setTextColor(getResources().getColor(R.color.text_color));
        et_fname.requestFocus();
        int pos = et_fname.getText().length();
        et_fname.setSelection(pos);
        et_lname.setEnabled(true);
        et_lname.setTextColor(getResources().getColor(R.color.text_color));
        et_email.setEnabled(false);
        et_email.setTextColor(getResources().getColor(R.color.text_color));
        et_phone.setEnabled(true);
        et_phone.setTextColor(getResources().getColor(R.color.text_color));
        et_zip.setEnabled(true);
        et_zip.setTextColor(getResources().getColor(R.color.text_color));
        et_state.setEnabled(true);
        et_state.setTextColor(getResources().getColor(R.color.text_color));
        img_edit.setEnabled(true);
        btn_edit.setEnabled(false);

    }

    public void disable() {


        et_fname.setEnabled(false);
        et_fname.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        et_lname.setEnabled(false);
        et_lname.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        et_email.setEnabled(false);
        et_email.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        et_phone.setEnabled(false);
        et_phone.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        et_zip.setEnabled(false);
        et_zip.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        et_state.setEnabled(false);
        et_state.setTextColor(getResources().getColor(R.color.hint_floating_dark));
        img_edit.setEnabled(false);
        btn_edit.setEnabled(true);
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
            sweetDialog = new SweetAlertDialog(ProfileInfo.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();

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
                Log.e("tag", "" + img_path);
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
            sweetDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(result);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.e("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    img_frm_web = true;
                    String url = jo.getString("url");

                    Log.e("tag", url);


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_photo_url", url);
                    editor.putString("photo_path", url);
                    editor.commit();

                    Picasso.with(context)
                            .load(new File(web_photo))
                            .into(iv_profile_pic);

                } else if (status.equals("fail")) {

                    Toast.makeText(getApplicationContext(), "Profile Picture not uploaded.\nTry Again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Profile Picture not uploaded.\nTry Again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}