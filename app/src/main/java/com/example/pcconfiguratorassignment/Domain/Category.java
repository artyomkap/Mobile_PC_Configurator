package com.example.pcconfiguratorassignment.Domain;

//Represents a category in the application
public class Category {
    // Attributes
    private int id; //Category Id
    private String ImagePath; //ImagePath from Db for category
    private String Name; //Category Name

    //Default Constructor
    public Category() {
    }

    //Default getters and setter for all attributes in Category class
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
