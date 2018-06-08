package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.json.UserData;
import myst.developersystem.classes.HashPassword;

public class LoginActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password, hashedPassword;
    private EditText usernameET, passwordET;
    private Button loginButton, registerButton;
    private final static String TAG = "LoginActivity";
    private final static String PREFS_NAME = "loginDetails";
    public static Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariables();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
            }
        });

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });
    }

    private void initVariables() {
        usernameET = (EditText)findViewById(R.id.usernameInput);
        passwordET = (EditText)findViewById(R.id.passwordInput);
        passwordET.setTransformationMethod(new PasswordTransformationMethod());
        loginButton = (Button)findViewById(R.id.loginButton);
    }

    private void sendPostRequest() {
        username = usernameET.getText().toString();
        password = passwordET.getText().toString();
        hashedPassword = HashPassword.sha256(password);
        communicator = new Communicator();
        communicator.loginPost(username, hashedPassword);
    }

    private void gotoRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
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
        Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();

        if(serverEvent.getServerResponse().getStatus().equals("fail")) {
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString("username", username);
        editor.putString("password", hashedPassword);
        editor.apply();

        List<UserData> userData =  serverEvent.getServerResponse().getData();

        gotoDashboard(userData.get(0));
    }
}
