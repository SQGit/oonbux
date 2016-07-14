package sqindia.net.oonbux;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Salman on 5/27/2016.
 */

//asdf
public class DbC extends SQLiteOpenHelper {


    public DbC(Context context) {
        super(context, "oonbux", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, size VARCHAR, pickup VARCHAR, photo VARCHAR, cost VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS chat(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sender VARCHAR, from_id VARCHAR, to_id VARCHAR, message VARCHAR, time VARCHAR, message_id VARCHAR );");

        db.execSQL("CREATE TABLE IF NOT EXISTS virtual(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, addr_id VARCHAR, addr_line1 VARCHAR, addr_line2 VARCHAR, city VARCHAR, state VARCHAR, zip VARCHAR, country VARCHAR, loc VARCHAR );");

        db.execSQL("CREATE TABLE IF NOT EXISTS physical(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, addr_line1 VARCHAR, addr_line2 VARCHAR, city VARCHAR, state VARCHAR, zip VARCHAR, phone VARCHAR, country VARCHAR, note VARCHAR, loc VARCHAR );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor fetchdata(String qry2) {
        Cursor c2 = null;
        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            c2 = sdb1.rawQuery(qry2, null);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return c2;
    }


    public void deletedata(String qry2, String txt) {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "DELETE FROM cart WHERE id = " + txt;

            Log.e("tag","cart deleted"+query);


            sdb1.execSQL(query);

        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

    }


    public void del_last_row() {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            //sdb1.rawQuery(qry2,null);
            String query = "DELETE FROM cart WHERE id = (SELECT MAX(id) FROM cart);";

            // DELETE FROM test WHERE id = (SELECT MAX(id) FROM test);
            // sdb1.delete("cart","size =?", new String[] { txt });

            sdb1.execSQL(query);

        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

    }

    public void del_table() {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "drop table cart;";
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }

    }


    public void chat_insert(int sender,String chat_from,String chat_message,String chat_to,String time,String msg_id) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into chat (sender,from_id,message,to_id,time,message_id) VALUES(\""+String.valueOf(sender)+"\",\""+chat_from+"\",\""+chat_to+"\",\""+chat_message+"\",\""+time+"\",\""+msg_id+"\");";
            Log.e("tag",""+query+"\n"+sender+chat_from+chat_to+chat_message+msg_id);
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }



    public void virtual_insert(String addr_id,String addr_line1,String addr_line2,String city,String state,String zip,String country,String def) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into virtual (addr_id,addr_line1,addr_line2,city,state,zip,country,loc) VALUES(\""+addr_id+"\",\""+addr_line1+"\",\""+addr_line2+"\",\""+city+"\",\""+state+"\",\""+zip+"\",\""+country+"\",\""+def+"\");";
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }


    public void physical_insert(String addr_line1,String addr_line2,String city,String state,String zip,String phone,String country,String note,String def) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into physical (addr_line1,addr_line2,city,state,zip,phone,country,note,loc) VALUES(\""+addr_line1+"\",\""+addr_line2+"\",\""+city+"\",\""+state+"\",\""+zip+"\",\""+phone+"\",\""+country+"\",\""+note+"\",\""+def+"\");";
            Log.e("tag",""+query);
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }




}
