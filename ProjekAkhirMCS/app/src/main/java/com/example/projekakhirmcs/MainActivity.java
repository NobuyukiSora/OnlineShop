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

import java.text.BreakIterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Variable
    EditText email, pass;
    Button login, register;
    TextView alert;
    String getusername, getphone;
    FirebaseFirestore db;
    int valid = 0;
    String name, phone, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.inemail);
        pass = findViewById(R.id.inpassword);
        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregis);
        alert = findViewById(R.id.textView2);

        db = FirebaseFirestore.getInstance();

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Onclick Login
        if(view == login){
            // kalau email & pass berisi
            if(pass.getText().toString().length()>0 && email.getText().toString().length()>0) {
                // validasi udh regis
                Log.d("1234", "sucess1 " + valid);
                validation();
                Log.d("1234", "sucess2 " + valid);
                if(valid==1){
                    valid=0;
                Log.d("1234", "sucess3");
                    Intent i = new Intent(MainActivity.this, HomePage.class);
                    i.putExtra("username",name);
                    i.putExtra("useremail",email.getText().toString());
                    i.putExtra("userphone",phone);
                    i.putExtra("userid", id);
                    startActivity(i);
                }
            }
            // kalau email & pass kosong
            else {
                alert.setText("Please fill Email & Password correctly");
            }
        }
        // Onclick regis
        else if(view == register){
            Intent i = new Intent(MainActivity.this,RegisterPage.class);
            startActivity(i);
        }
    }
    int validation(){
        String rege = email.getText().toString();
        Pattern patte = Pattern.compile(rege);
        String regp = pass.getText().toString();
        Pattern pattp = Pattern.compile(regp);
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot doc : task.getResult()){
                    Matcher valide = patte.matcher(doc.get("email").toString());
                    if(valide.matches() == true){
                        Log.d("1234email", email.getText().toString() + " - " + doc.get("email").toString());
                        Matcher validp = pattp.matcher(doc.get("password").toString());
                        if(validp.matches() == true){
                            Log.d("1234pass", pass.getText().toString() + " - " + doc.get("password").toString());
                            name = doc.get("name").toString();
                            phone = doc.get("phone").toString();
                            id = doc.get("id").toString();
                            valid = 1;
                        }
                        else if (validp.matches() != true){
//                            alert.setText("Password not correct");
                            break;
                        }
                    }
                    else if (valide.matches() != true){
//                        alert.setText("Email not correct");
                        break;
                    }

//                    Log.d("getName", doc.get("name").toString());
//                    Log.d("getAge", doc.get("age").toString());
                }
            }
        }
        });
        return 0;
    }


//    void getphonenum() {
//        for (User u : database.getUserdata()) {
//            Pattern pattemail = Pattern.compile(u.getUseremail());
//            Matcher validemail = pattemail.matcher(email.getText().toString());
//            if (validemail.matches() == true) {
//                getphone = u.getUserphone();
//                break;
//            }
//        }
//    }

//    void getusername(){
//        for(User u : database.getUserdata()){
//            Pattern pattemail = Pattern.compile(u.getUseremail());
//            Matcher validemail = pattemail.matcher(email.getText().toString());
//            if(validemail.matches() == true ){
//                getusername = u.getUsername();
//                break;
//            }
//        }
//    }
}