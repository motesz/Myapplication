package com.montesdiether.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DB_PATH = "/data/data/com.montesdiether.myapplication/databases/";

    private static String DB_NAME = "MyApplication.db";

    private SQLiteDatabase myDatabase;

    private final Context myContext;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

            Log.i("DatabaseHelper", "DB is existing. NOT COPYING");
        } else {

            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("DatabaseHelper", e.getMessage());
                throw new Error("Error copying database");

            }
        }

    }
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                File dbFile = myContext.getDatabasePath(DB_NAME);
                myPath = dbFile.getPath();
                Log.i("DatabaseHelper", "here1: " + myPath);

            } else {
                myPath = DB_PATH + DB_NAME;
                Log.i("DatabaseHelper", "here2: " + myPath);
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {


            Log.e("DatabaseHelper", e.getLocalizedMessage());

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.i("DatabaseHelper", "DB is not existing. COPYING");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);



        String outFileName = DB_PATH + DB_NAME;


        OutputStream myOutput = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<musicwishlist> getAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<musicwishlist> data = new ArrayList<>();

        Cursor cursor;

        try{
            cursor = db.query("tbMusic", null, null, null, null, null,null);
            while(cursor.moveToNext()){
                musicwishlist note = new musicwishlist(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2));
                data.add(note);
            }
            Log.i("DatabaseHelper", "" + data);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;
    }


    public List<musicwishlist> getNotesWithMatches(String searchText){
        SQLiteDatabase db = this.getWritableDatabase();
        List<musicwishlist> data = new ArrayList<>();

        Cursor cursor;

        try{

            cursor = db.query("tbMusic", null,"musicTitle LIKE ? OR musicAuthor LIKE ?",new String[]{"%"+ searchText + "%", "%"+ searchText + "%"},null, null,null);

            while(cursor.moveToNext()){
                musicwishlist note = new musicwishlist(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2));
                data.add(note);
            }

            Log.i("DatabaseHelper", "" + data);

        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }

        return data;

    }

    public musicwishlist getNote(int musicID){
        SQLiteDatabase db = this.getWritableDatabase();
        musicwishlist note = null;

        Cursor cursor;

        try{
            cursor = db.query("tbMusic", null, "musicID = ?", new String[]{"" + musicID}, null, null,null);
            cursor.moveToFirst();
            note = new musicwishlist(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2));
            Log.i("DatabaseHelper", "" + note);
        }catch(Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return note;
    }

    public int updateNote(int musicID, String musicName, String musicAuthor){
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("musicTitle", musicName);
        cv.put("musicAuthor", musicAuthor);
        try {
            result = db.update("tbMusic", cv, "musicID = ?", new String[]{"" + musicID});
        }catch (Exception e){
            Log.e("DatabaseHelper", "" + e.getLocalizedMessage());
        }
        return result;
    }
    public long addNote(String musicName, String musicAuthor){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("musicTitle", musicName);
        cv.put("musicAuthor", musicAuthor);
        result = db.insert("tbMusic", null, cv);
        return result;
    }
    public void deleteNote(int musicID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        db.delete("tbMusic", "musicID = ?", new String[]{"" + musicID});
    }
}
