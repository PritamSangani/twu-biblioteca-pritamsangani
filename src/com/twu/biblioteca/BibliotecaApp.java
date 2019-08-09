package com.twu.biblioteca;

import com.twu.biblioteca.helpers.InvalidMenuOptionException;
import com.twu.biblioteca.services.Menu;

import java.util.HashMap;
import java.util.Scanner;

public class BibliotecaApp {
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!" + "\n";
    private Menu menu;
    private boolean continueApp;

    BibliotecaApp() {
        menu = new Menu();
        continueApp = true;
    }

    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

    void start() {
        System.out.println(WELCOME_MESSAGE);
        do {
            menu.displayMenu();
            try {
                Scanner scanner = new Scanner(System.in);
                int option = scanner.nextInt();
                executeOptionMenu(option);
            } catch (InvalidMenuOptionException e) {
                System.err.println(e.getMessage());
            }
        } while (continueApp);
    }

    private void executeOptionMenu(int option) throws InvalidMenuOptionException {
        switch (option) {
            case 1:
                displayListOfBooks();
                break;
            default:
                throw new InvalidMenuOptionException("Invalid Menu Option Selected");
        }
    }

    private void displayListOfBooks() {

    }
}
