package ca.louisechan.labtest2covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClerkActivity extends AppCompatActivity {
    private static final String TAG = "ClerkActivity";
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk);

        Intent i = getIntent();

        loggedUser = (User) i.getSerializableExtra("loggedUser");
        TextView tvHeader = (TextView) findViewById(R.id.txtWelcomeLabel);
        tvHeader.setText("Welcome " + loggedUser.getFullName());

    }

    public void addCaseButtonPressed(View view) {
        Log.d(TAG, "addCaseButtonPressed: Add case pressed");
        Intent i = new Intent(this, AddCaseActivity.class);
        startActivity(i);

    }

    public void viewCaseButtonPressed(View view) {
        Log.d(TAG, "viewCaseButtonPressed: View case pressed");
        Intent i = new Intent(this, ViewCasesActivity.class);
        startActivity(i);
    }

    public void logoutButtonPressed(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: BACK button clicks are disabled in this activity.");
        // Exit to home screen on BACK button pressed.
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
