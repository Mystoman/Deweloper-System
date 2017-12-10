package myst.developersystem.api.communicator;

import android.util.Log;

import com.squareup.otto.Produce;

import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ErrorEvent;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.ServerResponse;
import myst.developersystem.api.service.Interface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Michal on 11.11.17.
 */

public class Communicator {

    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://magdziarek.dio.vobacom.pl";
    private final HttpLoggingInterceptor logging;
    private final OkHttpClient.Builder httpClient;
    private final Retrofit retrofit;
    private final Interface service;

    public Communicator() {
        //Here a logging interceptor is created
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        retrofit = new Retrofit.Builder()
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_URL)
            .build();

        service = retrofit.create(Interface.class);
    }

    public void loginPost(String username, String password) {

        Call<ServerResponse> call = service.login(username,password);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"Nope");
            }
        });

    }

    public void registerPost(String username, String email, String password) {

        Call<ServerResponse> call = service.register(username, email, password);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"Nope");
            }
        });

    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }

}