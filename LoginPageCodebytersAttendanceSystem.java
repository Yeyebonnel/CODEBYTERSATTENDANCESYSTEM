package codebytersattendancesystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;

public class LoginPageCodebytersAttendanceSystem {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setBackground(Color.black);
        frame.setSize(800, 550);
        frame.setLayout(new BorderLayout());
        
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Bonnel Jhon Files\\codebyters_logo.png");// kini na part sa sa icon mismo sa atoang frame instead na java logo cb logo mo gawas
        frame.setIconImage(icon);
        

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);//NAKA NULL NI SYA KAY NAG GAMIT TAG BORDERLAYOUT SA ATOANG FRAME.
        firstPanel.setPreferredSize(new Dimension(800,100));//MAO NI ANG FINAL SIZE GAYUD NIYA INSTEAD NA NAKA SETSIZE MAS OKAY NI SYA NA METHOD
        firstPanel.setBackground( Color.BLACK);
        
        ImageIcon originalImageIcon = new ImageIcon("C:\\Bonnel Jhon Files\\ATTENDANCE SYSTEM.jpg"); 
        Image image = originalImageIcon.getImage(); 
        Image resizedImage = image.getScaledInstance(800, 100, Image.SCALE_FAST);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        imageLabel.setBounds(0, 0, 800, 100);// para sa position mismo sa image na gi patung nato sa panel
        firstPanel.add(imageLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setPreferredSize(new Dimension(800,380));
        centerPanel.setBackground( Color.CYAN);
        
        ImageIcon originalImageIcon2 = new ImageIcon("C:\\Bonnel Jhon Files\\facet.png"); 
        Image image2 = originalImageIcon2.getImage(); 
        Image resizedImage2 = image2.getScaledInstance(800, 380, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon2 = new ImageIcon(resizedImage2);
        JLabel imageLabel2 = new JLabel(resizedImageIcon2);
        imageLabel2.setBounds(0, 0, 800, 380);
        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(250, 75, 300, 180);
        loginPanel.setBackground(Color.CYAN);
   
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        usernameLabel.setBounds(30, 30, 80, 30);
        usernameField.setBounds(120, 30, 150, 30);
        passwordLabel.setBounds(30, 80, 80, 30);
        passwordField.setBounds(120, 80, 150, 30);
        loginButton.setBounds(120, 130, 100, 30);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//kining cursor is java swing framework ni sya na naka change sa cursor na mahimo ug hand

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char [] password = passwordField.getPassword();
            if (username.equals("ADMIN") && String.valueOf(password).equals("pass")) {//kaning condition diri na valueof na method is basta
                 JOptionPane.showMessageDialog(frame, "Login Successful");
                 frame.dispose();//ang frame na dispose hahahaha
                 frame.disable();//para ma wala ang login page ug mo appear ang new frame
                 Mainpage main = new Mainpage();//mao ni ang mo puli na frame which is mag add nakag event
                 main.HomePage();//mao ning method na naa sa atoang Mainpage na class
             } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);//mo display ni if mali ang pass or username
             }
       });
        centerPanel.add(loginPanel);
        centerPanel.add(imageLabel2);
        
        
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null); //naka null kay nag gamit man tag borderlayout sa frame
        secondPanel.setPreferredSize(new Dimension(800, 70));  
        secondPanel.setBackground(Color.black);
        
        ImageIcon originalImageIcon1 = new ImageIcon("C:\\Bonnel Jhon Files\\IT2F.jpg");
        Image image1 = originalImageIcon1.getImage(); 
        Image resizedImage1 = image1.getScaledInstance(800, 70, Image.SCALE_FAST);
        ImageIcon resizedImageIcon1 = new ImageIcon(resizedImage1);
        JLabel imageLabel1 = new JLabel(resizedImageIcon1);
        imageLabel1.setBounds(0, 0, 800, 70);// exact position sa image mismo sa atong panel
        secondPanel.add(imageLabel1);
        
        
        frame.add(centerPanel,BorderLayout.CENTER);//mao ni ang position mismo sa mga panel mao nag null ta sa setlayout na method
        frame.add(secondPanel,BorderLayout.SOUTH);
        frame.add(firstPanel,BorderLayout.NORTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //naka null ni sya para mo appear and frame mismo sa center sa screen
        frame.setVisible(true);
    }
}