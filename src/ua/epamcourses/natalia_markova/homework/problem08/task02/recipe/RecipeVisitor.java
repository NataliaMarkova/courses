package ua.epamcourses.natalia_markova.homework.problem08.task02.recipe;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by natalia_markova on 12.06.2016.
 */

class Recipe {
    String prescription;
    Date endDate;

    public Recipe(String prescription, Date endDate) {
        this.prescription = prescription;
        this.endDate = endDate;
    }

    public String getPrescription() {
        return prescription;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void changeRecipe(RecipeChanger recipeChanger) {
        recipeChanger.changeRecipe(this);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "prescription='" + prescription + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}

abstract class RecipeChanger {
    abstract void changeRecipe(Recipe recipe);
}

class RecipeEndDateProlongator extends RecipeChanger {
    private int days;

    public RecipeEndDateProlongator(int days) {
        this.days = days;
    }

    @Override
    public void changeRecipe(Recipe recipe) {
        Date date = recipe.getEndDate();
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    }
}

public class RecipeVisitor {

    public static void main(String[] args) {
        Recipe recipe = new Recipe("Prescription", new Date(System.currentTimeMillis()));
        System.out.println(recipe);
        RecipeChanger recipeChanger = new RecipeEndDateProlongator(7);
        recipe.changeRecipe(recipeChanger);
        System.out.println(recipe);
    }
}
