package codebytersAttendanceSystembyBonnel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CodebytersAttendanceSystem {

    private static JTable table;
    private static DefaultTableModel tableModel;
    private static JPanel panelCenter; 
    private static JPanel panelWest; 
    private static String currentEvent;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("CODEBYTERS ATTENDANCE SYSTEM");
        mainFrame.setSize(800, 550);
        mainFrame.setLayout(new BorderLayout());

 
        JPanel panelOne = new JPanel();
        panelOne.setLayout(null); 
        panelOne.setPreferredSize(new Dimension(800, 100));  
        panelOne.setBackground(Color.black);
        panelOne.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        
        
        ImageIcon originalImageIcon = new ImageIcon("C:\\Bonnel Jhon Files\\ATTENDANCE SYSTEM.jpg"); 
        Image image = originalImageIcon.getImage(); 
        Image resizedImage = image.getScaledInstance(800, 100, Image.SCALE_FAST);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        imageLabel.setBounds(0, 0, 800, 100);
        panelOne.add(imageLabel);
        
        
        panelWest = new JPanel();
        panelWest.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));  
        panelWest.setPreferredSize(new Dimension(150, 500));  
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS)); 
        
        
        JLabel dashboardLabel = new JLabel("DASHBOARD");
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 16));  
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        panelWest.add(dashboardLabel);  
        panelWest.add(Box.createVerticalStrut(50));

        
        JButton createEventbtn = new JButton("Create Event");
        createEventbtn.setPreferredSize(new Dimension(120, 30));
        createEventbtn.setMaximumSize(new Dimension(120, 30));
        createEventbtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        createEventbtn.setFocusPainted(false);
        createEventbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createEventbtn.addActionListener(e -> {
            String eventName = JOptionPane.showInputDialog(mainFrame, "Enter the Event Name:");
            if (eventName != null && !eventName.trim().isEmpty()) {
                createEventFile(eventName);
            }
        });
        panelWest.add(createEventbtn);
        panelWest.add(Box.createVerticalStrut(15)); 

        
        JButton viewbtn = new JButton("View Events");
        viewbtn.setPreferredSize(new Dimension(120, 30)); 
        viewbtn.setMaximumSize(new Dimension(120, 30));
        viewbtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        viewbtn.setFocusPainted(false);
        viewbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewbtn.addActionListener(e -> loadExistingEvents());
        panelWest.add(viewbtn);
        panelWest.add(Box.createVerticalStrut(15));

        
        JPanel panelTwo = new JPanel();
        panelTwo.setLayout(null); 
        panelTwo.setPreferredSize(new Dimension(800, 70));  
        panelTwo.setBackground(Color.black);
        panelTwo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ImageIcon originalImageIcon1 = new ImageIcon("C:\\Bonnel Jhon Files\\IT2F.jpg"); 
        Image image1 = originalImageIcon1.getImage(); 
        Image resizedImage1 = image1.getScaledInstance(800, 70, Image.SCALE_FAST);
        ImageIcon resizedImageIcon1 = new ImageIcon(resizedImage1);
        JLabel imageLabel1 = new JLabel(resizedImageIcon1);
        imageLabel1.setBounds(0, 0, 800, 70);
        panelTwo.add(imageLabel1);
        
        
        panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBackground(Color.CYAN);
        panelCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  
    

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.red);
        tablePanel.setLayout(new BorderLayout()); 

        // Define column names and create table model
        String[] columnNames = {"Event Name"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    currentEvent = (String) tableModel.getValueAt(row, 0);
                    loadAttendanceData(currentEvent);
                    enableCrudButtonsForStudents(currentEvent); // Enable CRUD buttons for students
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));  // Border for the table
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        panelCenter.add(tablePanel, BorderLayout.CENTER);

        mainFrame.add(panelCenter, BorderLayout.CENTER);
        mainFrame.add(panelWest, BorderLayout.WEST);
        mainFrame.add(panelOne, BorderLayout.NORTH);
        mainFrame.add(panelTwo, BorderLayout.SOUTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); 
        mainFrame.setVisible(true);

        // Automatically load existing events when the application starts
        loadExistingEvents(); // This is the key line that will automatically load events
        
        createAttendancePanel();
    }
    private static void createAttendancePanel() {
    // Initialize panelAttendance (the panel for attendance-related controls)
    JPanel panelAttendance = new JPanel();
    panelAttendance.setLayout(new FlowLayout(FlowLayout.LEFT));
    panelAttendance.setPreferredSize(new Dimension(800, 80));  // Set the height for the attendance panel
    panelAttendance.setBackground(Color.LIGHT_GRAY);  // Background color for distinction

    // Search Bar (JTextField) for attendance search
    JTextField searchField = new JTextField(20);  // Create a text field for search input
    searchField.setToolTipText("Search Attendance");
    panelAttendance.add(new JLabel("Search Attendance: "));
    panelAttendance.add(searchField);

    // Combo Box (JComboBox) for filtering attendance
    String[] comboBoxItems = {"All Students", "Present", "Absent", "Late"};  // Sample items for combo box
    JComboBox<String> comboBox = new JComboBox<>(comboBoxItems);
    comboBox.setToolTipText("Filter by Status");
    panelAttendance.add(new JLabel("Filter by Status: "));
    panelAttendance.add(comboBox);

    // Add the attendance panel to the frame or a specific panel
    panelCenter.add(panelAttendance, BorderLayout.NORTH);
}

    private static void loadExistingEvents() {
        tableModel.setRowCount(0);
        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".csv"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                tableModel.addRow(new Object[]{file.getName().replace(".csv", "")});
            }
        } else {
            JOptionPane.showMessageDialog(null, "No events found.");
        }
    }
    

    private static void createEventFile(String eventName) {
        try {
            File file = new File(eventName + ".csv");
            
            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "The file " + eventName + ".csv already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    JOptionPane.showMessageDialog(null, "Event created successfully with file: " + eventName + ".csv");
                    tableModel.addRow(new Object[]{eventName});
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating the event file.");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating the event file: " + e.getMessage());
        }
    }

    private static void loadAttendanceData(String eventName) {
        String[] attendanceColumnNames = {"Student ID", "Student Name", "Year Level", "Section"};
        DefaultTableModel attendanceTableModel = new DefaultTableModel(null, attendanceColumnNames);
        JTable attendanceTable = new JTable(attendanceTableModel);
        attendanceTable.setFillsViewportHeight(true);

        File eventFile = new File(eventName + ".csv");

        if (eventFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(eventFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] studentData = line.split(",");
                    attendanceTableModel.addRow(studentData);
                }

                JPanel attendancePanel = new JPanel(new BorderLayout());
                JScrollPane attendanceScrollPane = new JScrollPane(attendanceTable);
                attendanceScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                attendancePanel.add(attendanceScrollPane, BorderLayout.CENTER);

                // Replace only the panelCenter content (keeping other parts of the frame intact)
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

    private static void enableCrudButtonsForStudents(String eventName) {
        // Clear existing buttons and add CRUD operations for selected event
        panelWest.removeAll();
        panelWest.add(createCrudButtonsPanelForStudents(eventName)); 
        panelWest.revalidate();
        panelWest.repaint();
    }

    private static JPanel createCrudButtonsPanelForStudents(String eventName) {    
    JPanel crudPanel = new JPanel();

    crudPanel.setLayout(new BoxLayout(crudPanel, BoxLayout.Y_AXIS));
    crudPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));  
    crudPanel.setPreferredSize(new Dimension(150, 500));

    JLabel dashboardLabel = new JLabel("ATTENDANCE");
    dashboardLabel.setFont(new Font("Arial", Font.BOLD, 16));  
    dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
    crudPanel.add(dashboardLabel);
    crudPanel.add(Box.createVerticalStrut(20));

    JButton createButton = new JButton("Add Student");
    createButton.setPreferredSize(new Dimension(120, 30)); 
    createButton.setMaximumSize(new Dimension(120, 30));
    createButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
    createButton.setFocusPainted(false);
    createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    createButton.addActionListener(e -> addStudent(eventName));
    crudPanel.add(createButton);
    crudPanel.add(Box.createVerticalStrut(15));  // Add some vertical spacing

    JButton readButton = new JButton("View Attendance");
    readButton.setPreferredSize(new Dimension(120, 30)); 
    readButton.setMaximumSize(new Dimension(120, 30));
    readButton.setAlignmentX(Component.CENTER_ALIGNMENT);  
    readButton.setFocusPainted(false);
    readButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    readButton.addActionListener(e -> loadAttendanceData(eventName));
    crudPanel.add(readButton);
    crudPanel.add(Box.createVerticalStrut(15));  
    
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
    updateButton.addActionListener(e -> updateStudent(eventName));
    crudPanel.add(updateButton);

    return crudPanel;
}


    private static void addStudent(String eventName) {
    JFrame addStudentFrame = new JFrame("Add Student");
    addStudentFrame.setSize(400, 250); 
    addStudentFrame.setLayout(new GridBagLayout());  
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Set up some padding
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

        if (!studentId.isEmpty() && !studentName.isEmpty() && !yearLevel.isEmpty() && !section.isEmpty()) {
            try {
                File eventFile = new File(eventName + ".csv");
                BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile, true));
                writer.append(studentId + "," + studentName + "," + yearLevel + "," + section + "\n");
                writer.close();
                loadAttendanceData(eventName); // Refresh the attendance table

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


    private static void deleteStudent(String eventName) {
        try {
            File eventFile = new File(eventName + ".csv");
            BufferedReader reader = new BufferedReader(new FileReader(eventFile));
            ArrayList<String> studentList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                studentList.add(line);
            }
            reader.close();

            String studentIdToDelete = JOptionPane.showInputDialog("Enter the Student ID to delete:");

            // Remove the student with the given ID
            studentList.removeIf(student -> student.split(",")[0].equals(studentIdToDelete));

            // Rewrite the file without the deleted student
            BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile));
            for (String student : studentList) {
                writer.write(student + "\n");
            }
            writer.close();
            loadAttendanceData(eventName); // Refresh the attendance table
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deleting student: " + e.getMessage());
        }
    }

    private static void updateStudent(String eventName) {
   
    JFrame updateStudentFrame = new JFrame("Update Student");
    updateStudentFrame.setSize(400, 250);  
    updateStudentFrame.setLayout(new GridBagLayout()); 
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField studentIdField = new JTextField(20);
    JTextField studentNameField = new JTextField(20);
    JTextField yearLevelField = new JTextField(20);
    JTextField sectionField = new JTextField(20);

    // Create labels for the fields
    JLabel studentIdLabel = new JLabel("Student ID:");
    JLabel studentNameLabel = new JLabel("New Student Name:");
    JLabel yearLevelLabel = new JLabel("New Year Level:");
    JLabel sectionLabel = new JLabel("New Section:");

    gbc.gridx = 0; gbc.gridy = 0;
    updateStudentFrame.add(studentIdLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 0;
    updateStudentFrame.add(studentIdField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    updateStudentFrame.add(studentNameLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 1;
    updateStudentFrame.add(studentNameField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    updateStudentFrame.add(yearLevelLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 2;
    updateStudentFrame.add(yearLevelField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    updateStudentFrame.add(sectionLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 3;
    updateStudentFrame.add(sectionField, gbc);

    
    JButton updateButton = new JButton("Update");
    updateButton.setPreferredSize(new Dimension(100, 30));
    updateButton.setBackground(Color.BLUE); 
    updateButton.setForeground(Color.WHITE);
    updateButton.setFocusPainted(false);
    updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    
    gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
    updateStudentFrame.add(updateButton, gbc);

    
    updateButton.addActionListener(e -> {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String yearLevel = yearLevelField.getText();
        String section = sectionField.getText();

        if (!studentId.isEmpty() && !studentName.isEmpty() && !yearLevel.isEmpty() && !section.isEmpty()) {
            try {
                File eventFile = new File(eventName + ".csv");
                BufferedReader reader = new BufferedReader(new FileReader(eventFile));
                ArrayList<String> studentList = new ArrayList<>();
                String line;
                boolean studentFound = false;

                
                while ((line = reader.readLine()) != null) {
                    studentList.add(line);
                }
                reader.close();
                
                for (int i = 0; i < studentList.size(); i++) {
                    String[] studentData = studentList.get(i).split(",");
                    if (studentData[0].equals(studentId)) {
                        studentList.set(i, studentId + "," + studentName + "," + yearLevel + "," + section);
                        studentFound = true;
                        break;
                    }
                }

                if (!studentFound) {
                    JOptionPane.showMessageDialog(updateStudentFrame, "Student ID not found.");
                    return;
                }

                
                BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile));
                for (String student : studentList) {
                    writer.write(student + "\n");
                }
                writer.close();
                loadAttendanceData(eventName); // Refresh the attendance table
                updateStudentFrame.dispose();  // Close the update window after saving

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error updating student: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(updateStudentFrame, "Please fill out all fields.");
        }
    });

    // Set window properties
    updateStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    updateStudentFrame.setResizable(false);
    updateStudentFrame.setLocationRelativeTo(null); // Center the window
    updateStudentFrame.setVisible(true);
}
    

}
