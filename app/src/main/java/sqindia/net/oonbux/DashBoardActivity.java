package sqindia.net.oonbux;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


@SuppressWarnings("deprecation")
public class DashBoardActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    public final static int REQUEST_CODE = 1;
    public ProgressBar progressBar;
    com.rey.material.widget.TextView tv_nav_hd_ship_txt, tv_nav_hd_ship_id, tv_nav_cont_loc, tv_nav_cont_address, tv_nav_cont_phone, tv_nav_cont_prof, tv_nav_cont_pal_req, tv_nav_cont_how_it, tv_nav_cont_help_line, tv_nav_cont_share, tv_nav_cont_rec_pkg, tv_nav_cont_payment, tv_nav_cont_d_wallet, tv_nav_cont_logout;
    Button btn_nav_cont_loc_adr, btn_nav_cont_int_adr, btn_nav_cont_add_loc, btn_dash_ship, btn_dash_deliver, btn_dash_shop, btn_shop_online, btn_add_shipment, btn_done_shipment;
    com.rey.material.widget.TextView tv_dash_hd_txt;
    String str_oonbux_id, str_photo, web_photo,photo_path;
    Toolbar toolbar;
    Fragment fragment;
    Context context = this;
    ImageView btm_cam, nav_pro_pic,btm_chat;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    PhotoAdapter photoAdapter;

    String va1_line1, va1_line2, va1_city, va1_state, va1_zip, va1_country, va2_line1, va2_line2, va2_city, va2_state, va2_zip, va2_country;

    Bitmap bitmap;
    Uri uri;

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        photoAdapter = new PhotoAdapter(this, selectedPhotos);

      /*  try {
            btm_cam.setImageURI(photoAdapter.uri);
        } catch (NullPointerException e) {

            Log.d("tag", String.valueOf(e));

        }*/


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
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

        String asdf = sharedPreferences.getString("sessionid", "");

        Log.e("tag", "" + asdf);


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
        //txt = (com.rey.material.widget.TextView) findViewById(R.id.txt4);
        tv_nav_cont_pal_req = (com.rey.material.widget.TextView) findViewById(R.id.txt5);
        tv_nav_cont_how_it = (com.rey.material.widget.TextView) findViewById(R.id.txt6);
        tv_nav_cont_help_line = (com.rey.material.widget.TextView) findViewById(R.id.txt7);
        tv_nav_cont_share = (com.rey.material.widget.TextView) findViewById(R.id.txt8);
        tv_nav_cont_rec_pkg = (com.rey.material.widget.TextView) findViewById(R.id.txt9);
        tv_nav_cont_payment = (com.rey.material.widget.TextView) findViewById(R.id.txt10);
        tv_nav_cont_d_wallet = (com.rey.material.widget.TextView) findViewById(R.id.txt11);
        tv_nav_cont_logout = (TextView) findViewById(R.id.txt12);

        btn_add_shipment = (Button) findViewById(R.id.add_ship_button);
        btn_done_shipment = (Button) findViewById(R.id.add_done_button);

        btn_nav_cont_loc_adr = (Button) findViewById(R.id.btn1);
        btn_nav_cont_int_adr = (Button) findViewById(R.id.btn2);
        btn_nav_cont_add_loc = (Button) findViewById(R.id.btn3);

        btn_shop_online = (Button) findViewById(R.id.btn_dash_shop_online);

        btn_dash_ship = (Button) findViewById(R.id.btn_dash_ship);
        btn_dash_deliver = (Button) findViewById(R.id.btn_dash_deliver);
        btn_dash_shop = (Button) findViewById(R.id.btn_dash_shop_online);

        btm_cam = (ImageView) findViewById(R.id.cam);
        btm_chat = (ImageView) findViewById(R.id.chat);
        nav_pro_pic = (ImageView) findViewById(R.id.nav_header_propic);


     /*   if (web_photo != null) {
            Log.d("tag", "inside");

            Picasso.with(context)
                    .load(web_photo)
                    .resize(50, 50)
                    .into(nav_pro_pic);

        }*/



        Log.e("tag",""+sharedPreferences.getString("photo_path", ""));

        if (sharedPreferences.getString("photo_path", "").equals("")) {

            Log.e("tag","inside");

        }
        else{
            Log.e("tag","outside");
            photo_path = sharedPreferences.getString("photo_path", "");



            Picasso.with(context)
                    .load(photo_path)
                    .fit()
                    .into(nav_pro_pic);
        }



     /*   if(!(web_photo == "")) {
            //new LoadImage().execute(web_photo);


            Picasso.with(context)
                    .load(web_photo)
                    .resize(50, 50)
                    .centerCrop()
                    .into(nav_pro_pic);

        }*/


        // uri = Uri.fromFile(new File(str_photo));
      /*  try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }*/

        // nav_pro_pic.setImageBitmap(bitmap);

      /*  tv_nav_cont_loc.setText(sharedPreferences.getString("state", ""));
        tv_nav_cont_address.setText(sharedPreferences.getString("zip", ""));
        tv_nav_cont_phone.setText(sharedPreferences.getString("phone", ""));*/


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

        tv_nav_cont_logout.setTypeface(tf);

        tv_nav_hd_ship_id.setText(str_oonbux_id);
        btn_add_shipment.setVisibility(View.VISIBLE);
        btn_done_shipment.setVisibility(View.GONE);
        progressBar.setProgress(33);
        First_Fragment fragment = new First_Fragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();


        btn_nav_cont_loc_adr.setText(va1_zip + "\n" + va1_country);
        btn_nav_cont_int_adr.setText(va2_zip + "\n" + va2_country);


        tv_nav_cont_loc.setText(va1_state);
        tv_nav_cont_address.setText(va1_line1 + "\t" + va1_line2 + "\t" + va1_city);


        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) DashBoardActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        btn_dash_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.VISIBLE);
                btn_done_shipment.setVisibility(View.GONE);
                progressBar.setProgress(33);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                //ShipPackageFragment fragment = new ShipPackageFragment();
                First_Fragment fragment = new First_Fragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });

        btn_dash_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((sharedPreferences.getString("shipment_photo", "")) == "") {
                    new SweetAlertDialog(DashBoardActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please Add the Shipment")
                            .setConfirmText("Ok")


                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();

                } else {


                    btn_add_shipment.setVisibility(View.GONE);
                    // btn_done_shipment.setVisibility(View.VISIBLE);

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
                btn_done_shipment.setVisibility(View.GONE);


                progressBar.setProgress(100);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_brown));

                //ShopOnlineFragment fragment = new ShopOnlineFragment();


                ShopOn fragment = new ShopOn();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();


            }
        });


        btn_nav_cont_add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*AddLoc_Fragment fragment = new AddLoc_Fragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
*/

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("vir_sts", "1");
                editor.commit();


                Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                inte.putExtra("sts", 1);
                startActivity(inte);

                // toggle.syncState();
                // drawer.closeDrawer(GravityCompat.START);

               /* Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                startActivity(inte);*/
            }
        });

        tv_nav_cont_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(DashBoardActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to Logout the Application?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
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


        tv_nav_cont_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_how_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), HowItWorks.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_help_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:198"));
                if (ActivityCompat.checkSelfPermission(DashBoardActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

               /* Intent inte = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(inte);*/
            }
        });

        btn_nav_cont_loc_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "button worked", Toast.LENGTH_LONG).show();
                tv_nav_cont_loc.setText(va1_state);
                tv_nav_cont_address.setText(va1_line1 + "\t" + va1_line2 + "\t" + va1_city);
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


        btn_nav_cont_int_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
                tv_nav_cont_loc.setText(sharedPreferences.getString("int_state", ""));
                tv_nav_cont_address.setText(sharedPreferences.getString("int_addr1", "") + "\t" + sharedPreferences.getString("int_addr2", ""));
                tv_nav_cont_phone.setText(sharedPreferences.getString("int_phone", ""));*/

                tv_nav_cont_loc.setText(va2_state);
                tv_nav_cont_address.setText(va2_line1 + "\t" + va2_line2 + "\t" + va2_city);

            }
        });


        tv_nav_cont_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent inte = new Intent(getApplicationContext(), Invite_Pal.class);
                startActivity(inte);*/
            }
        });


        btm_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "tsdf", Toast.LENGTH_LONG).show();

                PhotoPickerIntent intent = new PhotoPickerIntent(DashBoardActivity.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        btn_add_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(DashBoardActivity.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



        btm_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goChat = new Intent(getApplicationContext(),Pal_Chat_List.class);
                startActivity(goChat);
            }
        });


    /*    final NavigationTabBar ntbSample4 = (NavigationTabBar) findViewById(R.id.ntb_sample_4);
        final int bgColor = Color.TRANSPARENT;
        final ArrayList<NavigationTabBar.Model> models4 = new ArrayList<>();
        models4.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.chat_ico), bgColor

                ).build()
        );
        models4.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.group_ico), bgColor
                ).build()
        );
        models4.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.camera), bgColor
                ).build()
        );
        models4.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.receive_ico), bgColor
                ).build()
        );
        models4.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.pkg_ico), bgColor
                ).build()
        );

        ntbSample4.setModels(models4);
        ntbSample4.setModelIndex(2, true);



        ntbSample4.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

                if(index ==0){
                    Intent goChat = new Intent(getApplicationContext(),Pal_Chat_List.class);
                    startActivity(goChat);
                }

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });

*/


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

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
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
            btn_done_shipment.setVisibility(View.GONE);

            //btm_cam.setImageURI(uri);
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
        //super.onBackPressed();


        new SweetAlertDialog(DashBoardActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                    Intent i = new Intent(getApplicationContext(), DashBoardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }

            }
        }
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DashBoardActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

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
                pDialog.dismiss();

                nav_pro_pic.setImageBitmap(bitmap);

            } else {

                pDialog.dismiss();
                Toast.makeText(DashBoardActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
