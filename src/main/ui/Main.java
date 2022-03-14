package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        /*
        try {
            new Showroom();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        */

        try {
            new ShowroomGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}
