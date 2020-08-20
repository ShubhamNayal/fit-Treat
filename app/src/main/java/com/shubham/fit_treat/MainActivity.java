package com.shubham.fit_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements FindCaloriesDialog.Communicator{
    private BottomNavigationView bottomNavigationView;

    int cbPercent,prPercent,fatPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);


        startActivity(new Intent(getApplicationContext(),registerActivity.class));
        hideOnCreate();
        EditText calories = (EditText) findViewById(R.id.calories_needed);
        calories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    hideKeyboard(v);
            }
        });
        changeCarbsSeekBarValue();
        changeProteinSeekBarValue();
        changeFatsSeekBarValue();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),loginActivity.class));
        finish();;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override




                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                    Intent myIntent=null;
                    switch (menuItem.getItemId()) {
                        case R.id.stepCount:
                            myIntent = new Intent(getBaseContext(),StepCountActivity.class);
                            startActivity(myIntent);
                            break;
                        case R.id.bmi_bmr:
                             myIntent = new Intent(getBaseContext(),BMR_BMI.class);
                            startActivity(myIntent);
                            break;


                    }
                    return true;
                }
            };
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void changeCarbsSeekBarValue()
    {
        SeekBar carbsSeekBar = (SeekBar) findViewById(R.id.carbs_seekBar);
        final TextView carbsPercentage = (TextView) findViewById(R.id.carbs_percent);
        carbsSeekBar.setMax(100);

        carbsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carbsPercentage.setText(String.valueOf(progress + "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void changeProteinSeekBarValue()
    {
        SeekBar proteinsSeekBar = (SeekBar) findViewById(R.id.protein_seekBar);
        final TextView proteinsPercentage = (TextView) findViewById(R.id.protein_percent);
        proteinsSeekBar.setMax(100);

        proteinsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                proteinsPercentage.setText(String.valueOf(progress + "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void changeFatsSeekBarValue()
    {
        SeekBar fatsSeekBar = (SeekBar) findViewById(R.id.fats_seekBar);
        final TextView fatsPercentage = (TextView) findViewById(R.id.fat_percent);
        fatsSeekBar.setMax(100);

        fatsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fatsPercentage.setText(String.valueOf(progress + "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void PresetButtons(View view)
    {
        SeekBar carbsSeekBar = (SeekBar) findViewById(R.id.carbs_seekBar);
        SeekBar proteinsSeekBar = (SeekBar) findViewById(R.id.protein_seekBar);
        SeekBar fatsSeekBar = (SeekBar) findViewById(R.id.fats_seekBar);
        TextView carbsPercent = (TextView) findViewById(R.id.carbs_percent);
        TextView proteinsPercent = (TextView) findViewById(R.id.protein_percent);
        TextView fatsPercent = (TextView) findViewById(R.id.fat_percent);
        Button highCarbsButton = (Button) findViewById(R.id.high_carbs_button);
        Button moderateButton = (Button) findViewById(R.id.moderate_button);
        Button lowCarbsButton = (Button) findViewById(R.id.low_carbs_button);
        if (highCarbsButton.isPressed()) {
            cbPercent=60;
            prPercent=25;
            fatPercent=15;
            carbsPercent.setText("60%");
            proteinsPercent.setText("25%");
            fatsPercent.setText("15%");
            carbsSeekBar.setProgress(cbPercent);
            proteinsSeekBar.setProgress(prPercent);
            fatsSeekBar.setProgress(fatPercent);
        }
        else if (moderateButton.isPressed())
        { cbPercent=50;
            prPercent=30;
            fatPercent=20;
            carbsPercent.setText("50%");
            proteinsPercent.setText("30%");
            fatsPercent.setText("20%");
            carbsSeekBar.setProgress(50);
            proteinsSeekBar.setProgress(30);
            fatsSeekBar.setProgress(20);
        }
        else if (lowCarbsButton.isPressed()){
            cbPercent=20;
            prPercent=40;
            fatPercent=40;
            carbsPercent.setText("20%");
            proteinsPercent.setText("40%");
            fatsPercent.setText("40%");
            carbsSeekBar.setProgress(20);
            proteinsSeekBar.setProgress(40);
            fatsSeekBar.setProgress(40);
        }

        showTotalGrams(cbPercent,prPercent,fatPercent);
    }


    public void submitCalories(View view)
    {
        EditText calories = (EditText) findViewById(R.id.calories_needed);
        TextView carbsPercent = (TextView) findViewById(R.id.carbs_percent);
        TextView proteinPercent = (TextView) findViewById(R.id.protein_percent);
        TextView fatPercent = (TextView) findViewById(R.id.fat_percent);
        String calsStr = calories.getText().toString();
        double cPercent = Double.parseDouble(carbsPercent.getText().toString().replace("%",""));
        double pPercent = Double.parseDouble(proteinPercent.getText().toString().replace("%", ""));
        double fPercent = Double.parseDouble(fatPercent.getText().toString().replace("%",""));
        int cals = 0;

        if (calsStr.isEmpty())
            Toast.makeText(this,"Please enter a valid number to calculate.",Toast.LENGTH_SHORT).show();
        else {
            cals = Integer.parseInt(calsStr);
            makeVisibleAgain();
            showTotalGrams(Utilities.calculcateCarbs(cals, cPercent), Utilities.calculcateProtein(cals, pPercent),
                    Utilities.calculcateFat(cals, fPercent));
        }
        hideKeyboard(view);
    }

    public void dontKnowButton(View view) {
        FragmentManager manager = getFragmentManager();
        FindCaloriesDialog findCalories = new FindCaloriesDialog();
        findCalories.show(manager, "FindCalories");

    }

    private void hideOnCreate() {
        TextView carbGrams = (TextView) findViewById(R.id.carbs_grams);
        TextView proteinGrams = (TextView) findViewById(R.id.protein_grams);
        TextView fatGrams = (TextView) findViewById(R.id.fat_grams);
        TextView carbsCalc = (TextView) findViewById(R.id.carbs_calculated_text_view);
        TextView proteinsCalc = (TextView) findViewById(R.id.protein_calculated_text_view);
        TextView fatsCalc = (TextView) findViewById(R.id.fat_calculated_text_view);
        ImageView logo = (ImageView) findViewById(R.id.logo);

        logo.setVisibility(View.GONE);
        carbGrams.setVisibility(View.GONE);
        proteinGrams.setVisibility(View.GONE);
        fatGrams.setVisibility(View.GONE);
        carbsCalc.setVisibility(View.GONE);
        proteinsCalc.setVisibility(View.GONE);
        fatsCalc.setVisibility(View.GONE);


    }

    private void makeVisibleAgain() {
        TextView carbGrams = (TextView) findViewById(R.id.carbs_grams);
        TextView proteinGrams = (TextView) findViewById(R.id.protein_grams);
        TextView fatGrams = (TextView) findViewById(R.id.fat_grams);
        TextView proteinsCalc = (TextView) findViewById(R.id.protein_calculated_text_view);
        TextView fatsCalc = (TextView) findViewById(R.id.fat_calculated_text_view);
        TextView carbsCalc = (TextView) findViewById(R.id.carbs_calculated_text_view);
        ImageView logo = (ImageView) findViewById(R.id.logo);

        logo.setVisibility(View.VISIBLE);
        carbGrams.setVisibility(View.VISIBLE);
        proteinGrams.setVisibility(View.VISIBLE);
        fatGrams.setVisibility(View.VISIBLE);
        carbsCalc.setVisibility(View.VISIBLE);
        proteinsCalc.setVisibility(View.VISIBLE);
        fatsCalc.setVisibility(View.VISIBLE);


    }

    private void showTotalGrams (int carbs,int proteins, int fats)
    {
        TextView carbGrams = (TextView) findViewById(R.id.carbs_grams);
        TextView proteinGrams = (TextView) findViewById(R.id.protein_grams);
        TextView fatGrams = (TextView) findViewById(R.id.fat_grams);

        carbGrams.setText(Integer.toString(carbs) + " grams");
        proteinGrams.setText(Integer.toString(proteins) + " grams");
        fatGrams.setText(Integer.toString(fats) + " grams");
    }

    @Override
    public void sendCalories(String recommendedCalories) {
        EditText calories = (EditText) findViewById(R.id.calories_needed);
        calories.setText(recommendedCalories);

    }

}