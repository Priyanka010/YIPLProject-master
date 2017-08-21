package com.example.root.yiplproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.root.yiplproject.model.Complain;
import com.example.root.yiplproject.model.Likes;

import java.util.List;

public class ComplainDetail extends AppCompatActivity {
    Complain complain;
    TextView head, body, date,upvoteCount;
    Button upvote;
    List<Likes> likeList;
    String TAG = "TAG";
    Likes likes;
    String ls;
    Boolean flag=false;
    private List<Complain> complains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_detail);
        complain = (Complain) getIntent().getSerializableExtra("hello");

        head = (TextView) findViewById(R.id.head);
        body = (TextView) findViewById(R.id.body);
        date = (TextView) findViewById(R.id.date);
        upvoteCount=(TextView)findViewById(R.id.upvote_count);
        upvote = (Button) findViewById(R.id.upvote);

        head.setText(complain.getHead());
        body.setText(complain.getBody());
        date.setText(complain.getDatee());

        getLikesCount();



        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                likes = new Likes();
                likes.setComplainId(complain.getObjectId());
                likes.setOwnerId(complain.getOwnerId());
                try {
                    ls=Backendless.UserService.CurrentUser().getUserId();
                }catch (Exception e){
                    Toast.makeText(ComplainDetail.this, "User not logged in.", Toast.LENGTH_SHORT).show();
                }

                try{
                    for (Likes like:likeList) {
                        if (like.getLikedById().equalsIgnoreCase(ls)){
                            flag=true;
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(ComplainDetail.this, "Please wait", Toast.LENGTH_SHORT).show();
                }
                likes.setLikedById(ls);
                Log.e(TAG, "onClick:ls is  "+ls );
                //save likes in backendlss
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Likes c = Backendless.Persistence.save(likes);
                        Log.e(TAG, "run: " + c.toString());
                    }
                });
                if (flag==false){
                    thread.start();
                    getLikesCount();
                    Toast.makeText(ComplainDetail.this, "Voted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ComplainDetail.this, "Already voted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getLikesCount(){

        String whereClause = "complainId = '"+complain.getObjectId()+"'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause( whereClause );
        dataQuery.setPageSize(100);
        Log.e(TAG, "onCreate:object Id "+complain.getObjectId() );


        Backendless.Persistence.of(Likes.class).find(dataQuery,new AsyncCallback<List<Likes>>() {


            @Override
            public void handleResponse(List<Likes> response) {
                likeList= response;
                Log.e(TAG, "handleResponse: size"+likeList.size() );

                upvoteCount.setText(""+likeList.size());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("TAG", "handleFault: "+fault.toString() );

            }
        });
    }
}