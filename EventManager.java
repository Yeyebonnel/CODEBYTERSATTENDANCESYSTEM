package codebyters.attendance.system;

import static codebyters.attendance.system.Mainpage.tableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

class EventManager{

    private static JPanel panelWest;
    
    void createEventFile(String eventName) {
        
        try {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String fileName = eventName + ".csv";
            File file = new File(fileName);

            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "The file " + fileName + " already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    JOptionPane.showMessageDialog(null, "Event created successfully with file: " + fileName);
                    tableModel.addRow(new Object[]{eventName, currentDate}); 
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating the event file.");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating the event file: " + e.getMessage());
        }
    }
    
    void loadExistingEvents() {
        tableModel.setRowCount(0);
        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".csv"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                // Get the last modified time
                long lastModified = file.lastModified();

                // Format the date as yyyy-MM-dd
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(lastModified));

                // Add file name and formatted date to the table
                tableModel.addRow(new Object[]{file.getName().replace(".csv", ""), formattedDate});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No events found.");
        }
    } 

    Iterable<Event> getEvents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}

    
    