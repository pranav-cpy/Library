package com.team.pranav.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pranav on 03/02/18.
 */

public class DB_Controller extends SQLiteOpenHelper {
    public DB_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Database.db", factory, version);
    }
     //create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE BOOKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, BOOK_NAME TEXT NOT NULL , AUTHOR_NAME TEXT NOT NULL , ISBN TEXT NOT NULL , DESCRIPTION TEXT NOT NULL )");
        sqLiteDatabase.execSQL("CREATE TABLE AUTHORS(ID INTEGER PRIMARY KEY AUTOINCREMENT, AUTHOR_NAME TEXT NOT NULL ,AGE TEXT NOT NULL , GENDER TEXT NOT NULL , BORN_IN TEXT NOT NULL , ABOUT TEXT NOT NULL);");
    }
//Upgrade version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             db.execSQL("DROP TABLE IF EXISTS BOOKS;");
        db.execSQL("DROP TABLE IF EXISTS AUTHORS;");
        onCreate(db);
    }
    //insert into table book
    public void insert_book(String book_name, String author_name, String isbn, String description){
        ContentValues contentValues=new ContentValues();
        contentValues.put("BOOK_NAME",book_name);
        contentValues.put("AUTHOR_NAME",author_name);
        contentValues.put("ISBN",isbn);
        contentValues.put("DESCRIPTION",description);
        this.getWritableDatabase().insertOrThrow("BOOKS","",contentValues);

    }
    //insert into author
    public void insert_author(String author_name, String age, String gender, String born, String about){
        ContentValues contentValues=new ContentValues();
        contentValues.put("AUTHOR_NAME",author_name);
        contentValues.put("AGE",age);
        contentValues.put("GENDER",gender);
        contentValues.put("BORN_IN",born);
        contentValues.put("ABOUT",about);
        this.getWritableDatabase().insertOrThrow("AUTHORS","",contentValues);

    }
    //return books
    public Cursor return_books(){
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM BOOKS",null);
        return cursor;
    }
    //return authors
    public Cursor return_authors(){
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM AUTHORS",null);
        return cursor;
    }
    /*public Cursor return_author(int id){
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM AUTHORS WHERE ID='"+id+"'",null);
        return cursor;
    }
    public Cursor return_book(int id){
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM BOOKS WHERE ID='"+id+"'",null);
        return cursor;
    }*/

}
