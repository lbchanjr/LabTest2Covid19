package ca.louisechan.labtest2covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddCaseActivity extends AppCompatActivity {

    private static final String TAG = "AddCaseActivity";

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        db = FirebaseFirestore.getInstance();
    }

    public void addCaseButtonPressed(View view) {
        // Check if any of the fields are empty.
        EditText etTotalCases = (EditText) findViewById(R.id.edtTotalCases);
        EditText etTotalRecovered = (EditText) findViewById(R.id.edtTotalRecovered);
        EditText etTotalDeaths = (EditText) findViewById(R.id.edtTotalDeaths);

        long totalCases = 0;
        long totalDeaths = 0;
        long totalRecovered = 0;

        try {
            totalCases = Long.parseLong(etTotalCases.getText().toString());
            totalRecovered = Long.parseLong(etTotalRecovered.getText().toString());
            totalDeaths = Long.parseLong(etTotalDeaths.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spProvince = (Spinner) findViewById(R.id.spinAddCaseProv);
        String province = spProvince.getSelectedItem().toString();

        final Map<String, Object> provCase = new HashMap<>();
        provCase.put("totalCases", totalCases);
        provCase.put("totalRecovered", totalRecovered);
        provCase.put("totalDeaths", totalDeaths);
        provCase.put("province", province);

        // Check if province already exists on the cases collection
        db.collection("cases").whereEqualTo("province", province).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 0) {
                        db.collection("cases").add(provCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "onSuccess: New case was added successfully!");
                                Toast.makeText(AddCaseActivity.this, "Case document was added successfully!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddCaseActivity.this, ClerkActivity.class);
                                startActivity(i);
                            }
                        });
                    } else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String docId = document.getId();
                            db.collection("cases").document(docId).set(provCase).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddCaseActivity.this, "Existing data for province was updated successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            break;
                        }
                    }
                }
            }
        });
    }
}
