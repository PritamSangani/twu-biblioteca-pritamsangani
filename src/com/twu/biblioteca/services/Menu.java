package com.twu.biblioteca.services;

import java.util.HashMap;

public class Menu {
    private HashMap<Integer, String> options;

    public Menu () {
        options = new HashMap<Integer, String>();
        options.put(1, "List of Books");
    }

    public void displayMenu() {
        System.out.println("Choose a menu option below: ");
        for (HashMap.Entry<Integer, String> option: options.entrySet()) {
            System.out.println(String.format("[%d] %s", option.getKey(), option.getValue()));
        }
    }

}
