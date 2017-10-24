package com.example.phamm.appchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.phamm.appchat.R.id.btnlogin;

public class Register extends AppCompatActivity {
    EditText txtbusername, txtbpass, txtbconfirmpass, txtbemail;
    TextView txtvlogin;
    Button btnregister;
    int RESULT_LOAD_IMG=1;
    FirebaseDatabase database;
    DatabaseReference myref;
    String stringbmapavatar;
    User newuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        AnhXa();
    }

    private void AnhXa() {
        txtbusername = (EditText)findViewById(R.id.txtbusername);
        txtbpass = (EditText)findViewById(R.id.txtbpass);
        txtbconfirmpass = (EditText)findViewById(R.id.txtbconfirmpass);
        btnregister = (Button)findViewById(btnlogin);
        txtvlogin = (TextView)findViewById(R.id.txtvlogin);
        txtbemail = (EditText)findViewById(R.id.txtbemail);
        btnregister = (Button)findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnregister.setEnabled(false);
                if(txtbusername.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "Please check username!",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    return;
                }
                if(txtbpass.getText().toString().length()<6 || txtbpass.getText().toString().length()>32){
                    Toast.makeText(getApplicationContext(), "Your password be between 6-32 character",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    return;
                }
                if(txtbpass.getText().toString().equals(txtbconfirmpass.getText().toString())== false  ){
                    Toast.makeText(getApplicationContext(), "Your password not match!",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    return;
                }
                if(txtbemail.getText().toString().length()<4){
                    Toast.makeText(getApplicationContext(), "Please email is corect !",Toast.LENGTH_LONG).show();
                    btnregister.setEnabled(true);
                    return;
                }
                Log.d("kiemtra",txtbemail.getText().toString().length()+"");

                myref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(txtbusername.getText().toString())) {
                            Toast.makeText(getApplicationContext(),"Username is exist",Toast.LENGTH_LONG).show();
                            btnregister.setEnabled(true);
                        }
                        else{ ///neu chua co

                            newuser = new User(txtbusername.getText().toString(),txtbemail.getText().toString(),txtbpass.getText().toString());
                            myref.child("User").child(newuser.userName).setValue(newuser);

                            myref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.hasChild(newuser.userName)) {
                                        Toast.makeText(getApplicationContext(),"Sign up success",Toast.LENGTH_LONG).show();
                                        btnregister.setEnabled(true);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Sign up fail",Toast.LENGTH_LONG).show();
                                        btnregister.setEnabled(true);
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    btnregister.setEnabled(true);

                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        btnregister.setEnabled(true);
                    }
                });

            }
        });

        txtvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
