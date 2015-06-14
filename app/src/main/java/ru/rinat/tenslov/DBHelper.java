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
import java.util.Random;

/**
 * rinat on 03.06.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Cursor cursorTen;

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

        //Init cursorTen
        nextTenWords();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "onCreate() called");
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
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                Word.COL_STUD + " = ?",
                new String[]{getCurrentDate()},
                null, null, null, null);

        return (cursor.getCount() != 0);
    }

    public synchronized Cursor nextTenWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        cursorTen = db.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                Word.COL_STUD + " = ?",
                new String[] { "0" },
                null, null, null, "10");

        return cursorTen;
    }

    public synchronized Cursor getCurTenWords() {
        cursorTen.moveToFirst();

        return cursorTen;
    }

    public synchronized int getCountTenWords(){
        return cursorTen.getCount();
    }

    public synchronized TestValue getTestForWord(int positionOfPage) {
        Random rand = new Random();
        TestValue mTestValue = new TestValue(-1,"null",-1,"null",-1,"null",-1,"null",-1,"null");

        int positionOfCursor = positionOfPage - 1;

        if ( (positionOfCursor < 0) | (positionOfCursor >= getCountTenWords()) )
            return mTestValue;

        if (cursorTen.isClosed()) nextTenWords();

        cursorTen.moveToPosition(positionOfCursor);
        int curRec = cursorTen.getPosition();

        mTestValue.setValue(0, cursorTen.getInt(0), cursorTen.getString(1));
        mTestValue.setValue(1, cursorTen.getInt(0), cursorTen.getString(2));

        cursorTen.moveToFirst();

        final SQLiteDatabase dbAll = this.getReadableDatabase();
        Cursor cursor = dbAll.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                null, null, null, null, null, null);

        int countRecs = cursor.getCount();

        for (int i = 2; i < 5; i++) {
            int rec = rand.nextInt(countRecs);

            while (rec == curRec) {
                rec = rand.nextInt(countRecs);
            }

            cursor.moveToPosition(rec);
            mTestValue.setValue(i, cursor.getInt(0), cursor.getString(2));
        }

        cursor.close();

        mTestValue.shuffleValues();

        return mTestValue;
    }

    /*public synchronized Cursor nextTenWords(){
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(
                Word.TABLE_NAME,
                Word.FIELDS,
                Word.COL_STUD + " = ?",
                new String[] { "0" },
                null, null, null, "10");

        return cursor;
    }*/

    public synchronized void completeTest(Cursor cursor){
        final SQLiteDatabase dbRW = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            DatabaseUtils.cursorRowToContentValues(cursor, cv);
            cv.put(Word.COL_STUD, getCurrentDate());
            dbRW.update(
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

    /*static void shuffleArray(String[][] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 1; i--) {
            int index = rnd.nextInt(i) + 1; //Первую строку не трогаем
            String[] a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }*/
}

