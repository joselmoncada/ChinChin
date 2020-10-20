package android.example.chinchin_prueba.ui.decoder;

import android.content.ContextWrapper;
import android.example.chinchin_prueba.MainActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.example.chinchin_prueba.R;

public class DecoderFragment extends Fragment{
    View root;
    Bitmap bitmap;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
       root = inflater.inflate(R.layout.fragment_decoder, container, false);

     setViewElements();

        return root;
    }

    private void setViewElements() {
        final Button chargeQR = root.findViewById(R.id.chargeQRButton);
        chargeQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = ((MainActivity)getActivity()).getBitmap();
                final ImageView img = root.findViewById(R.id.imageQR);
                img.setImageBitmap(bitmap);
            }
        });
    }

    private void readCode(){
        // todo leer codigo QR
    }




    public void getExchangeRates(){

    }
}