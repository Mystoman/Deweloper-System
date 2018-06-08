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
import myst.developersystem.api.model.json.FlatData;
import myst.developersystem.classes.FlatsRowAdapter;

public class FlatListActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password, buildingID;
    private ListView list;
    private ArrayAdapter<FlatData> adapter;
    private Button addBuildingButton;
    private ImageButton settingsButton;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_list);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        Intent intent = getIntent();
        if(intent.hasExtra("BUILDING_ID")) {
            buildingID = intent.getStringExtra("BUILDING_ID");
            sendPostRequest();
        }

        addBuildingButton = (Button)findViewById(R.id.addBuilding);
        addBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddFlat();
            }
        });
    }

    private void sendPostRequest() {
        communicator = new Communicator();
        communicator.flatList(username, password, buildingID);
    }

    private void gotoAddFlat() {
        Intent intent = new Intent(this, FlatFormActivity.class);
        intent.putExtra("BUILDING_ID", buildingID);
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

        ArrayList<FlatData> flatsDataList = new ArrayList<>();
        flatsDataList.addAll(serverEvent.getServerResponse().getData());

        list = (ListView) findViewById(R.id.flatList);
        adapter = new FlatsRowAdapter(this, flatsDataList);
        list.setAdapter(adapter);
    }

}
