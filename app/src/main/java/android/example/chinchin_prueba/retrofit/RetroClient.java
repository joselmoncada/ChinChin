package android.example.chinchin_prueba.retrofit;

import android.example.chinchin_prueba.ui.appUtils.AppConstants;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60 * 5, TimeUnit.SECONDS).connectTimeout(60 * 5, TimeUnit.SECONDS).build();
        httpClient.addInterceptor(logging);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.EXCHANGE_API)// PROD_URL---> Produccion TEST_URL--->Desarrollo y pruebas
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build();

        return retrofit;
    }

    /**
     * Get API ServiceInDirectory
     *
     * @return API ServiceInDirectory
     */
    public static AppInterface getApiService() {
        return getRetrofitInstance().create(AppInterface.class);
    }

}
