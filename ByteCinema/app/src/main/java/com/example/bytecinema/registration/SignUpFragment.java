package com.example.bytecinema.registration;

import static com.parse.Parse.getApplicationContext;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bytecinema.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText username, password, confirmPassword, email, zipcode;
    Button signUpBtn;
    TextView loginTV;
    Spinner spinnerState;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(getActivity(),R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        spinnerState.setOnItemSelectedListener(this);


        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).setFragment(new LoginFragment());

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setError(null);
                username.setError(null);
                zipcode.setError(null);
                password.setError(null);
                confirmPassword.setError(null);

                //Code to set Error message for Spinner
                TextView errorText = (TextView)spinnerState.getSelectedView();
                errorText.setError("STATE");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Required");//changes the selected item text to this

                //if any field is left blank it will send an error
                if(email.getText().toString().isEmpty()){
                    email.setError("Required");
                    return;
                }

                if(username.getText().toString().isEmpty()){
                    username.setError("Required");
                    return;
                }

                if(password.getText().toString().isEmpty()){
                    password.setError("Required");
                    return;
                }
                if(confirmPassword.getText().toString().isEmpty()){
                    confirmPassword.setError("Required");
                    return;
                }


                if(zipcode.getText().toString().isEmpty()){
                    zipcode.setError("Required");
                    return;
                }

                //if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()){
                //email.setError("Please enter a valid Email");
                //return;
                //}

                if(zipcode.getText().toString().length()!= 5){
                    zipcode.setError("Please enter a valid zipcode");
                    return;
                }

                //if password does not match confirm password send error
                if(!password.getText().toString().equals(confirmPassword)){
                    confirmPassword.setError("Password does not match");
                    return;
                }

                createAccount();

            }
        });
    }

    private void init(View view){
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        loginTV = view.findViewById(R.id.loginTV);
        zipcode= view.findViewById(R.id.zipcode);
        email = view.findViewById(R.id.email);
        signUpBtn = view.findViewById(R.id.create_account_btn);
        spinnerState= view.findViewById(R.id.state_spinner);

    }

    private void createAccount() {

        //figure out how to confirm emails and decline a new account
        // if an email or username already exists
        ParseUser user = new ParseUser();

        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        //FIGURE OUT HOW TO SET ZIPCODE AND STATE SPINNER
        //user.setZipcode(zipcode.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    //function if items from spinner are selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedState=parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(),selectedState,Toast.LENGTH_SHORT).show();

        //IF USER CHOOSES 'STATE' DISPLAY REQUIRED ERROR (DONT KNOW HOW TO DO THIS)
        //if(selectedState.matches("STATE")){
            //selectedState.
        //}
    }

    //function if items from spinner are not selected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}