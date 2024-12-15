package codebytersattendancesystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

class Mainpage{
    
    static JTable table;
    static DefaultTableModel tableModel;
    static JPanel panelCenter; 
    static JPanel panelWest; 
    static JLabel dashboardLabel;
    static JPanel searchPanel;
    static JTextField searchField ;
    static JButton createEventbtn;
    static JButton updatebtn;
    static JButton deletebtn;
    static JButton logoutbtn;
    
    void HomePage() {  //mao ni ang method na gi tawag sa login page na mo gawas
        
        
        JFrame mainFrame = new JFrame("CODEBYTERS ATTENDANCE SYSTEM");
        mainFrame.setSize(800, 550);
        mainFrame.setLayout(new BorderLayout());
        
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Bonnel Jhon Files\\codebyters_logo.png");// kini na part sa sa icon mismo sa atoang frame instead na java logo cb logo mo gawas
        mainFrame.setIconImage(icon);
        
        
        EventManager eventManager = new EventManager();//nag create tag class para sa pag handle ug different eveents sa ataong code like add,delete, etc


        JPanel panelOne = new JPanel();
        panelOne.setLayout(null); 
        panelOne.setPreferredSize(new Dimension(800, 100));  
        panelOne.setBackground(Color.black);

        ImageIcon originalImageIcon = new ImageIcon("C:\\Bonnel Jhon Files\\ATTENDANCE SYSTEM.jpg"); 
        Image image = originalImageIcon.getImage(); 
        Image resizedImage = image.getScaledInstance(800, 100, Image.SCALE_FAST);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        imageLabel.setBounds(0, 0, 800, 100);
        panelOne.add(imageLabel);


        panelWest = new JPanel();
        panelWest.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));  //This creates an empty border with specific spacing.
        panelWest.setPreferredSize(new Dimension(180, 500));  
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS)); // a layout manager na naga plastar ug mga components in a single row or column.

        dashboardLabel = new JLabel("DASHBOARD");
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 16));  
        dashboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        panelWest.add(dashboardLabel);  
        panelWest.add(Box.createVerticalStrut(50));

        createEventbtn = new JButton("Create Event");
        createEventbtn.setPreferredSize(new Dimension(120, 30));
        createEventbtn.setMaximumSize(new Dimension(120, 30));
        createEventbtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        createEventbtn.setFocusPainted(false);
        createEventbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createEventbtn.addActionListener(e ->{
            JFrame eventDetailsFrame = new JFrame("Event Details");
            
            Image icon1 = Toolkit.getDefaultToolkit().getImage("C:\\Bonnel Jhon Files\\codebyters_logo.png");
            eventDetailsFrame.setIconImage(icon1);
            
            eventDetailsFrame.setSize(400, 225);
            eventDetailsFrame.setLayout(null);
            
            JLabel eventNameLabel = new JLabel("Event Name:");
            JTextField eventNameField = new JTextField(); 
            eventNameField.setPreferredSize(new Dimension(200, 30));
            eventNameLabel.setBounds(50, 30, 150, 25);
            eventNameField.setBounds(200, 30, 150, 30);
            
            JLabel eventDateLabel = new JLabel("Date(YYYY-MM-DD):");
            JTextField eventDateField = new JTextField();
            eventDateField.setPreferredSize(new Dimension(200, 30));
            eventDateLabel.setBounds(50, 80, 150, 25);
            eventDateField.setBounds(200, 80, 150, 30);
            
            JButton submitButton = new JButton("Submit");
            submitButton.setBounds(150, 130, 100, 30);
            submitButton.setFocusPainted(false);
       
            submitButton.addActionListener(submitEvent -> {
                String eventName = eventNameField.getText().trim();
                String eventDate = eventDateField.getText().trim();
                
                if (eventName.isEmpty() || eventDate.isEmpty()) {
                    JOptionPane.showMessageDialog(eventDetailsFrame, "Event Name and Date are required.");
                    return;
                }
                
                if (!eventDate.matches("\\d{4}-\\d{2}-\\d{2}")) {   //REGULAR EXPRESSION CHECK NA DAPAT DATE LANG SYA
                    JOptionPane.showMessageDialog(eventDetailsFrame, "Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }
                eventDetailsFrame.dispose();
                eventManager.createEventFile(eventName);
  
            });
            
            eventDetailsFrame.add(eventNameLabel);
            eventDetailsFrame.add(eventNameField);
            eventDetailsFrame.add(eventDateLabel);
            eventDetailsFrame.add(eventDateField);
            eventDetailsFrame.add(submitButton);
            
            eventDetailsFrame.setLocationRelativeTo(mainFrame);
            
            eventDetailsFrame.setResizable(false);
            eventDetailsFrame.setVisible(true); 
        
        });
    
    
        panelWest.add(createEventbtn);
        panelWest.add(Box.createVerticalStrut(15)); 


        updatebtn = new JButton("Update Events");
        updatebtn.setPreferredSize(new Dimension(120, 30)); 
        updatebtn.setMaximumSize(new Dimension(120, 30));
        updatebtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        updatebtn.setFocusPainted(false);
        updatebtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updatebtn.addActionListener(e -> eventManager.updateEvent(table));
        panelWest.add(updatebtn);
        panelWest.add(Box.createVerticalStrut(15));
        
        deletebtn = new JButton("Delete Events");
        deletebtn.setPreferredSize(new Dimension(120, 30)); 
        deletebtn.setMaximumSize(new Dimension(120, 30));
        deletebtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        deletebtn.setFocusPainted(false);
        deletebtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deletebtn.addActionListener(e -> eventManager.deleteEvent(table));
        panelWest.add(deletebtn);
        panelWest.add(Box.createVerticalStrut(50));
        
        logoutbtn = new JButton("Logout");
        logoutbtn.setPreferredSize(new Dimension(120, 30)); 
        logoutbtn.setMaximumSize(new Dimension(120, 30));
        logoutbtn.setAlignmentX(Component.CENTER_ALIGNMENT);  
        logoutbtn.setFocusPainted(false);
        logoutbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutbtn.addActionListener((ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                mainFrame.dispose();
                mainFrame.disable();
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutbtn);
                currentFrame.setVisible(false);  // Hide the current frame
                
                
            }
        });
        panelWest.add(logoutbtn);
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
        tablePanel.setLayout(new BorderLayout()); 

        // Define column names and create table model
        String[] columnNames = {"Event Name","Date"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.clearSelection();
        table.addMouseListener(new MouseAdapter() {
            public boolean isCellEditable(int row, int column) {
                return false; // Always prevent editing
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    int option = JOptionPane.showConfirmDialog(null, "OPEN FILE?", " ", JOptionPane.YES_NO_OPTION);
                
                    if(option == JOptionPane.YES_OPTION){
                    
                    int row = table.rowAtPoint(e.getPoint());
                    String currentEvent = (String) tableModel.getValueAt(row, 0); // Assuming event name is in the first column
                   
                    eventManager.loadAttendanceData(currentEvent);
                    eventManager.enableCrudButtonsForStudents(currentEvent);
                    }
                }
            }
        });
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(30);
        
//        String[] comboBoxOptions = {"All Events", "Event A", "Event B", "Event C"};
//        JComboBox<String> comboBox = new JComboBox<>(comboBoxOptions);
//        comboBox.setPreferredSize(new Dimension(150, 19));
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        //searchPanel.add(comboBox);
        
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchEvents(searchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                searchEvents(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchEvents(searchField.getText());
            }
            
            private void searchEvents(String searchText) {
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null); // Show all rows if no text
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case insensitive search
                }
            }
        });
   
        tablePanel.add(searchPanel, BorderLayout.NORTH);
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

        eventManager.loadExistingEvents(); // This is the key line that will automatically load events
    }
} 