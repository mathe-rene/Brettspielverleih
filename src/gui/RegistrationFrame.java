package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrationFrame extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegistrationFrame() {
        setTitle("Registrierung");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("E-Mail:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Passwort:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerButton = new JButton("Registrieren");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
                dispose();
            }
        });
        add(registerButton);
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Brettspielverleih.db")) {
            String sql = "INSERT INTO Kunde (Name, eMail, Passwort) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registrierung erfolgreich!");
                pstmt.close();
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler bei der Registrierung.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame frame = new RegistrationFrame();
            frame.setVisible(true);
        });
    }
}
