package com.example.pcconfiguratorassignment.Domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
//Represents a Details with different attrubutes
public class Details implements Serializable {
    private int CategoryId; //CategoryId for items
    private String Description; //Description of item
    private boolean BestDetails; //boolean value for the getting BestDetail
    private int Id; //id of the Detail
    private double Price; //detail price
    private String ImagePath; //link for the image for this item
    private String ExtraInfo; // extra information for the item
    private String Title; //detail title
    private int numberInConf; // undu

    //default constructor for Details class
    public Details() {
    }

    //default getters and setters for Details class
    @NonNull
    @Override
    public String toString() {
        return Title;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isBestDetails() {
        return BestDetails;
    }

    public void setBestDetails(boolean bestDetails) {
        BestDetails = bestDetails;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getExtraInfo() {
        return ExtraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        ExtraInfo = extraInfo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
