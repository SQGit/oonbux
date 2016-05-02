package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Salman on 4/15/2016.
 */
public class ProfileInfo extends Activity {

    public final static int REQUEST_CODE = 1;
    ImageButton btn_back;

    com.rey.material.widget.LinearLayout lt_back;
    LinearLayout bck_lt;
    Button btn_next, btn_edit;
    TextView header;
    PhotoAdapter photoAdapter;
    MaterialEditText et_fname, et_lname, et_email, et_phone, et_zip, et_state;
    ImageView imgview, img_edit;
    Bitmap bitmap;
    Uri uri;

    ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitiy_profile_info);

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

        imgview = (ImageView) findViewById(R.id.imageView);
        img_edit = (ImageView) findViewById(R.id.img_change);


        et_fname.setText("Sam");
        et_lname.setText("Andres");
        et_email.setText("samand@gmail.com");
        et_phone.setText("738993838939");
        et_zip.setText("613");
        et_state.setText("PR");


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header.setTypeface(tf1);
        et_fname.setTypeface(tf1);
        et_lname.setTypeface(tf1);
        et_email.setTypeface(tf1);
        et_phone.setTypeface(tf1);
        et_zip.setTypeface(tf1);
        et_state.setTypeface(tf1);


        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPickerIntent intent = new PhotoPickerIntent(ProfileInfo.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DeliveryAddress.class);
                startActivity(inte);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_fname.setEnabled(true);
                et_lname.setEnabled(true);
                et_email.setEnabled(true);
                et_phone.setEnabled(true);
                et_zip.setEnabled(true);
                et_state.setEnabled(true);
                btn_edit.setEnabled(false);
                btn_edit.setClickable(false);

            }
        });


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

            uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);


            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // imgview.setImageURI(uri);
            imgview.setImageBitmap(bitmap);
        }
    }


    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }


}
