package c03.ppl.hidupsehat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wahyuoi on 04/04/15.
 */
public class DatabaseInfo extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public DatabaseInfo(Context context) {
        super(context, DatabaseField.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DatabaseField.USER_TABLE + " ("+DatabaseField.USER_COLUMN_ID+" number primari key, "+DatabaseField.USER_COLUMN_USERNAME
                        +" text unique, "+ DatabaseField.USER_COLUMN_PASSWORD+" text, "+DatabaseField.USER_COLUMN_IS_LOGIN
                        +" number, "+DatabaseField.USER_COLUMN_NAMA+" text, "+DatabaseField.USER_COLUMN_TINGGI
                        +" number, "+DatabaseField.USER_COLUMN_BERAT+" number, "+DatabaseField.USER_COLUMN_UMUR
                        +" number, "+DatabaseField.USER_COLUMN_KELAMIN+" text)"
        );
        
      db.execSQL("CREATE TABLE " + DatabaseField.RESEP_MAKANAN_TABLE + "( "
                + DatabaseField.RESEP_MAKANAN_ID_USER + " NUMBER, "
                + DatabaseField.RESEP_MAKANAN_ID + " NUMBER, "
                + DatabaseField.RESEP_MAKANAN_NAMA + " TEXT, "
                + DatabaseField.RESEP_MAKANAN_KALORI + " NUMBER, "
                + DatabaseField.RESEP_MAKANAN_CARA_MEMBUAT + " TEXT, "
                + DatabaseField.RESEP_MAKANAN_SARAN_PENYAJIAN + " TEXT "
                + ")"
        );


        db.execSQL("CREATE TABLE "+DatabaseField.BAHAN_MAKANAN_TABLE + "( "
                + DatabaseField.BAHAN_MAKANAN_ID + " NUMBER, "
                + DatabaseField.BAHAN_MAKANAN_NAMA + " TEXT, "
                + DatabaseField.BAHAN_MAKANAN_KALORI + " NUMBER)"
        );

        db.execSQL("CREATE TABLE "+DatabaseField.FAVORIT_TABLE+"( "
                + DatabaseField.FAVORIT_RESEP + " NUMBER, "
                + DatabaseField.FAVORIT_USER + " NUMBER)"
        );



        db.execSQL("CREATE TABLE "+DatabaseField.RESEP_DAN_BAHAN_TABLE + "( "
                + DatabaseField.RESEP_DAN_BAHAN_ID_RESEP + " NUMBER NOT NULL, "
                + DatabaseField.RESEP_DAN_BAHAN_ID_BAHAN + " NUMBER NOT NULL, "
                + DatabaseField.RESEP_DAN_BAHAN_KETERANGAN + " VARCHAR(100))"
                );
        
        
        
        Log.e(DatabaseInfo.class.getName(), "Create database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DatabaseField.USER_TABLE);
        onCreate(db);
    }

    public boolean insert(String table, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(table, null, contentValues);
        db.close();
        return id != -1;
    }

    public boolean update(String table, ContentValues contentValues, String where, String[] values){
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.update(table, contentValues, where, values);
        db.close();
        return ret>0;
    }

    public ArrayList getAllDataFromTable(String table, String column_name){
        ArrayList arr = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + table, null);

        res.moveToFirst();
        while(res.isAfterLast() == false){
            arr.add(res.getString(res.getColumnIndex(column_name)));
            res.moveToNext();
        }
        res.close();
        db.close();
        return arr;
    }

    public boolean isLogin(String userTable, String userColumnIsLogin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + userTable + " where " + userColumnIsLogin + " = 1", null);

        res.moveToFirst();
        boolean ret = false;
        if (res.isAfterLast() == false)
            ret = res.getInt(res.getColumnIndex(userColumnIsLogin)) == 1;
        res.close();
        db.close();
        return ret;
    }
    public int getIdLogin(String userTable, String userColumnIsLogin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + userTable + " where " + userColumnIsLogin + " = 1", null);

        res.moveToFirst();
        int ret = -1;
        if (res.isAfterLast() == false)
            ret = res.getInt(res.getColumnIndex(DatabaseField.USER_COLUMN_ID));
        res.close();
        db.close();
        return ret;
    }
    public Cursor getFromQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);

        res.moveToFirst();
        return res;
    }

    public void deleteTableContent(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null );
    }

    public void deleteFrom(String table, String where, String[] arg) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, where, arg);
    }

    public boolean isFavorit(int isResep, int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DatabaseField.FAVORIT_TABLE + " where "
                + DatabaseField.FAVORIT_RESEP + " = " + isResep + " AND " + DatabaseField.FAVORIT_USER
                + " = " + idUser, null);

        res.moveToFirst();
        boolean ret = (res.isAfterLast() == false);
        res.close();
        db.close();
        return ret;
    }

    public boolean isExists(String userTable, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + userTable + " where "
                + DatabaseField.USER_COLUMN_USERNAME + " = '" + username + "'", null);

        res.moveToFirst();
        boolean ret = (res.isAfterLast() == false);
        res.close();
        db.close();
        return ret;
    }
}
