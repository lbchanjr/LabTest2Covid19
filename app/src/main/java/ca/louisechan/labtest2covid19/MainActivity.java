package ca.louisechan.labtest2covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    private EditText etUsername;
    private EditText etPassword;
    private String savedFirestorePassword = "";
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        etUsername = (EditText) findViewById(R.id.edtUsername);
        etPassword = (EditText) findViewById(R.id.edtPassword);
    }

    public void loginButtonPressed(View view) {



        String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        // check if username and password are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or Password should not be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // Check if username is saved in the users database
            db.collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();
                        if (documents.size() == 0) {
                            Log.d(TAG, "onComplete: Username does not exist on database");
                            Toast.makeText(MainActivity.this, "Username does not exist in database.", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            for (QueryDocumentSnapshot document: documents) {
                                Object passwordObj = document.getData().get("password");
                                Object fnameObj = document.getData().get("firstName");
                                Object lnameObj = document.getData().get("lastName");
                                Object usernameObj = document.getData().get("username");
                                Object typeObj = document.getData().get("type");

                                String password = passwordObj == null ? "" : passwordObj.toString();
                                String fname = fnameObj == null ? "" : fnameObj.toString();
                                String lname = lnameObj == null ? "" : lnameObj.toString();
                                String username = usernameObj == null ? "" : usernameObj.toString();
                                String type = typeObj == null ? "" : typeObj.toString();

                                loggedInUser = new User(fname, lname, username, password, type);

                                break;
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (etPassword.getText().toString().equals(loggedInUser.getPassword())) {
                                        // Password is a match, check the user type to select the write activity to go to
                                        if (loggedInUser.getType().equals("clerk")) {
                                            // Switch to clerk activity
                                            Intent i = new Intent(MainActivity.this, ClerkActivity.class);
                                            i.putExtra("loggedUser", loggedInUser);
                                            startActivity(i);
                                        } else {
                                            // Switch to regular user activity
                                            Intent i = new Intent(MainActivity.this, RegularUserActivity.class);
                                            i.putExtra("loggedUser", loggedInUser);
                                            startActivity(i);
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public void RegisterButtonPressed(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
