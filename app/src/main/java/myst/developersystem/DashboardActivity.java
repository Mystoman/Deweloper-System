package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {

    private Button investmentsButton;
    private ImageButton settingsButton;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        investmentsButton = (Button)findViewById(R.id.gotoInvestments);
        investmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInvestments();
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

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
