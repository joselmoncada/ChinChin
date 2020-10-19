package android.example.chinchin_prueba.ui.exchangeCalculator;

import android.example.chinchin_prueba.MainActivity;
import android.example.chinchin_prueba.models.RateResponse;
import android.example.chinchin_prueba.retrofit.AppInterface;
import android.example.chinchin_prueba.retrofit.RetroClient;
import android.example.chinchin_prueba.ui.appUtils.BaseFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.example.chinchin_prueba.R;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeCalculator extends BaseFragment {
    private BigDecimal USD,ETH,EUR,BS,PTR, BTC, amount;
    private String USDexchange, ETHexchange , EURexchange, BSexchange , PTRexchange, BTCexchange;
    private TextView USDtv, ETHtv , EURtv, BStv , PTRtv, BTCtv;
    private View root;
    private ExchangeCalculatorModel exchangeCalculatorModel;
    private TextInputLayout amountLayout;
    private TextInputEditText amountEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchExchangeRates("USD");
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
                        USD = new BigDecimal(rateData.getRates().getUSD());
                        BTC = new BigDecimal(rateData.getRates().getBTC());
                        BS = new BigDecimal(2);
                        PTR = new BigDecimal(3);
                        ETH = new BigDecimal(4);
                        EUR = new BigDecimal(response.body().getRates().getEUR());

                    }else{
                        System.out.println("Error: " + response.errorBody());
                        showToast("Ha ocurrido un error");
                    }
                }

                @Override
                public void onFailure(Call<RateResponse> call, Throwable t) {
                        showToast("Error de conexion, intenta mas tarde");
                }
            });
        }else {
            showToast("Usted no tiene conexión a internet, intente más tarde");
        }
    }

    private void calculateExchanges() {
        //calculo el cambio
        USDexchange = amount.divide(USD,  new MathContext(5, RoundingMode.HALF_UP)) + " USD";
        ETHexchange = amount.divide(ETH, new MathContext(5, RoundingMode.HALF_UP)) + " ETH";
        EURexchange = amount.divide(EUR, new MathContext(5, RoundingMode.HALF_UP)) + " EUR";
        BSexchange  = amount.divide(BS, new MathContext(5, RoundingMode.HALF_UP)) +" BS";
        PTRexchange = amount.divide(PTR, new MathContext(5, RoundingMode.HALF_UP)) + " PTR";
        BTCexchange = amount.divide(BTC, new MathContext(5, RoundingMode.HALF_UP)) +" BTC";
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
        exchangeCalculatorModel =
                new ViewModelProvider(this).get(ExchangeCalculatorModel.class);
        root = inflater.inflate(R.layout.fragment_exchange_calculator, container, false);
        setViewElements();
        return root;
    }

    private void setViewElements() {
        final Button generateQRbutton = root.findViewById(R.id.generateQRButton);
        ETHtv =  root.findViewById(R.id.ETHexchange);
        BTCtv = root.findViewById(R.id.BTCexchange);
        EURtv = root.findViewById(R.id.EUROexchange);
        BStv = root.findViewById(R.id.BSexchange);
        PTRtv = root.findViewById(R.id.PTRexchange);
        amountEditText = root.findViewById(R.id.amountEditText);
        amountLayout = root.findViewById(R.id.amountLayout);

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Double.parseDouble(s.toString())>0 && !TextUtils.isEmpty(s.toString())){
                    amount = new BigDecimal(s.toString());
                    calculateExchanges();
                }else{
                    amountLayout.setError("Ingrese un monto válido");
                }

            }
        });

        //todo dropdown de monedas



    }
}