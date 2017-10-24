package com.example.phamm.appchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    EditText txtbuser, txtbpass;
    TextView txtvregister, txtvforgetAcount;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        txtvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,Register.class);
                startActivity(intent1);
            }
        });
    }
    void AnhXa(){
        txtbuser = (EditText)findViewById(R.id.txtbname);
        txtbpass =(EditText)findViewById(R.id.txtbpassword);
        btnlogin =(Button)findViewById(R.id.btnlogin);
        txtvregister = (TextView)findViewById(R.id.txtvregister);
        txtvforgetAcount = (TextView)findViewById(R.id.txtvchangepass);
        txtvforgetAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),":))",Toast.LENGTH_LONG).show();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnlogin.setEnabled(false);
                if(txtbuser.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(),"Please check username",Toast.LENGTH_LONG).show();
                    btnlogin.setEnabled(true);
                }
                else{
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {  // kiểm tra xem có con tên "anh" hay ko
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(txtbuser.getText().toString())) { //new co ton tai username
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User").child(txtbuser.getText().toString()).child("pass");
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {  // kiểm tra xem có con tên "anh" hay ko
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        if (snapshot.getValue().toString().equals(txtbpass.getText().toString())) {          ///////dang nhap thanh cong
                                            Toast.makeText(getApplicationContext(),"Dang nhap thanh cong",Toast.LENGTH_LONG).show();
                                            btnlogin.setEnabled(true);
                                            //Intent intent = new Intent(MainActivity.this,ScreenChat_group_friends_Activity.class);
                                           // startActivity(intent);
                                        }
                                        else{
                                            btnlogin.setEnabled(true);
                                            Toast.makeText(getApplicationContext(),"Dang nhap that bai",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(),"Dang nhap that bai",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else {
                                // new ko ton tai username
                                btnlogin.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Username not exist",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            btnlogin.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
