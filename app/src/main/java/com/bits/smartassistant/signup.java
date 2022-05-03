package com.bits.smartassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signUp;
    TextView skip2;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.appbar_layout);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.EmailAddress2);
        phone = findViewById(R.id.Phone);
        password = findViewById(R.id.Password2);
        fAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signup1);
        skip2 = findViewById(R.id.skip2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                }
                String rname = name.getText().toString().trim();
                String remail = email.getText().toString().trim();
                String rphone = phone.getText().toString().trim();
                String rpassword = password.getText().toString().trim();

                if (rname.isEmpty()) {
                    name.setError("Name if required");
                    name.requestFocus();
                    return;
                }
                if (remail.isEmpty()) {
                    email.setError("Email if required");
                    email.requestFocus();
                    return;
                }
                if (rphone.isEmpty()) {
                    phone.setError("Phone if required");
                    phone.requestFocus();
                    return;
                }
                if (rpassword.isEmpty()) {
                    password.setError("Password if required");
                    password.requestFocus();
                    return;
                }
                fAuth.createUserWithEmailAndPassword(remail, rpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User user = new User(rname, remail);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(getApplicationContext(),login.class);
                                                startActivity(intent);
                                                Toast.makeText(signup.this, "User has been registered!", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(signup.this, "Failed to register! Please try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signup.this, "Failed to register! Please try again", Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });
                skip2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CaptureImage.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}