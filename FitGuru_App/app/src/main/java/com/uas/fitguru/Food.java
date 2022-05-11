package com.uas.fitguru;

public class Food {

    private long _id;
    private String _foodName; private String _foodDescription; private long _foodCalories;

    //Constructor untuk Class Food
    public Food(){

    }

    //Method untuk set ID Food
    public void setID(long id){
        this._id = id;
    }

    //Method untuk mendapatkan ID Food
    public long getID(){
        return this._id;
    }

    //Method untuk set Nama Food
    public void setFoodName(String foodName){
        this._foodName = foodName;
    }

    //Method untuk mendapatkan Nama Food
    public String getFoodName(){
        return this._foodName;
    }

    //Method untuk set Deskripsi Food
    public void setFoodDescription(String foodDescription){
        this._foodDescription = foodDescription;
    }

    //Method untuk mendapatkan Deskripsi Food
    public String getFoodDescription(){
        return this._foodDescription;
    }

    //Method untuk set Calorie Food
    public void setFoodCalories(long foodCalories){
        this._foodCalories = foodCalories;
    }

    //Method untuk mendapatkan Calorie Food
    public long getFoodCalories(){
        return this._foodCalories;
    }

    //Method override yang dipakai untuk mengubah objek Food menjadi String
    @Override
    public String toString(){
        return "Food Name\t\t\t\t\t\t\t: " + _foodName + " \nFood Description\t\t: " + _foodDescription + " \nFood Calories\t\t\t\t\t: " + _foodCalories + " Calories";
    }
}
