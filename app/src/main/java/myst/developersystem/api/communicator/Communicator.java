package myst.developersystem.api.communicator;

import android.util.Log;

import com.squareup.otto.Produce;

import java.util.ArrayList;

import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ErrorEvent;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.responses.BasicServerResponse;
import myst.developersystem.api.model.responses.BuildingServerResponse;
import myst.developersystem.api.model.responses.FlatServerResponse;
import myst.developersystem.api.model.responses.InvestmentsServerResponse;
import myst.developersystem.api.model.responses.UserServerResponse;
import myst.developersystem.api.service.CallsInterface;
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
    private final CallsInterface service;

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

        service = retrofit.create(CallsInterface.class);
    }

    public void loginPost(String username, String password) {

        Call<UserServerResponse> call = service.login(username,password);

        call.enqueue(new Callback<UserServerResponse>() {
            @Override
            public void onResponse(Call<UserServerResponse> call, Response<UserServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<UserServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"Nope");
            }
        });

    }

    public void registerPost(String username, String email, String password, String role) {

        Call<BasicServerResponse> call = service.register(username, email, password, role);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"Nope");
            }
        });

    }

    public void investmentList(String username, String password) {

        Call<InvestmentsServerResponse> call = service.investmentList(username, password);

        call.enqueue(new Callback<InvestmentsServerResponse>() {
            @Override
            public void onResponse(Call<InvestmentsServerResponse> call, Response<InvestmentsServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<InvestmentsServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void loadInvestmentID(String username, String password, String investmentID) {

        Call<InvestmentsServerResponse> call = service.loadInvestmentData(username, password, investmentID);

        call.enqueue(new Callback<InvestmentsServerResponse>() {
            @Override
            public void onResponse(Call<InvestmentsServerResponse> call, Response<InvestmentsServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<InvestmentsServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void addInvestment(String username, String password, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.addInvestment(username, password, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void updateInvestment(String username, String password, String investmentID, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.updateInvestment(username, password, investmentID, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void deleteInvestment(String username, String password, String investmentID) {

        Call<BasicServerResponse> call = service.deleteInvestment(username, password, investmentID);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void buildingList(String username, String password, String investmentID) {

        Call<BuildingServerResponse> call = service.buildingList(username, password, investmentID);

        call.enqueue(new Callback<BuildingServerResponse>() {
            @Override
            public void onResponse(Call<BuildingServerResponse> call, Response<BuildingServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BuildingServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void loadBuildingID(String username, String password, String buildingID) {

        Call<BuildingServerResponse> call = service.loadBuildingData(username, password, buildingID);

        call.enqueue(new Callback<BuildingServerResponse>() {
            @Override
            public void onResponse(Call<BuildingServerResponse> call, Response<BuildingServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BuildingServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void addBuilding(String username, String password, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.addBuilding(username, password, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void updateBuilding(String username, String password, String buildingID, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.updateBuilding(username, password, buildingID, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void deleteBuilding(String username, String password, String buildingID) {

        Call<BasicServerResponse> call = service.deleteBuilding(username, password, buildingID);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void flatList(String username, String password, String buildingID) {

        Call<FlatServerResponse> call = service.flatList(username, password, buildingID);

        call.enqueue(new Callback<FlatServerResponse>() {
            @Override
            public void onResponse(Call<FlatServerResponse> call, Response<FlatServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<FlatServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void loadFlatID(String username, String password, String flatID) {

        Call<FlatServerResponse> call = service.loadFlatData(username, password, flatID);

        call.enqueue(new Callback<FlatServerResponse>() {
            @Override
            public void onResponse(Call<FlatServerResponse> call, Response<FlatServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<FlatServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void addFlat(String username, String password, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.addFlat(username, password, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void updateFlat(String username, String password, String flatID, ArrayList<String> data) {

        Call<BasicServerResponse> call = service.updateFlat(username, password, flatID, data);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    public void deleteFlat(String username, String password, String flatID) {

        Call<BasicServerResponse> call = service.deleteFlat(username, password, flatID);

        call.enqueue(new Callback<BasicServerResponse>() {
            @Override
            public void onResponse(Call<BasicServerResponse> call, Response<BasicServerResponse> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG,"Success");
            }

            @Override
            public void onFailure(Call<BasicServerResponse> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.d(TAG,"A");
            }
        });

    }

    @Produce
    public ServerEvent produceServerEvent(BasicServerResponse basicServerResponse) {
        return new ServerEvent(basicServerResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }

}