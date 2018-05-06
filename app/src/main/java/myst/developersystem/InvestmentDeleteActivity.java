package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;

public class InvestmentDeleteActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password, investmentID, investmentName;
    private Button submitButton;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_delete);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        Intent intent = getIntent();
        if(intent.hasExtra("INVESTMENT_ID")) {
            investmentID = intent.getStringExtra("INVESTMENT_ID");
        }
        if(intent.hasExtra("INVESTMENT_NAME")) {
            investmentName = intent.getStringExtra("INVESTMENT_NAME");
        }

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(investmentName);

        submitButton = (Button)findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendRequest(investmentID);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    private void sendRequest(String investmentID) {
        communicator = new Communicator();
        communicator.deleteInvestment(username, password, investmentID);
    }

    private void gotoInvestments() {
        Intent intent = new Intent(this, InvestmentsListActivity.class);
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
        if(serverEvent.getServerResponse().getStatus().equals("fail")) {
            Toast.makeText(getApplicationContext(), serverEvent.getServerResponse().getMessage().toString(), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        gotoInvestments();
        finish();
    }

}
