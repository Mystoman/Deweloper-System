package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;

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
                if(checkSession()) {
                    usePost(username, password);
                } else {
                    gotoLogin();
                }
            }
        }, 2000);
    }

    private boolean checkSession() {
        if(username.equals("none") || password.equals("none")) {
            return false;
        } else {
            return true;
        }
    }

    private void usePost(String username, String password) {
        communicator = new Communicator();
        communicator.loginPost(username, password);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void gotoDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        gotoDashboard();
    }
}
