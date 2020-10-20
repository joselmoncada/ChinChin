package android.example.chinchin_prueba.ui.exchangeCalculator;

import android.content.Intent;
import android.example.chinchin_prueba.MainActivity;
import android.example.chinchin_prueba.models.OperationResume;
import android.example.chinchin_prueba.models.RateResponse;
import android.example.chinchin_prueba.retrofit.AppInterface;
import android.example.chinchin_prueba.retrofit.RetroClient;
import android.example.chinchin_prueba.ui.appUtils.AppConstants;
import android.example.chinchin_prueba.ui.appUtils.BaseFragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.example.chinchin_prueba.R;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeCalculator extends BaseFragment {
    private BigDecimal USD,ETH,EUR,BS,PTR, BTC, amount;
    private String USDexchange, ETHexchange , EURexchange, BSexchange , PTRexchange, BTCexchange , currency = "USD";
    private TextView USDtv, ETHtv , EURtv, BStv , PTRtv, BTCtv;
    private View root;
    private TextInputLayout amountLayout;
    private TextInputEditText amountEditText;
    private Bitmap bitmap;
    private AutoCompleteTextView currencyTypeDropdown;
    private ProgressBar progressBar;

    final MathContext mathContext= new MathContext(2, RoundingMode.HALF_UP);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //siempre inicia en $$
        BS = new BigDecimal(0.0000022,  mathContext);
        PTR = new BigDecimal(42.93,  mathContext);
        ETH = new BigDecimal(370.41,  mathContext);

    }

    private void fetchExchangeRates(String base) {
        if (((MainActivity)getActivity()).checkInternetConnection()) {
            AppInterface service = RetroClient.getApiService();
            Call<RateResponse> call= service.getCurrencyRates(base);
            call.enqueue(new Callback<RateResponse>() {
                @Override
                public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                    if(response.isSuccessful()){
                        RateResponse rateData = response.body();
                        System.out.println("RESPONSE: " + response.body().toString());
                        System.out.println("isSuccess: " + response.body().getSuccess());
                        USD = new BigDecimal(rateData.getRates().getUSD(), mathContext);
                        BTC = new BigDecimal(rateData.getRates().getBTC(), mathContext);
                        EUR = new BigDecimal(response.body().getRates().getEUR(), mathContext);
                        setRates();
                    }else{
                        System.out.println("Error: " + response.errorBody());
                        showToast("Ha ocurrido un error");
                    }

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<RateResponse> call, Throwable t) {
                        showToast("Error de conexion, intenta mas tarde");
                        System.out.println("Error conexion: " + t.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else {
            showToast("Usted no tiene conexión a internet, intente más tarde");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setRates() {
        //set rates
        ((TextView) root.findViewById(R.id.USD_rate)).setText(USD.toString());
        ((TextView) root.findViewById(R.id.BTC_rate)).setText(BTC.toString());
        ((TextView) root.findViewById(R.id.BS_rate)).setText(BS.toString());
        ((TextView) root.findViewById(R.id.PTR_rate)).setText(PTR.toString());
        ((TextView) root.findViewById(R.id.ETH_rate)).setText(ETH.toString());
        ((TextView) root.findViewById(R.id.EUR_rate)).setText(EUR.toString());
        progressBar.setVisibility(View.GONE);
    }

    private void calculateExchanges() {
        //calculo el cambio
        USDexchange = amount.divide(USD , mathContext ) + " USD";
        ETHexchange = amount.divide(ETH, mathContext) + " ETH";
        EURexchange = amount.divide(EUR, mathContext) + " EUR";
        BSexchange  = amount.divide(BS, mathContext) +" BS";
        PTRexchange = amount.divide(PTR, mathContext) + " PTR";
        BTCexchange = amount.divide(BTC, mathContext) +" BTC";
        //seteo la informacion
        ETHtv.setText(ETHexchange);
        BTCtv.setText(BTCexchange);
        EURtv.setText(EURexchange);
        BStv.setText(BSexchange);
        PTRtv.setText(PTRexchange);
        USDtv.setText(USDexchange);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_exchange_calculator, container, false);
        setViewElements();
        fetchExchangeRates("USD");
        return root;
    }

    private void setViewElements() {
        progressBar = root.findViewById(R.id.progressBar);
        ETHtv =  root.findViewById(R.id.ETHexchange);
        USDtv = root.findViewById(R.id.USDexchange);
        BTCtv = root.findViewById(R.id.BTCexchange);
        EURtv = root.findViewById(R.id.EUROexchange);
        BStv = root.findViewById(R.id.BSexchange);
        PTRtv = root.findViewById(R.id.PTRexchange);
        amountEditText = root.findViewById(R.id.amountEditText);
        amountLayout = root.findViewById(R.id.amountLayout);
        ArrayAdapter currencyAdapter = new ArrayAdapter(getContext(), R.layout.list_item, AppConstants.getCurrencyTypes()); //setting currency type values for the Dropdown
        currencyTypeDropdown = root.findViewById(R.id.currencyDropdown);
        currencyTypeDropdown.setAdapter(currencyAdapter);
        currencyTypeDropdown.setListSelection(0);
        currencyTypeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                currency = currencyTypeDropdown.getText().toString();
                switch (currency){
                    case "USD":
                        BS = new BigDecimal(0.00001, mathContext);
                        PTR = new BigDecimal(60, mathContext);
                        ETH = new BigDecimal(370.41, mathContext);
                        fetchExchangeRates(currency);
                        break;
                    case "EUR":
                        BS = new BigDecimal(0.0000019, mathContext);
                        PTR = new BigDecimal(50.84, mathContext);
                        ETH = new BigDecimal(312.11, mathContext);
                        fetchExchangeRates(currency);
                        break;
                    case "BTC":
                        BS = new BigDecimal(1.9e-10, mathContext);
                        PTR = new BigDecimal(0.0036, mathContext);
                        ETH = new BigDecimal(0.031, mathContext);
                        fetchExchangeRates(currency);
                        break;
                    case "PTR":
                        BS = new BigDecimal(9.49e-5, mathContext);
                        PTR = new BigDecimal(1, mathContext);
                        ETH = new BigDecimal(0.12, mathContext);
                        BTC = new BigDecimal(0.0036, mathContext);
                        USD = new BigDecimal(0.016, mathContext);
                        EUR = new BigDecimal(0.019, mathContext);
                        setRates();
                        break;
                    case "BS":
                        //precio de monedas en bs
                        BS = new BigDecimal(1, mathContext);
                        PTR = new BigDecimal(19416294.54, mathContext);
                        ETH = new BigDecimal(167211699.30, mathContext);
                        BTC = new BigDecimal(5377540192.00, mathContext);
                        USD = new BigDecimal(100000, mathContext);
                        EUR = new BigDecimal(534300.88, mathContext);
                        setRates();
                        break;
                    case "ETH":
                        BS = new BigDecimal(6.0e-9, mathContext);
                        PTR = new BigDecimal(0.11, mathContext);
                        ETH = new BigDecimal(1, mathContext);
                        BTC = new BigDecimal(32.31, mathContext);
                        USD = new BigDecimal(0.0027, mathContext);
                        EUR = new BigDecimal(0.0032, mathContext);
                        setRates();
                        break;
                    default:
                        break;

                }
            }
        });

        currencyTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button generateQRbutton = root.findViewById(R.id.generateQRButton);
        generateQRbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(amountEditText.getText().toString())){
                    if(Double.parseDouble(amountEditText.getText().toString())>0 ){
                        generateQRcode();
                        Bundle args = new Bundle();
                        args.putParcelable("qrCode", bitmap);
                        Navigation.findNavController(v).navigate(R.id.showQR,args);
                        return;
                    }
                }
                amountLayout.setError("Ingrese un monto válido");
            }
        });
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if( !TextUtils.isEmpty(s.toString())) {
                    if (Double.parseDouble(s.toString()) > 0) {
                        amount = new BigDecimal(s.toString());
                        calculateExchanges();
                        return;
                    }
                }

                    amountLayout.setError("Ingrese un monto válido");

            }
        });


    }

    private void generateQRcode() {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                OperationResume info = new OperationResume();
                info.setBSrate(BS.toString());
                info.setBSvalue(BSexchange);
                info.setBTCrate(BTC.toString());
                info.setBTCvalue(BTCexchange);
                info.setETHrate(ETH.toString());
                info.setETHvalue(ETHexchange);
                info.setEURrate(EUR.toString());
                info.setEURvalue(EURexchange);
                info.setUSDrate(USD.toString());
                info.setUSDvalue(USDexchange);
                info.setEURvalue(USDexchange);
                info.setPTRrate(PTR.toString());
                info.setPTRvalue(PTRexchange);
                info.setCurrency(currency);


                String gsonObj = new Gson().toJson(info);
                System.out.println("GSON: "+ gsonObj);
                BitMatrix bitMatrix = qrCodeWriter.encode(gsonObj, BarcodeFormat.QR_CODE, 200, 200);
                bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
                for (int x = 0; x<200; x++){
                    for (int y=0; y<200; y++){
                        bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}