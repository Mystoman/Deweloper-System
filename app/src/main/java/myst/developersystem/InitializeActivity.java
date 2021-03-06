package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.json.UserData;

public class InitializeActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        new android.os.Handler().postDelayed(
        new Runnable() {
            public void run() {
                if(noSession()) {
                    gotoLogin();
                } else {
                    sendPostRequest();
                }
            }
        }, 2000);
    }

    private boolean noSession() {
        return username.equals("none") || password.equals("none");
    }

    private void sendPostRequest() {
        communicator = new Communicator();
        communicator.loginPost(username, password);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void gotoDashboard(UserData userData) {
        String data = new Gson().toJson(userData);
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("USER_DATA", data);
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        if(serverEvent.getServerResponse().getStatus().equals("fail")) {
            gotoLogin();
        }

        List<UserData> userData =  serverEvent.getServerResponse().getData();
        gotoDashboard(userData.get(0));
    }
}
