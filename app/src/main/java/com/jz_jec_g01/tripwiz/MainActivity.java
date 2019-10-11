package com.jz_jec_g01.tripwiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText editTextMailAddress;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonLogout;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView textViewSignup;


    public void init() {
        editTextMailAddress = findViewById(R.id.editTextMailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignup = findViewById(R.id.textViewSignup);
        buttonLogin = findViewById(R.id.buttonSignup);
        buttonLogout = findViewById(R.id.buttonLogout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        textViewSignup.setClickable(true);
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        InputFilter inputFilterMail = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(source.toString().matches("^[0-9a-zA-Z@¥.¥_¥¥-]$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };

        InputFilter[] filtersMail = new InputFilter[] { inputFilterMail };
        editTextMailAddress.setFilters(filtersMail);

        InputFilter inputFilterPass = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(source.toString().matches("^[0-9a-zA-Z]$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };

        InputFilter[] filtersPass = new InputFilter[] { inputFilterPass };
        editTextPassword.setFilters(filtersPass);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailAddress = editTextMailAddress.getText().toString();
                String password = editTextPassword.getText().toString();
//                EditText mailErr = findViewById(R.id.editTextMailAddress);
                EditText passErr = findViewById(R.id.editTextPassword);

                if(!mailAddress.isEmpty() && !password.isEmpty()) {
                    signIn(mailAddress, password);
                } else if(!mailAddress.isEmpty() && password.isEmpty()) {
                    passErr.setError("パスワードを入力してください");
                } else {
                    Toast.makeText(getApplicationContext(), "メールアドレスとパスワードが\n入力されていません", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
                editTextMailAddress.setText("");
                editTextPassword.setText("");
            }
        });





    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]




    }


    private boolean validateForm() {
        boolean valid = true;

        String email = editTextMailAddress.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextMailAddress.setError("Required.");
            valid = false;
        } else {
            editTextMailAddress.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            findViewById(R.id.editTextMailAddress).setVisibility(View.GONE);
            findViewById(R.id.editTextPassword).setVisibility(View.GONE);
            findViewById(R.id.buttonSignup).setVisibility(View.GONE);
            findViewById(R.id.textViewResetPass).setVisibility(View.GONE);
            findViewById(R.id.textViewLoginRe).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonLogout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.editTextMailAddress).setVisibility(View.VISIBLE);
            findViewById(R.id.editTextPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonSignup).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewResetPass).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewLoginRe).setVisibility(View.GONE);
            findViewById(R.id.buttonLogout).setVisibility(View.GONE);
        }
    }
}
