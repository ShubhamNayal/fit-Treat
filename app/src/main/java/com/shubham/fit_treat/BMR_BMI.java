package com.shubham.fit_treat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMR_BMI extends AppCompatActivity {
   EditText height,age,gender,weight;
   Button check;
   TextView BMI_result,BMR_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_r__b_m_i);

        //BMR (metric) = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5 MALE
        //BMR (metric) = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161 FEMALE



        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        check=findViewById(R.id.check);
        BMI_result=findViewById(R.id.BMI_result);
        BMR_result=findViewById(R.id.BMR_result);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float h=Float.parseFloat(height.getText().toString());
                float w=Float.parseFloat(weight.getText().toString());
                float a=Float.parseFloat(age.getText().toString());
                String gen=(gender.getText().toString());
                char genChar=gen.charAt(0);
                float Bmr = 0,Bmi=0;

                //BMR

                if(genChar=='M'||genChar=='m')
                {
                    Bmr= (float) ((w*10)+(6.25*h)-(5*a)-5);
                }
                else if(genChar=='F'||genChar=='f')
                {
                    Bmr= (float) ((w*10)+(6.25*h)-(5*a)-161);
                }
                BMR_result.setText(String.valueOf(Bmr)+" cals\n\nare your daily maintenance calories ");

                //               BMI = weight (kg) / [height (m)]2

                Bmi=(w*10000)/(h*h);


                    BMI_result.setText(String.valueOf(Bmi)+
                            " \n\nBelow 18.5\tUnderweight\n\n" +
                            "18.5 – 24.9\tNormal or Healthy Weight\n\n" +
                            "25.0 – 29.9\tOverweight\n\n" +
                            "30.0 and Above\tObese");

            }
        });


    }



}
