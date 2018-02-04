package com.team.pranav.library;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pranav on 01/02/18.
 */

public class Author_info extends AppCompatActivity {
    //Element declaration
    String a_id;
    int count;
    TextView author_name,author_age,author_gender,author_bornin, info_count1;
    String author,age,gender,bornin;
    String book_name[],isbn[],details[];
    DB_Controller db_controller;
    int[] b_id;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_info);
        Bundle extras=getIntent().getExtras();

        author_name=(TextView)findViewById(R.id.info_author_name);

        author_age=(TextView)findViewById(R.id.info_age);

        author_gender=(TextView)findViewById(R.id.info_gender);

        info_count1=(TextView)findViewById(R.id.info_count);

        author_bornin=(TextView)findViewById(R.id.info_born_in);

        //Reading from extras

        if(extras !=null)
            a_id=extras.getString("name");
        db_controller=new DB_Controller(this,"",null,1);

        //Reading authors database

        cursor=db_controller.return_authors();
        while(cursor.moveToNext()){
            if(cursor.getString(1).equals(a_id))
                author=cursor.getString(1);
                age=cursor.getString(2);
                gender=cursor.getString(3);
                bornin=cursor.getString(4);
        }

        //Read books table

        cursor=db_controller.return_books();
        int i=0;String dummy;
        int len=cursor.getCount();
        b_id=new int[len];
        book_name=new String[len];
        isbn=new String[len];
        details=new String[len];
        while (cursor.moveToNext()) {
            dummy=cursor.getString(2);
            if( dummy.equals(author)){
                b_id[i] = Integer.parseInt(cursor.getString(0));
                book_name[i] = cursor.getString(1);
                isbn[i] = cursor.getString(3);
                details[i] = cursor.getString(4);
                i++;
            }
        }
        //Display in layout

        author_name.setText(author);
        author_age.setText("age "+age+" / ");
        author_gender.setText(gender);
        author_bornin.setText("Born in "+bornin);
        info_count1.setText(String.valueOf(i));

        //Listview

        final ListView listview = (ListView) findViewById(R.id.listview_authorinfo);
        CustomAdapter customadapter = new CustomAdapter(this);
        listview.setAdapter(customadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Author_info.this, Books_info.class));
                    //retrieve id of the book
                    int id1=b_id[position];
                    Intent i=new Intent(getApplicationContext(),Books_info.class);
                    i.putExtra("id",book_name[position]);

                     startActivity(i);


            }
        });


    }
     //Custom adapter for list view
    class CustomAdapter extends BaseAdapter {
        Context context;
        CustomAdapter(Context context){this.context=context;}
        int count;
        @Override
        public int getCount() {
            if(b_id.length==1)
                return 0;

            count=b_id.length;
            return  count-1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup parent) {
                view = getLayoutInflater().inflate(R.layout.listview_book_info, null);

                TextView book_name11=(TextView)view.findViewById(R.id.b_name);
                TextView isbn11=(TextView)view.findViewById(R.id.b_isbn);
                TextView details11=(TextView)view.findViewById(R.id.b_details);

                book_name11.setText(book_name[i]);

                isbn11.setText(isbn[i]);
                ImageView img=(ImageView)findViewById(R.id.img_author);
                details11.setText(details[i]);

            return view;

        }
    }


}