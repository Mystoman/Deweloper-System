package myst.developersystem.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import myst.developersystem.InvestmentFormActivity;
import myst.developersystem.R;
import myst.developersystem.api.model.InvestmentsData;

/**
 * Created by Michal on 01.03.18.
 */

public class InvestmentsRowAdapter extends ArrayAdapter<InvestmentsData> {

    Activity activity;
    ArrayList<InvestmentsData> invesmtnetList;

    public InvestmentsRowAdapter(Activity context, ArrayList<InvestmentsData> objects) {
        super(context, 0, objects);
        activity = context;
        invesmtnetList = objects;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        InvestmentsData currentInvestment = getItem(position);
        view = inflater.inflate(R.layout.list_view_row, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(currentInvestment.getName());

        Button editButton = (Button) view.findViewById(R.id.editButton);
        editButton.setTag(currentInvestment.getId());

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                String tag = v.getTag().toString();
                gotoEdit(tag);
            }

        });

        return view;
    }

    private void gotoEdit(String tag) {
        Intent intent = new Intent(activity, InvestmentFormActivity.class);
        intent.putExtra("INVESTMENT_ID", tag);
        activity.startActivity(intent);
    }
}