package ru.rinat.tenslov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rinat on 03.06.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    final static int DB_VER = 1;
    final static String DB_NAME = "dict.db";
    final String TABLE1_NAME = "dict";
    final String CREATE_TABLE1 = "CREATE TABLE "+TABLE1_NAME+
            "( _id INTEGER PRIMARY KEY , "+
            "word TEXT NOT NULL, " +
            "trans TEXT NOT NULL, " +
            "stud INTEGER DEFAULT (0))";

    final String TABLE2_NAME = "work";
    final String CREATE_TABLE2 = "CREATE TABLE "+TABLE2_NAME+
            "( _id INTEGER PRIMARY KEY , "+
            "id_dict INTEGER  NOT NULL, " +
            "word TEXT NOT NULL, " +
            "trans TEXT NOT NULL)";

    final String DROP_TABLE1 = "DROP TABLE IF EXISTS "+TABLE1_NAME;
    final String DROP_TABLE2 = "DROP TABLE IF EXISTS "+TABLE2_NAME;
    final String DATA_FILE_NAME = "data.txt";
    final String DEBUG_TAG = "tenslov-db";

    Context mContext;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        Log.d(DEBUG_TAG, "constructor called");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG,"onCreate() called");
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        fillData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE1);
        db.execSQL(DROP_TABLE2);
        onCreate(db);
    }

    private ArrayList<String> getData() {
        InputStream stream = null;
        ArrayList<String> list = new ArrayList<>();
        try {
            stream = mContext.getAssets().open(DATA_FILE_NAME);
        }
        catch (IOException e) {
            Log.d(DEBUG_TAG,e.getMessage());
        }

        BufferedReader dataStream
                = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        String data;
        try {
            while( (data=dataStream.readLine()) != null ) {
                list.add(data);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void fillData(SQLiteDatabase db){
        ArrayList<String> data = getData();
        for(String dt:data) Log.d(DEBUG_TAG,"item="+dt);

        if( db != null ){
            ContentValues values;
            String[] separated;

            for(String dat:data){
                values = new ContentValues();
                separated = dat.split(":!:");
                values.put("word", separated[0]);
                values.put("trans",separated[1]);
                db.insert(TABLE1_NAME, null, values);
            }
        }
        else {
            Log.d(DEBUG_TAG,"db null");
        }
    }

    public synchronized boolean workToday(){
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                Word.COL_STUD + " = ?",
                new String[] { getCurrentDate() },
                null, null, null, null);

        return (cursor.getCount() != 0);
    }

    public synchronized Cursor nextTenWords(){
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                Word.COL_STUD + " = ?",
                new String[] { "0" },
                null, null, null, "10");

        return cursor;
    }

    public synchronized void completeTest(Cursor cursor){
        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            DatabaseUtils.cursorRowToContentValues(cursor, cv);
            cv.put(Word.COL_STUD, getCurrentDate());
            db.update(
                    Word.TABLE_NAME,
                    cv,
                    Word.COL_ID + " = ?",
                    new String[]{cv.getAsString(Word.COL_ID)});

            cursor.moveToNext();
        }

        cursor.moveToFirst();
    }

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        return format.format(cal.getTime());
    }
}

