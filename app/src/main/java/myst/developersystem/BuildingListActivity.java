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
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.json.BuildingData;
import myst.developersystem.classes.BuildingsRowAdapter;

public class BuildingListActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password, investmentID;
    private ListView list;
    private ArrayAdapter<BuildingData> adapter;
    private Button addBuildingButton;
    private ImageButton settingsButton;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        Intent intent = getIntent();
        if(intent.hasExtra("INVESTMENT_ID")) {
            investmentID = intent.getStringExtra("INVESTMENT_ID");
            sendPostRequest();
        }

        addBuildingButton = (Button)findViewById(R.id.addBuilding);
        addBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddBuilding();
            }
        });

    }

    private void sendPostRequest() {
        communicator = new Communicator();
        communicator.buildingList(username, password, investmentID);
    }

    private void gotoAddBuilding() {
        Intent intent = new Intent(this, BuildingFormActivity.class);
        intent.putExtra("INVESTMENT_ID", investmentID);
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

        ArrayList<BuildingData> buildingsDataList = new ArrayList<>();
        buildingsDataList.addAll(serverEvent.getServerResponse().getData());

        list = (ListView) findViewById(R.id.buildingsList);
        adapter = new BuildingsRowAdapter(this, buildingsDataList);
        list.setAdapter(adapter);
    }

}
