package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.api.model.json.BuildingData;
import myst.developersystem.api.model.json.FlatData;

public class FlatFormActivity extends AppCompatActivity {

    private Communicator communicator;
    private Spinner floorSpinner;
    private String username, password, number, area, buildingID, flatID;
    private EditText numberET, areaET;
    private ArrayList<String> inputs;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_form);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        initVariables();

        Intent intent = getIntent();
        buildingID = intent.getStringExtra("BUILDING_ID");
        if (intent.hasExtra("FLAT_ID")) {
            flatID = intent.getStringExtra("FLAT_ID");
            communicator = new Communicator();
            communicator.loadFlatID(username, password, flatID);
        }

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setVariables();
                    emptyFields();
                    inputs.add(getFloor());
                    inputs.add(buildingID);
                    sendRequest();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    private void setFloorSpinner() {
        floorSpinner = (Spinner)findViewById(R.id.flatFloor);
        List<Integer> floorOptions = new ArrayList<Integer>();
        for(Integer i = 0; i < 5; i++) {
            floorOptions.add(i);
        }

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, floorOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(dataAdapter);
        floorSpinner.setSelection(0, false);
    }

    private void initVariables() {
        flatID = "0";
        numberET = (EditText)findViewById(R.id.flatNumber);
        areaET = (EditText)findViewById(R.id.flatArea);
        setFloorSpinner();
    }

    private void loadData(List data) {
        ArrayList<FlatData> flats = new ArrayList<>();
        flats.addAll(data);
        numberET.setText(flats.get(0).getNumber());
        areaET.setText(flats.get(0).getArea().toString());
        floorSpinner.setSelection(flats.get(0).getFloor(), false);
    }

    private void setVariables() {
        number = numberET.getText().toString();
        area = areaET.getText().toString();
        inputs = new ArrayList<>(Arrays.asList(number, area));
    }

    private void emptyFields() throws Exception {
        for (String input : inputs) {
            if(input == null || input.isEmpty()) {
                throw new Exception("Nie zostały wypełnione wszystkie pola.");
            }
        }
    }

    private String getFloor() {
        return floorSpinner.getSelectedItem().toString();
    }

    private void sendRequest() {
        communicator = new Communicator();
        if(flatID.equals("0")) {
            communicator.addFlat(username, password, inputs);
        } else {
            communicator.updateFlat(username, password, flatID, inputs);
        }
    }

    private void gotoFlats() {
        Intent intent = new Intent(this, FlatListActivity.class);
        intent.putExtra("BUILDING_ID", buildingID);
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
        if(serverEvent.getServerResponse().getMessage().equals("Mieszkanie zostało załadowane")) {

            loadData(serverEvent.getServerResponse().getData());

        } else {

            if(serverEvent.getServerResponse().getStatus().equals("fail")) {
                Toast.makeText(getApplicationContext(), "Dodawanie mieszkania nie powiodło się", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
            gotoFlats();
            finish();

        }
    }
}
