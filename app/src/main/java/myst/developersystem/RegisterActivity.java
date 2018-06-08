package myst.developersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myst.developersystem.api.communicator.Communicator;
import myst.developersystem.api.model.BusProvider;
import myst.developersystem.api.model.ServerEvent;
import myst.developersystem.classes.HashPassword;

public class RegisterActivity extends AppCompatActivity {

    private Communicator communicator;
    private Spinner roleSpinner;
    private String username, email, password, passwordCheck, hashedPassword, role;
    private EditText usernameET, emailET, passwordET, passwordCheckET;
    private Button registerButton;
    private ArrayList<String> inputs;
    private final static String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initVariables();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setVariables();
                    emptyFields();
                    validEmail();
                    validPassword();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                sendPostRequest();
            }
        });
    }

    private void setRoleSpinner() {
        roleSpinner = (Spinner)findViewById(R.id.role);
        List<String> roleOptions = new ArrayList<String>();
        roleOptions.add("Deweloper");
        roleOptions.add("Rynek deweloperski");
        roleOptions.add("Użytkownik");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(dataAdapter);
        roleSpinner.setSelection(0, false);
    }

    private void initVariables() {
        usernameET = (EditText)findViewById(R.id.registerUsername);
        emailET = (EditText)findViewById(R.id.registerEmail);
        passwordET = (EditText)findViewById(R.id.registerPassword);
        passwordCheckET = (EditText)findViewById(R.id.registerPasswordCheck);
        passwordET.setTransformationMethod(new PasswordTransformationMethod());
        passwordCheckET.setTransformationMethod(new PasswordTransformationMethod());
        registerButton = (Button)findViewById(R.id.registerButton);
        setRoleSpinner();
    }

    private void setVariables() {
        username = usernameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        passwordCheck = passwordCheckET.getText().toString();
        role = String.valueOf(roleSpinner.getSelectedItemPosition() + 1);
        inputs = new ArrayList<>(Arrays.asList(username, email, password, passwordCheck, role));
    }

    private void sendPostRequest() {
        hashedPassword = HashPassword.sha256(password);
        communicator = new Communicator();
        communicator.registerPost(username, email, hashedPassword, role);
    }

    private void emptyFields() throws Exception {
        for (String input : inputs) {
            if(input == null || input.isEmpty()) {
                throw new Exception("Nie zostały wypełnione wszystkie pola.");
            }
        }
    }

    private void validEmail() throws Exception {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception("Niepoprawny adres e-mail.");
        }
    }

    private void validPassword() throws Exception {
        if(!password.equals(passwordCheck)) {
            throw new Exception("Hasła nie są takie same");
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        if(serverEvent.getServerResponse().getStatus().equals("fail")) {
            return;
        }

        Toast.makeText(this, serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        gotoLogin();
    }

}
