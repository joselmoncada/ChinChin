package android.example.chinchin_prueba.ui.exchangeCalculator;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExchangeCalculatorModel extends ViewModel {

    private MutableLiveData<Bitmap> qrCode;

    public ExchangeCalculatorModel() {
        qrCode = new MutableLiveData<>();
    }

    public void setQrCode(Bitmap image) {
        this.qrCode = qrCode;
    }

    public LiveData<Bitmap> getQrCode() {
        return qrCode;
    }
}