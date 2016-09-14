package sqindia.net.oonbux.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.Activity.Dashboard;
import sqindia.net.oonbux.Chat.Pal_Chat;
import sqindia.net.oonbux.Dialog.Dialog_Anim_Loading;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.HttpUtils;


public class Adapter_PalLists extends BaseAdapter {
    Context context;
    HashMap<String, String> data = new HashMap<>();
    int count;
    TextView tv_name, tv_oonbuxid, tv_mail, tv_phone, tv_name_txt, tv_oonbuxid_txt, tv_mail_txt, tv_phone_txt, tv_req;
    ImageView img_view;
    ArrayList<HashMap<String, String>> datas = new ArrayList<>();
    HashMap<String, String> result = new HashMap<String, String>();
    String str_oonbux_id, str_sessionid, str_status, str_name;
    LinearLayout lt_submit;
    FrameLayout lt_request, lt_sub;
    Button btn_accept, btn_reject;
    int sts;
    Activity activity;
    Dialog loading_dialog,dialog;
    Typeface tf;

    Dialog_Anim_Loading dialog_loading;

    public Adapter_PalLists(Activity act, Context cont, ArrayList<HashMap<String, String>> dat, int k, int s) {

        this.datas.clear();
        this.context = cont;
        this.datas = dat;
        this.count = k;
        this.sts = s;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflat = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflat.inflate(R.layout.pal_list_adapter, null);

        tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        str_sessionid = sharedPreferences.getString("sessionid", "");


        result = datas.get(position);

        lt_request = (FrameLayout) convertView.findViewById(R.id.layout_request);
        lt_submit = (LinearLayout) convertView.findViewById(R.id.layout_submit);
        btn_accept = (Button) convertView.findViewById(R.id.button_accept);
        btn_reject = (Button) convertView.findViewById(R.id.button_reject);

        //  lt_sub = (FrameLayout) convertView.findViewById(R.id.layout_sub);


        tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_name_txt = (TextView) convertView.findViewById(R.id.tv_name_txt);
        tv_oonbuxid = (TextView) convertView.findViewById(R.id.tv_oonbuxid);
        tv_oonbuxid_txt = (TextView) convertView.findViewById(R.id.tv_oonbuxid_txt);
        tv_mail = (TextView) convertView.findViewById(R.id.tv_mail);
        tv_mail_txt = (TextView) convertView.findViewById(R.id.tv_mail_txt);
        tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
        tv_phone_txt = (TextView) convertView.findViewById(R.id.tv_phone_txt);
        img_view = (ImageView) convertView.findViewById(R.id.imgview);

        tv_req = (TextView) convertView.findViewById(R.id.tv_request);

        tv_name.setTypeface(tf);
        tv_name_txt.setTypeface(tf);
        tv_oonbuxid.setTypeface(tf);
        tv_oonbuxid_txt.setTypeface(tf);
        tv_mail.setTypeface(tf);
        tv_mail_txt.setTypeface(tf);
        tv_phone.setTypeface(tf);
        tv_phone_txt.setTypeface(tf);
        btn_accept.setTypeface(tf);
        btn_reject.setTypeface(tf);
        tv_req.setTypeface(tf);


        if (sts == 0) {
            lt_request.setVisibility(View.VISIBLE);
            lt_submit.setVisibility(View.GONE);
            tv_req.setText("Chat");


        } else if (sts == 1) {
            lt_request.setVisibility(View.INVISIBLE);
            lt_submit.setVisibility(View.VISIBLE);

        } else if (sts == 2) {
            lt_request.setVisibility(View.INVISIBLE);
            lt_submit.setVisibility(View.GONE);

        } else if (sts == 3) {
            lt_request.setVisibility(View.VISIBLE);
            lt_submit.setVisibility(View.GONE);
        }
        else if (sts == 4) {
            lt_request.setVisibility(View.VISIBLE);
            lt_submit.setVisibility(View.GONE);
            tv_req.setText("Chat");
        }

        if (!(result.get("photourl").isEmpty())) {

            Picasso.with(context)
                    .load(result.get("photourl"))
                    .into(img_view);

        }

        tv_name.setText(result.get("firstname") + " " + result.get("lastname"));
        tv_oonbuxid.setText(result.get("oonbux_id"));
        tv_mail.setText(result.get("email"));
        tv_phone.setText(result.get("loc_phone"));


        lt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (sts == 4 || sts == 0) {
                    result = datas.get(position);
                    String oonbux_id = result.get("oonbux_id");
                    Intent goChat = new Intent(context,Pal_Chat.class);
                    goChat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goChat.putExtra("oonbuxid",oonbux_id);
                    context.startActivity(goChat);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pal_oonbuxid", oonbux_id);
                    editor.commit();



                }
                else {
                    result = datas.get(position);
                    str_name = result.get("firstname") + " " + result.get("lastname");

                 /*   new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Send Pal Request to " + result.get("firstname") + " " + result.get("lastname"))
                            .setConfirmText("Yes!")
                            .setCancelText("No")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    str_oonbux_id = result.get("oonbux_id");
                                    new sendRequestPal().execute();
                                    sDialog.dismiss();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();*/


                    dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_yesno);
                    TextView tv = (TextView) dialog.findViewById(R.id.tv_cont);
                    Button btnYes = (Button) dialog.findViewById(R.id.button_yes);
                    Button btnNo = (Button) dialog.findViewById(R.id.button_no);

                    tv.setTypeface(tf);
                    btnYes.setTypeface(tf);
                    btnNo.setTypeface(tf);

                    tv.setText("Send Pal Request to " + result.get("firstname") + " " + result.get("lastname"));
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("tag","yes");
                            str_oonbux_id = result.get("oonbux_id");
                            new sendRequestPal().execute();
                            dialog.dismiss();
                        }
                    });
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("tag","no");
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();



                }
            }
        });


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_oonbux_id = result.get("oonbux_id");
                str_status = "ACCEPTED";
                new acceptPal().execute();
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_oonbux_id = result.get("oonbux_id");
                str_status = "IGNORE";
                new acceptPal().execute();
            }
        });


        return convertView;
    }


    class sendRequestPal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            dialog_loading = new Dialog_Anim_Loading(activity);
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount = 1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/request";
                Log.e("tag", "" + virtual_url);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("oonbux_id", str_oonbux_id);
                // 4. convert JSONObject to JSON to String
                Log.e("tag", "" + jsonobject.toString());
                json = jsonobject.toString();

                return jsonStr = HttpUtils.makeRequest34(virtual_url, json, str_sessionid);
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
                dialog_loading.dismiss();
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            dialog_loading.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {


                  /*  new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Pal Request Sent to " + str_name)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent goDash = new Intent(context, Dashboard.class);
                                    context.startActivity(goDash);
                                }
                            })
                            .show();*/


                    dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_message);
                    TextView tv = (TextView) dialog.findViewById(R.id.tv_cont);
                    Button btn = (Button) dialog.findViewById(R.id.button_close);
                    btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    tv.setTypeface(tf);
                    btn.setTypeface(tf);

                    tv.setText("Pal Request Sent to " + str_name);
                    btn.setText("OK");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent goDash = new Intent(context, Dashboard.class);
                            context.startActivity(goDash);
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();



                } else {

                    if (msg.contains("Friend request already sent")) {

                        Dialog_Msg dialog_fail = new Dialog_Msg(activity,"Pal Request Already Sent.\nWaiting for Pal to Approve.");
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();

                    }
                    else if (msg.contains("already sent you an request")) {

                        Dialog_Msg dialog_fail = new Dialog_Msg(activity,"Pal already sent you a Request.\nPlease Accept Request.");
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();
                    }
                    else if (msg.contains("Pal is already in your friend list")) {

                        Dialog_Msg dialog_fail = new Dialog_Msg(activity,"Pal already in your Friends List");
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();
                    }
                    else {

                        Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Network Error \nTry Again Later");
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Network Error \nTry Again Later");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }

        }

    }


    class acceptPal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            dialog_loading = new Dialog_Anim_Loading(activity);
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount = 1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/respond";
                Log.e("tag", "" + virtual_url);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("oonbux_id", str_oonbux_id);
                jsonobject.accumulate("Action", str_status);

                // 4. convert JSONObject to JSON to String
                Log.e("tag", "" + jsonobject.toString());
                json = jsonobject.toString();

                return jsonStr = HttpUtils.makeRequest34(virtual_url, json, str_sessionid);
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
                dialog_loading.dismiss();
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            dialog_loading.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {

                    Log.e("tag", "<-----result----->" + "" + status + msg);
                    Intent goDash = new Intent(context, Dashboard.class);
                    context.startActivity(goDash);


                } else {

                    Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Try Again Later");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }

        }

    }


}
