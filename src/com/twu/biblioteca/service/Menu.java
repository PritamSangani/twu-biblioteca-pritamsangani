package com.twu.biblioteca.service;

import java.io.PrintStream;
import java.util.HashMap;

public class Menu {
    private HashMap<Integer, String> options;
    private PrintStream outPrinter = System.out;

    public Menu () {
        options = new HashMap<>();
        options.put(1, "List of Books");
        options.put(2, "Checkout a book");
        options.put(3, "Return a book");
        options.put(4, "Quit the application");
    }

    Menu(PrintStream outPrinter, HashMap<Integer, String> options) {
        this.outPrinter = outPrinter;
        this.options = options;
    }

    public void displayMenu() {
        outPrinter.println("\nChoose a menu option below: ");
        for (HashMap.Entry<Integer, String> option: options.entrySet()) {
            outPrinter.println(String.format("[%d] %s", option.getKey(), option.getValue()));
        }
    }

}
