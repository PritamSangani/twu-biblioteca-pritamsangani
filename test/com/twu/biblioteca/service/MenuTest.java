package com.twu.biblioteca.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import java.util.HashMap;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MenuTest {
    private Menu menu;
    private HashMap<Integer, String> options;

    @Mock
    PrintStream outPrinter;

    @BeforeEach
    void setUp() {
        options = new HashMap<>();
        options.put(1, "List of Books");
        menu = new Menu(outPrinter, options);
    }

    @Test
    void displayMenu() {
        // Given options hashmap

        // when
        menu.displayMenu();

        // then
        for (HashMap.Entry<Integer, String> option:
            options.entrySet()) {
            verify(outPrinter).println(String.format("[%d] %s", option.getKey(), option.getValue()));
        }
    }
}