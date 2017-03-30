package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import sqindia.net.oonbux.Chat.Pal_Chat_List;
import sqindia.net.oonbux.Dialog.DialogYesNo;
import sqindia.net.oonbux.Fragment.DeliverPackageFragment;
import sqindia.net.oonbux.Fragment.First_Fragment;
import sqindia.net.oonbux.Fragment.ShopOn;
import sqindia.net.oonbux.Pal.MyPalLists;
import sqindia.net.oonbux.PhotoAdapter;
import sqindia.net.oonbux.Profile.ProfileDashboard;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.Cart;


@SuppressWarnings("deprecation")
public class Dashboard extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    public final static int REQUEST_CODE = 1;
    public ProgressBar progressBar;
    com.rey.material.widget.TextView tv_nav_hd_ship_txt, tv_nav_hd_ship_id, tv_nav_cont_loc, tv_nav_cont_address, tv_nav_cont_phone, tv_nav_cont_prof, tv_nav_cont_pal_req, tv_nav_cont_how_it, tv_nav_cont_help_line, tv_nav_cont_share, tv_nav_cont_rec_pkg, tv_nav_cont_payment, tv_nav_cont_d_wallet, tv_nav_cont_logout,tv_offer,tv_cart,tv_track,tv_chat;
    Button btn_nav_cont_loc_adr, btn_nav_cont_int_adr, btn_nav_cont_add_loc, btn_dash_ship, btn_dash_deliver, btn_dash_shop, btn_shop_online, btn_add_shipment, btn_done_shipment;
    com.rey.material.widget.TextView tv_dash_hd_txt;
    String str_oonbux_id, str_photo, web_photo, photo_path;
    Toolbar toolbar;
    Fragment fragment;
    Context context = this;
    ImageView btm_cam, nav_pro_pic, btn_chat,btn_menu,btn_cart;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    PhotoAdapter photoAdapter;

    String va1_line1, va1_line2, va1_city, va1_state, va1_zip, va1_country, va2_line1, va2_line2, va2_city, va2_state, va2_zip, va2_country,session_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_photo = sharedPreferences.getString("photourl", "");

        va1_line1 = sharedPreferences.getString("va1_line1", "");
        va1_line2 = sharedPreferences.getString("va1_line2", "");
        va1_city = sharedPreferences.getString("va1_city", "");
        va1_state = sharedPreferences.getString("va1_state", "");
        va1_zip = sharedPreferences.getString("va1_zip", "");
        va1_country = sharedPreferences.getString("va1_country", "");


        va2_line1 = sharedPreferences.getString("va2_line1", "");
        va2_line2 = sharedPreferences.getString("va2_line2", "");
        va2_city = sharedPreferences.getString("va2_city", "");
        va2_state = sharedPreferences.getString("va2_state", "");
        va2_zip = sharedPreferences.getString("va2_zip", "");
        va2_country = sharedPreferences.getString("va2_country", "");

        web_photo = sharedPreferences.getString("photo_path", "");

        session_id = sharedPreferences.getString("sessionid", "");

        Log.e("tag", "" + session_id);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tv_dash_hd_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);

        tv_nav_hd_ship_txt = (com.rey.material.widget.TextView) findViewById(R.id.textview_id_txt);
        tv_nav_hd_ship_id = (com.rey.material.widget.TextView) findViewById(R.id.textview_id);
        tv_nav_cont_loc = (com.rey.material.widget.TextView) findViewById(R.id.txt1);
        tv_nav_cont_address = (com.rey.material.widget.TextView) findViewById(R.id.txt2);
        tv_nav_cont_phone = (com.rey.material.widget.TextView) findViewById(R.id.txt3);
        tv_nav_cont_prof = (com.rey.material.widget.TextView) findViewById(R.id.txt4);
        tv_nav_cont_pal_req = (com.rey.material.widget.TextView) findViewById(R.id.txt5);
        tv_nav_cont_how_it = (com.rey.material.widget.TextView) findViewById(R.id.txt6);
        tv_nav_cont_help_line = (com.rey.material.widget.TextView) findViewById(R.id.txt7);
        tv_nav_cont_share = (com.rey.material.widget.TextView) findViewById(R.id.txt8);
        tv_nav_cont_rec_pkg = (com.rey.material.widget.TextView) findViewById(R.id.txt9);
        tv_nav_cont_payment = (com.rey.material.widget.TextView) findViewById(R.id.txt10);
        tv_nav_cont_d_wallet = (com.rey.material.widget.TextView) findViewById(R.id.txt11);
        tv_nav_cont_logout = (TextView) findViewById(R.id.txt12);

        tv_offer = (TextView) findViewById(R.id.tv_offer);
        tv_cart = (TextView) findViewById(R.id.tv_cart);
        tv_track = (TextView) findViewById(R.id.tv_track);
        tv_chat = (TextView) findViewById(R.id.tv_chat);

        btn_add_shipment = (Button) findViewById(R.id.add_ship_button);

        btn_nav_cont_loc_adr = (Button) findViewById(R.id.btn1);
        btn_nav_cont_int_adr = (Button) findViewById(R.id.btn2);
        btn_nav_cont_add_loc = (Button) findViewById(R.id.btn3);

        btn_shop_online = (Button) findViewById(R.id.btn_dash_shop_online);

        btn_dash_ship = (Button) findViewById(R.id.btn_dash_ship);
        btn_dash_deliver = (Button) findViewById(R.id.btn_dash_deliver);
        btn_dash_shop = (Button) findViewById(R.id.btn_dash_shop_online);

        btm_cam = (ImageView) findViewById(R.id.cam);
        btn_chat = (ImageView) findViewById(R.id.chat);
        nav_pro_pic = (ImageView) findViewById(R.id.nav_header_propic);
        btn_menu = (ImageView) findViewById(R.id.img_menu);

        btn_cart = (ImageView) findViewById(R.id.iv_cart);

        Log.e("tag", "" + sharedPreferences.getString("photo_path", ""));

        if (sharedPreferences.getString("photo_path", "").equals("")) {

            Log.e("tag", "inside");

        } else {
            Log.e("tag", "outside");
            photo_path = sharedPreferences.getString("photo_path", "");


            Picasso.with(context)
                    .load(photo_path)
                    .fit()
                    .into(nav_pro_pic);
        }


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        tv_dash_hd_txt.setTypeface(tf1);
        tv_nav_hd_ship_txt.setTypeface(tf);
        tv_nav_hd_ship_id.setTypeface(tf);
        tv_nav_cont_loc.setTypeface(tf);
        tv_nav_cont_address.setTypeface(tf);
        tv_nav_cont_phone.setTypeface(tf);
        tv_nav_cont_prof.setTypeface(tf);
        tv_nav_cont_pal_req.setTypeface(tf);
        tv_nav_cont_how_it.setTypeface(tf);
        tv_nav_cont_help_line.setTypeface(tf);
        tv_nav_cont_share.setTypeface(tf);
        tv_nav_cont_rec_pkg.setTypeface(tf);
        tv_nav_cont_payment.setTypeface(tf);
        tv_nav_cont_d_wallet.setTypeface(tf);
        btn_nav_cont_loc_adr.setTypeface(tf);
        btn_nav_cont_int_adr.setTypeface(tf);
        btn_nav_cont_add_loc.setTypeface(tf);
        btn_dash_ship.setTypeface(tf);
        btn_dash_deliver.setTypeface(tf);
        btn_shop_online.setTypeface(tf);
        btn_add_shipment.setTypeface(tf);

        tv_offer.setTypeface(tf);
        tv_cart.setTypeface(tf);
        tv_track.setTypeface(tf);
        tv_chat.setTypeface(tf);


        tv_nav_cont_logout.setTypeface(tf);

        if (!(sharedPreferences.getString("shipment_photo1", "").equals(""))) {

            Log.e("tag", "" + sharedPreferences.getString("shipment_photo1", ""));

            btn_add_shipment.setVisibility(View.GONE);
            //btn_done_shipment.setVisibility(View.GONE);

            Log.e("tag", "" + "2nd");

            progressBar.setProgress(66);
            btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
            btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
            btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));
            DeliverPackageFragment fragment = new DeliverPackageFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

        } else {

            Log.e("tag", "" + "1st");

            tv_nav_hd_ship_id.setText(str_oonbux_id);
            btn_add_shipment.setVisibility(View.VISIBLE);
            progressBar.setProgress(33);
            First_Fragment fragment = new First_Fragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

        }



      //  Intent intent = getIntent();

       // String asd  = intent.getStringExtra("add");

    /*    if (!(asd.equals(null))){

            progressBar.setProgress(66);
            btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
            btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
            btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));
            DeliverPackageFragment fragmenta = new DeliverPackageFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();




        }*/






        btn_nav_cont_loc_adr.setText(va1_zip + "\n" + va1_country);
        btn_nav_cont_int_adr.setText(va2_zip + "\n" + va2_country);


        tv_nav_cont_loc.setText(va1_state);
        tv_nav_cont_address.setText(va1_line1 + "\t" + va1_line2 + "\t" + va1_city);


        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) Dashboard.this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        btn_dash_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.VISIBLE);
                progressBar.setProgress(33);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                First_Fragment fragment = new First_Fragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });

        btn_dash_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sharedPreferences.getString("shipment_photo1", "")).equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Add shipment!", Toast.LENGTH_SHORT).show();
                } else {
                    btn_add_shipment.setVisibility(View.GONE);

                    progressBar.setProgress(66);
                    btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                    btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                    btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                    DeliverPackageFragment fragment = new DeliverPackageFragment();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
                }
            }
        });


        btn_shop_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.GONE);

                progressBar.setProgress(100);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_brown));

                ShopOn fragment = new ShopOn();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

            }
        });



        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        btn_nav_cont_add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("vir_sts", "1");
                editor.commit();

                Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                inte.putExtra("sts", 1);
                startActivity(inte);

            }
        });

        tv_nav_cont_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                DialogYesNo dialog_logout = new DialogYesNo(Dashboard.this, "Do You Want to Logout ?", 0,session_id);
                dialog_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_logout.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_logout.show();
            }
        });

        tv_nav_cont_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_how_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent inte = new Intent(getApplicationContext(), HowitWorks.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(Gravity.LEFT);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Oonbux is shipping items wherever you wants...");
                share.putExtra(Intent.EXTRA_TEXT, "http://www.sqindia.net");

                startActivity(Intent.createChooser(share, "Share Oonbux !"));
            }
        });

        tv_nav_cont_help_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(Gravity.LEFT);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:198"));
                if (ActivityCompat.checkSelfPermission(Dashboard.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        btn_nav_cont_loc_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_nav_cont_loc.setText(va1_state);
                tv_nav_cont_address.setText(va1_line1 + "\t" + va1_line2 + "\t" + va1_city);
            }
        });

        btn_nav_cont_int_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_nav_cont_loc.setText(va2_state);
                tv_nav_cont_address.setText(va2_line1 + "\t" + va2_line2 + "\t" + va2_city);
            }
        });

        tv_nav_cont_pal_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent inte = new Intent(getApplicationContext(), MyPalLists.class);
                startActivity(inte);
            }
        });


        btn_add_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(Dashboard.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(Gravity.LEFT);

                Intent go_Cart = new Intent(getApplicationContext(), Cart.class);
                ActivityOptions options = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    options = ActivityOptions.makeScaleUpAnimation(btn_cart, 0,
                            0, btn_cart.getWidth(),
                            btn_cart.getHeight());
                    startActivity(go_Cart, options.toBundle());
                } else {
                    go_Cart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(go_Cart);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }


                startActivity(go_Cart);

            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if(sharedPreferences.getString("friends_count","") != "") {
                    drawer.closeDrawer(Gravity.LEFT);
                    Intent goChat = new Intent(getApplicationContext(), Pal_Chat_List.class);
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        options = ActivityOptions.makeScaleUpAnimation(btn_chat, 0,
                                0, btn_chat.getWidth(),
                                btn_chat.getHeight());
                        startActivity(goChat, options.toBundle());
                    } else {
                        goChat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goChat);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                /*}
                else{
                    Toast.makeText(getApplicationContext(),"You Don't Have any Friends",Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        btm_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(Dashboard.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
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

            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("shipment_photo", selectedPhotos.get(0));
            editor.putString("fromdash", "asdfg");
            editor.commit();

            progressBar.setProgress(66);

            btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
            btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
            btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

            DeliverPackageFragment fragment = new DeliverPackageFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            btn_add_shipment.setVisibility(View.GONE);
        }
    }


    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onBackPressed() {
        DialogYesNo dialog_exit = new DialogYesNo(Dashboard.this, "Do You Want to Exit ?", 1,"nothing");
        dialog_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_exit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_exit.show();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }


    private class PhoneCallListener extends PhoneStateListener {
        String LOG_TAG = "LOGGING 123";
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "Rining, number: " + incomingNumber);
            }
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "Offline");
                isPhoneCalling = true;
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");
                if (isPhoneCalling) {
                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }
            }
        }
    }

}
