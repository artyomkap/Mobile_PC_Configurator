package com.example.pcconfiguratorassignment.Domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Represents a list of items in the Configuration
public class ConfiguratorCart {
    //Map for storing the CategoryId and Item Ids
    public static Map<Integer, Integer> categoryToItemIdMap = new HashMap<>();

    //Initializing default view of the ConfiguratorCart {0: -1, 1: -1, 2: -1, 3: -1, 4: -1}
    // -1 is a value of empty itemId in the cart
    static {
        for (int i = 0; i < 5; i++) {
            categoryToItemIdMap.put(i, -1);
        }
        System.out.println("INITIALIZED ConfCart" + categoryToItemIdMap);
    }

    //default getter of the Configuration list
    public static Map<Integer, Integer> getCategoryToItemIdMap() {
        return categoryToItemIdMap;
    }

    public static void setCategoryToItemIdMap(Map<Integer, Integer> categoryToItemIdMap) {
        ConfiguratorCart.categoryToItemIdMap = categoryToItemIdMap;
    }

    //method which resets all values in the Map
    public static void resetCategoryToItemIdMap() {
        categoryToItemIdMap.clear();
        for (int i = 0; i < 5; i++) {
            categoryToItemIdMap.put(i, -1);
        }
        System.out.println("RESET ConfCart: " + categoryToItemIdMap);
    }
    //method which updated all values in the Map, getting the itemIds when called
    public static void updateCategoryToItemIdMap(List<Integer> itemIds) {
        for (int i = 0; i < Math.min(itemIds.size(), 5); i++) {
            categoryToItemIdMap.put(i, itemIds.get(i));
        }
        System.out.println("UPDATED ConfCart: " + categoryToItemIdMap);
    }

}
