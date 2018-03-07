package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.InvestmentsData;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.classes.InvestmentsRowAdapter;

public class InvestmentsListActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password;
    private ListView list;
    private ArrayAdapter<InvestmentsData> adapter;
    private Button addInvestmentButton;
    private ImageButton settingsButton;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investments_list);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");
        sendPostRequest();

        addInvestmentButton = (Button)findViewById(R.id.addInvestment);
        addInvestmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddInvestment();
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

    private void sendPostRequest() {
        communicator = new Communicator();
        communicator.investmentList(username, password);
    }

    private void gotoAddInvestment() {
        Intent intent = new Intent(this, InvestmentFormActivity.class);
        startActivity(intent);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
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
            gotoLogin();
        }

        ArrayList<InvestmentsData> investmentsDataList = new ArrayList<>();
        investmentsDataList.addAll(serverEvent.getServerResponse().getData());

        list = (ListView) findViewById(R.id.investmentsList);
        adapter = new InvestmentsRowAdapter(this, investmentsDataList);
        list.setAdapter(adapter);
    }
}
