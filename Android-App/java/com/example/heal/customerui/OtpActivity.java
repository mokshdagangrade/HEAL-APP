package com.example.heal.customerui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heal.CustomerHomeActivity;
import com.example.heal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    EditText phonenumber, otprec;
    String phone;
    Button submit, sendotp;
    private FirebaseAuth mAuth;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phonenumber=findViewById(R.id.phonenumber);
        //submit=(Button)findViewById(R.id.submit);
        sendotp=(Button)findViewById(R.id.sendotp);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=phonenumber.getText().toString();
                if (phone.trim().length() == 10) {
                    sendSMS("+91" + phone.trim());
                } else {
                    Toast.makeText(OtpActivity.this, "Enter Valid PhoneNumber", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendSMS(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
         /*   String code = credential.getSmsCode();
            if(code!=null)
                verifyCode(code);*/
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            phonenumber.setText("");

            mVerificationId = verificationId;
            PhoneAuthProvider.ForceResendingToken mResendToken = token;

            showDialog();

            // ...
        }
    };
   /* private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }*/

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(OtpActivity.this, CustomerHomeActivity.class));
                            finish();
                            Toast.makeText(OtpActivity.this,"Verification Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpActivity.this,"Verification Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /*public void onClickSubmit(View view) {
        String code = otprec.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }*/
    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittextCode = new EditText(OtpActivity.this);
        alert.setMessage("Enter Your OTP below");
        alert.setTitle("OTP Verification");

        alert.setView(edittextCode);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValueString = edittextCode.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, YouEditTextValueString);
                signInWithPhoneAuthCredential(credential);

            }
        });
        alert.show();
    }
}