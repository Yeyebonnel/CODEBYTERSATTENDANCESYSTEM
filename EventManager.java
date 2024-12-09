package codebyters.attendance.system;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

class EventManager {

    // Method to create an event file (CSV)
    void createEventFile(String eventName) {
        try {
            File file = new File(eventName + ".csv");

            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "The file " + eventName + ".csv already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    JOptionPane.showMessageDialog(null, "Event created successfully with file: " + eventName + ".csv");
                    tableModel.addRow(new Object[]{eventName}); // Add the event to the table
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating the event file.");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating the event file: " + e.getMessage());
        }
    }

    // Method to view an event from its file (CSV)
    void viewEventFile(String eventName, DefaultTableModel tableModel) {
        File file = new File(eventName + ".csv");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Event file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventDetails = line.split(",");
                tableModel.addRow(eventDetails); // Adds rows of event data to the JTable
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the event file: " + e.getMessage());
        }
    }
}
