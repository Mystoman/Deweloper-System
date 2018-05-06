package myst.developersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.json.InvestmentsData;
import myst.developersystem.api.model.ServerEvent;

public class InvestmentFormActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, password, name, city, postal, street, investmentID;
    private EditText nameET, cityET, postalET, streetET;
    private Button submitButton;
    private ArrayList<String> inputs;
    private List<Address> addresses;
    private final static String PREFS_NAME = "loginDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_form);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username = settings.getString("username", "none");
        password = settings.getString("password", "none");

        initVariables();

        Intent intent = getIntent();
        if(intent.hasExtra("INVESTMENT_ID")) {
            investmentID = intent.getStringExtra("INVESTMENT_ID");
            communicator = new Communicator();
            communicator.loadInvestmentID(username, password, investmentID);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setVariables();
                    emptyFields();
                    inputs.add(getCoords());
                    sendRequest(investmentID);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    private void initVariables() {
        investmentID = "0";
        nameET = (EditText)findViewById(R.id.investmentName);
        cityET = (EditText)findViewById(R.id.investmentCity);
        postalET = (EditText)findViewById(R.id.investmentPostal);
        streetET = (EditText)findViewById(R.id.investmentStreet);
        submitButton = (Button)findViewById(R.id.submitButton);
    }

    private void loadData(List data) {
        ArrayList<InvestmentsData> investments = new ArrayList<>();
        investments.addAll(data);
        nameET.setText(investments.get(0).getName());
        cityET.setText(investments.get(0).getCity());
        postalET.setText(investments.get(0).getPostal());
        streetET.setText(investments.get(0).getStreet());
    }

    private void setVariables() {
        name = nameET.getText().toString();
        city = cityET.getText().toString();
        postal = postalET.getText().toString();
        street = streetET.getText().toString();
        inputs = new ArrayList<>(Arrays.asList(name, city, postal, street));
    }

    private void emptyFields() throws Exception {
        for (String input : inputs) {
            if(input == null || input.isEmpty()) {
                throw new Exception("Nie zostały wypełnione wszystkie pola.");
            }
        }
    }

    private String getCoords() {
        Geocoder geocoder = new Geocoder(this);
        try {
            addresses = geocoder.getFromLocationName(postal + " " + city + " " + street, 1);
        } catch (Exception e) {
        }
        String latitude = String.valueOf(addresses.get(0).getLatitude());
        String longitude = String.valueOf(addresses.get(0).getLongitude());

        return latitude + "," + longitude;
    }

    private void sendRequest(String investmentID) {
        communicator = new Communicator();
        if(investmentID.equals("0")) {
            communicator.addInvestment(username, password, inputs);
        } else {
            communicator.updateInvestment(username, password, investmentID, inputs);
        }
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
        if(serverEvent.getServerResponse().getMessage().equals("Inwestycja została załadowana")) {

            loadData(serverEvent.getServerResponse().getData());

        } else {

            if(serverEvent.getServerResponse().getStatus().equals("fail")) {
                Toast.makeText(getApplicationContext(), "Dodawanie inwestycji nie powiodło się", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
            gotoInvestments();
            finish();

        }
    }
}
