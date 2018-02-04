package com.team.pranav.library;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by pranav on 04/02/18.
 */

public class Books_info extends AppCompatActivity {
    String name;
    String bookname,authorname,isbn,details;
    TextView book_name,author_name,isbn1,details1;
    Cursor cursor;
    DB_Controller db_controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        //get extras

        Bundle extras=getIntent().getExtras();
        if(extras !=null)
            name=extras.getString("name");
        //Retreive data from db

        db_controller=new DB_Controller(this,"",null,1);
        cursor=db_controller.return_books();
        bookname= "";
        authorname= "";
        isbn= "";
        details= "";
        while(cursor.moveToNext()){
            if(name !=null && name.equals(cursor.getString(1))){
                bookname=cursor.getString(1);
                authorname = cursor.getString(2);
                isbn = cursor.getString(3);
                details = cursor.getString(4);
            }
        }


        book_name=(TextView)findViewById(R.id.book_name_info);
        author_name=(TextView)findViewById(R.id.author_name_info);
        isbn1=(TextView)findViewById(R.id.isbn_info);
        details1=(TextView)findViewById(R.id.details_info);
        book_name.setText(bookname);
        author_name.setText(authorname);
        isbn1.setText(isbn);
        details1.setText(details);
    }
}