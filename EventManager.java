package codebytersattendancesystem;

import static codebytersattendancesystem.Mainpage.createEventbtn;
import static codebytersattendancesystem.Mainpage.dashboardLabel;
import static codebytersattendancesystem.Mainpage.deletebtn;
import static codebytersattendancesystem.Mainpage.logoutbtn;
import static codebytersattendancesystem.Mainpage.tableModel;
import static codebytersattendancesystem.Mainpage.panelCenter;
import static codebytersattendancesystem.Mainpage.panelWest;
import static codebytersattendancesystem.Mainpage.searchPanel;
import static codebytersattendancesystem.Mainpage.table;
import static codebytersattendancesystem.Mainpage.tableModel;
import static codebytersattendancesystem.Mainpage.updatebtn;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;


class EventManager{
    
    void createEventFile(String eventName) {
        
        try {
            String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
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
                    String formattedDate = new SimpleDateFormat("YYYY-MM-dd").format(new Date(lastModified));

                // Add file name and formatted date to the table
                tableModel.addRow(new Object[]{file.getName().replace(".csv", ""), formattedDate});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No events found.");
        }
    } 

    void updateEvent(JTable table) {
    int selectedRow = table.getSelectedRow();
    
    if (selectedRow >= 0) {
        String eventName = (String) tableModel.getValueAt(selectedRow, 0);

        // Open a dialog to update the event name
        String newEventName = JOptionPane.showInputDialog("Enter new event name:", eventName);
        
        if (newEventName != null && !newEventName.trim().isEmpty()) {
            // Update the event in the table (remove date-related code)
            tableModel.setValueAt(newEventName, selectedRow, 0);

            // Rename the event file (no need to handle the date)
            File oldFile = new File(eventName + ".csv");
            File newFile = new File(newEventName + ".csv");

            if (oldFile.exists() && oldFile.renameTo(newFile)) {
                JOptionPane.showMessageDialog(null, "Event file renamed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Error renaming the event file.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Event name cannot be empty.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select an event to update.");
    }
}


    
    void deleteEvent(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String eventName = (String) tableModel.getValueAt(selectedRow, 0);
            String fileName = eventName + ".csv"; // File name associated with the event

            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete the event: " + eventName + "?", 
                "Delete Event", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Remove the event from the table
                tableModel.removeRow(selectedRow);

                // Delete the corresponding file
                File file = new File(fileName);
                if (file.exists() && file.delete()) {
                    JOptionPane.showMessageDialog(null, "Event deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error deleting the event file.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an event to delete.");
        }
    }

    void loadAttendanceData(String eventName) {
        String[] attendanceColumnNames = {"Student ID", "Student Name", "Year Level", "Section"};
        DefaultTableModel attendanceTableModel = new DefaultTableModel(null, attendanceColumnNames);
        JTable attendanceTable = new JTable(attendanceTableModel);
        attendanceTable.setFillsViewportHeight(true);
        TableColumnModel columnModel = attendanceTable.getColumnModel();

        // Set preferred width for columns
        columnModel.getColumn(0).setPreferredWidth(100);  // Student ID
        columnModel.getColumn(1).setPreferredWidth(250);  // Student Name
        columnModel.getColumn(2).setPreferredWidth(80);  // Year Level
        columnModel.getColumn(3).setPreferredWidth(80);  // Section

        // File path for event data (CSV)
        File eventFile = new File(eventName + ".csv");

        if (eventFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] studentData = line.split(",");
                    attendanceTableModel.addRow(studentData);  // Add student row to table
                }

                JPanel attendancePanel = new JPanel(new BorderLayout());

                // Create search panel with search bar
                JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JTextField searchField = new JTextField(30);
                searchPanel.add(new JLabel("Search:"));
                searchPanel.add(searchField);

                // Add document listener to search bar for real-time filtering
                searchField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        filterTableData(attendanceTableModel, searchField.getText(), eventName);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        filterTableData(attendanceTableModel, searchField.getText(), eventName);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        filterTableData(attendanceTableModel, searchField.getText(), eventName);
                    }
                });

                // Add search bar panel to attendance panel
                attendancePanel.add(searchPanel, BorderLayout.NORTH);

                // Add scroll pane to the table
                JScrollPane attendanceScrollPane = new JScrollPane(attendanceTable);
                attendanceScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                attendancePanel.add(attendanceScrollPane, BorderLayout.CENTER);

                // Update main panel with new content
                panelCenter.removeAll();
                panelCenter.add(attendancePanel, BorderLayout.CENTER);
                panelCenter.revalidate();
                panelCenter.repaint();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading attendance data: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Event file not found.");
        }
    }
    
    void filterTableData(DefaultTableModel tableModel, String searchText, String eventName) {
        // Clear the table rows first
        tableModel.setRowCount(0);

        // Return if the search text is empty
        if (searchText.trim().isEmpty()) {
            // Reload data without any filters if search is empty
            loadAttendanceData(eventName);
            return;
        }

        // File path for event data (CSV)
        File eventFile = new File(eventName + ".csv");

        if (eventFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] studentData = line.split(",");
                    String studentId = studentData[0];
                    String studentName = studentData[1];

                    // Filter based on searchText matching ID or Name
                    if (studentId.contains(searchText) || studentName.contains(searchText)) {
                        tableModel.addRow(studentData);  // Add matching row to table
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
            }
        }
    }

    void enableCrudButtonsForStudents(String eventName) {
        Mainpage.panelWest.removeAll();
        Mainpage.panelWest.add(createCrudButtonsPanelForStudents(eventName));
        Mainpage.panelWest.revalidate();
        Mainpage.panelWest.repaint();
        
    }

    private JPanel createCrudButtonsPanelForStudents(String eventName) {
        JPanel crudPanel = new JPanel();

        crudPanel.setLayout(new BoxLayout(crudPanel, BoxLayout.Y_AXIS));
        crudPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));  
        crudPanel.setPreferredSize(new Dimension(150, 500));

        JLabel dashboardLabel = new JLabel("ATTENDANCE");
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 16));  
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        crudPanel.add(dashboardLabel);
        crudPanel.add(Box.createVerticalStrut(40));

        JButton createButton = new JButton("Add Student");
        createButton.setPreferredSize(new Dimension(120, 30)); 
        createButton.setMaximumSize(new Dimension(120, 30));
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        createButton.setFocusPainted(false);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(e -> addStudent(eventName));
        crudPanel.add(createButton);
        crudPanel.add(Box.createVerticalStrut(15));  // Add some vertical spacing

        // Create Delete Student button
        JButton deleteButton = new JButton("Delete Student");
        deleteButton.setPreferredSize(new Dimension(120, 30)); 
        deleteButton.setMaximumSize(new Dimension(120, 30));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(e -> deleteStudent(eventName));
        crudPanel.add(deleteButton);
        crudPanel.add(Box.createVerticalStrut(15));  

        JButton updateButton = new JButton("Update Student");
        updateButton.setPreferredSize(new Dimension(120, 30)); 
        updateButton.setMaximumSize(new Dimension(120, 30));
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateButton.setFont(new Font("ARIAL", Font.BOLD, 11));
        updateButton.addActionListener(e -> updateStudent(eventName));
        crudPanel.add(updateButton);
        crudPanel.add(Box.createVerticalStrut(35));

        JButton backbtn = new JButton("Back");
        backbtn.setPreferredSize(new Dimension(120,30));
        backbtn.setMaximumSize(new Dimension(120, 30));
        backbtn.setAlignmentX(Component.CENTER_ALIGNMENT); 
        backbtn.setFocusPainted(false);
        backbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backbtn.addActionListener((ActionEvent e) -> {
            EventManager eventManager = new EventManager();
            eventManager.loadExistingEvents();  // Reload events in the table

            // Create the panel to display the events and the search bar
            JPanel eventsPanel = new JPanel(new BorderLayout());

            // Create the search panel with the search field
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField searchField = new JTextField(30);
            searchPanel.add(new JLabel("Search:"));
            searchPanel.add(searchField);

            // Add document listener for real-time filtering
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    searchEvents(tableModel, searchField.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    searchEvents(tableModel, searchField.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    searchEvents(tableModel, searchField.getText());
                }
                private void searchEvents(DefaultTableModel tableModel, String searchText) {
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null); // Show all rows if no text
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case insensitive search
                }
            }
            });

            // Add the search panel to the events panel
            eventsPanel.add(searchPanel, BorderLayout.NORTH);

            // Add the scrollable table to the center of the events panel
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            eventsPanel.add(scrollPane, BorderLayout.CENTER);
            
            
            panelCenter.removeAll();  // Clear the current panel
            panelCenter.add(eventsPanel, BorderLayout.CENTER);
            panelCenter.revalidate();
            panelCenter.repaint();
            
            panelWest.removeAll();
            panelWest.add(dashboardLabel);
            panelWest.add(Box.createVerticalStrut(50));
            panelWest.add(createEventbtn);
            panelWest.add(Box.createVerticalStrut(15));
            panelWest.add(updatebtn);
            panelWest.add(Box.createVerticalStrut(15));
            panelWest.add(deletebtn);
            panelWest.add(Box.createVerticalStrut(50));
            panelWest.add(logoutbtn);
    
            panelWest.revalidate();
            panelWest.repaint();
        });
        crudPanel.add(backbtn);
        crudPanel.add(Box.createVerticalStrut(15));
        
        JButton logoutbtn = new JButton("Logout");
        logoutbtn.setPreferredSize(new Dimension(120,30));
        logoutbtn.setMaximumSize(new Dimension(120, 30));
        logoutbtn.setAlignmentX(Component.CENTER_ALIGNMENT); 
        logoutbtn.setFocusPainted(false);
        logoutbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutbtn.addActionListener((ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutbtn);
                currentFrame.setVisible(false);  // Hide the current frame
                
                // Show the Login Page by calling the main method of the LoginPageCodebytersAttendanceSystem class
                // Assuming LoginPageCodebytersAttendanceSystem is a class with a main method to start the login page
                LoginPageCodebytersAttendanceSystem.main(new String[0]);
            }
        });
        crudPanel.add(logoutbtn);
        

        return crudPanel;
    }

    private void addStudent(String eventName) {
        JFrame addStudentFrame = new JFrame("Add Student");

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Bonnel Jhon Files\\codebyters_logo.png");
        addStudentFrame.setIconImage(icon);

        addStudentFrame.setSize(400, 250); 
        addStudentFrame.setLayout(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField studentIdField = new JTextField(20);
        JTextField studentNameField = new JTextField(20);
        JTextField yearLevelField = new JTextField(20);
        JTextField sectionField = new JTextField(20);

        JLabel studentIdLabel = new JLabel("Student ID:");
        JLabel studentNameLabel = new JLabel("Student Name:");
        JLabel yearLevelLabel = new JLabel("Year Level:");
        JLabel sectionLabel = new JLabel("Section:");

        gbc.gridx = 0; gbc.gridy = 0;
        addStudentFrame.add(studentIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        addStudentFrame.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addStudentFrame.add(studentNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        addStudentFrame.add(studentNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        addStudentFrame.add(yearLevelLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        addStudentFrame.add(yearLevelField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        addStudentFrame.add(sectionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        addStudentFrame.add(sectionField, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setBackground(Color.BLUE); // Blue color for the button
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        addStudentFrame.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String studentId = studentIdField.getText();
            String studentName = studentNameField.getText();
            String yearLevel = yearLevelField.getText();
            String section = sectionField.getText();

            if(!studentId.matches("^\\d{4}-\\d{4}$")){
                JOptionPane.showMessageDialog(null, "Invalid ID format");
                return;
            }

            if (!studentId.isEmpty() && !studentName.isEmpty() && !yearLevel.isEmpty() && !section.isEmpty()) {
                try {
                    File eventFile = new File(eventName + ".csv");
                    if (!eventFile.exists()) {
                        JOptionPane.showMessageDialog(null, "Event file not found.");
                        return;
                    }

                    // Check for duplicate student
                    try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] studentData = line.split(",");
                            if (studentData[0].equals(studentId)) {
                                JOptionPane.showMessageDialog(null, "Student ID already exists.");
                                return;
                            }
                        }
                    }

                    // Add student to file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile, true))) {
                        writer.append(studentId + "," + studentName + "," + yearLevel + "," + section + "\n");
                    }

                    // Refresh table
                    loadAttendanceData(eventName);

                    // Close the add student frame after saving
                    addStudentFrame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding student: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(addStudentFrame, "Please fill out all fields.");
            }
        });

        // Set window properties
        addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addStudentFrame.setResizable(false);
        addStudentFrame.setLocationRelativeTo(null); // Center the window
        addStudentFrame.setVisible(true);
    }

   private void deleteStudent(String eventName) {
        File eventFile = new File(eventName + ".csv");

        if (!eventFile.exists()) {
            JOptionPane.showMessageDialog(null, "The event file does not exist.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
            ArrayList<String> studentList = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                studentList.add(line);
            }

            String studentIdToDelete = JOptionPane.showInputDialog("Enter the Student ID to delete:");

            // Check if the studentIdToDelete is valid
            boolean studentFound = false;
            for (String student : studentList) {
                if (student.split(",")[0].equals(studentIdToDelete)) {
                    studentFound = true;
                    break;
                }
            }

            if (!studentFound) {
                JOptionPane.showMessageDialog(null, "Student ID not found.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to delete the Student: " + studentIdToDelete + "?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Remove the student with the given ID
                studentList.removeIf(student -> student.split(",")[0].equals(studentIdToDelete));

                // Rewrite the file without the deleted student
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile))) {
                    for (String student : studentList) {
                        writer.write(student + "\n");
                    }
                }

                loadAttendanceData(eventName); // Refresh the attendance table
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deleting student: " + e.getMessage());
        }
    }

    private void updateStudent(String eventName) {
        // Ask the user for the Student ID they want to update
        String studentIdToUpdate = JOptionPane.showInputDialog("Enter the Student ID to update:");
        // Open the event file
        File eventFile = new File(eventName + ".csv");
        if (!eventFile.exists()) {
            JOptionPane.showMessageDialog(null, "Event file not found.");
            return;
        }

        ArrayList<String> studentList = new ArrayList<>();
        boolean studentFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
            String line;
            // Read the student data into the list
            while ((line = reader.readLine()) != null) {
                studentList.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the event file: " + e.getMessage());
            return;
        }

        // Find the student with the given ID
        for (int i = 0; i < studentList.size(); i++) {
            String[] studentData = studentList.get(i).split(",");
            if (studentData[0].equals(studentIdToUpdate)) {
                studentFound = true;

                // Prompt for new details
                String newStudentName = JOptionPane.showInputDialog("Enter new Student Name:", studentData[1]);
                String newYearLevel = JOptionPane.showInputDialog("Enter new Year Level:", studentData[2]);
                String newSection = JOptionPane.showInputDialog("Enter new Section:", studentData[3]);

                if (newStudentName != null && !newStudentName.trim().isEmpty() &&
                    newYearLevel != null && !newYearLevel.trim().isEmpty() &&
                    newSection != null && !newSection.trim().isEmpty()) {

                    // Update the student data in the list
                    studentList.set(i, studentIdToUpdate + "," + newStudentName + "," + newYearLevel + "," + newSection);

                    // Rewrite the updated data to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile))) {
                        for (String student : studentList) {
                            writer.write(student + "\n");
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error updating the student data: " + e.getMessage());
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "Student details updated successfully.");
                    loadAttendanceData(eventName); // Reload the attendance table
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
                break;
            }
        }

        if (!studentFound) {
            JOptionPane.showMessageDialog(null, "Student ID not found.");
        }
    }  
}

    
    