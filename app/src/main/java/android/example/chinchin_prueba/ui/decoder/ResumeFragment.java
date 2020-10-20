package android.example.chinchin_prueba.ui.decoder;

import android.example.chinchin_prueba.models.OperationResume;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.chinchin_prueba.R;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import org.json.JSONObject;


public class ResumeFragment extends Fragment { // Decodifica el QR y muestra la informacion
    Bitmap bitmap;
    Result info;
    View root;
    OperationResume operationResume;


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
        root = inflater.inflate(R.layout.fragment_resume, container, false);
        setViewItems();
        decodeQR();
        return root;
    }

    private void setViewItems() {
        final TextView back = root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.goBackExchange);
            }
        });

        final Button recalculateButton = root.findViewById(R.id.recalculateButton);
        recalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.recalculate);
            }
        });
    }

    private  Result decodeQR(){

        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        bitmap.recycle();
        bitmap = null;
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        try
        {
            Result result = reader.decode(bBitmap);
            operationResume = new Gson().fromJson(result.getText(), OperationResume.class);
            System.out.println("QR info: " + operationResume.toString());
            setValues();
            return result;
        }
        catch (NotFoundException e)
        {
            Log.e("QR", "decode exception", e);
            return null;
        }
    }

    private void setValues() {
        //CURRENCIES
        ((TextView) root.findViewById(R.id.currency)).setText(operationResume.getCurrency());
        //RATES
        ((TextView) root.findViewById(R.id.USD_rate)).setText(operationResume.getUSDrate());
        ((TextView) root.findViewById(R.id.BTC_rate)).setText(operationResume.getBTCrate());
        ((TextView) root.findViewById(R.id.BS_rate)).setText(operationResume.getBSrate());
        ((TextView) root.findViewById(R.id.PTR_rate)).setText(operationResume.getPTRrate());
        ((TextView) root.findViewById(R.id.ETH_rate)).setText(operationResume.getETHrate());
        ((TextView) root.findViewById(R.id.EUR_rate)).setText(operationResume.getEURrate());
        //EXCHANGES
        ((TextView) root.findViewById(R.id.USDexchange)).setText(operationResume.getUSDvalue());
        ((TextView) root.findViewById(R.id.BTCexchange)).setText(operationResume.getBTCvalue());
        ((TextView) root.findViewById(R.id.BSexchange)).setText(operationResume.getBSvalue());
        ((TextView) root.findViewById(R.id.PTRexchange)).setText(operationResume.getPTRvalue());
        ((TextView) root.findViewById(R.id.ETHexchange)).setText(operationResume.getETHvalue());
        ((TextView) root.findViewById(R.id.EURexchange)).setText(operationResume.getEURvalue());
    }
}