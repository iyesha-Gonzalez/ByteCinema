package com.example.bytecinema.registration;

import static com.parse.Parse.getApplicationContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bytecinema.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class LoginFragment extends Fragment {

    EditText usernameET, passwordET;
    Button loginBtn;
    TextView forgotPasswordTV, signUpTV;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        init(view);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if username and password is not empty log user in
                if (!usernameET.getText().toString().isEmpty() &&
                        !passwordET.getText().toString().isEmpty()) {

                    ParseUser.logInInBackground(usernameET.getText().toString(),
                            passwordET.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    //if user is not null it exists
                                    if (user != null) {

                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();

                                    } else {  // if user is null print error message

                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                }

            }
        });

        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).setFragment(new SignUpFragment());


            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity) getActivity()).setFragment(new ForgotPasswordFragment());
            }
        });


    }

    private void init(View view) {
        usernameET = view.findViewById(R.id.username);
        passwordET = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginBtn);
        forgotPasswordTV = view.findViewById(R.id.forgot_password);
        signUpTV = view.findViewById(R.id.create_account_tv);

    }
}



