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
import myst.developersystem.FlatFormActivity;
import myst.developersystem.R;
import myst.developersystem.api.model.json.FlatData;

/**
 * Created by Michal on 24.05.18.
 */

public class FlatsRowAdapter extends ArrayAdapter<FlatData> {

    private Activity activity;

    public FlatsRowAdapter(Activity context, ArrayList<FlatData> objects) {
        super(context, 0, objects);
        activity = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final FlatData currentFlat = getItem(position);
        view = inflater.inflate(R.layout.list_view_row, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        Spinner spinner = (Spinner) view.findViewById(R.id.rowSpinner);

        name.setText(currentFlat.getNumber());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, spinnerOptions());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 1:
                        gotoEdit(currentFlat.getBuildingID(), currentFlat.getId());
                        break;
                    case 2:
                        gotoDelete(currentFlat.getId(), currentFlat.getNumber(), currentFlat.getBuildingID());
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
        options.add(activity.getString(R.string.spinner_edit));
        options.add(activity.getString(R.string.spinner_delete));

        return options;
    }

    private void gotoEdit(String buildingID, String id) {
        Intent intent = new Intent(activity, FlatFormActivity.class);
        intent.putExtra("BUILDING_ID", buildingID);
        intent.putExtra("FLAT_ID", id);
        activity.startActivity(intent);
    }

    private void gotoDelete(String id, String name, String buildingID) {
        Intent intent = new Intent(activity, BuildingDeleteActivity.class);
        intent.putExtra("FLAT_ID", id);
        intent.putExtra("FLAT_NUMBER", name);
        intent.putExtra("BUILDING_ID", buildingID);
        activity.startActivity(intent);
    }

}
