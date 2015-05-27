package c03.ppl.hidupsehat.Tools;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by wahyuoi on 06/05/15.
 */
public class ServiceGenerator {
    private ServiceGenerator(){}

    public static <S> S createService(Class<S> serviceClass, String baseURL){
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
