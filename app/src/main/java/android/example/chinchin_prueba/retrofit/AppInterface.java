package android.example.chinchin_prueba.retrofit;

import android.example.chinchin_prueba.models.RateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppInterface {

    @GET("latest?symbols=ETH,BTC,PTR,BS,EUR,USD&places=10")
    Call<RateResponse> getCurrencyRates(@Query("base") String base);
}
