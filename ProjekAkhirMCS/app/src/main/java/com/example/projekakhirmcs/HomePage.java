package com.example.projekakhirmcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.Vector;

public class HomePage extends AppCompatActivity implements View.OnClickListener, BarangAdapter.ItemClickListener{

    Button home, transaction, profile, about;
    TextView username;
    private RecyclerView rv;
    BarangAdapter BA;
    FirebaseFirestore db;
    Database dbb;

    ////////////////////////////////////////////////////
    // Json -->


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ////////////////////////////////////////////////////
        // Json -->
        db = FirebaseFirestore.getInstance();
        get_barang();


        ///////////////////////////////////////////////////

        rv = findViewById(R.id.listbarang_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        BA = new BarangAdapter(Database.getDatabarang());
        BA.setICL(this);
        rv.setAdapter(BA);

        /////////////////////////////////////////////////////

        home = findViewById(R.id.btnhome);
        transaction = findViewById(R.id.btntransaction);
        profile = findViewById(R.id.btnprofile);
        about = findViewById(R.id.btnabout);
        username = findViewById(R.id.username);

        home.setOnClickListener(this);
        transaction.setOnClickListener(this);
        profile.setOnClickListener(this);
        about.setOnClickListener(this);

        ////////////////////////////////////////////////////

        // Get Name -->
        Bundle ext;
        ext = getIntent().getExtras();
        username.setText(""+ext.getString("username").toString());




    }

    @Override
    public void onClick(View view) {
        if(view == home){
//            Intent i = new Intent(HomePage.this,HomePage.class);
//            startActivity(i);
        }
        else if(view == transaction){
            Intent i = new Intent(HomePage.this,TransactionHistoryPage.class);
            startActivity(i);
        }
        else if(view == profile){
            Bundle ext;
            ext = getIntent().getExtras();
            String uname = ext.getString("username").toString();
            String uemail = ext.getString("useremail").toString();
            String uphone = ext.getString("userphone").toString();

            Intent i = new Intent(HomePage.this,ProfilePage.class);

            i.putExtra("username",uname);
            i.putExtra("useremail",uemail);
            i.putExtra("userphone",uphone);
            i.putExtra("userid",ext.getString("userid").toString());
            startActivity(i);
        }
        else if(view == about){
            Intent i = new Intent(HomePage.this,AboutPage.class);
            startActivity(i);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String linkname = BA.getItem(position).getNamabarang().toString();
        String linkrating = BA.getItem(position).getRatingbarang().toString();

        Log.d("getNameLink", linkname);
        Intent i = new Intent(HomePage.this, FurnitureDetailPage.class);
        i.putExtra("linknama",linkname);
        i.putExtra("linkrating",linkrating);

        startActivity(i);
    }

    void get_barang(){
        db.collection("object").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d("getName", doc.get("product_name").toString());
                        String nama = doc.get("product_name").toString();
                        String rating = doc.get("rating").toString();
                        String harga = doc.get("price").toString();

                        dbb.getDatabarang().add(new Barang(nama, rating, harga));
                    }
                }
            }
        });
    }
}