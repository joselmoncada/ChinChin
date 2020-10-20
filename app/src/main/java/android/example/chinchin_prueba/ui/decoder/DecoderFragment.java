package android.example.chinchin_prueba.ui.decoder;

import android.content.ContextWrapper;
import android.example.chinchin_prueba.MainActivity;
import android.example.chinchin_prueba.ui.appUtils.BaseFragment;
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
import androidx.navigation.Navigation;

import android.example.chinchin_prueba.R;

public class DecoderFragment extends BaseFragment {
    View root;
    Bitmap bitmap;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
                if(bitmap!=null) {
                    img.setImageBitmap(bitmap);
                }else{
                    showToast("No existe el codigo Qr");
                }
            }
        });

        final Button decodeQR = root.findViewById(R.id.decodeQRbutton);
        decodeQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap!=null){
                    Bundle args = new Bundle();
                    args.putParcelable("qrCode", bitmap);
                    Navigation.findNavController(v).navigate(R.id.showQRinfo,args);
                }
            }
        });
    }



}