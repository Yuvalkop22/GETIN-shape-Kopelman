package com.example.yalla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {
    private Button btnlogin;
    EditText inputEmail,inputPassword;
    String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String name;
    private TextView TosignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.etemail);
        inputPassword = findViewById(R.id.mypass);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        TosignUp = findViewById(R.id.createnewac);

        TosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(loginActivity.this,signUpActivity.class);
                startActivity(it);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //performLogin();
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            if (snapshot.getValue(User.class).getEmail().equals(inputEmail.getText().toString()) &&
                                    snapshot.getValue(User.class).getPassword().equals(inputPassword.getText().toString())) {
                                if (inputEmail.getText().toString().equals("Kopelman050@gmail.com") &&
                                        inputPassword.getText().toString().equals("Kopelman1")) {
                                    Toast.makeText(loginActivity.this, "Manager Logged In", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(loginActivity.this, MainActivity3.class);
                                    startActivity(it);
                                }
                                else if (!(inputEmail.getText().toString().equals("Kopelman050@gmail.com"))){
                                    Toast.makeText(loginActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(loginActivity.this,MainActivity2.class);
                                    startActivity(it);
                                }
                            }
                        else
                            Toast.makeText(loginActivity.this, "No User Found", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String managerEmail = "Kopelman050@gmail.com";
        String managerPassword = "Kopelman1";

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter current Email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password");
        } else {
            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (email.equals(managerEmail) && password.equals(managerPassword)) {
                            progressDialog.dismiss();
                            sendManagerToNextActivity();
                            Toast.makeText(loginActivity.this, "Manager Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(loginActivity.this,MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void sendManagerToNextActivity() {
        Intent intent = new Intent(loginActivity.this,MainActivity3 .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}