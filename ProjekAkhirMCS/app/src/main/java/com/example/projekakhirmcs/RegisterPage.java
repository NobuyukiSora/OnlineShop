package com.example.projekakhirmcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    //Variable
    EditText email, name, phone, pass;
    TextView alert;
    Button regis, back;
    FirebaseFirestore db;
    int idindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        db = FirebaseFirestore.getInstance();


        /////////////////////////////////////////////////////

        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        idindex++;
                        Log.d("idindex_1234", idindex + "");
                    }
                }
            }
        });

        ////////////////////////////////////////////////////

        email = findViewById(R.id.fillemailadd);
        name = findViewById(R.id.fillusername);
        phone = findViewById(R.id.fillphonenumber);
        pass = findViewById(R.id.fillpassword);
        regis = findViewById(R.id.register);
        back = findViewById(R.id.redirecttologin);
        alert = findViewById(R.id.alertregis);

        regis.setOnClickListener(this);
        back.setOnClickListener(this);

        ///////////////////////////////////////////////////////////

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validasi berisi
                if(pass.getText().toString().length()>0 && email.getText().toString().length()>0 &&
                    phone.getText().toString().length()>0 && name.getText().toString().length()>=3 && name.getText().toString().length()<=20) {

                    // Validasi Email
                    String reg = "^(.+)(.)com$";
                    Pattern patt = Pattern.compile(reg);
                    Matcher valid = patt.matcher(email.getText().toString());
                    if(valid.matches() == true){

                        //Validasi Pass
                        if(ValidPass(pass.getText().toString()) == 1){
                            //Insert Data///////////////////////////////////////////////////////////
                            Map<String, Object> data = new HashMap<>();
                            data.put("name", name.getText().toString());
                            data.put("email", email.getText().toString());
                            data.put("phone", phone.getText().toString());
                            data.put("password", pass.getText().toString());
                            String id = GetUserId();
                            data.put("id", id);


                            db.collection("User").document(id).set(data);

                            Intent i = new Intent(RegisterPage.this, MainActivity.class);
//                                i.putExtra("username",name.getText().toString());
//                                i.putExtra("useremail",email.getText().toString());
//                                i.putExtra("userphone",phone.getText().toString());
                            startActivity(i);
                        }
                    }
                    else{
                        alert.setText("Email should end with '.com'");
                    }
                }
                // Klo Kosong
                else{
                    alert.setText("Please fill the data");
                }
            }
        });
    }

//    int validation(){
//        for(User u : database.getUserdata()){
//            Pattern pattemail = Pattern.compile(u.getUseremail());
//            Matcher validemail = pattemail.matcher(email.getText().toString());
//
//            Pattern pattname = Pattern.compile(u.getUsername());
//            Matcher validname = pattname.matcher(name.getText().toString());
//            if(validemail.matches() == true || validname.matches() == true){
//               alert.setText("Please use other email & username");
//                return 0;
//            }
//        }
//        return 1;
//    }

    String GetUserId(){
        String num = "";

        if(idindex<10){
            num = ("00"+idindex);
            Log.d("num_1234", num);
        }
        else if(idindex>=10 && idindex<100){
            num = ("0"+idindex);
        }
        else if(idindex>=100 && idindex<1000){
            num = (idindex+"");
        }
        idindex = 0;
        return ("SU" + num);
    }

    int ValidPass(String pass){
        String AZ = "(.*[A-Z].*)";
        String az = "(.*[a-z].*)";
        String og = "(.*[0-9].*)";
        if(pass.matches(AZ) == false && pass.matches(az) == false){
            alert.setText("Your Password should use alphabet");
            return 0;
        }
        else if (pass.matches(og)==false){
            alert.setText("Your Password should use numeric");
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public void onClick(View view) {
        //Button Back
        if(view == back){
                Intent i = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(i);
        }
    }
}