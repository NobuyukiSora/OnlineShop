package com.example.projekakhirmcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FurnitureDetailPage extends AppCompatActivity implements View.OnClickListener {

    TextView productname, productprice, productrating, productdescription;
    TextView alert;
    Button buy;
    EditText quantity;
    FirebaseFirestore db;
    String nama, rating, harga, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_detail_page);

        db = FirebaseFirestore.getInstance();

        Bundle ext;
        ext = getIntent().getExtras();
        nama = ext.getString("linknama").toString();
        get_product();

        productname = findViewById(R.id.productname);
        productprice = findViewById(R.id.productprice);
        productrating = findViewById(R.id.rating);
        productdescription = findViewById(R.id.productdescription);

//        alert = findViewById(R.id.alertquantity);
//        buy = findViewById(R.id.furniturebuy1);
//        quantity = findViewById(R.id.furniturequantity1);
//        buy.setOnClickListener(this);

        productname.setText(nama);
        productrating.setText(rating);
        productdescription.setText(desc);
        productprice.setText(harga);
    }

    @Override
    public void onClick(View view) {
        if(view == buy){
            int getquantity = Integer.parseInt(quantity.getText().toString());
            // kalo quantity 0
            if(getquantity == 0){
                alert.setText("Quantity can't be 0");
            }
            // pasing value ke database
            else{
                alert.setText("");

            }
        }
    }

    void get_product(){
        Pattern patte = Pattern.compile(nama);
        db.collection("object").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d("getName", doc.get("product_name").toString());
                        Matcher valid = patte.matcher(doc.get("productname").toString());
                        if(valid.matches() == true){
                            nama = doc.get("product_name").toString();
                            rating = doc.get("rating").toString();
                            harga = doc.get("price").toString();
                            desc = doc.get("description").toString();
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        });
    }
}