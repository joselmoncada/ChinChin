package android.example.chinchin_prueba;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.example.chinchin_prueba.ui.appUtils.BaseActivity;
import android.example.chinchin_prueba.ui.appUtils.SharedPreferenceUtils;
import android.example.chinchin_prueba.ui.login.LoginActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends BaseActivity {

    Bitmap bitmap;
    public boolean checkInternetConnection(){
        return super.checkInternetConn(this);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_exchange_calculator, R.id.navigation_decoder, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navView, navController);
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();

        SharedPreferenceUtils.logoutUser();
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    public void saveQR(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("chinchin", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, "qrCode.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }
    }

    public void chargeCode(){

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File path1 = cw.getDir("chinchin", Context.MODE_PRIVATE);
            File f = new File(path1, "qrCode.png");
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(){
        chargeCode();
        return this.bitmap;
    }

}