package android.example.chinchin_prueba.ui.login;

import android.content.Intent;
import android.example.chinchin_prueba.R;
import android.example.chinchin_prueba.ui.appUtils.AppConstants;
import android.example.chinchin_prueba.ui.appUtils.BaseActivity;
import android.example.chinchin_prueba.ui.appUtils.SharedPreferenceUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends BaseActivity {
    private  String email, password;
    private FirebaseAuth firebaseAuth;
    private EditText emailEditText, passwordEditText;
    private TextInputLayout passwordLayout,emailLayout;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        new SharedPreferenceUtils(this);
        setViewElements();
        boolean isUserLoggedIn = SharedPreferenceUtils.isUserLoggedIn();
        print("Usuario loggeado: "+SharedPreferenceUtils.isUserLoggedIn());
        if ( firebaseAuth.getCurrentUser()!= null || isUserLoggedIn) {
            startDashboardActivity();
            finish();
        }


    }





    private void setViewElements() {
        emailLayout =findViewById(R.id.emailLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordLayout= findViewById(R.id.passwordLayout);
        progressBar = findViewById(R.id.progressBar);

    }




    public void registrarme(View view) {
        Intent register= new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public void login(View view) {
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(email)){
           emailLayout.setError("Campo obligatorio");
           return;
        }

        if(TextUtils.isEmpty(password)){
            passwordLayout.setError("Campo obligatorio");
            return;
        }

        if(checkInternetConn(getApplicationContext())){
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        showToast("Bienvenido");
                        SharedPreferenceUtils.setEmail(email);
                        SharedPreferenceUtils.setUserNames(task.getResult().getUser().getDisplayName());
                        SharedPreferenceUtils.setUserLoggedIn(true);
                        startDashboardActivity();
                    }else{
                        progressBar.setVisibility(View.GONE);
                        System.out.println("Error: " + task.getException().getMessage());
                        showToast(AppConstants.NOT_REGISTERED_USER);
                    }
                }
            });
        }


    }



}