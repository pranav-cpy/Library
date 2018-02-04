package com.team.pranav.library;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pranav on 01/02/18.
 */
public class Add_book extends AppCompatActivity {
    //Elements declaration

    TextView textView_bookname, textView_isbn , textView_details;
    Button btn;
    String bookname,isbn,details,author;
    String[] authors;
    DB_Controller db_controller;
    Spinner spinner;
    Cursor cursor;
    int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        db_controller=new DB_Controller(this,"",null,1);

        //Reading authors from database and storing in array

        cursor=db_controller.return_authors();
        int i=0;
        int len=cursor.getCount();
        authors=new String[len];
        while (cursor.moveToNext()) {
            authors[i]=cursor.getString(1);
            i++;
        }
        textView_bookname=(TextView)findViewById(R.id.textView_b1);
        textView_isbn=(TextView)findViewById(R.id.textView_b2);
        textView_details=(TextView)findViewById(R.id.textView_b3);
        btn=(Button)findViewById(R.id.button_book);

        spinner=(Spinner)findViewById(R.id.spin_author);
        List<String> list=new ArrayList<>();

        //adding elements to spinner

        list.add("Author");
        if(authors==null) state=1;
        if(state ==0) {
            for (i = 0; i < authors.length; i++)
                list.add(authors[i]);
        }

        //item selected listener for spinner

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               author =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         //Handler for button click event

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookname=textView_bookname.getText().toString();
                isbn=textView_isbn.getText().toString();
                details=textView_details.getText().toString();

                if(bookname.length()==0||isbn.length()==0||details.length()==0)
                    Toast.makeText(getApplicationContext(),"Fill all the fields",Toast.LENGTH_SHORT).show();

                else if(author.equals("Author"))
                    Toast.makeText(getApplicationContext(),"Select a valid author name",Toast.LENGTH_SHORT).show();
                else {
                    //insert to database
                    db_controller.insert_book(bookname,author,isbn,details);

                    Toast.makeText(getApplicationContext(), "Book saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_book.this, MainActivity.class));
                }

            }
        });

    }
}