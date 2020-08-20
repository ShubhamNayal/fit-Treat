package com.shubham.fit_treat;

public class Utilities {
    public static int calculcateCarbs (int calories, double carbsPercent)
    {
        return (int) (calories * (carbsPercent/100)) / 4 ;
    }
    public static int calculcateProtein (int calories, double proteinPercent)
    {
        return (int) (calories * (proteinPercent/100)) / 4 ;
    }
    public static int calculcateFat (int calories, double fatPercent)
    {
        return (int) (calories * (fatPercent/100)) / 9 ;
    }

    /* This method calculates the daily calorie intake based on age, weight, height, your goals, and your activity level. The
    Revised Harriss Benedict Equation was used for this.*/

    public static int calcCalorieIntake(int age, double weight, double height, boolean isImperial, boolean isMale, int activitylevelIndex, int goalIndex) {
        double calories, bmr;

        if (isImperial) {
            weight = weight / 2.2046226218;
            height = height * 2.54;
        }
        if (isMale)
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        else
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);

        calories = bmr * (1.2 + (.175 * activitylevelIndex));
        calories = (calories + ((goalIndex - 2) * 250));
        return (calories > 1000) ? (int) calories : 1000;
    }

}

