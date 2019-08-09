package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BibliotecaAppTest {
    private BibliotecaApp app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        app = new BibliotecaApp();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldDisplayWelcomeMessageOnStart() {
        app.start();
        assertThat(outContent.toString().trim().replaceAll("(\r\n|\n\r|\r|\n)", ""), is("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!"));
    }
}
