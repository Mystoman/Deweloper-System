package myst.developersystem.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myst.developersystem.BuildingDeleteActivity;
import myst.developersystem.BuildingFormActivity;
import myst.developersystem.FlatListActivity;
import myst.developersystem.R;
import myst.developersystem.api.model.json.BuildingData;

/**
 * Created by Michal on 03.05.18.
 */

public class BuildingsRowAdapter extends ArrayAdapter<BuildingData> {

    private Activity activity;

    public BuildingsRowAdapter(Activity context, ArrayList<BuildingData> objects) {
        super(context, 0, objects);
        activity = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final BuildingData currentBuilding = getItem(position);
        view = inflater.inflate(R.layout.list_view_row, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        Spinner spinner = (Spinner) view.findViewById(R.id.rowSpinner);

        name.setText(currentBuilding.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, spinnerOptions());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 1:
                        gotoFlats(currentBuilding.getId());
                        break;
                    case 2:
                        gotoEdit(currentBuilding.getInvestmentID(), currentBuilding.getId());
                        break;
                    case 3:
                        gotoDelete(currentBuilding.getId(), currentBuilding.getName(), currentBuilding.getInvestmentID());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        return view;
    }

    private List<String> spinnerOptions() {
        List<String> options = new ArrayList<String>();
        options.add(activity.getString(R.string.spinner_default));
        options.add(activity.getString(R.string.spinner_to_flats));
        options.add(activity.getString(R.string.spinner_edit));
        options.add(activity.getString(R.string.spinner_delete));

        return options;
    }

    private void gotoFlats(String id) {
        Intent intent = new Intent(activity, FlatListActivity.class);
        intent.putExtra("BUILDING_ID", id);
        activity.startActivity(intent);
    }

    private void gotoEdit(String investmentID, String id) {
        Intent intent = new Intent(activity, BuildingFormActivity.class);
        intent.putExtra("INVESTMENT_ID", investmentID);
        intent.putExtra("BUILDING_ID", id);
        activity.startActivity(intent);
    }

    private void gotoDelete(String id, String name, String investmentID) {
        Intent intent = new Intent(activity, BuildingDeleteActivity.class);
        intent.putExtra("BUILDING_ID", id);
        intent.putExtra("BUILDING_NAME", name);
        intent.putExtra("INVESTMENT_ID", investmentID);
        activity.startActivity(intent);
    }

}
