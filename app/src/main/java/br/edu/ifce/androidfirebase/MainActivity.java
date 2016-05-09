package br.edu.ifce.androidfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.View;

public class MainActivity extends AppCompatActivity {

    // TRANSIÇÃO ENTRE TELAS
    private EditText editTextLogin;
    private EditText editTextPassword;
    private TextView textViewStatus;
    private String login;
    private String loginDigitado;
    private String password;
    private String passwordDigitado;

    // FIREBASE
    public static final String FIREBASE_REPO = "blinding-heat-9190";
    public static final String FIREBASE_URL = "https://" + FIREBASE_REPO + ".firebaseio.com";
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TRANSIÇÃO ENTRE TELAS
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = "edu@email.com";
        password = "edu";
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        // FIREBASE
        if (savedInstanceState == null){
            Firebase.setAndroidContext(this);
        }
        // Create a connection to your Firebase database
        firebase = new Firebase(FIREBASE_URL);
    }

    // TRANSIÇÃO ENTRE TELAS
    public void signin (View v){
        loginDigitado = editTextLogin.getText().toString();
        passwordDigitado = editTextPassword.getText().toString();

        if(login.equals(loginDigitado) && passwordDigitado.equals(password)){
                // TRANSIÇÃO ENTRE TELAS
                transitaParaTelaFirebaseShared();
        }
        else{
            editTextPassword.setError(getString(R.string.invalid));
        }
    }


    // TRANSIÇÃO ENTRE TELAS
    private void transitaParaTelaFirebaseShared() {
        setContentView(R.layout.status_firebase_shared);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        // FIREBASE
        // Listen for realtime changes
        firebase.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                textViewStatus.setText(snap.getValue().toString());
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

    }

    // FIREBASE
    public void felizClick (View v){
        firebase.child("status").setValue("Feliz");
    }
    public void tristeClick (View v){
        firebase.child("status").setValue("Triste");
    }
}
