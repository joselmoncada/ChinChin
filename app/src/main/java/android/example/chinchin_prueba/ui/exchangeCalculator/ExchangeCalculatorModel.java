package android.example.chinchin_prueba.ui.exchangeCalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExchangeCalculatorModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExchangeCalculatorModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}