package com.shubham.fit_treat;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FindCaloriesDialog extends DialogFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    RadioGroup measurement;
    RadioButton us, metric;
    Spinner gender, activitylevel, goal;
    EditText age, weight, in_cm;
    Button use_value;
    Communicator communicator;

    int recommendedCalories = 0;

    public FindCaloriesDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Your Daily Calorie Intake");
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.find_calories, container, false);

        final RadioGroup measurement = (RadioGroup) view.findViewById(R.id.measurement_radio_group);
        RadioButton us = (RadioButton) view.findViewById(R.id.US_radio_button);
        RadioButton metric = (RadioButton) view.findViewById(R.id.metric_radio_button);
        final Spinner gender = (Spinner) view.findViewById(R.id.gender_spinner);
        final Spinner activitylevel = (Spinner) view.findViewById(R.id.activity_level_spinner);
        final Spinner goal = (Spinner) view.findViewById(R.id.goal_spinner);
        final EditText age = (EditText) view.findViewById(R.id.age_edit_text);
        final EditText weight = (EditText) view.findViewById(R.id.weight_edit_text);
        final EditText in_cm = (EditText) view.findViewById(R.id.inch_cm_edit_text);
        Button calcCalories = (Button) view.findViewById(R.id.calories_dialog_button);
        final Button use_value = (Button) view.findViewById(R.id.use_value_button);
        final TextView recCalories = (TextView) view.findViewById(R.id.recommended_calories_text_view);
        final TextView weightEntry = (TextView) view.findViewById(R.id.weight_text_view);
        final TextView heightEntry = (TextView) view.findViewById(R.id.height_text_view);

        use_value.setVisibility(View.GONE);
        measurement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.metric_radio_button:
                        weightEntry.setText(getString(R.string.weight_label_metric));
                        heightEntry.setText(getString(R.string.height_label_metric));
                        break;
                    case R.id.US_radio_button:
                        weightEntry.setText(getString(R.string.weight_label_us));
                        heightEntry.setText(getString(R.string.height_label_us));
                }
            }
        });

        calcCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isMale = gender.getSelectedItemPosition() == 0;
                boolean isImperial = measurement.indexOfChild(measurement.findViewById(measurement.getCheckedRadioButtonId())) == 0;
                int activitylevelIndex = activitylevel.getSelectedItemPosition();
                int goalIndex = goal.getSelectedItemPosition();
                String ageEntry = age.getText().toString();
                String weightEntry = weight.getText().toString();
                String in_cmEntry = in_cm.getText().toString();

                if (ageEntry.isEmpty() || weightEntry.isEmpty() || in_cmEntry.isEmpty())
                    Toast.makeText(getActivity(), "Please fill in all spaces.", Toast.LENGTH_SHORT).show();
                else {
                    int parseAge = Integer.parseInt(ageEntry);
                    double parseWeight = Double.parseDouble(weightEntry);
                    double parseHeight = Double.parseDouble(in_cmEntry);

                    recommendedCalories = (Utilities.calcCalorieIntake
                            (parseAge, parseWeight, parseHeight, isImperial, isMale, activitylevelIndex, goalIndex));

                    recCalories.setText(String.valueOf(recommendedCalories));

                    use_value.setVisibility(View.VISIBLE);

                }
            }
        });

        use_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.sendCalories(String.valueOf(recommendedCalories));
                dismiss();
            }
        });
        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    interface Communicator {
        void sendCalories(String dialogCalories);
    }
}

