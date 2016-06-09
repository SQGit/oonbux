package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

/**
 * Created by Salman on 6/9/2016.
 */
public class SwipeList extends Activity {

    ListView listview;
    DbC dbclass;
    ArrayList<String> asss = new ArrayList<>();
    Context context = this;
    Adap_Shipment adapter1;
    private SwipeMenuListView mListView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dbclass.del_last_row();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_next_withdelete);

        dbclass = new DbC(context);

        asss.add("salman");
        asss.add("asdf");
        asss.add("radhika");


        mListView = (SwipeMenuListView) findViewById(R.id.swipe_list);

        ArrayAdapter<String> asdfd = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, asss);

        //mListView.setAdapter(asdfd);


        getFromDB();

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.des);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        mListView.setMenuCreator(creator);


        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        dbclass.del_last_row();
                        adapter1.notifyDataSetChanged();
                        getFromDB();
                        Log.d("tag", "button1");
                        break;
                    case 1:
                        dbclass.del_last_row();
                        adapter1.notifyDataSetChanged();
                        Log.d("tag", "button2");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    private void getFromDB() {

        ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        dbdatalist.clear();

        String query = " SELECT * FROM cart";


        Cursor c = dbclass.fetchdata(query);


        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DbGclass dbc = new DbGclass();

                    dbc.set_id(String.valueOf(c.getInt(c.getColumnIndex("id"))));

                    dbc.set_size(c.getString(c.getColumnIndex("size")));
                    dbc.set_pickup(c.getString(c.getColumnIndex("pickup")));
                    dbc.set_photo(c.getString(c.getColumnIndex("photo")));
                    dbdatalist.add(dbc);
                } while (c.moveToNext());
            }
        }


        adapter1 = new Adap_Shipment(SwipeList.this, dbdatalist);
        mListView.setAdapter(adapter1);


    }


}
