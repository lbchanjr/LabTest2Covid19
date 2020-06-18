package ca.louisechan.labtest2covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RegularUserActivity extends AppCompatActivity {

    private ListView lvRegularUser;
    private ArrayList<Case> cases;
    private ArrayList<Case> casesBackup;
    private FirebaseFirestore db;
    Spinner spinProvince;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user);

        db = FirebaseFirestore.getInstance();
        lvRegularUser = (ListView) findViewById(R.id.lvCanadaCases);


        cases = new ArrayList<Case>();
        casesBackup = new ArrayList<Case>();

        final ProvinceCaseAdapter adapter = new ProvinceCaseAdapter(this, R.layout.province_row, cases);
        lvRegularUser.setAdapter(adapter);

        db.collection("cases").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String province = document.getData().get("province").toString();
                        Long totalCases = document.getLong("totalCases");
                        Long totalRecovered = document.getLong("totalRecovered");
                        Long totalDeaths = document.getLong("totalDeaths");
                        cases.add(new Case(totalCases, totalRecovered, totalDeaths, province));
                        casesBackup.add(new Case(totalCases, totalRecovered, totalDeaths, province));
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        spinProvince = (Spinner) findViewById(R.id.spinProvince);
        spinProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provinceSelected = spinProvince.getSelectedItem().toString();

                cases.clear();

                for(int i = 0; i < casesBackup.size(); i++) {
                    if (casesBackup.get(i).getProvince().equals(provinceSelected)) {
                        cases.add(casesBackup.get(i));
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }



}
