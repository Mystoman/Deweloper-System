package myst.developersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class FlatSearchActivity extends AppCompatActivity {

    private Button searchButton;
    private Spinner floorSpinner;
    private EditText cityET, areaFromET, areaToET, roomsFromET, roomsToET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_search);

        initVariables();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMap();
            }
        });
    }

    private void setFloorSpinner() {
        floorSpinner = (Spinner)findViewById(R.id.floor);
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
        searchButton = (Button)findViewById(R.id.searchButton);
        cityET = (EditText)findViewById(R.id.city);
        areaFromET = (EditText)findViewById(R.id.areaFrom);
        areaToET = (EditText)findViewById(R.id.areaTo);
        roomsFromET = (EditText)findViewById(R.id.roomsFrom);
        roomsToET = (EditText)findViewById(R.id.roomsTo);
        setFloorSpinner();
    }

    private void gotoMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
