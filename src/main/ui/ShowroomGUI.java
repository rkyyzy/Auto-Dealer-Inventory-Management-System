package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.Customer;
import model.Staff;
import model.VehicleInventory;
import persistence.JsonReader;
import persistence.JsonWriter;

public class ShowroomGUI {
    private static VehicleInventory showroom = new VehicleInventory();
    private final Staff staffA;
    private final Customer customerA;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/inventory.json";

    // EFFECTS: runs the showroom application
    public ShowroomGUI() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        staffA = new Staff("Lee", "111-222-3333", "Male", 1);
        customerA = new Customer("Bill", "222-111-3333", "Male");
        showRoomInitialise();
        welcomeToShowroom();
    }

    // MODIFIES: this
    // EFFECTS: read Json file from directory. if a file is found, ask user to
    // whether load it or not.
    // otherwise initialise showroom with 3 demo cars.
    private void showRoomInitialise() {
        try {
            jsonReader.read();
            String input = JOptionPane.showInputDialog("A saved inventory has found\n"
                    + "To initialise new inventory file, enter 'new file', otherwise load saved file");
            if (input.equals("new file")) {
                showroom.addDemoVehicle();
                showroom.addDemoVehicle();
                showroom.addNewInventory("Chevy", staffA, 5000);
                JOptionPane.showMessageDialog(null, "New Inventory Initialised!"
                        + "\nNote: You are able to load data manually in Staff menu!");
            } else {
                showroom = jsonReader.read();
                JOptionPane.showMessageDialog(null, "Loaded Inventory from " + JSON_STORE);
            }
        } catch (IOException e) {
            showroom.addDemoVehicle();
            showroom.addDemoVehicle();
            showroom.addNewInventory("Chevy", staffA, 5000);
            JOptionPane.showMessageDialog(null, "No saved data found, a new inventory created");
        }
    }

    // MODIFIES: this
    // EFFECTS: create application dialog
    private void welcomeToShowroom() {
        JFrame menuFrame = new JFrame("showroom");
        menuFrame.setLayout(null);
        JLabel tipLabel = new JLabel("Welcome to the showroom!");
        tipLabel.setBounds(100, 20, 400, 50);
        tipLabel.setFont(new Font("", Font.BOLD, 22));
        menuFrame.add(tipLabel);
        JButton clientButton = new JButton("client");
        clientButton.setBounds(140, 100, 180, 50);
        menuFrame.add(clientButton);
        JButton staffButton = new JButton("staff");
        staffButton.setBounds(140, 200, 180, 50);
        menuFrame.add(staffButton);
        JButton leaveButton = new JButton("leave");
        leaveButton.setBounds(140, 300, 180, 50);
        menuFrame.add(leaveButton);
        menuFrame.setBounds(100, 100, 460, 450);
        menuFrame.setResizable(false);
        menuFrame.setVisible(true);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clientButtonEvent(clientButton);
        staffButtonEvent(staffButton);
        cancelButtonEvent(menuFrame, leaveButton);
    }

    // EFFECTS: create client button click event
    private void clientButtonEvent(JButton clientButton) {
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInventoryList();
            }
        });
    }

    // EFFECTS: create staff button click event
    private void staffButtonEvent(JButton staffButton) {
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffMenuFrame();
            }
        });
    }

    // EFFECTS: create staff menu frame
    private void staffMenuFrame() {
        JFrame staffFrame = new JFrame("Staff Menu");
        staffFrame.setLayout(null);
        JLabel tipLabel = new JLabel("Welcome to the Staff Menu!");
        tipLabel.setFont(new Font("", Font.BOLD, 24));
        JButton addButton = new JButton("Add a inventory");
        JButton editButton = new JButton("Edit a inventory");
        JButton listButton = new JButton("List of inventory");
        JButton markButton = new JButton("Mark inventory as sold");
        JButton deleteButton = new JButton("Delete a inventory");
        JButton saveButton = new JButton("Save inventory");
        JButton loadButton = new JButton("Load inventory");
        JButton leaveButton = new JButton("Leave");
        staffUpperLayout(staffFrame, tipLabel, addButton, editButton, listButton);
        staffLowerLayout(staffFrame, markButton, deleteButton, saveButton, loadButton, leaveButton);
        addButtonEventMain(addButton);
        editButtonEvent(editButton);
        saveDataButtonEvent(saveButton);
        loadButtonEvent(loadButton);
        deleteButtonEvent(deleteButton);
        leaveButtonEvent(staffFrame, leaveButton);
        staffListButton(listButton);
        markButtonEvent(markButton);
    }

    // EFFECTS: create lower side components of staff menu
    private void staffLowerLayout(JFrame f, JButton mark, JButton del, JButton sav, JButton load, JButton leave) {
        mark.setBounds(120, 290, 240, 50);
        f.add(mark);
        del.setBounds(120, 360, 240, 50);
        f.add(del);
        sav.setBounds(120, 430, 240, 50);
        f.add(sav);
        load.setBounds(120, 500, 240, 50);
        f.add(load);
        leave.setBounds(120, 570, 240, 50);
        f.add(leave);
        f.setBounds(100, 100, 460, 690);
        f.setResizable(false);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: create upper side components of staff menu
    private void staffUpperLayout(JFrame f, JLabel tipLabel, JButton add, JButton edit, JButton list) {
        tipLabel.setForeground(Color.red);
        tipLabel.setBounds(80, 20, 400, 50);
        f.add(tipLabel);
        add.setBounds(120, 80, 240, 50);
        f.add(add);
        edit.setBounds(120, 150, 240, 50);
        f.add(edit);
        list.setBounds(120, 220, 240, 50);
        f.add(list);
    }

    // EFFECTS: create add button click event
    private void addButtonEventMain(JButton addButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButtonEvent();
            }
        });
    }

    // EFFECTS: create save button click event
    private void saveDataButtonEvent(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInventory();
                JOptionPane.showMessageDialog(null, "Inventory Saved Successfully");
            }
        });
    }

    // EFFECTS: create edit button click event
    private void editButtonEvent(JButton editButton) {
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numStr = JOptionPane.showInputDialog(null,
                                "Enter the inventory number of the vehicle you'd like to edit:");
                if (numStr == null || "".equals(numStr)) {
                    return;
                }
                editFrame(numStr);
            }
        });
    }

    // EFFECTS: create edit frame
    private void editFrame(String numStr) {
        JFrame f = new JFrame("edit inventory");
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        p1.setBorder(BorderFactory.createTitledBorder("a inventory"));
        JTextArea t1 = new JTextArea(20, 40);
        tableFormatting(t1);
        int num = Integer.valueOf(numStr);
        t1.setText(showroom.vehicleList.get(showroom.invNumToIndex(num)).getInfoForStaff());
        p1.add(new JScrollPane(t1));
        contentPane.add(p1, BorderLayout.NORTH);
        JButton sureButton = new JButton("edit");
        JButton cancelButton = new JButton("cancel");
        JPanel jp2 = new JPanel(new GridLayout(1, 2));
        frameSetUp(f, contentPane, p1, sureButton, cancelButton, jp2);
        subSureButtonEvent(f, num, sureButton);
        cancelButtonEvent(f, cancelButton);
    }

    // EFFECTS: create sure button click event
    private void subSureButtonEvent(JFrame f, int num, JButton sureButton) {
        sureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editYear(num);
                editMaker(num);
                editModel(num);
                editType(num);
                editColor(num);
                editURL(num);
                editMileage(num);
                editVIN(num);
                editComment(num);
                editListPrice(num);
                JOptionPane.showMessageDialog(null, "Edited successfully!");
                f.dispose();
            }
        });
    }

    // EFFECTS: create load button click event
    private void loadButtonEvent(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadInventory();
                JOptionPane.showMessageDialog(null,
                        "Inventory Loaded Successfully");
            }
        });
    }

    // EFFECTS: create delete button click event
    private void deleteButtonEvent(JButton deleteButton) {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numStr = JOptionPane
                        .showInputDialog(null,
                                "Enter the inventory number of the vehicle you'd like to delete:");
                if (numStr == null || "".equals(numStr)) {
                    return;
                }
                deleteFrame(numStr);
            }
        });
    }

    // EFFECTS: create delete subframe
    private void deleteFrame(String numStr) {
        JFrame f = new JFrame("delete inventory");
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        p1.setBorder(BorderFactory.createTitledBorder("a inventory"));
        JTextArea t1 = new JTextArea(20, 40);
        tableFormatting(t1);
        int num = Integer.valueOf(numStr);
        t1.setText(showroom.vehicleList.get(showroom.invNumToIndex(num)).getInfoForStaff());
        p1.add(new JScrollPane(t1));
        contentPane.add(p1, BorderLayout.NORTH);
        JButton sureButton = new JButton("delete");
        JButton cancelButton = new JButton("cancel");
        JPanel jp2 = new JPanel(new GridLayout(1, 2));
        frameSetUp(f, contentPane, p1, sureButton, cancelButton, jp2);
        sureButtonEvent(f, num, sureButton);
        cancelButtonEvent(f, cancelButton);
    }

    // EFFECTS: create cancel button click event
    private void cancelButtonEvent(JFrame f, JButton cancelButton) {
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
    }

    // EFFECTS: create sure button click event
    private void sureButtonEvent(JFrame f, int num, JButton sureButton) {
        sureButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showroom.removeInventory(num)) {
                    JOptionPane.showMessageDialog(null, "Inventory deleted successful!");
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed!");
                }
            }
        });
    }

    // EFFECTS: create leave button click event
    private void leaveButtonEvent(JFrame staffFrame, JButton leaveButton) {
        cancelButtonEvent(staffFrame, leaveButton);
    }

    // EFFECTS: create mark button click event
    private void markButtonEvent(JButton markButton) {
        markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer num = markSoldButtonEvent();
                JFrame frame = new JFrame("sell");
                Container contentPane = frame.getContentPane();
                contentPane.setLayout(new BorderLayout());
                JPanel p1 = new JPanel();
                p1.setLayout(new GridLayout(1, 1));
                p1.setBorder(BorderFactory.createTitledBorder("Detailed Information"));
                JTextArea t1 = new JTextArea(20, 30);
                tableFormatting(t1);
                t1.setText(showroom.vehicleList.get(showroom.invNumToIndex(num)).getInfoForStaff());
                p1.add(new JScrollPane(t1));
                contentPane.add(p1, BorderLayout.NORTH);
                JButton sureButton = new JButton("sell");
                JButton cancelButton = new JButton("cancel");
                JPanel jp2 = new JPanel(new GridLayout(1, 2));
                frameSetUp(frame, contentPane, p1, sureButton, cancelButton, jp2);
                subButtonEvents(num, frame, sureButton, cancelButton);
            }
        });
    }

    // EFFECTS: setting up table format
    private void tableFormatting(JTextArea t1) {
        t1.setTabSize(10);
        t1.setFont(new Font("", Font.BOLD, 16));
        t1.setLineWrap(true);
        t1.setWrapStyleWord(true);
        t1.setEditable(false);
    }

    // EFFECTS: create components for frame
    private void frameSetUp(JFrame frame, Container conPane,
                            JPanel p1, JButton sureButton, JButton cancelButton, JPanel jp2) {
        jp2.add(sureButton);
        jp2.add(cancelButton);
        conPane.add(jp2, BorderLayout.SOUTH);
        p1.setSize(100, 300);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: create sure button click event
    private void subButtonEvents(Integer num, JFrame f, JButton sureButton, JButton cancelButton) {
        sureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellButtonEvent(num, f);
            }
        });
        cancelButtonEvent(f, cancelButton);
    }

    // EFFECTS: create list button click event
    private void staffListButton(JButton listButton) {
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInventoryList();
            }
        });
    }

    // EFFECTS: create mark sold button click event
    private Integer markSoldButtonEvent() {
        String numStr = JOptionPane
                .showInputDialog(null,
                        "Enter the inventory number of the vehicle you'd like to sell:");
        if (numStr.isEmpty()) {
            return null;
        }

        int num = Integer.valueOf(numStr);

        if (!showroom.isInventoryNumAvailable(num)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid INVENTORY NUMBER!");
            return null;
        }

        if (!showroom.vehicleList.get(showroom.invNumToIndex(num)).isReadyForExhibit()) {
            JOptionPane.showMessageDialog(null, "*This vehicle is not ready for sale!*");
            return null;
        }
        return num;
    }

    // EFFECTS: create add button click event
    private void addButtonEvent() {
        int choose = JOptionPane
                .showConfirmDialog(
                        null,
                        "Press Yes to add a new vehicle, Press No to add a demo vehicle",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
        if (choose == JOptionPane.YES_OPTION) {
            String autoMaker = JOptionPane.showInputDialog("Enter the auto maker: ");
            String strPrice = JOptionPane.showInputDialog("Enter the buy in Price: ");
            addInputCheck(autoMaker, strPrice);
        } else {
            showroom.addDemoVehicle();
            JOptionPane.showMessageDialog(null, "A new demo vehicle added!");
        }
    }

    // EFFECTS: create add input dialog
    private void addInputCheck(String autoMaker, String strPrice) {
        if (!autoMaker.isEmpty() && !strPrice.isEmpty()) {
            int price = Integer.valueOf(strPrice);
            int decision = JOptionPane
                    .showConfirmDialog(
                            null,
                            "You are adding " + autoMaker + " with buy in price: "
                                    + strPrice + ". Are you sure to add?",
                            null,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
            if (decision == JOptionPane.YES_OPTION) {
                int invNum = showroom.addNewInventory(autoMaker,staffA,price);
                showroom.vehicleList.get(showroom.invNumToIndex(invNum)).url = "curtain.jpg";
                JOptionPane.showMessageDialog(null, "Vehicle added!");
            } else {
                JOptionPane.showMessageDialog(null, "You gave up adding!");
            }
        }
    }

    // EFFECTS: create sell button click event
    private void sellButtonEvent(int num, JFrame f) {
        int choose = JOptionPane.showConfirmDialog(
                null,
                        "Do you want to sell it at expected price?",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
        if (choose == JOptionPane.YES_OPTION) {
            int expectedPrice = showroom.vehicleList.get(showroom.invNumToIndex(num)).sellExpectedPrice;
            showroom.vehicleList.get(showroom.invNumToIndex(num)).sellTheVehicle(expectedPrice, staffA, customerA);
            JOptionPane.showMessageDialog(null, "Vehicle sold!");
            f.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You gave up selling!");
        }
        f.dispose();
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

    // EFFECTS: create frame with table to show list of inventory
    private void showInventoryList() {
        JFrame jf = new JFrame("list window");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Object[] columnNames = { "contents" };
        String inventoryInfo = showroom.showInventoryWithNum();
        String[] info = inventoryInfo.split("\n");
        Object[][] rowData = new Object[info.length][1];
        setUpTableRow(info, rowData);
        JTable table = new JTable(rowData, columnNames) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tableSetup(jf, table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mousePressedHelper(table);
                }
            }
        });
    }

    // EFFECTS: create table contents
    private void setUpTableRow(String[] info, Object[][] rowData) {
        for (int i = 0; i < info.length; i++) {
            rowData[i][0] = info[i];
        }
    }

    // EFFECTS: sub frame to show detailed information of inventory
    private void mousePressedHelper(JTable table) {
        int num = table.getSelectedRow() + 1;
        String message = "Inventory Number: " + showroom.numToInvNum(num) + "\n"
                + showroom.showDetailInfoByNum(num);
        JFrame inventoryFrame = setDetailFrame(message);
        String url = "";
        setImage(num, message, inventoryFrame);
        JButton closeButton = new JButton("close");
        closeButton.setBounds(160, 580, 140, 50);
        inventoryFrame.setBounds(100, 100, 460, 690);
        inventoryFrame.setAlwaysOnTop(true);
        inventoryFrame.add(closeButton);

        cancelButtonEvent(inventoryFrame, closeButton);
        inventoryFrame.setResizable(false);
        inventoryFrame.setVisible(true);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // EFFECTS: set up table attribution
    private void tableSetup(JFrame jf, JTable table) {
        table.setRowHeight(33);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        table.setFont(new Font(null, Font.PLAIN, 15));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        scrollPane.setToolTipText("inventory list");
        scrollPane.setBounds(10, 80, 750, 540);
        jf.getContentPane().add(scrollPane);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    // EFFECTS: show the image of inventory
    private void setImage(int num, String message, JFrame inventoryFrame) {
        String url;
        if (showroom.vehicleList.get(num - 1).isReadyForExhibit()) {
            message = message.replace("\n", "<br>");
            int begin = message.indexOf("<br>URL:");
            int end = message.indexOf("<br>Mileage:");
            url = message.substring(begin + 9, end);
        } else {
            if (showroom.vehicleList.get(num - 1).url.isEmpty()) {
                url = "empty.jpg";
            }
            url = showroom.vehicleList.get(num - 1).url;
        }
        ImageIcon icon = new ImageIcon(url);

        icon.setImage(icon.getImage().getScaledInstance(400, 260, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(20, 300, 400, 260);
        inventoryFrame.add(imageLabel);
    }

    // EFFECTS: create detail info frame
    private JFrame setDetailFrame(String message) {
        JFrame inventoryFrame = new JFrame("Detailed Information");
        inventoryFrame.setLayout(null);
        JTextArea t1 = new JTextArea(9, 20);
        tableFormatting(t1);
        t1.setText(message);
        t1.setEditable(false);
        JScrollPane jsp = new JScrollPane(t1);
        jsp.setBounds(20, 20, 400, 260);
        inventoryFrame.add(jsp);
        return inventoryFrame;
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

    // EFFECTS: helper method to edit year
    private void editYear(int num) {
        String year = editDialog("year");
        if (!year.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateAutoYear(Integer.valueOf(year));
        }
    }

    // EFFECTS: helper method to edit maker
    private void editMaker(int num) {
        String maker = editDialog("maker");
        if (!maker.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateAutoMaker(maker);
        }
    }

    // EFFECTS: helper method to edit model
    private void editModel(int num) {
        String model = editDialog("model");
        if (!model.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateAutoModel(model);
        }
    }

    // EFFECTS: helper method to edit type
    private void editType(int num) {
        String type = editDialog("type");
        if (!type.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateAutoType(type);
        }
    }

    // EFFECTS: helper method to edit colour
    private void editColor(int num) {
        String color = editDialog("colour");
        if (!color.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateColour(color);
        }
    }

    // EFFECTS: helper method to edit image url
    private void editURL(int num) {
        String url = editDialog("image URL");
        if (!url.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).url = url;
        }
    }

    // EFFECTS: helper method to edit mileage
    private void editMileage(int num) {
        String mileage = editDialog("mileage");
        if (!mileage.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateMileage(Integer.valueOf(mileage));
        }
    }

    // EFFECTS: helper method to edit 17-character VIN
    private void editVIN(int num) {
        String vin = editDialog("VIN");
        if (!vin.equals("")) {
            try {
                showroom.vehicleList.get(showroom.invNumToIndex(num)).updateVin(vin);
            } catch (Exception ex) {
                //not expected
                JOptionPane.showMessageDialog(null, "You entered wrong VIN!");
            }
        }
    }

    // EFFECTS: helper method to edit comment
    private void editComment(int num) {
        String comment = editDialog("comment");
        if (!comment.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).addComment(comment);
        }
    }

    // EFFECTS: helper method to edit list price
    private void editListPrice(int num) {
        String price = editDialog("list price");
        if (!price.equals("")) {
            showroom.vehicleList.get(showroom.invNumToIndex(num)).updateListPrice(Integer.valueOf(price));
        }
    }

    // EFFECTS: helper method to prompt input to edit
    private String editDialog(String editItem) {
        int choose = JOptionPane
                .showConfirmDialog(
                        null,
                        "Edit " + editItem + " ?",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
        if (choose == JOptionPane.YES_OPTION) {

            String str = "";
            while (str == "") {
                str = JOptionPane.showInputDialog("Enter " + editItem + ": ");
            }
            return str;
        }
        return "";
    }
}
