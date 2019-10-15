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
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String emailTAG = "EmailPassword";
    private static final String googleTAG = "GoogleActivity";
    private static final String facebookTAG = "FacebookLogin";
    private static final int RC_SIGN_IN = 9001;
    private EditText editTextMailAddress;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonLogout;
    private LoginButton faceBookLoginBtn;
    private Button faceBookLogoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CallbackManager callbackManager;
//    private GoogleSignInClient googleSignInClient;

    public void init() {
        editTextMailAddress = findViewById(R.id.editTextMailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogout = findViewById(R.id.buttonLogout);
        faceBookLoginBtn = findViewById(R.id.facebookLoginBtn);
        faceBookLoginBtn.setReadPermissions("email", "public_profile");
        faceBookLogoutBtn = findViewById(R.id.facebookLogoutBtn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        callbackManager = CallbackManager.Factory.create();
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
                    emailSignIn(mailAddress, password);
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
                updateUI(null);
                editTextMailAddress.setText("");
                editTextPassword.setText("");
            }
        });

        faceBookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(facebookTAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(facebookTAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(facebookTAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });

        faceBookLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                updateUI(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(facebookTAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(facebookTAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(facebookTAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
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

    private void emailSignIn(String email, String password) {
        Log.d(emailTAG, "signIn:" + email);
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
                            Log.d(emailTAG, "signInWithEmail:success");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(emailTAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void googleSigIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(googleTAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(googleTAG, "signInWithCredential:success");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(googleTAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                }
        });
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
            findViewById(R.id.buttonLogin).setVisibility(View.GONE);
            findViewById(R.id.textViewResetPass).setVisibility(View.GONE);
            findViewById(R.id.textViewLoginRe).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonLogout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.editTextMailAddress).setVisibility(View.VISIBLE);
            findViewById(R.id.editTextPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonLogin).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewResetPass).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewLoginRe).setVisibility(View.GONE);
            findViewById(R.id.buttonLogout).setVisibility(View.GONE);
        }
    }
}
