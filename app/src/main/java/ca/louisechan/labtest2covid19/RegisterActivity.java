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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseFirestore db;
    private String firstName, lastName, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
    }

    public void registerUserPressed(View view) {
        // Check if any of the fields are empty
        EditText etFname = (EditText) findViewById(R.id.edtFirstName);
        EditText etLname = (EditText) findViewById(R.id.edtLastName);
        EditText etUname = (EditText) findViewById(R.id.edtUsername);
        EditText etPasswd = (EditText) findViewById(R.id.edtPassword);

        firstName = etFname.getText().toString();
        lastName = etLname.getText().toString();
        username = etUname.getText().toString();
        password = etPasswd.getText().toString();


        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields should not be empty!", Toast.LENGTH_LONG).show();
            return;
        } else {
            db.collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().size() == 0) {
                            // Username is not yet registered, it is ok to register user in the database
                            Log.d(TAG, "onComplete: Registering user to database");

                            User registeredUser = new User(firstName, lastName, username, password, "reg");
                            db.collection("users").add(registeredUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "onSuccess: User was successfully registered.");
                                    Toast.makeText(RegisterActivity.this, "User was successfully registered.", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            });

                        } else {
                            Log.d(TAG, "onComplete: Username already exists in database!");
                            Toast.makeText(RegisterActivity.this, "Username already exists in the database!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }
}
