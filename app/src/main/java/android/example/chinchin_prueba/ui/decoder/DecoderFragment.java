package android.example.chinchin_prueba.ui.decoder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.example.chinchin_prueba.R;

public class DecoderFragment extends Fragment{
    View root;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
       root = inflater.inflate(R.layout.fragment_decoder, container, false);

     //todo setear vistas
        return root;
    }

    private void readCode(){
        // todo leer codigo QR
    }


    public void getExchangeRates(){

    }
}