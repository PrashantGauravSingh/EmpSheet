package com.andtechno.singh.empsheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.andtechno.singh.empsheet.MainActivity.*;

public class EmployeeDetails extends AppCompatActivity {
    public static ArrayAdapter madapter;
    public ListView listitem;
    String [] EmpName;
   public static String [] imageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        madapter = new ArrayAdapter<String>(this,
                R.layout.recycler_list_view,MainActivity.nameList);

        // get the reference of RecyclerView

        listitem=findViewById(R.id.listView1);
//        listitem.setAdapter(madapter);
         EmpName = MainActivity.nameList.toArray(new String[MainActivity.nameList.size()]);
        imageId = MainActivity.imglist.toArray(new String[MainActivity.imglist.size()]);


        CustomAdapter adapter = new
                CustomAdapter(EmployeeDetails.this, EmpName, imageId);
        listitem=findViewById(R.id.listView1);
        listitem.setAdapter(adapter);
        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent newintent= new Intent(EmployeeDetails.this,Leave_Activtiy.class);
                newintent.putExtra("position",position);
                startActivity(newintent);

            }
        });
    }
    @Override
    public void onBackPressed()
    {

    }
}
