package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

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
    ImageView imgview, img_edit;
    Bitmap bitmap;
    Uri uri;
    boolean img_success, img_success1;
    String chekphoto;

    ArrayList<String> selectedPhotos = new ArrayList<>();

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


        chekphoto = sharedPreferences.getString("photourl", "");

        Log.e("tag", "pfs" + get_profile_sts);


        photoAdapter = new PhotoAdapter(this, selectedPhotos);

      /*  try {
            imgview.setImageURI(uri);
        } catch (NullPointerException e) {

            Log.d("tag", String.valueOf(e));

        }*/

        btn_back = (ImageButton) findViewById(R.id.btn_back);

        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);
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

        imgview = (ImageView) findViewById(R.id.nav_header_propic);
        img_edit = (ImageView) findViewById(R.id.img_change);


        if ((get_profile_sts.equals(""))) {
            btn_next.setVisibility(View.VISIBLE);
            lt_back.setVisibility(View.GONE);
        } else {
            btn_next.setVisibility(View.GONE);
        }




       /* if ((get_profile_sts.equals(""))) {
            enable();
            et_fname.setText("");
            et_lname.setText("");
            et_email.setText("");
            et_phone.setText("");
            et_zip.setText("");
            et_state.setText("");
        } else {

            disable();
        }*/


       /* et_fname.setText("Sam");
        et_lname.setText("Andres");
        et_email.setText("samand@gmail.com");
        et_phone.setText("738993838939");
        et_zip.setText("613");
        et_state.setText("PR");*/


        disable();
        et_fname.setText(str_fname);
        et_lname.setText(str_lname);
        et_email.setText(str_email);
        et_phone.setText(str_phone);
        et_zip.setText(str_zip);
        et_state.setText(str_state);


        if (!(chekphoto.equals(""))) {


            uri = Uri.fromFile(new File(chekphoto));

            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgview.setImageBitmap(bitmap);

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

                if ((get_profile_sts.equals(""))) {
                    //Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();

                } else {
                    Intent inte = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(inte);
                }


            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);

                img_success1 = sharedPreferences.getBoolean("img_add", false);


                if (img_success1 || (!chekphoto.equals(""))) {
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
                editor.commit();

                enable();

                btn_next.setVisibility(View.VISIBLE);


            }
        });


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
                                editor.putString("photourl", str_photo);
                                //editor.putString("photo",selectedPhotos.get(0));
                                editor.commit();

                                Intent inte = new Intent(getApplicationContext(), DeliveryAddress.class);
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            Log.d("tag", "worked");
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();

            str_photo = selectedPhotos.get(0);
            uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);


            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // imgview.setImageURI(uri);
            imgview.setImageBitmap(bitmap);
            img_success = true;

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileInfo.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("img_add", true);
            editor.commit();

        }
    }


   /* public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }*/


    public void enable() {

        et_fname.setEnabled(true);
        et_fname.setTextColor(getResources().getColor(R.color.text_color));
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

        // img_edit.setEnabled(false);

        btn_edit.setEnabled(true);
    }
}