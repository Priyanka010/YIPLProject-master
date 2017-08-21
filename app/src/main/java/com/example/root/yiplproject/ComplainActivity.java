package com.example.root.yiplproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.root.yiplproject.model.Complain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComplainActivity extends AppCompatActivity {
    public EditText  txtHead, txtBody;
    TextView txtChoose;
    private Spinner spinner1;
    Button send, btnPost, imgbutton, btnLogout;
    String TAG = "TAG";
    public String objectId;
    public String userid, to, head, body, datee;
    ListView listViewFiles;
    BackendlessUser user;

    ArrayList<Uri> arrayUri = new ArrayList<Uri>();
    ArrayAdapter<Uri> myFileListAdapter;

    final int RQS_LOADIMAGE = 0;
    final int RQS_SENDEMAIL = 1;

    Uri imageUri = null;
    private String toEmail, headEmail, bodyEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        Bundle bundle = getIntent().getExtras();
        //userid = bundle.getString("rumi");
        userid=bundle.getString("rumi");
        Log.e(TAG, "user is "+userid.toString() );
//        Log.e(TAG, "ComplainActivitya : "+user );
        txtChoose = (TextView) findViewById(R.id.txtchoose);
        spinner1 = (Spinner) findViewById(R.id.spinnerTo);
        txtHead = (EditText) findViewById(R.id.send_head);
        txtBody = (EditText) findViewById(R.id.send_body);
        send = (Button) findViewById(R.id.btn_send);
        btnPost = (Button) findViewById(R.id.btn_post);
        imgbutton = (Button) findViewById(R.id.imgbtn);
        btnLogout = (Button) findViewById(R.id.btn_logout);

        imgbutton.setOnClickListener(imgbuttonOnClickListener);
        send.setOnClickListener(sendOnClickListener);

        myFileListAdapter = new ArrayAdapter<Uri>(
                ComplainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayUri);
        listViewFiles = (ListView) findViewById(R.id.filelist);
        listViewFiles.setAdapter(myFileListAdapter);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Backendless.UserService.logout(new BackendlessCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        finish();
                    }
                });
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComplain();

            }
        });
    }

    View.OnClickListener imgbuttonOnClickListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RQS_LOADIMAGE);


        }
    };

    View.OnClickListener sendOnClickListener
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            toEmail = String.valueOf(spinner1.getSelectedItem());
            bodyEmail = txtBody.getText().toString();
            headEmail = txtHead.getText().toString();


            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
            intent.putExtra(Intent.EXTRA_SUBJECT, headEmail);
            intent.putExtra(Intent.EXTRA_TEXT, bodyEmail);
            if (arrayUri.isEmpty()) {
                //Send email without photo attached
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("plain/text");
            } else if (arrayUri.size() == 1) {
                //Send email with ONE photo attached
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, arrayUri.get(0));
                intent.setType("image/*");
            } else {
                //Send email with MULTI photo attached
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayUri);
                intent.setType("image/*");
            }
            startActivity(Intent.createChooser(intent, "Choice App to send email:"));
            finish();
            Log.i("Finished sending email", "");

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_LOADIMAGE:
                    imageUri = data.getData();
                    arrayUri.add(imageUri);
                    myFileListAdapter.notifyDataSetChanged();
                    //imgEmail.setImageURI(imageUri);-----imagekolagi
                    break;
                case RQS_SENDEMAIL:

                    break;
            }

        }
    }


    public void postComplain() {


        to = String.valueOf(spinner1.getSelectedItem());
        body = txtBody.getText().toString();
        head = txtHead.getText().toString();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        datee = df.format(Calendar.getInstance().getTime());


        Complain complain=new Complain(to, head, body, datee,userid);

        Backendless.Persistence.save(complain,new BackendlessCallback<Complain>() {

            @Override
            public void handleResponse(Complain response) {
                Toast.makeText(ComplainActivity.this, "Posted in forum", Toast.LENGTH_SHORT).show();
                objectId = response.getObjectId();
//                Log.e(TAG, "object id " + objectId);
                Backendless.Persistence.of(Complain.class).findFirst(new AsyncCallback<Complain>() {
                    @Override
                    public void handleResponse(Complain response) {
//                        Log.e(TAG, "handleResponse:2 " + response.getBody());
//                        Log.e(TAG, "handleResponse:2 " + response.toString());
                        Intent i = new Intent(ComplainActivity.this, ForumActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }
        });
    }


}


//    public void saveComplain(){
//
//
////                 BackendlessUser user = Backendless.UserService.CurrentUser();
////                userid=user.getUserId();
//
//        to=txtTo.getText().toString();
//        body=txtBody.getText().toString();
//        head=txtHead.getText().toString();
//        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
//        datee = df.format(Calendar.getInstance().getTime());
//        Log.e(TAG, "userid3: "+userid );
//        //complain.setId(userid);
//
//        Backendless.Persistence.save( new Complain(to,head, body,datee,userid), new BackendlessCallback<Complain>()
//        {
//
//            @Override
//            public void handleResponse(Complain response) {
//                Toast.makeText(ComplainActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
//                objectId=response.getObjectId();
//                Log.e(TAG, "object id "+objectId );
//                Backendless.Persistence.of( Complain.class).findFirst(new AsyncCallback<Complain>() {
//                    @Override
//                    public void handleResponse(Complain response) {
//                        Log.e(TAG, "handleResponse:2 " +response.getBody());
//                        Log.e(TAG, "handleResponse:2 " +response.toString());
//
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//
//                    }
//                });
//            }
//        } );
//    }
//
//    public void postComplain(){
//         String whereClause = "objectId='"+objectId+"'";
//                DataQueryBuilder dataQuery = DataQueryBuilder.create();
//                dataQuery.setWhereClause( whereClause );
//                //Log.e(TAG, "userid: "+userid );
//
//                Backendless.Persistence.of(Complain.class).find(dataQuery, new AsyncCallback<List<Complain>>() {
//                    @Override
//                    public void handleResponse(List<Complain> response) {
//                        Log.e(TAG, "object id2 "+objectId );
//                        Log.e(TAG, "onClick:2 "+response.get(0).toString()+"/n" );
//                        Toast.makeText(ComplainActivity.this, "Posted to Forum", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//                        Log.e(TAG, "handleFault: "+fault.toString() );
//
//                    }
//                });
//    }
