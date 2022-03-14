package ui;

import model.Customer;
import model.Staff;
import model.VehicleInventory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Showroom {
    private VehicleInventory showroom = new VehicleInventory();
    private Scanner input;
    private Staff staffA;
    private Customer customerA;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/inventory.json";

    // EFFECTS: runs the showroom application
    public Showroom() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        staffA = new Staff("Lee","111-222-3333","Male",1);
        customerA = new Customer("Bill","222-111-3333","Male");
        input = new Scanner(System.in);
        showRoomInitialise();
        welcomeToShowroom();
    }

    // MODIFIES: this
    // EFFECTS: read Json file from directory. if a file is found, ask user to whether load it or not.
    // otherwise initialise showroom with 3 demo cars.
    private void showRoomInitialise() {
        try {
            jsonReader.read();
            System.out.println("A saved inventory has found, enter 'y' to load it.");
            String command;
            command = input.next();
            if (command.equals("y")) {
                showroom = jsonReader.read();
                System.out.println("Loaded Inventory from " + JSON_STORE);
            } else {
                showroom.addDemoVehicle();
                showroom.addDemoVehicle();
                showroom.addNewInventory("Chevy",staffA,5000);
                System.out.println("New Inventory Initialised!");
            }
        } catch (IOException e) {
            showroom.addDemoVehicle();
            showroom.addDemoVehicle();
            showroom.addNewInventory("Chevy",staffA,5000);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input at welcome menu
    private void welcomeToShowroom() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayWelcomeMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                if (command.equals("c")) {
                    clientMenu();
                } else if (command.equals("s")) {
                    staffMenu();
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays welcome menu
    private void displayWelcomeMenu() {
        System.out.println("Welcome to the showroom!");
        System.out.println("Choose your identity");
        System.out.println("\tc -> client");
        System.out.println("\ts -> staff");
        System.out.println("\tq -> leave");
    }

    // EFFECTS: displays client menu
    private void clientMenu() {
        boolean keepGoing = true;
        System.out.println(showroom.showInventoryWithNum());
        System.out.print("Enter the number of the vehicle you interested:\n");
        System.out.println("Enter any other digits to return main menu!");
        int num;
        integerInputCheck();
        num = input.nextInt();

        while (keepGoing) {
            if (showroom.isNumAvailable(num)) {
                System.out.println("Here is the full information of this vehicle:\n");
                System.out.println(showroom.showDetailInfoByNum(num));
                System.out.print("Enter the number of the vehicle you interested:\n");
                System.out.println("Or enter any other digits to return main menu!");
                integerInputCheck();
                num = input.nextInt();
            } else {
                keepGoing = false;
            }
        }
    }

    // EFFECTS: constructs staff menu
    private void staffMenu() {
        staffMenuDisplay();
        String command;
        boolean keepGoing = true;
        while (keepGoing) {
            command = input.next();
            if (command.equals("l")) {
                System.out.println(showroom.staffShowInventoryList());
                staffMenuDisplay();
            } else if (command.equals("s")) {
                staffSellInventory();
                staffMenuDisplay();
            } else if (command.equals("d")) {
                staffDeleteInventory();
            } else if (command.equals("b")) {
                saveInventory();
            } else if (command.equals("j")) {
                loadInventory();
            } else if (command.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("please enter a valid selection!");
            }
        }
    }

    // EFFECTS: displays staff menu
    private void staffMenuDisplay() {
        System.out.println("\nStaff Menu: Choose your action");
        System.out.println("\tl -> list of inventory");
        System.out.println("\ts -> mark sold inventory");
        System.out.println("\td -> delete inventory data");
        System.out.println("\tb -> save inventory");
        System.out.println("\tj -> load inventory");
        System.out.println("\tq -> leave");
    }

    // MODIFIES: this
    // EFFECTS: sell the inventory at staff's request
    private void staffSellInventory() {
        System.out.print("Enter the inventory number of the vehicle you'd like to sell:\n");
        System.out.println("Enter 0 to return!");
        integerInputCheck();
        int num = input.nextInt();
        if (!showroom.isInventoryNumAvailable(num) || num == 0) {
            return;
        }
        System.out.println("You have chosen:");
        System.out.println(showroom.vehicleList.get(showroom.invNumToIndex(num)).getInfoForStaff());
        if (!showroom.vehicleList.get(showroom.invNumToIndex(num)).isReadyForExhibit()) {
            System.out.println("*This vehicle is not ready for sale!*");
            return;
        }
        System.out.println("Do you want to sell it at expected price? y/n");
        String command = input.next();
        if (command.equals("y")) {
            int expectedPrice = showroom.vehicleList.get(showroom.invNumToIndex(num)).sellExpectedPrice;
            showroom.vehicleList.get(showroom.invNumToIndex(num)).sellTheVehicle(expectedPrice,staffA,customerA);
            System.out.println("Vehicle sold!");
        } else {
            System.out.println("You gave up selling!");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the inventory at staff's request
    private void staffDeleteInventory() {
        System.out.print("Enter the inventory number of the vehicle you'd like to delete:\n");
        System.out.println("Enter 0 to return!");
        integerInputCheck();
        int num = input.nextInt();
        if (!showroom.isInventoryNumAvailable(num) || num == 0) {
            return;
        }
        System.out.println("You have chosen:");
        System.out.println(showroom.vehicleList.get(showroom.invNumToIndex(num)).getInfoForStaff());
        System.out.println("Do you want to delete the inventory? y/n");
        String command = input.next();
        if (command.equals("y")) {
            if (showroom.removeInventory(num)) {
                System.out.println("Inventory deleted!");
            } else {
                System.out.println("Failed!");
            }
        } else {
            System.out.println("You gave up deleting!");
        }
    }

    // EFFECTS: prompt user to enter a valid number
    private void integerInputCheck() {
        while (!input.hasNextInt()) {
            System.out.println("Please enter a number!");
            input.next();
        }
    }

    // EFFECTS: saves the inventory to file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(showroom);
            jsonWriter.close();
            System.out.println("Saved Inventory to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads inventory from file
    private void loadInventory() {
        try {
            showroom = jsonReader.read();
            System.out.println("Loaded Inventory from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
