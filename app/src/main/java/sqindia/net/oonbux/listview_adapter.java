package sqindia.net.oonbux;

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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/21/2016.
 */
public class listview_adapter extends BaseExpandableListAdapter {


    Typeface tf;
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    SweetAlertDialog sweetDialog;
    public String str_session_id;
    public String str_addr_id,str_addr_line1,str_addr_line2,str_addr_city,str_addr_state,str_addr_country,str_addr_zip,str_addr_note,str_addr_phone,str_addr_type;
    Activity activity;
    ArrayList<String> daa = new ArrayList<>();

    public listview_adapter(Activity act, Context context, List<String> expandableListTitle,
                            HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;

        this.activity = act;


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        str_session_id = sharedPreferences.getString("sessionid", "");
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

        tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listitem, null);
        }
        TextView expandedListTextView = (TextView) convertView
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


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
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
        ImageButton bbo = (ImageButton) convertView.findViewById(R.id.open);
        ImageView bbo1 = (ImageView) convertView.findViewById(R.id.edit);
        ImageView bbo2 = (ImageView) convertView.findViewById(R.id.delete);



        bbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpanded) ((ExpandableListView) parent).collapseGroup(listPosition);
                else ((ExpandableListView) parent).expandGroup(listPosition, true);

            }
        });


        bbo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_addr_id = expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);
                str_addr_line1 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(1);
                str_addr_line2 = expandableListDetail.get(expandableListTitle.get(listPosition)).get(2);
                str_addr_city = expandableListDetail.get(expandableListTitle.get(listPosition)).get(3);
                str_addr_state = expandableListDetail.get(expandableListTitle.get(listPosition)).get(4);
                str_addr_country = expandableListDetail.get(expandableListTitle.get(listPosition)).get(5);
                str_addr_zip= expandableListDetail.get(expandableListTitle.get(listPosition)).get(6);
                str_addr_note= expandableListDetail.get(expandableListTitle.get(listPosition)).get(7);
                str_addr_phone= expandableListDetail.get(expandableListTitle.get(listPosition)).get(8);
                str_addr_type= expandableListDetail.get(expandableListTitle.get(listPosition)).get(9);

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

                Dialog_Add_Address cdd = new Dialog_Add_Address(activity,1,daa);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        bbo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_addr_id = expandableListDetail.get(expandableListTitle.get(listPosition)).get(0);

                Log.e("tag",""+str_addr_id+" ---"+ str_session_id);


                new deleteAddress().execute();

            }
        });


        listTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "worked");


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
            Log.e("tag",str_addr_id);
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

                Intent itsad = new Intent(context,ManageAddress.class);
                itsad.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(itsad);


            }
            else {
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

}