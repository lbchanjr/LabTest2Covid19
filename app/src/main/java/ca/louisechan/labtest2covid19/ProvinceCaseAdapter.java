package ca.louisechan.labtest2covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProvinceCaseAdapter extends ArrayAdapter<Case> {
    Context context;
    int resource;
    ArrayList<Case> cases;

    public ProvinceCaseAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Case> objects) {
        super(context, resource, objects);

        cases = objects;
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String province = cases.get(position).getProvince();
        Long totalCases = cases.get(position).getTotalCases();
        Long totalRecovered = cases.get(position).getTotalRecovered();
        Long totalDeaths = cases.get(position).getTotalDeaths();

        // Create inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);


        // fetch text views
        TextView tvProvince = (TextView) convertView.findViewById(R.id.txtProvinceName);
        TextView tvTotCase = (TextView)  convertView.findViewById(R.id.txtTotalCases);
        TextView tvTotRec = (TextView) convertView.findViewById(R.id.txtTotalRecovered);
        TextView tvTotDeath = (TextView) convertView.findViewById(R.id.txtTotalDeaths);

        tvProvince.setText("Province: " + province);
        tvTotCase.setText("Total Cases: " + totalCases);
        tvTotRec.setText("Total Recovered: " + totalRecovered);
        tvTotDeath.setText("Total Deaths: " + totalDeaths);

        return convertView;

    }
}
