package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import myst.developersystem.api.model.json.UserData;

public class DashboardActivity extends AppCompatActivity {

    private Button investmentsButton;
    private ImageButton settingsButton;
    private UserData userData;
    private Integer role;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        investmentsButton = (Button)findViewById(R.id.gotoInvestments);

        Intent intent = getIntent();
        userData = new Gson().fromJson(intent.getStringExtra("USER_DATA"), UserData.class);
        role = userData.getRole();

        TextView username = (TextView)findViewById(R.id.username);
        TextView email = (TextView)findViewById(R.id.email);
        TextView created = (TextView)findViewById(R.id.created);
        username.setText(userData.getName());
        email.setText(userData.getEmail());
        created.setText(userData.getCreated());

        if(role != 1) {
            investmentsButton.setText(R.string.button_goto_search);
        }

        investmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role == 1) {
                    gotoInvestments();
                } else {
                    gotoSearch();
                }

            }
        });

        settingsButton = (ImageButton)findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();

                gotoLogin();
            }
        });
    }

    private void gotoInvestments() {
        Intent intent = new Intent(this, InvestmentsListActivity.class);
        startActivity(intent);
    }

    private void gotoSearch() {
        Intent intent = new Intent(this, FlatSearchActivity.class);
        startActivity(intent);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
