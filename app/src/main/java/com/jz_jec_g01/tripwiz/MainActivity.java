package com.jz_jec_g01.tripwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMailAddress;
    private EditText editTextPassword;
    private Button buttonLogin;

    public void init() {
        editTextMailAddress = findViewById(R.id.editTextMailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
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
//
//        InputFilter inputFilterPass = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end,
//                                       Spanned dest, int dstart, int dend) {
//                if(source.toString().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")) {
//                    return source;
//                } else {
//                    return "";
//                }
//            }
//        };

//        InputFilter[] filtersPass = new InputFilter[] { inputFilterPass };
//        editTextPassword.setFilters(filtersPass);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailAddress = editTextMailAddress.getText().toString();
                String password = editTextPassword.getText().toString();
//                EditText mailErr = findViewById(R.id.editTextMailAddress);
                EditText passErr = findViewById(R.id.editTextPassword);

                if(!mailAddress.isEmpty() && !password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ログインします", Toast.LENGTH_SHORT).show();
                } else if(!mailAddress.isEmpty() && password.isEmpty()) {
                    passErr.setError("パスワードを入力してください");
                } else {
                    Toast.makeText(getApplicationContext(), "メールアドレスとパスワードが\n入力されていません", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
