package android.example.chinchin_prueba.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.example.chinchin_prueba.R;
import android.example.chinchin_prueba.ui.appUtils.BaseActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends BaseActivity {
    private  String email, password, name;
    private FirebaseAuth firebaseAuth;
    private EditText emailEditText, passwordEditText, nameEditText;
    private TextInputLayout passwordLayout,emailLayout, nameLayout;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setViewElements();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setViewElements() {
        emailLayout =findViewById(R.id.emailLayout);
        emailEditText = findViewById(R.id.emailEditText);
        nameLayout =findViewById(R.id.namesLayout);
        nameEditText = findViewById(R.id.namesEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordLayout= findViewById(R.id.passwordLayout);
        progressBar = findViewById(R.id.progressBar);

    }

    public void register(View view){
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            emailLayout.setError("Campo obligatorio");
            return;
        }
        if(TextUtils.isEmpty(name)){
            emailLayout.setError("Campo obligatorio");
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordLayout.setError("Campo obligatorio");
            return;
        }

        if(password.length()<6){
            passwordLayout.setError("La contraseña debe tener mas de 6 caracteres");
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLayout.setError("Ingresa un correo valido");
            return;
        }

       if(checkInternetConn(getApplicationContext())){
           progressBar.setVisibility(View.VISIBLE);

           firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       showToast("Usuario registrado exitosamente");
                       finish();
                   }else{
                       showToast("Ha ocurrido un problema");
                       System.out.println("Error:" + task.getException().getMessage());
                   }
               }
           });
       }

    }

    public void goBack(View view) {
        finish();
    }


}