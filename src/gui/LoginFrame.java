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

public class LoginFrame extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField emailField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;
    private Kunde kunde;

    public Kunde getKunde() {
        return this.kunde;
    }

    public LoginFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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
                mainFrame.setKunde(loginUser());
            }
        });
        add(loginButton);
    }

    private Kunde loginUser() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Brettspielverleih.db")) {
            String sql = "SELECT * FROM Kunde WHERE eMail = ? AND Passwort = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Login erfolgreich!");
                        String name = rs.getString("Name");
                        String mail = rs.getString("eMail");
                        int id = rs.getInt("Kunden_Id");
                        Kunde kunde = new Kunde(name, mail, id);
                        if ("Chef".equals(name)) {
                            // Admin-Panel öffnen, wenn der Chef sich anmeldet
                            AdminFrame adminFrame = new AdminFrame(mainFrame);
                            //mainFrame.dispose();
                            //adminFrame.setVisible(true);
                        } else {
                            // Benutzer zur Hauptanwendung weiterleiten
                            mainFrame.showArticleSelection();
                        }
                        dispose();
                        return kunde;
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
            MainFrame mainFrame = new MainFrame();
            LoginFrame frame = new LoginFrame(mainFrame);
            frame.setVisible(true);
        });
    }
}
