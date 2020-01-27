package com.example.daily_shopping_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private Button login_btn;
    private TextView reg_text_view;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_email=findViewById(R.id.email_login);
        login_password=findViewById(R.id.password_login);
        login_btn=findViewById(R.id.log_in_btn);
        reg_text_view=findViewById(R.id.sign_up_text_view);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

login_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String mMail;
        String mPassword;
        mMail=login_email.getText().toString().trim();
        mPassword=login_password.getText().toString().trim();



        if(TextUtils.isEmpty(mMail)){
            login_email.setError("Required Field");
            return;
        }
        if(TextUtils.isEmpty(mPassword)){
            login_password.setError("Required Field");
            return;
        }
        mDialog.setMessage("Processing..");
        mDialog.show();



    mAuth.signInWithEmailAndPassword(mMail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                FirebaseUser log_user=mAuth.getCurrentUser();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                mDialog.dismiss();
            }
            else{
                //Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                    return;

            }

        }
    });
    }
});


        reg_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
            }
        });

    }
}
