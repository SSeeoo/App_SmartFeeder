package com.example.smartfeeder;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Spinner animalTypeSpinner = findViewById(R.id.animalTypeSpinner);
        final Spinner breedSpinner = findViewById(R.id.breedSpinner);

        animalTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ArrayAdapter<CharSequence> adapter;
                if (position == 0) { // Dog Selected
                    adapter = ArrayAdapter.createFromResource(ProfileActivity.this, R.array.dog_breeds_array, android.R.layout.simple_spinner_item);
                } else { // Cat Selected
                    adapter = ArrayAdapter.createFromResource(ProfileActivity.this, R.array.cat_breeds_array, android.R.layout.simple_spinner_item);
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                breedSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}


