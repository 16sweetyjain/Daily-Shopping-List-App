package com.example.daily_shopping_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daily_shopping_list.Model.Data;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {

    private EditText edit_type;
    private EditText edit_amount;
    private Button save_btn_101;
    private EditText edit_note;
    private int amount;
  //  private DatabaseReference mReference;
    private Firebase mReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edit_type = findViewById(R.id.edt_type);
        edit_amount = findViewById(R.id.edt_amount);
        save_btn_101 = findViewById(R.id.edt_save_btn);
        mAuth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);

        mReference=new Firebase("https://daily-shopping-list-a43fc.firebaseio.com/");

       // database=FirebaseDatabase.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        edit_note = findViewById(R.id.edt_note);

      //  mReference=database.getReference();

     //   String uid = mUser.getUid();
     //   mReference = FirebaseDatabase.getInstance().getReference().child(uid);

        save_btn_101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mType = edit_type.getText().toString().trim();
                String mAmount = edit_amount.getText().toString().trim();
                String mNote = edit_note.getText().toString().trim();

                amount = Integer.parseInt(mAmount);


                if (TextUtils.isEmpty(mType)) {
                    edit_type.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(mAmount)) {
                    edit_amount.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(mNote)) {
                    edit_note.setError("Required Field");
                    return;
                }

                String id = mReference.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());


                Data mData = new Data(mType, mNote, id, amount, date);
                Toast.makeText(getApplicationContext(), "Data Added Successfully", Toast.LENGTH_LONG).show();


                mReference.child(id).setValue(mData);

            }
        });


    }
}
