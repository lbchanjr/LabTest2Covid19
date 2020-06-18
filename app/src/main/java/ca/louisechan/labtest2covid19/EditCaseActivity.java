package ca.louisechan.labtest2covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditCaseActivity extends AppCompatActivity {
    private static final String TAG = "EditCaseActivity";
    private EditText etTotalCases;
    private EditText etTotalRecovered;
    private EditText etTotalDeaths;
    private TextView txtProviceCase;

    private String docId;
    private Case caseItem;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);

        db = FirebaseFirestore.getInstance();

        etTotalCases = (EditText) findViewById(R.id.edtTotalCases2);
        etTotalDeaths = (EditText) findViewById(R.id.edtTotalDeaths2);
        etTotalRecovered = (EditText) findViewById(R.id.edtTotalRecovered2);
        txtProviceCase = (TextView) findViewById(R.id.txtCaseProvince);


        Intent i = getIntent();
        caseItem = (Case) i.getSerializableExtra("caseSelected");
        docId = i.getStringExtra("caseDocId");

        etTotalCases.setText(Long.toString(caseItem.getTotalCases()));
        etTotalRecovered.setText(Long.toString(caseItem.getTotalRecovered()));
        etTotalDeaths.setText(Long.toString(caseItem.getTotalDeaths()));
        txtProviceCase.setText(caseItem.getProvince());

    }

    public void updateCaseButtonPressed(View view) {
        // check if any of the inputs are invalid
        try {
            caseItem.setTotalCases(Long.parseLong(etTotalCases.getText().toString()));
            caseItem.setTotalRecovered(Long.parseLong(etTotalRecovered.getText().toString()));
            caseItem.setTotalDeaths(Long.parseLong(etTotalDeaths.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("cases").document(docId).set(caseItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditCaseActivity.this, "Case was updated successfully!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(EditCaseActivity.this, ViewCasesActivity.class);
                startActivity(i);
            }
        });

    }
}
