package com.example.root.yiplproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListofContractor extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_contractor);
        listView = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(ListofContractor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.projects));
        // return view;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent aboutcsit = new Intent(ListofContractor.this, FirstProject.class);
                    // aboutcsit.putExtra("listname",listView.getItemAtPosition(i).toString());
                    startActivity(aboutcsit);
                }
                else if (i == 1) {
                    Intent pakistan = new Intent(ListofContractor.this, SecondProject.class);
                    //pakistan.putExtra("listname",listView.getItemAtPosition(i).toString());
                    startActivity(pakistan);
                }
            }
        });
        listView.setAdapter(madapter);

    }
    }

