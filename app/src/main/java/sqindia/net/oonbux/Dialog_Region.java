package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.rey.material.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Salman on 7/28/2016.
 */

public class Dialog_Region extends Dialog {

    public Activity activity;
    TextView tv_header;
    ImageView img_back;
    Typeface tf;
    ArrayList<String> country_lists = new ArrayList<>();
    ArrayList<String> state_lists = new ArrayList<>();
    ArrayList<String> zip_lists = new ArrayList<>();
    ListAdapter adapter1, adapter2, adapter3;

    DbC dbclass;
    Context context;
    Cursor cursor;
    String query, str_country, str_state, str_zip;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RegisterClassNew regi;


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                tv_header.setText("Country");
                img_back.setVisibility(View.GONE);
                country_lists.clear();
                query = "Select DISTINCT country from region;";
                cursor = dbclass.region_db(query);
                retrive_region_country(cursor);
                adapter1.notifyDataSetChanged();

            } else if (position == 1) {
                tv_header.setText("State");
                img_back.setVisibility(View.GONE);
                state_lists.clear();
                query = "Select DISTINCT state from region where country =\"" + str_country + "\";";
                cursor = dbclass.region_db(query);
                retrive_region_state(cursor);
                adapter2.notifyDataSetChanged();
            } else {
                tv_header.setText("Zip");
                img_back.setVisibility(View.GONE);
                zip_lists.clear();
                query = "Select DISTINCT zip from region where country =\"" + str_country + "\" and state =\"" + str_state + "\";";
                cursor = dbclass.region_db(query);
                retrive_region_zip(cursor);
                adapter3.notifyDataSetChanged();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private ViewPager viewPager;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;


    public Dialog_Region(Activity activity) {
        super(activity);
        this.activity = activity;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_region);

        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/prox.otf");
        tv_header = (TextView) findViewById(R.id.textview_header);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        img_back = (ImageView) findViewById(R.id.img_back);

        tv_header.setTypeface(tf);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = sharedPreferences.edit();
        editor.putString("d_country", "");
        editor.putString("d_state", "");
        editor.putString("d_zip", "");
        editor.commit();


        layouts = new int[]{
                R.layout.register_country,
                R.layout.register_state,
                R.layout.register_zip,};

        dbclass = new DbC(activity);
        regi = new RegisterClassNew();

        img_back.setVisibility(View.GONE);

        country_lists.clear();
        query = "Select DISTINCT country from region;";
        cursor = dbclass.region_db(query);
        retrive_region_country(cursor);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // viewPager.beginFakeDrag();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = 0;
                current = viewPager.getCurrentItem() - 1;
                // Do something after 5s = 5000ms
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);

                } else {
                    // launchHomeScreen();
                }

            }
        });


    }

    public void retrive_region(Cursor cursor, int column) {
        Log.e("tag", "get_data");
        Cursor c = null;
        c = cursor;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if (column == 0) {

                        String str_country = c.getString(c.getColumnIndex("country"));
                        country_lists.add(str_country);
                    } else if (column == 1) {
                        String str_state = c.getString(c.getColumnIndex("state"));
                        state_lists.add(str_state);
                    } else {
                        String str_zip = c.getString(c.getColumnIndex("zip"));
                        zip_lists.add(str_zip);
                    }

                    Log.e("tag", "000 ");
                } while (c.moveToNext());
            } else {
                Log.e("tag", "data_null");

            }
        }
    }

    public void retrive_region_country(Cursor cursor) {
        Log.e("tag", "get_data");
        Cursor c = null;
        c = cursor;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Log.e("tag", "country");
                    String country = c.getString(c.getColumnIndex("country"));
                    country_lists.add(country);
                } while (c.moveToNext());
            } else {
                Log.e("tag", "data_null");

            }
        }
    }

    public void retrive_region_state(Cursor cursor) {
        Log.e("tag", "get_data1");
        Cursor c = null;
        c = cursor;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Log.e("tag", "state");
                    String state = c.getString(c.getColumnIndex("state"));
                    Log.e("tag", state);
                    state_lists.add(state);
                } while (c.moveToNext());
            } else {
                Log.e("tag", "data_null1");

            }
        } else {
            Log.e("tag", "data_null0");

        }
    }

    public void retrive_region_zip(Cursor cursor) {
        Log.e("tag", "get_data");
        Cursor c = null;
        c = cursor;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Log.e("tag", "zip");
                    String zip = c.getString(c.getColumnIndex("zip"));
                    zip_lists.add(zip);
                } while (c.moveToNext());
            } else {
                Log.e("tag", "data_null");

            }
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        ListView lview_cont, lview_state, lview_zip;
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            if (position == 0) {

                lview_cont = (ListView) view.findViewById(R.id.lview);
                adapter1 = new ListAdapter(activity.getApplicationContext(), R.layout.dialog_region_txts, country_lists);
                lview_cont.setAdapter(adapter1);


            } else if (position == 1) {

                state_lists.clear();
                query = "Select DISTINCT state from region where country =\"" + str_country + "\";";
                cursor = dbclass.region_db(query);
                retrive_region_state(cursor);

                lview_state = (ListView) view.findViewById(R.id.lview);
                adapter2 = new ListAdapter(activity.getApplicationContext(), R.layout.dialog_region_txts, state_lists);
                lview_state.setAdapter(adapter2);


            } else {

                lview_zip = (ListView) view.findViewById(R.id.lview);
                adapter3 = new ListAdapter(activity.getApplicationContext(), R.layout.dialog_region_txts, zip_lists);
                lview_zip.setAdapter(adapter3);

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

    public class ListAdapter extends ArrayAdapter<String> {

        Context cc;
        ArrayList<String> data_lists;
        int resourceid;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.cc = context;
            this.data_lists = objects;
            this.resourceid = textViewResourceId;
        }

        @Override
        public View getDropDownView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }

        @Override
        public View getView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }


        public View getCustomView(final int posi, View row, ViewGroup parent) {

            Typeface tf = Typeface.createFromAsset(cc.getAssets(), "fonts/prox.otf");

            LayoutInflater inflater = (LayoutInflater) cc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View arow = inflater.inflate(resourceid, parent, false);

            TextView label = (TextView) arow.findViewById(R.id.textview_header);

            label.setTypeface(tf);

            label.setText(data_lists.get(posi));

            arow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (viewPager.getCurrentItem() == 0) {

                        regi.str_country = data_lists.get(posi);
                        str_country = data_lists.get(posi);


                        editor.putString("d_country", str_country);
                        editor.commit();

                        Log.e("tag", "cc " + str_country);
                        int current = 0;
                        current = viewPager.getCurrentItem() + 1;
                        if (current < layouts.length) {
                            viewPager.setCurrentItem(current);
                        } else {
                        }
                    } else if (viewPager.getCurrentItem() == 1) {

                        str_state = data_lists.get(posi);
                        regi.str_state = data_lists.get(posi);

                        editor.putString("d_state", str_state);
                        editor.commit();

                        Log.e("tag", "ss " + str_state);
                        int current = 0;
                        current = viewPager.getCurrentItem() + 1;
                        if (current < layouts.length) {
                            viewPager.setCurrentItem(current);
                        } else {
                        }
                    } else {
                        str_zip = data_lists.get(posi);
                        regi.str_zip = data_lists.get(posi);

                        editor.putString("d_zip", str_zip);
                        editor.commit();

                        Log.e("tag", "zz " + regi.str_zip +regi.str_country+regi.str_state);
                        dismiss();
                    }
                }


            });


            return arow;
        }
    }


}