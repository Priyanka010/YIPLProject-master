package com.example.root.yiplproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView contrator, complain, forum, aboutus;
    TextView glossary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contrator = (ImageView) findViewById(R.id.contractor);
        complain = (ImageView) findViewById(R.id.complain);
        forum = (ImageView) findViewById(R.id.forum);
        aboutus = (ImageView) findViewById(R.id.aboutus);
        glossary = (TextView) findViewById(R.id.glossary);

        contrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListofContractor.class);
                intent.putExtra("rumi", "rumi");
                startActivity(intent);
            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.isValidLogin(new BackendlessCallback<Boolean>() {
                    @Override
                    public void handleResponse(Boolean response) {
                        String loggedInUser = Backendless.UserService.loggedInUser();
                        Intent intent;
                        if (response){

                            intent = new Intent(MainActivity.this, ComplainActivity.class);
                            intent.putExtra("rumi", loggedInUser);
                        }
                        else
                            intent = new Intent(MainActivity.this, Login.class);

                        startActivity(intent);
                    }
                });
            }
        });

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                startActivity(intent);


            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);

            }
        });
        glossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Glossary.class);
                startActivity(intent);

            }
        });

        Backendless.initApp(getApplicationContext(), "F4C26AE7-9CA0-8CB8-FF49-D66D9F1C0D00", "9D8E4C70-E734-CAA5-FFFD-B69BAE068400");
    }
}

