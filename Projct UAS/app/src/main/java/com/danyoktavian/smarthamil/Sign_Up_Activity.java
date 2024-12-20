package com.danyoktavian.smarthamil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up_Activity extends AppCompatActivity {

    private TextInputEditText emailSignUp, passwordSignUp, confirmPasswordSignUp;
    private Button btnSignUp;
    private TextView tvSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        confirmPasswordSignUp = findViewById(R.id.confirmPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign In Activity
                finish(); // or startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    private void registerUser() {
        String email = emailSignUp.getText().toString().trim();
        String password = passwordSignUp.getText().toString().trim();
        String confirmPassword = confirmPasswordSignUp.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailSignUp.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordSignUp.setError("Password is required.");
            return;
        }

        if (password.length() < 6) {
            passwordSignUp.setError("Password must be at least 6 characters.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordSignUp.setError("Passwords do not match.");
            return;
        }

        // Register the user in Firebase
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(Sign_Up_Activity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                // Navigate to main or home activity
                // startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(Sign_Up_Activity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
