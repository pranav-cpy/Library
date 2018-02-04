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


    public class Add_author extends AppCompatActivity {
        //Elements declarations
        TextView textView_name , textView_age, textView_bornin, textView_about;
        String name,age,bornin,about,gender;
        Button btn;
        Intent i;
        Spinner spinner;
        DB_Controller db_controller;Cursor cursor;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_author);
            db_controller=new DB_Controller(this,"",null,1);
            textView_name=(TextView)findViewById(R.id.textView_a1);
            textView_age=(TextView)findViewById(R.id.textView_a2);
            textView_bornin=(TextView)findViewById(R.id.textView_a3);
            textView_about=(TextView)findViewById(R.id.textView_a4);
            btn=(Button)findViewById(R.id.button_author);
            spinner=(Spinner)findViewById(R.id.spin_gender);

            //Creating array list for spinner

            List<String> list=new ArrayList<>();
            list.add("Gender");
            list.add("Male");
            list.add("Female");
            list.add("Other");
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //setting up spinner and item click listener

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    gender=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Handling button click event

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    name=textView_name.getText().toString();
                    age=textView_age.getText().toString();
                    bornin=textView_bornin.getText().toString();
                    about=textView_about.getText().toString();
                    if (name.length() == 0 || age.length() == 0 || about.length() == 0 || bornin.length() == 0)
                        Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();

                    else if(gender.equals("Gender"))
                             Toast.makeText(getApplicationContext(), "Select proper gender", Toast.LENGTH_SHORT).show();
                    else{

                        //inserting to database

                        db_controller.insert_author(name,age,gender,bornin,about);
                        Toast.makeText(getApplicationContext(), "Author saved successfully", Toast.LENGTH_SHORT).show();
                        i=new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("list_book",0);

                        startActivity(i);
                    }
                }
            });

        }
    }

