package sqindia.net.oonbux;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/27/2016.
 */
public class Adapter_PalLists extends BaseAdapter {
    Context context;
    HashMap<String, String> data = new HashMap<>();
    int count;
    TextView tv_name, tv_oonbuxid, tv_mail, tv_phone, tv_name_txt, tv_oonbuxid_txt, tv_mail_txt, tv_phone_txt, tv_req;
    ImageView img_view;
    ArrayList<HashMap<String, String>> datas = new ArrayList<>();
    HashMap<String, String> result = new HashMap<String, String>();
    SweetAlertDialog sweetDialog;
    String str_oonbux_id, str_sessionid, str_status, str_name;
    LinearLayout lt_submit;
    FrameLayout lt_request, lt_sub;
    Button btn_accept, btn_reject;
    int sts;

    public Adapter_PalLists(Context cont, ArrayList<HashMap<String, String>> dat, int k, int s) {

        this.datas.clear();

        this.context = cont;
        this.datas = dat;
        this.count = k;
        this.sts = s;
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

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

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
            lt_request.setVisibility(View.INVISIBLE);
            lt_submit.setVisibility(View.GONE);


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


                if(sts == 4){
                    result = datas.get(position);
                    String oonbux_id = result.get("oonbux_id");
                    Intent goChat = new Intent(context,Pal_Chat.class);
                    goChat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goChat.putExtra("oonbuxid",oonbux_id);
                    context.startActivity(goChat);


                }
                else {
                    result = datas.get(position);
                    str_name = result.get("firstname") + " " + result.get("lastname");

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
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
                            .show();

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
            sweetDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
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
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            sweetDialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {


                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Pal Request Sent to " + str_name)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent goDash = new Intent(context, DashBoardActivity.class);
                                    context.startActivity(goDash);
                                }
                            })
                            .show();

                } else {

                    if (msg.contains("Friend request already sent")) {

                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Friend Request Already Sent")
                                .setContentText("Waiting for pal to Approve.")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                    } else if (msg.contains("already sent you an request")) {
                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Pal Requested Already!")
                                .setContentText("please accept the request")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    else if (msg.contains("Pal is already in your friend list")) {
                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("You are friends!")
                                .setContentText("Pal is already in your friend list")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                    }




                    else {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Try Check your Network")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


    class acceptPal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
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
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            sweetDialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {

                    Log.e("tag", "<-----result----->" + "" + status + msg);
                    Intent goDash = new Intent(context, DashBoardActivity.class);
                    context.startActivity(goDash);


                } else {

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


}
