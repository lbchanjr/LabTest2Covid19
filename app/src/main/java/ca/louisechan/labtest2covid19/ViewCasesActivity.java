package ca.louisechan.labtest2covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewCasesActivity extends AppCompatActivity {
    private static final String TAG = "ViewCasesActivity";

    private ListView lvProvinces;
    private ArrayList<Case> cases = new ArrayList<>();
    private ArrayList<String> provinces = new ArrayList<>();
    private ArrayList<String> docIds = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cases);

        lvProvinces = (ListView) findViewById(R.id.lvViewCases);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, provinces);
        lvProvinces.setAdapter(adapter);
        lvProvinces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Case c = cases.get(position);
                Intent i = new Intent(ViewCasesActivity.this, EditCaseActivity.class);
                i.putExtra("caseSelected", c);
                i.putExtra("caseDocId", docIds.get(position));
                startActivity(i);
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("cases").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        long totalCases = document.getLong("totalCases");
                        long totalRecovered = document.getLong("totalRecovered");
                        long totalDeaths = document.getLong("totalDeaths");
                        Object provObj = document.getData().get("province");
                        String province = provObj == null ? "": provObj.toString();

                        // Add case to arraylist
                        cases.add(new Case(totalCases, totalRecovered, totalDeaths, province));
                        provinces.add(province);
                        docIds.add(document.getId());
                    }

                    // Refresh listview
                    adapter.notifyDataSetChanged();

                }
            }
        });




    }
}
