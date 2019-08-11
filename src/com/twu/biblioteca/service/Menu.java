package com.twu.biblioteca.service;

import java.io.PrintStream;
import java.util.HashMap;

public class Menu {
    private HashMap<Integer, String> options;
    private PrintStream outPrinter = System.out;

    public Menu () {
        options = new HashMap<Integer, String>();
        options.put(1, "List of Books");
    }

    public Menu(PrintStream outPrinter, HashMap<Integer, String> options) {
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
