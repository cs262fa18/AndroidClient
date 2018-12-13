package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class handles the sign in page.
 *
 * @author Quentin Barnes
 */
public class signInPage extends AppCompatActivity {

    Button signInButton;
    Button registerButton;
    EditText signInUsername;
    EditText signInPassword;
    EditText registerUsername;
    EditText registerPassword;

    /**
     * It takes the info form the sign in/register feilds and passes it on to main activity if
     * the feilds are filled in for the given button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        setTitle("Sign In");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInUsername = (EditText) findViewById(R.id.signInUsername);
        signInPassword = (EditText) findViewById(R.id.signInPassword);
        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        signInButton = (Button) findViewById(R.id.signInButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                if (signInUsername.getText().toString().isEmpty() || signInPassword.getText().toString().isEmpty()) {
                    displayToast(getString(R.string.username_error));
                } else {
                    intent.putExtra("username", signInUsername.getText());
                    intent.putExtra("password", signInPassword.getText());
                    intent.putExtra("status", "SIGNIN");
                    setResult(1, intent);
                    finish();//finishing activity
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                if (registerUsername.getText().toString().isEmpty() || registerPassword.getText().toString().isEmpty()) {
                    displayToast(getString(R.string.username_error));
                } else {
                    intent.putExtra("username", registerUsername.getText());
                    intent.putExtra("password", registerPassword.getText());
                    intent.putExtra("status", "REGISTER");
                    setResult(2, intent);
                    finish();//finishing activity
                }
            }
        });


    }


    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
