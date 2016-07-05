package sqindia.net.oonbux;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Salman on 5/27/2016.
 */
public class DbC extends SQLiteOpenHelper {


    public DbC(Context context) {
        super(context, "oonbux", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, size VARCHAR, pickup VARCHAR, photo VARCHAR);");
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




}
