package sqindia.net.oonbux.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
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

        db.execSQL("CREATE TABLE IF NOT EXISTS region(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, country VARCHAR, state VARCHAR, zip VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS chat(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sender VARCHAR, from_id VARCHAR, to_id VARCHAR, message VARCHAR, time VARCHAR, message_id VARCHAR, receiver VARCHAR );");

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



    public void del_table1() {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();

            String query = "delete from chat;";
           // String query = "drop table chat;";


            sdb1.execSQL(query);


        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }

    }





    public void insertFast(String a,String b,String c) {

        // you can use INSERT only
      //  String sql = "INSERT OR REPLACE INTO region ( country, state, zip ) VALUES ( \""+a+"\",\""+b+"\",\""+c+"\")";

        String sql = "INSERT OR REPLACE INTO region (  country, state, zip ) VALUES ( ?, ?, ? )";

        SQLiteDatabase db = this.getWritableDatabase();

        /*
         * According to the docs http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
         * Writers should use beginTransactionNonExclusive() or beginTransactionWithListenerNonExclusive(SQLiteTransactionListener)
         * to start a transaction. Non-exclusive mode allows database file to be in readable by other threads executing queries.
         */
        db.beginTransactionNonExclusive();
        // db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);

       // String sql = "INSERT OR REPLACE INTO " + tableName + " ( name, description ) VALUES ( ?, ? )";

            stmt.bindString(1,a);
            stmt.bindString(2,b);
            stmt.bindString(2,c);




            stmt.execute();
            stmt.clearBindings();



        db.setTransactionSuccessful();
        db.endTransaction();

       // db.close();
    }













    public void region_insert(String country,String state,String zip) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into region (country,state,zip) VALUES(\""+country+"\",\""+state+"\",\""+zip+"\");";
            sdb1.execSQL(query);

            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }


    public Cursor region_get(String zip) {
        Cursor cur = null;
        String query = "select * from region where zip = \""+zip+"\";";
        Log.e("tag",""+query);
        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
            Log.e("tag",""+cur);
            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return cur;
    }


    public Cursor region_getall() {
        Cursor cur = null;

        String query = "select * from region;";

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
            Log.e("tag",""+cur);
            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;


    }



    public Cursor region_db(String query) {
        Cursor cur = null;

       // String query = "select * from region;";

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
            Log.e("tag",""+cur);
            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;


    }


    public void region_insert1(String country, String state, String zip) {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into region (country,state,zip) VALUES(\""+country+"\",\""+state+"\",\""+zip+"\");";
            Log.e("tag",""+query);
            sdb1.execSQL(query);

            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }

    }




    public void region_update(String country,String state,String zip) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "update region set zip = \""+zip+"\" where state =\""+state+"\" and country =\""+country+"\";";
            Log.e("tag",""+query);
            sdb1.execSQL(query);

            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }


    }


    public void chat_insert(int sender,String chat_from,String chat_message,String chat_to,String time,String msg_id,String receiver) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "insert into chat (sender,from_id,message,to_id,time,message_id,receiver) VALUES(\""+String.valueOf(sender)+"\",\""+chat_from+"\",\""+chat_to+"\",\""+chat_message+"\",\""+time+"\",\""+msg_id+"\",\""+receiver+"\");";
            Log.e("tag",""+query+"\n"+receiver);
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }

    public void chat_delete(String id) {

        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "DELETE FROM chat WHERE message_id = \""+ id +"\";" ;
            Log.e("tag",""+query);
            sdb1.execSQL(query);

        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

    }




    public Cursor chat_getdata(String from,String to,String reciver) {
        Cursor cur = null;

        String query = "select * from chat where receiver  = \""+reciver+ "\";"  ;

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;
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

    public void physical_update(String addr_line1,String addr_line2,String city,String state,String zip,String phone,String country,String note,String def) {
        try {
            SQLiteDatabase sdb1;
            sdb1 = getWritableDatabase();
            String query = "update physical set addr_line1 = \""+addr_line1+"\" addr_line2 = \""+addr_line2+"\" city = \""+city+"\" state = \""+state+"\" zip = \""+zip+"\" phone = \""+phone+"\" country = \""+country+"\" note = \""+note+"\" where loc = \""+def+"\";";
            //(addr_line1,addr_line2,city,state,zip,phone,country,note,loc) VALUES(\""+addr_line1+"\",\""+addr_line2+"\",\""+city+"\",\""+state+"\",\""+zip+"\",\""+phone+"\",\""+country+"\",\""+note+"\",\""+def+"\");";
            Log.e("tag",""+query);
            sdb1.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
            Log.e("tag",""+e);
        }
    }


    public Cursor getCountry() {
        Cursor cur = null;

        String query = "select DISTINCT country from region;"  ;

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);

            //sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;


    }

    public Cursor getState(String country) {

        Cursor cur = null;

        String query = "select DISTINCT state from region where country = \""+ country +"\";"  ;

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
           // sdb1.close();
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;
    }

    public Cursor getZip(String country, String state) {
        Cursor cur = null;

        String query = "select DISTINCT zip from region where country = \""+ country +"\" and state = \""+ state +"\";"  ;

        Log.e("tag",""+query);

        try {
            SQLiteDatabase sdb1;
            sdb1 = getReadableDatabase();
            cur = sdb1.rawQuery(query, null);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);

        }

        return cur;


    }


}
