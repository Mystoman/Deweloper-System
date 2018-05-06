package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.json.BuildingData;

public class BuildingFormActivity extends AppCompatActivity {

    private Communicator communicator;
    private Spinner floorsSpinner;
    private DatePicker finishDate;
    private String username, password, name, investmentID, buildingID;
    private EditText nameET;
    private ArrayList<String> inputs;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_form);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        initVariables();

        Intent intent = getIntent();
        investmentID = intent.getStringExtra("INVESTMENT_ID");
        if(intent.hasExtra("BUILDING_ID")) {
            buildingID = intent.getStringExtra("BUILDING_ID");
            communicator = new Communicator();
            communicator.loadBuildingID(username, password, buildingID);
        }

        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setVariables();
                    emptyFields();
                    inputs.add(getFloors());
                    inputs.add(getFinishDate());
                    inputs.add(investmentID);
                    sendRequest();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    private void setFloorsSpinner() {
        floorsSpinner = (Spinner)findViewById(R.id.buildingFloors);
        List<Integer> floorOptions = new ArrayList<Integer>();
        for(Integer i = 0; i < 5; i++) {
            floorOptions.add(i);
        }

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, floorOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorsSpinner.setAdapter(dataAdapter);
        floorsSpinner.setSelection(0, false);
    }

    private void initVariables() {
        buildingID = "0";
        nameET = (EditText)findViewById(R.id.buildingName);
        finishDate = (DatePicker)findViewById(R.id.buildingFinishDate);
        setFloorsSpinner();
    }

    private void loadData(List data) {
        ArrayList<BuildingData> buildings = new ArrayList<>();
        buildings.addAll(data);
        nameET.setText(buildings.get(0).getName());
        String[] dataParts = buildings.get(0).getFinishDate().split("-");
        finishDate.updateDate(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
        floorsSpinner.setSelection((buildings.get(0).getFloors()), false);
    }

    private void setVariables() {
        name = nameET.getText().toString();
        inputs = new ArrayList<>(Arrays.asList(name));
    }

    private void emptyFields() throws Exception {
        for (String input : inputs) {
            if(input == null || input.isEmpty()) {
                throw new Exception("Nie zostały wypełnione wszystkie pola.");
            }
        }
    }

    private String getFloors() {
        return floorsSpinner.getSelectedItem().toString();
    }

    private String getFinishDate() {
        return finishDate.getYear() + "-" + finishDate.getMonth() + "-" + finishDate.getDayOfMonth();
    }

    private void sendRequest() {
        communicator = new Communicator();
        if(buildingID.equals("0")) {
            communicator.addBuilding(username, password, inputs);
        } else {
            communicator.updateBuilding(username, password, buildingID, inputs);
        }
    }

    private void gotoBuildings() {
        Intent intent = new Intent(this, BuildingListActivity.class);
        intent.putExtra("INVESTMENT_ID", investmentID);
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
        if(serverEvent.getServerResponse().getMessage().equals("Budynek został załadowany")) {

            loadData(serverEvent.getServerResponse().getData());

        } else {

            if(serverEvent.getServerResponse().getStatus().equals("fail")) {
                Toast.makeText(getApplicationContext(), "Dodawanie budynku nie powiodło się", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
            gotoBuildings();
            finish();

        }
    }

}
