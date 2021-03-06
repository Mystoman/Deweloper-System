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

import myst.developersystem.BuildingListActivity;
import myst.developersystem.InvestmentDeleteActivity;
import myst.developersystem.InvestmentFormActivity;
import myst.developersystem.R;
import myst.developersystem.api.model.json.InvestmentsData;

/**
 * Created by Michal on 01.03.18.
 */

public class InvestmentsRowAdapter extends ArrayAdapter<InvestmentsData> {

    private Activity activity;

    public InvestmentsRowAdapter(Activity context, ArrayList<InvestmentsData> objects) {
        super(context, 0, objects);
        activity = context;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        final InvestmentsData currentInvestment = getItem(position);
        view = inflater.inflate(R.layout.list_view_row, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        Spinner spinner = (Spinner) view.findViewById(R.id.rowSpinner);

        name.setText(currentInvestment.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, spinnerOptions());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 1:
                        gotoBuildings(currentInvestment.getId());
                        break;
                    case 2:
                        gotoEdit(currentInvestment.getId());
                        break;
                    case 3:
                        gotoDelete(currentInvestment.getId(), currentInvestment.getName());
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
        options.add(activity.getString(R.string.spinner_to_buildings));
        options.add(activity.getString(R.string.spinner_edit));
        options.add(activity.getString(R.string.spinner_delete));

        return options;
    }

    private void gotoBuildings(String id) {
        Intent intent = new Intent(activity, BuildingListActivity.class);
        intent.putExtra("INVESTMENT_ID", id);
        activity.startActivity(intent);
    }

    private void gotoEdit(String id) {
        Intent intent = new Intent(activity, InvestmentFormActivity.class);
        intent.putExtra("INVESTMENT_ID", id);
        activity.startActivity(intent);
    }

    private void gotoDelete(String id, String name) {
        Intent intent = new Intent(activity, InvestmentDeleteActivity.class);
        intent.putExtra("INVESTMENT_ID", id);
        intent.putExtra("INVESTMENT_NAME", name);
        activity.startActivity(intent);
    }

}