package codebyters.attendance.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import javax.swing.JOptionPane;

public class LoginPageCodebytersAttendanceSystem {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setBackground(Color.black);
        frame.setSize(800, 550);
        frame.setLayout(new BorderLayout());

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);
        firstPanel.setPreferredSize(new Dimension(800,100));
        firstPanel.setBackground( Color.BLACK);
        firstPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        
        ImageIcon originalImageIcon = new ImageIcon("C:\\Users\\User\\Desktop\\CODEBYTERS GUI\\ATTENDANCE SYSTEM.jpg"); 
        Image image = originalImageIcon.getImage(); 
        Image resizedImage = image.getScaledInstance(800, 100, Image.SCALE_FAST);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        imageLabel.setBounds(0, 0, 800, 100);
        firstPanel.add(imageLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setPreferredSize(new Dimension(800,380));
        centerPanel.setBackground( Color.CYAN);
        
        ImageIcon originalImageIcon2 = new ImageIcon("C:\\Users\\User\\Desktop\\CODEBYTERS GUI\\facet.png"); 
        Image image2 = originalImageIcon2.getImage(); 
        Image resizedImage2 = image2.getScaledInstance(800, 380, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon2 = new ImageIcon(resizedImage2);
        JLabel imageLabel2 = new JLabel(resizedImageIcon2);
        imageLabel2.setBounds(0, 0, 800, 380);
        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(250, 75, 300, 180);
        loginPanel.setBackground(Color.GRAY);
   
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.blue);

        usernameLabel.setBounds(30, 30, 80, 30);
        usernameField.setBounds(120, 30, 150, 30);
        passwordLabel.setBounds(30, 80, 80, 30);
        passwordField.setBounds(120, 80, 150, 30);
        loginButton.setBounds(120, 130, 100, 30);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
//            if (username.equals("ADMIN") && String.valueOf(password).equals("pass")) {
//                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose();
                frame.disable();
                Mainpage main = new Mainpage();
                main.HomePage();
//            } else {
//                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
//            }
      });
        centerPanel.add(loginPanel);
        centerPanel.add(imageLabel2);
        
        
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null); 
        secondPanel.setPreferredSize(new Dimension(800, 70));  
        secondPanel.setBackground(Color.black);
        secondPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ImageIcon originalImageIcon1 = new ImageIcon("C:\\Users\\User\\Desktop\\CODEBYTERS GUI\\IT2F.jpg");
        Image image1 = originalImageIcon1.getImage(); 
        Image resizedImage1 = image1.getScaledInstance(800, 70, Image.SCALE_FAST);
        ImageIcon resizedImageIcon1 = new ImageIcon(resizedImage1);
        JLabel imageLabel1 = new JLabel(resizedImageIcon1);
        imageLabel1.setBounds(0, 0, 800, 70);
        secondPanel.add(imageLabel1);
        
        
        frame.add(centerPanel,BorderLayout.CENTER);
        frame.add(secondPanel,BorderLayout.SOUTH);
        frame.add(firstPanel,BorderLayout.NORTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
