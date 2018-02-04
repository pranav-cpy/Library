package com.team.pranav.library;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //element declaration
    int FabStatus = 0;
    int list_book=1;
    int state=0;
    Intent i;
    String book_bname[],book_author[],book_isbn1[],book_details1[];
    String author_aname[],author_age[],author_gender[],author_bornin[],author_about[];
    TextView bookORauthor,CountField;
    int bookid[],authorid[];
    DB_Controller db_controller;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get extras
            Bundle extras=getIntent().getExtras();
            if(extras !=null)
                list_book=extras.getInt("list_book");

         //retreive data from tables books and authors

        db_controller=new DB_Controller(this,"",null,1);
        cursor=db_controller.return_books();
        int len2=0;
        len2=cursor.getCount();
        book_bname=new String[len2];
        book_author=new String[len2];
        book_isbn1=new String[len2];
        book_details1=new String[len2];
        bookid=new int[len2];
        int i=0;
        while (cursor.moveToNext()) {
            bookid[i] = Integer.parseInt(cursor.getString(0));
            book_bname[i] = cursor.getString(1);
            book_author[i] = cursor.getString(2);
            book_isbn1[i] = cursor.getString(3);
            book_details1[i] = cursor.getString(4);
            i++;
        }
         i=0;

        cursor=db_controller.return_authors();
        int len1=0;
        len1=cursor.getCount();
        //cursor.moveToNext()
        author_aname=new String[len1];
        author_age=new String[len1];
        author_gender=new String[len1];
        author_bornin=new String[len1];
        author_about=new String[len1];
        authorid=new int[len1];
        while (cursor.moveToNext()) {

            author_aname[i] = cursor.getString(1);
            authorid[i] = Integer.parseInt(cursor.getString(0));
            author_age[i] = cursor.getString(2);
            author_gender[i] = cursor.getString(3);
            author_bornin[i] = cursor.getString(4);
            author_about[i] =cursor.getString(5);
            i++;
        }


         bookORauthor=(TextView)findViewById(R.id.main_bookORauthors);
        CountField=(TextView)findViewById(R.id.main_count);
         if(list_book==1){
             bookORauthor.setText("BOOKS");
            if(book_bname==null){
                CountField.setText("0 Books");
            state=1;}
             else
             CountField.setText(bookid.length+" Books");
         }
        else {

             bookORauthor.setText("AUTHORS");
             if(author_aname==null){
                 bookORauthor.setText("0");
             state=1;}
             else
             CountField.setText(authorid.length+" Authors");
         }

        //floating buttons declaration
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);


        fab.setVisibility(View.VISIBLE);

        //onclick listener for floating buttons
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                if (FabStatus == 0) {
                    fab1.setVisibility(View.VISIBLE);
                    fab2.setVisibility(View.VISIBLE);
                    FabStatus = 1;
                    fab.setImageResource(R.drawable.btn1);
                } else {
                    fab1.setVisibility(View.GONE);
                    fab2.setVisibility(View.GONE);
                    FabStatus = 0;
                    fab.setImageResource(R.drawable.btn0);
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                FabStatus = 0;
                fab.setImageResource(R.drawable.btn0);
                startActivity(new Intent(MainActivity.this,Add_book.class));

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                FabStatus = 0;
                fab.setImageResource(R.drawable.btn0);
                startActivity(new Intent(MainActivity.this, Add_author.class));
            }
        });
        //Adding new toolbar

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.nav);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         //Listview handling

        final ListView listview = (ListView) findViewById(R.id.list_main);
        CustomAdapter customadapter = new CustomAdapter(this);
        listview.setAdapter(customadapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(list_book==1)
                {
                     intent=new Intent(getApplicationContext(),Books_info.class);
                    intent.putExtra("name",book_bname[position]);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(getApplicationContext(), Author_info.class);
                    intent.putExtra("name", author_aname[position]);
                    startActivity(intent);
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
//Navigation drawer handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Books) {
            i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("list_book",1);

            startActivity(i);
            finish();

        } else if (id == R.id.Authors) {
            i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("list_book",0);

            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//class custom adapter for listview inflation
    class CustomAdapter extends BaseAdapter {
        Context context;
        CustomAdapter(Context context){this.context=context;}
        int count;
        @Override
        public int getCount() {
            if(state==1)
                return 0;
            else{
            if(list_book==1){
                //retreive no of records in book table
                count=bookid.length;
            }
              else  {
                //retreive no of records in author table
               count= authorid.length;
            }
            return  count;
        }}

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
            if(list_book==1){



                view = getLayoutInflater().inflate(R.layout.listview_book, null);

                TextView book_name1=(TextView) view.findViewById(R.id.book_name3);
                TextView author_name=(TextView) view.findViewById(R.id.author_name3);
                TextView isbn=(TextView)view.findViewById(R.id.isbn3);
                TextView details=(TextView)view.findViewById(R.id.details3);

                author_name.setText(book_author[i]);
                book_name1.setText(book_bname[i]);
                isbn.setText("ISBN - "+book_isbn1[i]);
                details.setText(book_details1[i]);

            }
            else {


                view = getLayoutInflater().inflate(R.layout.listview_author, null);

                TextView author_name2=(TextView)view.findViewById(R.id.author_name11);
                TextView age1=(TextView)view.findViewById(R.id.age1);
                TextView gender1=(TextView)view.findViewById(R.id.gender1);
                TextView bornin=(TextView)view.findViewById(R.id.born_in);
                author_name2.setText(author_aname[i]);
                age1.setText("Age "+author_age[i]+" / ");
                gender1.setText(author_gender[i]);
                bornin.setText("Born in "+author_bornin[i]);



            }
            return view;

        }
    }



}