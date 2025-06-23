package gui;


	import javax.swing.*;

import model.Kunde;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;

	public class Anmeldung extends JFrame {
	    
		private static final long serialVersionUID = 1L;
		private JTextField emailField;
	    private JPasswordField passwordField;
	    private Kunde kunde;

	    public Anmeldung() {
	        setTitle("Login");
	        setSize(400, 200);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLayout(new GridLayout(3, 2));

	        add(new JLabel("E-Mail:"));
	        emailField = new JTextField();
	        add(emailField);

	        add(new JLabel("Passwort:"));
	        passwordField = new JPasswordField();
	        add(passwordField);

	        JButton loginButton = new JButton("Login");
	        loginButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                kunde = loginUser();
	            }
	        });
	        add(loginButton);
	    }

	    private Kunde loginUser() {
	        String email = emailField.getText();
	        String password = new String(passwordField.getPassword());

	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:your_database.db")) {
	            String sql = "SELECT * FROM Kunde WHERE eMail = ? AND Passwort = ?";
	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setString(1, email);
	                pstmt.setString(2, password);
	                try (ResultSet rs = pstmt.executeQuery()) {
	                    if (rs.next()) {
	                        JOptionPane.showMessageDialog(this, "Login erfolgreich!");
	                        String name = rs.getString("Name");
	                        String mail = rs.getString("eMail");;
	                        int id = rs.getInt("Kunden_Id");
	                        // Benutzer zur Hauptanwendung weiterleiten
	                        return new Kunde(name,mail, id);
	                    } else {
	                        JOptionPane.showMessageDialog(this, "Ungültige Anmeldedaten.");
	                    }
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Fehler beim Login.");
	        }
			return null;
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            LoginFrame frame = new LoginFrame(null);
	            frame.setVisible(true);
	        });
	    }
	}


