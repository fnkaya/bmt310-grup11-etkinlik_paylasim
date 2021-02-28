package com.gazitf.etapp.giris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.gazitf.etapp.MainActivity;
import com.gazitf.etapp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayoutOtp;
    private TextInputLayout textInputLayoutVerificationCode;
    private TextInputEditText textInputVerificationCode;
    private Button buttonVerify;

    private FirebaseAuth auth;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        constraintLayoutOtp = findViewById(R.id.constraint_layout_otp);
        textInputLayoutVerificationCode = findViewById(R.id.text_input_layout_verification_code);
        textInputVerificationCode = findViewById(R.id.text_input_verification_code);
        buttonVerify = findViewById(R.id.button_verify);

        auth = FirebaseAuth.getInstance();
        otp = getIntent().getStringExtra("auth");

        initListeners();
    }

    private void initListeners() {
        buttonVerify.setOnClickListener(view -> {
            String verification_code = textInputVerificationCode.getText().toString();

            if (verification_code.length() == 6){
                textInputLayoutVerificationCode.setError(null);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp, verification_code);
                authWithPhoneNumber(credential);
            }
            else{
                textInputLayoutVerificationCode.setError("Please enter verification code");
            }
        });
    }

    private void authWithPhoneNumber(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    this.finishAffinity();
                })
                .addOnFailureListener(error -> {
                    Snackbar.make(constraintLayoutOtp, error.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                });
    }
}