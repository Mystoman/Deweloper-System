package myst.developersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.classes.HashPassword;

public class RegisterActivity extends AppCompatActivity {

    private Communicator communicator;
    private String username, email, password, passwordCheck, hashedPassword;
    private EditText usernameET, emailET, passwordET, passwordCheckET;
    private Button registerButton;
    private ArrayList<String> inputs;
    private final static String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = (EditText)findViewById(R.id.registerUsername);
        emailET = (EditText)findViewById(R.id.registerEmail);
        passwordET = (EditText)findViewById(R.id.registerPassword);
        passwordCheckET = (EditText)findViewById(R.id.registerPasswordCheck);
        passwordET.setTransformationMethod(new PasswordTransformationMethod());
        passwordCheckET.setTransformationMethod(new PasswordTransformationMethod());

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                passwordCheck = passwordCheckET.getText().toString();
                inputs = new ArrayList<>(Arrays.asList(username, email, password, passwordCheck));
                if(emptyFields(inputs)) {
                    Toast.makeText(getApplicationContext(), "Nie zostały wypełnione wszystkie pola", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!validEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Niepoprawny adres e-mail!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!validPassword(password, passwordCheck)) {
                    Toast.makeText(getApplicationContext(), "Hasła nie są takie same!", Toast.LENGTH_LONG).show();
                    return;
                }
                hashedPassword = HashPassword.sha256(password);
                communicator = new Communicator();
                communicator.registerPost(username, email, hashedPassword);
            }
        });
    }

    private boolean emptyFields(ArrayList<String> inputs) {
        for (String input : inputs) {
            if(input == null || input.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean validEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validPassword(String password, String passwordCheck) {
        return password.equals(passwordCheck);
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        Toast.makeText(this, ""+serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
    }

}
