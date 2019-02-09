package id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASEURL = "http://api.github.com/search/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
