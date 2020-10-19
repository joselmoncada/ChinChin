package android.example.chinchin_prueba.ui.appUtils;

import android.content.Context;
import android.content.Intent;
import android.example.chinchin_prueba.MainActivity;
import android.example.chinchin_prueba.ui.login.LoginActivity;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    public static void print(String string) {
        /**for us to write less code*/
        System.out.println(string);
    }

    public void startDashboardActivity() {
        Intent dashBoard = new Intent(this, MainActivity.class);
        startActivity(dashBoard);

    }



    protected static boolean checkInternetConn(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                //NetworkInfo is deprecated. I'm doing the validation anyway
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {

                    print("internet status: " + ni.isConnected());

                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI
                            || ni.getType() == ConnectivityManager.TYPE_MOBILE)
                            || ni.getType() == (NetworkCapabilities.TRANSPORT_VPN));
                }
            } else {
                //the latest option
                final Network network = cm.getActiveNetwork();

                if (network != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(network);
                    print("internet status: \n");
                    print("cellular: " + nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) + "\n");
                    print("wifi: " + nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) + "\n");
                    print("VPN: " + nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN) + "\n");
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
                }
            }
        }

        return false;
    }

    protected void showToast(String string) {
        Toast.makeText(BaseActivity.this, string, Toast.LENGTH_SHORT).show();
    }

}
