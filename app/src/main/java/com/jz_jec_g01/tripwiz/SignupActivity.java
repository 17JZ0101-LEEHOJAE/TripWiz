package com.jz_jec_g01.tripwiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText editTextName;
    private FirebaseAuth mAuth;
    final String url = "http://10.210.20.161";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();
    private Button btnSignUp;
    private String NatioSpinners[] = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String AgeSpinners[] = {"10代", "20代", "30代", "40代", "50代"};
    private RadioGroup genderGroup;
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSignUp = findViewById(R.id.buttonSignup);
        mAuth = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.buttonSignup);
        inputEmail = findViewById(R.id.editTextMailAddress);
        inputPassword = findViewById(R.id.editTextPassword);
//        genderGroup = findViewById(R.id.radioGroupGender);
        editTextName = findViewById(R.id.editTextName);
        Spinner Natiospinner = findViewById(R.id.NatioSpinner);
        Spinner Agespinner = findViewById(R.id.AgeSpinner);
/*************************Spinner***************************/
        //ArrayAdapter
        ArrayAdapter<String> NatioAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, NatioSpinners);
        ArrayAdapter<String> AgeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, AgeSpinners);

        NatioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinner に adapter をセット
        Natiospinner.setAdapter(NatioAdapter);
        Agespinner.setAdapter(AgeAdapter);
        //NATIOリスナーに登録
        Natiospinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            //アイテムが選択された時の処理
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "国籍が\n選択されていません", Toast.LENGTH_SHORT).show();
            }
        });
        //AGEリスナーに登録
        Agespinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            //アイテムが選択された時の処理
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "年齢が\n選択されていません", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String nationality = Natiospinner.getSelectedItem().toString();
                String age = Agespinner.getSelectedItem().toString();
                String name = editTextName.getText().toString().trim();
                int checkedId = genderGroup.getCheckedRadioButtonId();
                int gender = 0;
                if(-1 != checkedId) {
                    View radioButton = genderGroup.findViewById(checkedId);
                    int radioId = genderGroup.indexOfChild(radioButton);
                    RadioButton btnRadio = (RadioButton) genderGroup.getChildAt(radioId);
                    String selection = (String) btnRadio.getText();
                    if(selection.equals("男性")) {
                        gender = 0;
                    } else if(selection.equals("女性")) {
                        gender = 1;
                    }
                }

                final MediaType JSON = MediaType.get("application/json; charset=utf-8");
                String json = "{\"mailAddress\":\"" + email + "\", \"password\":\"" + password + "\", " +
                        "\"gender\":\"" + gender + "\", \"age\":\"" + age + "\", \"nationality\":\"" + nationality + "\", " +
                        "\"name\":\"" + name + "\"}";
                RequestBody body = RequestBody.create(json, JSON);

                try {
                    if(validateForm() == true) {
                        client.newCall(request).enqueue(new Callback() {
                            final Handler mHandler = new Handler(Looper.getMainLooper());


                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String urlS = url + "/Signup.php";
                                Request request = new Request.Builder()
                                        .url(urlS)
                                        .post(body)
                                        .build();

                                OkHttpClient client = new OkHttpClient.Builder().build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "会員登録成功", Toast.LENGTH_SHORT).show();
                                                createAccount(email, password);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                } catch(NetworkOnMainThreadException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        //showProgressDialog();
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignupActivity.this, ProfilePageActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed." + ((FirebaseAuthWeakPasswordException)task.getException()).getReason(), //画面にエラーを表示する
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        // [END create_user_with_email]
    }

    /**
     *入力内容のフォームが正しいかどうかチェックするメソッド
     *  @return　false 正しくなかったら登録しない
     *  @return　true 入力内容正しかったら登録する
     *
     */

    private boolean validateForm() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}