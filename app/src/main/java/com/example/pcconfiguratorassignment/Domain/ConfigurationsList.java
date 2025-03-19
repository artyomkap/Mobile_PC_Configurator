package com.example.pcconfiguratorassignment.Domain;

import java.util.List;

//Represents a list of configurations containing a name and a list of values
public class ConfigurationsList {
    private String name; // Name of the configuration list
    private List<String> values; // List of configuration IDs in the configuration


    //Default constructor
    public ConfigurationsList(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    //getter of ConfigurationList attributes
    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

}
