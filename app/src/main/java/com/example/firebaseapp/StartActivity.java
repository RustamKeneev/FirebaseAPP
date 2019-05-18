package com.example.firebaseapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private AppCompatSpinner spinnerCountries;
    private EditText edit_phone_number;
    private Button button_register_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        spinnerCountries = findViewById(R.id.spinnerCountries);
        spinnerCountries.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames));

        edit_phone_number = findViewById(R.id.edit_phone_number);
        button_register_next = findViewById(R.id.button_register_next);

        button_register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryCodes[spinnerCountries.getSelectedItemPosition()];

                String numberPhone = edit_phone_number.getText().toString().trim();
                if (numberPhone.isEmpty()|| numberPhone.length()<10){
                    edit_phone_number.setError("Требуется действительный номер");
                    edit_phone_number.requestFocus();
                    return;
                }
                String phoneNumbers = "+"+ code + numberPhone;
                Intent intent = new Intent(StartActivity.this,VerifyPhoneActivity.class);
                intent.putExtra("numberPhone",phoneNumbers);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
