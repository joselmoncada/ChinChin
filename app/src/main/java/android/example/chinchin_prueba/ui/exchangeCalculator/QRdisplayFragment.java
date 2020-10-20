package android.example.chinchin_prueba.ui.exchangeCalculator;

import android.example.chinchin_prueba.ui.appUtils.BaseFragment;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.chinchin_prueba.R;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;


public class QRdisplayFragment extends BaseFragment {
    View root;
    ExchangeCalculatorModel exchangeCalculatorModel;
    ImageView qrImage;
    private Bitmap bitmap;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        bitmap= args.getParcelable("qrCode");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_qr_display, container, false);
        setViewElements();
        return root;
    }

    private void setViewElements() {
        qrImage = root.findViewById(R.id.imageQR);
        System.out.println("foto: "+ qrImage.toString());
        qrImage.setImageBitmap(bitmap);
        final TextView back = root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.goBackExchange);
            }
        });

        final Button saveQrButton = root.findViewById(R.id.saveImgButton);
        saveQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQR();
                showToast("Codigo guardado en galeria");
            }
        });
    }

   private void saveQR(){ //save the qr code in files
        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput("qrCode", getContext().MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}