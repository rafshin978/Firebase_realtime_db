package com.example.realtime_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText name,email,password;
    Button signup;
    FirebaseAuth fauth;
    FirebaseDatabase fd;
    String userId;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameid);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.pswdid);
        signup = findViewById(R.id.signbtn);
        fauth = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString().trim();
                String mail =email.getText().toString().trim();
                String passwd = password.getText().toString().trim();

                fauth.createUserWithEmailAndPassword(mail,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            userId = fauth.getCurrentUser().getUid();
                            Map<String,Object> user = new HashMap<>();
                            user.put("fname",username);
                            user.put("Email",mail);
                            user.put("Password",passwd);
                            fd.getReference("user").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignUp.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, "not registered", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });



            }
        });

    }
}