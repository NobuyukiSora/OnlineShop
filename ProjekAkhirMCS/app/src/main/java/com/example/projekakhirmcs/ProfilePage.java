package com.example.projekakhirmcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {
    Button logout, delete, editsave;
    EditText editun;
    TextView username, useremail, userphone;
    public String newuname;
    Bundle ext;
    int a = 1;
    int b = 1;
    FirebaseFirestore db;
    String id, newname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        logout = findViewById(R.id.logout);
        delete = findViewById(R.id.deleteaccount);
        editsave = findViewById(R.id.editsaveun);
        editun = findViewById(R.id.editusername);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.email);
        userphone = findViewById(R.id.phone);

        db = FirebaseFirestore.getInstance();

        logout.setOnClickListener(this);
        delete.setOnClickListener(this);
        editsave.setOnClickListener(this);

        ///////////////////////////////////////////////////

        ext = getIntent().getExtras();
        if(b==1){
            username.setText(""+ext.getString("username").toString());
            useremail.setText(""+ext.getString("useremail").toString());
            userphone.setText(""+ext.getString("userphone").toString());
            id = ext.getString("userid");
            b++;
        }

        //////////////////////////////////////////////////

    }

    @Override
    public void onClick(View view) {
        // klo klik logout
        if(view == logout){
            Intent i = new Intent(ProfilePage.this,MainActivity.class);
            startActivity(i);
        }
        //klo klik delete
        else if (view == delete){
            deletedata();
            Intent i = new Intent(ProfilePage.this,MainActivity.class);
            startActivity(i);
        }
        // klo klik edit/save
        else if (view == editsave){
            if(a%2==1) {
                editun.setVisibility(View.VISIBLE);
                username.setVisibility(View.INVISIBLE);
                editsave.setText("Save");
                save();
            }
            else if(a%2==0){
                editun.setVisibility(View.INVISIBLE);
                username.setVisibility(View.VISIBLE);
                editsave.setText("Edit");
            }
            a++;
            username.setText(newname);
        }
    }
    void deletedata(){
        db.collection("User").document(id).delete();
    }
    void save(){
        newname = editun.getText().toString();
        Map<String, Object> data = new HashMap<>();
        data.put("name", newname);

        db.collection("User").document(id).update(data);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Intent i = new Intent(ProfilePage.this,HomePage.class);
//        i.putExtra("username",newuname);

    }
}
