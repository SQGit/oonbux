package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.Dialog.Dialog_Region;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.HttpUtils;


public class RegisterClassNew extends AppCompatActivity {

    final Handler handler = new Handler();
    public String str_country, str_state, str_zip, str_fname, str_lname, str_email, str_phone, str_pass, str_repass;
    Button btn_submit;
    ImageView iv_indicator1, iv_indicator2, iv_indicator3;
    TextView tv_header, tv_sub_header, tv_sub_header1, tv_footer_txt1, tv_footer_txt2, tv_sub_footer;
    Typeface tf, tf1;
    RelativeLayout rl_top;
    LinearLayout ll_login;
    android.widget.TextView tv_snack;
    DbC dbclass;
    Context context = this;
    Cursor cursor;
    Dialog loading_dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Locale locale;
    private ViewPager viewPager;
    private int[] layouts;

    android.widget.TextView txtview;



    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if (position == layouts.length - 1) {
                btn_submit.setVisibility(View.VISIBLE);
            } else {
                btn_submit.setVisibility(View.INVISIBLE);
            }

            if (position == 0) {
                tv_sub_header.setText("Location Details");
                // iv_indicator1.setBackground(getResources().getDrawable(R.drawable.indicator));
                iv_indicator1.setImageDrawable(getResources().getDrawable(R.drawable.indicator));
                iv_indicator2.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));
                iv_indicator3.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));

                if (!(sharedPreferences.getString("reg_country", "").equals(""))) {

                    MaterialEditText et = (MaterialEditText) viewPager.findViewById(R.id.edittext_country);
                    MaterialEditText et1 = (MaterialEditText) viewPager.findViewById(R.id.edittext_state);
                    MaterialEditText et2 = (MaterialEditText) viewPager.findViewById(R.id.edittext_zip);
                    et.setText(sharedPreferences.getString("reg_country", ""));
                    et1.setText(sharedPreferences.getString("reg_state", ""));
                    et2.setText(sharedPreferences.getString("reg_zip", ""));

                }


            } else if (position == 1) {
                tv_sub_header.setText("User Details");
                iv_indicator2.setImageDrawable(getResources().getDrawable(R.drawable.indicator));
                iv_indicator1.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));
                iv_indicator3.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));
            } else {


                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                tv_sub_header.setText("Security Details");
                iv_indicator3.setImageDrawable(getResources().getDrawable(R.drawable.indicator));
                iv_indicator2.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));
                iv_indicator1.setImageDrawable(getResources().getDrawable(R.drawable.indicator_hover));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private MyViewPagerAdapter myViewPagerAdapter;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newregisterclass);

        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        // hideSoftKeyboard(RegisterClassNew.this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btn_submit = (Button) findViewById(R.id.button_submit);

        tv_header = (TextView) findViewById(R.id.textview_header_reg);
        tv_sub_header = (TextView) findViewById(R.id.textview_header);
        tv_sub_header1 = (TextView) findViewById(R.id.textview_sub_header);
        tv_footer_txt1 = (TextView) findViewById(R.id.textview_footer_txt1);
        tv_footer_txt2 = (TextView) findViewById(R.id.textview_footer_txt2);
        tv_sub_footer = (TextView) findViewById(R.id.textview_sub_footer);

        iv_indicator1 = (ImageView) findViewById(R.id.img_indicator1);
        iv_indicator2 = (ImageView) findViewById(R.id.img_indicator2);
        iv_indicator3 = (ImageView) findViewById(R.id.img_indicator3);

        rl_top = (RelativeLayout) findViewById(R.id.top);
        ll_login = (LinearLayout) findViewById(R.id.linear_login_text);

        tv_header.setTypeface(tf1);
        btn_submit.setTypeface(tf);
        tv_sub_header.setTypeface(tf);
        tv_sub_header1.setTypeface(tf);
        tv_footer_txt1.setTypeface(tf);
        tv_footer_txt2.setTypeface(tf);
        tv_sub_footer.setTypeface(tf);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterClassNew.this);
        editor = sharedPreferences.edit();

        btn_submit.setVisibility(View.INVISIBLE);



     /*   locale = new Locale("pt");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);*/


        // setLocale("pt");


        layouts = new int[]{
                R.layout.register_slide1,
                R.layout.register_slide2,
                R.layout.register_slide3,};

        dbclass = new DbC(context);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setupUI(findViewById(R.id.top));


        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), NewLoginActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(login_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_country = sharedPreferences.getString("reg_country", "");
                str_state = sharedPreferences.getString("reg_state", "");
                str_zip = sharedPreferences.getString("reg_zip", "");
                str_fname = sharedPreferences.getString("reg_fname", "");
                str_lname = sharedPreferences.getString("reg_lname", "");
                str_email = sharedPreferences.getString("reg_email", "");
                str_phone = sharedPreferences.getString("reg_phone", "");
                str_pass = sharedPreferences.getString("reg_pass", "");
                str_repass = sharedPreferences.getString("reg_repass", "");


                Log.e("tag", "as" + str_country);


                if (!(str_country.equals(""))) {
                    if (!(str_email.equals(""))) {

                        MaterialEditText et_phone = (MaterialEditText) viewPager.findViewById(R.id.edittext_phone);
                        MaterialEditText et_pass = (MaterialEditText) viewPager.findViewById(R.id.edittext_pass);
                        MaterialEditText et_repass = (MaterialEditText) viewPager.findViewById(R.id.edittext_repass);

                        if (!(et_phone.getText().toString().equals(""))) {
                            if (et_phone.getText().toString().length() == 10) {
                                if (!(et_pass.getText().toString().equals(""))) {
                                    if (!(et_repass.getText().toString().equals(""))) {
                                        if ((et_pass.getText().toString().equals(et_repass.getText().toString()))) {

                                            str_phone = sharedPreferences.getString("reg_phone", "");
                                            str_pass = sharedPreferences.getString("reg_pass", "");

                                            new RegisterTask().execute();
                                        } else {
                                            et_repass.setError("Pasword Not Match");
                                            et_repass.requestFocus();
                                        }
                                    } else {
                                        et_repass.setError("ReEnter Password");
                                        et_repass.requestFocus();
                                    }
                                } else {
                                    et_pass.setError("Enter Password");
                                    et_pass.requestFocus();
                                }
                            } else {
                                et_phone.setError("Enter Valid Phone Number");
                                et_phone.requestFocus();
                            }
                        } else {
                            et_phone.setError("Enter Phone Number");
                            et_phone.requestFocus();
                        }


                    } else {
                        viewPager.setCurrentItem(1);
                    }
                } else {
                    viewPager.setCurrentItem(0);
                }


            }
        });

    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegisterClassNew.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tag", "stop");
        //loading_dialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("tag", "restart");
    }

    public void retrive_region(Cursor cursor) {
        Log.e("tag", "get_data");
        Cursor c = null;
        c = cursor;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    str_state = c.getString(c.getColumnIndex("state"));
                    str_country = c.getString(c.getColumnIndex("country"));
                    Log.e("tag", "data" + str_state + str_country);
                } while (c.moveToNext());
            } else {
                Log.e("tag", "data_null" + str_state + str_country);
                str_state = null;
                str_country = null;
            }
        }
    }

    public void setLocale(String lang) {

        locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, NewLoginActivity.class);
        startActivity(refresh);
        finish();
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        MaterialEditText et_zip, et_state, et_country, et_fname, et_lname, et_email, et_phone, et_pass, et_repass;
        TextView tv_manual;
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            if (position == 0) {

                et_zip = (MaterialEditText) view.findViewById(R.id.edittext_zip);
                et_state = (MaterialEditText) view.findViewById(R.id.edittext_state);
                et_country = (MaterialEditText) view.findViewById(R.id.edittext_country);
                tv_manual = (TextView) view.findViewById(R.id.textview_manual);

                et_zip.setTypeface(tf);
                et_state.setTypeface(tf);
                et_country.setTypeface(tf);
                tv_manual.setTypeface(tf);

                et_state.setEnabled(false);
                et_country.setEnabled(false);

                tv_manual.setVisibility(View.GONE);

                tv_manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("tag", "click");

                        // hideSoftKeyboard(RegisterClassNew.this);
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.RESULT_HIDDEN);

                        Dialog_Region dialog_region = new Dialog_Region(RegisterClassNew.this);
                        dialog_region.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.aaa)));
                        dialog_region.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        //dialog_region.getWindow().setStatusBarColor(getResources().getColor(R.color.aaa));
                        dialog_region.show();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                        dialog_region.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                                InputMethodManager inputManager = (InputMethodManager)
                                        getSystemService(Context.INPUT_METHOD_SERVICE);

                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                        InputMethodManager.RESULT_HIDDEN);
                                //hideSoftKeyboard(RegisterClassNew.this);

                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                                Log.e("tag", "zz0 " + str_zip + str_country + str_state);

                                if (!(sharedPreferences.getString("d_zip", "").equals(""))) {

                                    str_country = sharedPreferences.getString("d_country", "");
                                    str_state = sharedPreferences.getString("d_state", "");
                                    str_zip = sharedPreferences.getString("d_zip", "");

                                    et_zip.setText(str_zip);
                                    et_state.setText(str_state);
                                    et_country.setText(str_country);

                                    tv_manual.requestFocus();
                                    et_zip.requestFocus();
                                    tv_manual.setVisibility(View.INVISIBLE);
                                    Drawable img = getResources().getDrawable(R.drawable.zip_ico);
                                    et_zip.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            int current = 0;
                                            current = position + 1;

                                            if (current < layouts.length) {

                                                editor.putString("reg_zip", et_zip.getText().toString());
                                                editor.putString("reg_state", et_state.getText().toString());
                                                editor.putString("reg_country", et_country.getText().toString());
                                                editor.commit();

                                                viewPager.setCurrentItem(current);

                                            } else {
                                                // launchHomeScreen();
                                            }
                                        }
                                    }, 1500);

                                } else {
                                    et_zip.requestFocus();
                                }


                            }
                        });


                    }
                });

                et_zip.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_DONE) {


                            if (et_zip.getText().toString().trim().length() < 2) {

                                /*Snackbar snackbar = Snackbar.make(rl_top, "Enter Valid Zip", Snackbar.LENGTH_LONG);
                                View sbView = snackbar.getView();
                                sbView.setBackgroundColor(getResources().getColor(R.color.text_color));
                               // snackbar.setActionTextColor(getResources().getColor(R.color.text_Red));

                               tv_snack= (android.widget.TextView) (sbView.findViewById(android.support.design.R.id.snackbar_text));
                                tv_snack.setTypeface(tf);
                                tv_snack.setTextColor(getResources().getColor(R.color.text_Red));

                                snackbar.show();*/

                                if (et_zip.getText().toString().trim().length() == 0) {
                                    et_zip.setError("Zip values Should Not be Empty");
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    tv_manual.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getApplicationContext(),"Click \"FindZipCode\" to get Location Details.",Toast.LENGTH_LONG).show();


                                    Toast toast = Toast.makeText(context, "Click \"FindZipCode\" to get Location Details.", Toast.LENGTH_LONG);
                                    View view = toast.getView();
                                 //   view.setBackgroundColor(getResources().getColor(R.color.abg_color));
                                    txtview = (android.widget.TextView) view.findViewById(android.R.id.message);
                                    txtview.setTypeface(tf);
                                    txtview.setTextColor(getResources().getColor(R.color.text_color));
                                    toast.show();


                                    et_zip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                                } else {
                                    et_zip.setError("Enter Valid Zip Code");
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    tv_manual.setVisibility(View.VISIBLE);
                                    // Toast.makeText(getApplicationContext(),"Click \"FindZipCode\" to get Location Details.",Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(context, "Click \"FindZipCode\" to get Location Details.", Toast.LENGTH_LONG);
                                    View view = toast.getView();
                                  //  view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                                  //  view.setBackgroundResource(android.R.color.transparent);
                                    txtview = (android.widget.TextView) view.findViewById(android.R.id.message);
                                    txtview.setTypeface(tf);
                                    txtview.setTextColor(getResources().getColor(R.color.text_color));
                                    toast.show();


                                    et_zip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                                }


                            } else {
                                // et_state.setEnabled(true);
                                // et_country.setEnabled(true);
                                cursor = null;
                                cursor = dbclass.region_get(et_zip.getText().toString().trim());

                                Log.e("tag", "" + cursor);

                                retrive_region(cursor);

                                Log.e("tag", "" + str_state + str_country);

                                //  et_state.setText("New York");
                                //  et_country.setText("US");

                                if (str_state == null) {
                                    et_zip.setText("");
                                    et_state.setText("");
                                    et_country.setText("");
                                   // Toast.makeText(getApplicationContext(), "Click \"FindZipCode\" to get Location Details.", Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(context, "Click \"FindZipCode\" to get Location Details.", Toast.LENGTH_LONG);
                                    View view = toast.getView();
                                    txtview = (android.widget.TextView) view.findViewById(android.R.id.message);
                                    txtview.setTypeface(tf);
                                    txtview.setTextColor(getResources().getColor(R.color.text_color));
                                    toast.show();


                                    tv_manual.setVisibility(View.VISIBLE);
                                    et_zip.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                                } else {

                                    tv_manual.setVisibility(View.INVISIBLE);
                                    Drawable img = getResources().getDrawable(R.drawable.zip_ico);
                                    et_zip.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                                    et_state.setText(str_state);
                                    et_country.setText(str_country);
                                    et_zip.requestFocus();

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            int current = 0;
                                            current = position + 1;
                                            // Do something after 5s = 5000ms
                                            if (current < layouts.length) {

                                                editor.putString("reg_zip", et_zip.getText().toString());
                                                editor.putString("reg_state", et_state.getText().toString());
                                                editor.putString("reg_country", et_country.getText().toString());
                                                editor.commit();

                                                viewPager.setCurrentItem(current);

                                            } else {
                                                // launchHomeScreen();
                                            }
                                        }
                                    }, 450);
                                }


                            }
                        }


                        return false;
                    }
                });

            } else if (position == 1) {

                et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
                et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
                et_email = (MaterialEditText) findViewById(R.id.edittext_email);

                et_fname.setTypeface(tf);
                et_lname.setTypeface(tf);
                et_email.setTypeface(tf);


                et_email.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {


                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                            if (!(et_fname.getText().toString().trim().isEmpty())) {
                                if (!(et_lname.getText().toString().trim().isEmpty())) {
                                    if (!(et_email.getText().toString().trim().isEmpty())) {
                                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {


                                            InputMethodManager inputManager = (InputMethodManager)
                                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                                    InputMethodManager.RESULT_HIDDEN);
                                            //hideSoftKeyboard(RegisterClassNew.this);

                                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int current = 0;
                                                    current = position + 1;

                                                    if (current < layouts.length) {


                                                        editor.putString("reg_fname", et_fname.getText().toString());
                                                        editor.putString("reg_lname", et_lname.getText().toString());
                                                        editor.putString("reg_email", et_email.getText().toString());
                                                        editor.commit();


                                                        viewPager.setCurrentItem(current);
                                                    } else {
                                                        // launchHomeScreen();
                                                    }
                                                }
                                            }, 450);


                                        } else {
                                            et_email.setError("Enter Valid Email");
                                            et_email.requestFocus();
                                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        }
                                    } else {
                                        et_email.setError("Email Should not be Empty");
                                        et_email.requestFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    }
                                } else {
                                    et_lname.setError("Enter Last Name");
                                    et_lname.requestFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                }
                            } else {
                                et_fname.setError("Enter First Name");
                                et_fname.requestFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            }
                        }
                        return true;
                    }
                });


            } else {

                et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
                et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);
                et_repass = (MaterialEditText) findViewById(R.id.edittext_repass);

                et_phone.setTypeface(tf);
                et_pass.setTypeface(tf);
                et_repass.setTypeface(tf);

                //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                et_repass.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {


                        if (actionId == EditorInfo.IME_ACTION_DONE) {

                            if (!(et_phone.getText().toString().trim().isEmpty())) {
                                if (!(et_phone.getText().toString().trim().length() < 10)) {
                                    if (!(et_pass.getText().toString().trim().isEmpty())) {
                                        if (!(et_repass.getText().toString().trim().isEmpty())) {
                                            if ((et_pass.getText().toString().equals(et_repass.getText().toString()))) {

                                                btn_submit.requestFocus();
                                                InputMethodManager inputManager = (InputMethodManager)
                                                        getSystemService(Context.INPUT_METHOD_SERVICE);

                                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                                        InputMethodManager.RESULT_HIDDEN);

                                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


                                                editor.putString("reg_phone", et_phone.getText().toString());
                                                editor.putString("reg_pass", et_pass.getText().toString());
                                                editor.putString("reg_repass", et_repass.getText().toString());
                                                editor.commit();

                                            } else {
                                                et_repass.setError("PasswordS Not Match");
                                                et_repass.requestFocus();
                                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                            }
                                        } else {
                                            et_repass.setError("Re Enter Password");
                                            et_repass.requestFocus();
                                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                        }
                                    } else {
                                        et_pass.setError("Enter Password");
                                        et_pass.requestFocus();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    }
                                } else {
                                    et_phone.setError("Enter Valid Phone number");
                                    et_phone.requestFocus();
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                }
                            } else {
                                et_phone.setError("Enter Phone Number");
                                et_phone.requestFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            }
                        }
                        return false;
                    }
                });


            }


            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    class RegisterTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            loading_dialog = new Dialog(RegisterClassNew.this);
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.setContentView(R.layout.dialog_loading);
            loading_dialog.setCancelable(false);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("firstname", str_fname);
                jsonObject.accumulate("lastname", str_lname);
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("phone", str_phone);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("state", str_state);
                jsonObject.accumulate("country", str_country);
                jsonObject.accumulate("zip", str_zip);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.SER_URL + "registration", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            loading_dialog.dismiss();
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("success")) {


                        String sus_txt = "Thank you for Signing Up Oonbux.\nAn Activation Email Sent to " + str_email + "\nPlease check your mail for Activation details.";
                        Dialog_new dialog_reg_success = new Dialog_new(RegisterClassNew.this, sus_txt, 0);
                        dialog_reg_success.setCancelable(false);
                        dialog_reg_success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_reg_success.show();


                        editor.putString("register", "SUCCESS");
                        editor.commit();

                    } else if (status.equals("fail")) {

                        Dialog_Msg dialog_reg_fail = new Dialog_Msg(RegisterClassNew.this, msg);
                        dialog_reg_fail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog_reg_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_reg_fail.setCancelable(false);
                        dialog_reg_fail.show();

                        if (msg.contains("Email")) {

                            viewPager.setCurrentItem(1);

                        } else if (msg.contains("Region")) {

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Dialog_Msg dialog_fail = new Dialog_Msg(RegisterClassNew.this, "Network Error,Please Try Again Later.");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }
            } else {
                Dialog_Msg dialog_fail = new Dialog_Msg(RegisterClassNew.this, "Network Error,Please Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }
    }


}
