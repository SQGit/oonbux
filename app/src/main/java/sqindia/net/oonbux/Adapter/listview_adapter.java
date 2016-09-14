package sqindia.net.oonbux.Adapter;

import android.app.Activity;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sqindia.net.oonbux.Activity.ManageAddress;
import sqindia.net.oonbux.Dialog.Dialog_Add_Address;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.HttpUtils;

/**
 * Created by Salman on 6/21/2016.
 */
public class listview_adapter extends BaseExpandableListAdapter {


    public String str_session_id;
    public String str_addr_id, str_addr_line1, str_addr_line2, str_addr_city, str_addr_state, str_addr_country, str_addr_zip, str_addr_note, str_addr_phone, str_addr_type;
    Typeface tf;
    SweetAlertDialog sweetDialog;
    Activity activity;
    ArrayList<String> daa = new ArrayList<>();
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private HashMap<String, List<String>> expandable_ListDetails;

    public listview_adapter(Activity act, Context context, List<String> expandableListTitle,
                            HashMap<String, List<String>> expandableListDetail, HashMap<String, List<String>> exp_datas1) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.expandable_ListDetails = exp_datas1;
        this.activity = act;


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        str_session_id = sharedPreferences.getString("sessionid", "");
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        str_addr_id = this.expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);
        //Log.e("tag",""+ str_addr_id);
        str_addr_line1 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(1);
        str_addr_line2 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(2);
        str_addr_city = expandableListDetail.get(expandableListTitle.get(listPosition)).get(3);
        str_addr_state = expandableListDetail.get(expandableListTitle.get(listPosition)).get(4);
        str_addr_country = expandableListDetail.get(expandableListTitle.get(listPosition)).get(5);
        str_addr_zip = expandableListDetail.get(expandableListTitle.get(listPosition)).get(6);
        str_addr_note = expandableListDetail.get(expandableListTitle.get(listPosition)).get(7);
        str_addr_phone = expandableListDetail.get(expandableListTitle.get(listPosition)).get(8);
        str_addr_type = expandableListDetail.get(expandableListTitle.get(listPosition)).get(9);


        // Log.e("tag",""+this.expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition));


        return this.expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition);

    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        String expandedListText = (String) getChild(listPosition, expandedListPosition);

        Spinner spin_country, spin_state, spin_zip;
        MaterialEditText et_line1, et_line2, et_phone, et_note, et_city, et_country, et_state, et_zip;


        tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listitem, null);
        }

        //  spin_country = (Spinner) convertView.findViewById(R.id.spinner_country);
        //spin_state = (Spinner) convertView.findViewById(R.id.spinner_state);
        // spin_zip = (Spinner) convertView.findViewById(R.id.spinner_zip);

        et_line1 = (MaterialEditText) convertView.findViewById(R.id.edittext_address1);
        et_line2 = (MaterialEditText) convertView.findViewById(R.id.edittext_address2);
        et_country = (MaterialEditText) convertView.findViewById(R.id.edittext_country);
        et_state = (MaterialEditText) convertView.findViewById(R.id.edittext_state);
        et_zip = (MaterialEditText) convertView.findViewById(R.id.edittext_zip);
        et_phone = (MaterialEditText) convertView.findViewById(R.id.edittext_phone);
        et_city = (MaterialEditText) convertView.findViewById(R.id.edittext_city);
        et_note = (MaterialEditText) convertView.findViewById(R.id.edittext_note);

        et_line1.setTypeface(tf);
        et_line2.setTypeface(tf);
        et_country.setTypeface(tf);
        et_state.setTypeface(tf);
        et_zip.setTypeface(tf);
        et_city.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_note.setTypeface(tf);


        str_addr_id = expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);

        str_addr_line1 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(1);
        str_addr_line2 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(2);
        str_addr_city = expandableListDetail.get(expandableListTitle.get(listPosition)).get(3);
        str_addr_state = expandableListDetail.get(expandableListTitle.get(listPosition)).get(4);
        str_addr_country = expandableListDetail.get(expandableListTitle.get(listPosition)).get(5);
        str_addr_zip = expandableListDetail.get(expandableListTitle.get(listPosition)).get(6);
        str_addr_note = expandableListDetail.get(expandableListTitle.get(listPosition)).get(7);
        str_addr_phone = expandableListDetail.get(expandableListTitle.get(listPosition)).get(8);
        str_addr_type = expandableListDetail.get(expandableListTitle.get(listPosition)).get(9);

        Log.e("tag", "0" + str_addr_id + "\t" + str_addr_line1 + "\t" + str_addr_line2 + "\t" + str_addr_city + "\t" + str_addr_state);

        et_line1.setText(str_addr_line1);
        et_line2.setText(str_addr_line2);
        et_country.setText(str_addr_country);
        et_state.setText(str_addr_state);
        et_zip.setText(str_addr_zip);
        et_city.setText(str_addr_city);
        et_phone.setText(str_addr_phone);
        et_note.setText(str_addr_note);





       /* ArrayList<String> lisss = new ArrayList<>();

        lisss.add("asdf");
        lisss.add("wer");
        lisss.add("bds");
        lisss.add("zxv");

        ListAdapter_Class adapter_int_cont = new ListAdapter_Class(context, R.layout.dropdown_lists1, lisss);
        spin_country.setAdapter(adapter_int_cont);

        ListAdapter_Class adapter_int_stat = new ListAdapter_Class(context, R.layout.dropdown_lists1, lisss);
        spin_state.setAdapter(adapter_int_stat);

        ListAdapter_Class adapter_int_zip = new ListAdapter_Class(context, R.layout.dropdown_lists1, lisss);
        spin_zip.setAdapter(adapter_int_zip);*/


        if (expandedListPosition == 0) {
            expandedListText = "Address Id : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 1) {
            expandedListText = "Address Line 1 : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 2) {
            expandedListText = "Address Line 2 : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 3) {
            expandedListText = "Country : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 4) {
            expandedListText = "State : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 5) {
            expandedListText = "City : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 6) {
            expandedListText = "Zip : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 7) {
            expandedListText = "Note : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 8) {
            expandedListText = "Phone : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }
        if (expandedListPosition == 9) {
            expandedListText = "Address Type : " + expandedListText;
            Log.e("tag", "" + expandedListText);
        }


        //  Log.e("tag",""+listPosition+expandedListPosition);

        // String asdf = expandable_ListDetails.get(0).get(0);






        //String asdf = str_addr_id +"\n"+ str_addr_line1 +"\n"+ str_addr_line2 +"\n"+ str_addr_city +"\n"+ str_addr_state +"\n"+ str_addr_country +"\n"+ str_addr_zip +"\n"+ str_addr_phone +"\n"+ str_addr_note +"\n"+ str_addr_type ;



   /*     TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.list_item1);

        expandedListTextView.setTypeface(tf);
        expandedListTextView.setText(expandedListText);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("tag", "" + expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition));
                // Log.d("tag","isnss"+listPosition+expandableListTitle.get(listPosition) +expandedListPosition);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.e("tag", "" + expandableListDetail.get(expandableListTitle.get(listPosition)).get(0));
                return false;
            }
        });
*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        //return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(final int listPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {

        tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listgroup, null);
        }


        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.list_group);
        listTitleTextView.setTypeface(tf);
        listTitleTextView.setText(listTitle);
        final ImageView bbo = (ImageView) convertView.findViewById(R.id.open);
        ImageView bbo1 = (ImageView) convertView.findViewById(R.id.edit);
        ImageView bbo2 = (ImageView) convertView.findViewById(R.id.delete);

        LinearLayout btn_expand = (LinearLayout) convertView.findViewById(R.id.btn_expand);


        bbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) {
                    bbo.setRotation(0);
                    ((ExpandableListView) parent).collapseGroup(listPosition);
                } else {
                    bbo.setRotation(90);
                    ((ExpandableListView) parent).expandGroup(listPosition, true);
                }


            }
        });


        bbo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_addr_id = expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);
                Log.e("tag", "" + str_addr_id);
                str_addr_line1 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(1);
                str_addr_line2 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(2);
                str_addr_city = expandableListDetail.get(expandableListTitle.get(listPosition)).get(3);
                str_addr_state = expandableListDetail.get(expandableListTitle.get(listPosition)).get(4);
                str_addr_country = expandableListDetail.get(expandableListTitle.get(listPosition)).get(5);
                str_addr_zip = expandableListDetail.get(expandableListTitle.get(listPosition)).get(6);
                str_addr_note = expandableListDetail.get(expandableListTitle.get(listPosition)).get(7);
                str_addr_phone = expandableListDetail.get(expandableListTitle.get(listPosition)).get(8);
                str_addr_type = expandableListDetail.get(expandableListTitle.get(listPosition)).get(9);

                daa.add(str_addr_id);
                daa.add(str_addr_line1);
                daa.add(str_addr_line2);
                daa.add(str_addr_city);
                daa.add(str_addr_state);
                daa.add(str_addr_country);
                daa.add(str_addr_zip);
                daa.add(str_addr_note);
                daa.add(str_addr_phone);
                daa.add(str_addr_type);

                Dialog_Add_Address cdd = new Dialog_Add_Address(activity, 1, daa);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        bbo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_addr_id = expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);

                Log.e("tag", "" + str_addr_id + " ---" + str_session_id);


                new deleteAddress().execute();

            }
        });


        listTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "worked");


                if (isExpanded) {
                    bbo.setRotation(0);
                    ((ExpandableListView) parent).collapseGroup(listPosition);
                } else {
                    bbo.setRotation(90);
                    ((ExpandableListView) parent).expandGroup(listPosition, true);
                }

            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    class deleteAddress extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("addr_id", str_addr_id);
                Log.e("tag", str_addr_id);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "addshippingaddr/delete", json, str_session_id);
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


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("success")) {

                    Intent itsad = new Intent(context, ManageAddress.class);
                    itsad.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(itsad);


                } else {
                    Toast.makeText(context, "Network Error,Please Try Again Later", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "Network Error,Please Try Again Later", Toast.LENGTH_SHORT).show();
            }

        }

    }

}