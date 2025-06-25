package gui;

import model.Kunde;
//import service.ArtikelService;
//import service.KundeService;
import service.VerleihService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    //private ArtikelService artikelService = new ArtikelService();
    //private KundeService kundeService = new KundeService();
    private VerleihService verleihService = new VerleihService();

    DefaultTableModel tableModel;
    private JTable artikelTable;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private Kunde kunde;
    private JRadioButton zweiWochen;
    private JRadioButton vierWochen;
    
    public MainFrame() {
        setTitle("Brettspielverleih");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();
        JMenu titelMenu = new JMenu("Bezeichnung");
        JMenu kategorieMenu = new JMenu("Kategorie");
        JMenu spielerZahlMenu = new JMenu("Spielerzahl");
        JMenu alterMenu = new JMenu("Empfohlenes_Alter");

        // Menüs dynamisch befüllen
        fillMenuFromDatabase(titelMenu, "SELECT DISTINCT Bezeichnung FROM Artikel");
        fillMenuFromDatabase(kategorieMenu, "SELECT DISTINCT Kategorie FROM Artikel");
        fillMenuFromDatabase(spielerZahlMenu, "SELECT DISTINCT Spielerzahl FROM Artikel");
        fillMenuFromDatabase(alterMenu, "SELECT DISTINCT Empfohlenes_Alter FROM Artikel");

        // Menüs zur Menüleiste hinzufügen
        menuBar.add(titelMenu);
        menuBar.add(kategorieMenu);
        menuBar.add(spielerZahlMenu);
        menuBar.add(alterMenu);

        // Menüleiste zum Frame hinzufügen
        setJMenuBar(menuBar);

        // Tabelle für Artikel erstellen
        String[] columnNames = {"Auswahl", "Bezeichnung", "Kategorie", "Spielerzahl", "Empfohlenes Alter", "Beschreibung"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        artikelTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons für Anmelden und Registrieren hinzufügen
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Anmelden");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame lf = new LoginFrame(MainFrame.this);
                lf.setVisible(true);
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Registrieren");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationFrame().setVisible(true);
            }
        });
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        // RadioButtons für Ausleihzeitraum hinzufügen, aber zunächst unsichtbar machen
        zweiWochen = new JRadioButton("2 Wochen");
        vierWochen = new JRadioButton("4 Wochen");
        ButtonGroup zeitAuswahl = new ButtonGroup();
        zeitAuswahl.add(zweiWochen);
        zeitAuswahl.add(vierWochen);
        zweiWochen.setVisible(false);
        vierWochen.setVisible(false);

        // Button für Auswahl bestätigen hinzufügen, aber zunächst unsichtbar machen
        confirmButton = new JButton("Auswahl bestätigen");
        confirmButton.setVisible(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmSelection();
            }
        });
        buttonPanel.add(confirmButton);
        buttonPanel.add(zweiWochen);
        buttonPanel.add(vierWochen);
        
        setVisible(true);
    }

    public void setKunde(Kunde k) {
        this.kunde = k;
    };
    
    private void fillMenuFromDatabase(JMenu menu, String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Brettspielverleih.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                JMenuItem item = new JMenuItem(rs.getString(1));
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        filterArticles(menu.getText(), item.getText());
                    }
                });
                menu.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Abrufen der Daten.");
        }
    }

    private void loadArticles(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Brettspielverleih.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            tableModel.setRowCount(0); // Bestehende Daten löschen
            while (rs.next()) {
                Object[] row = {
                    false, // Checkbox
                    rs.getString("Bezeichnung"),
                    rs.getString("Kategorie"),
                    rs.getString("Spielerzahl"),
                    rs.getString("Empfohlenes_Alter"),
                    rs.getString("Beschreibung")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Abrufen der Daten.");
        }
    }

    private void filterArticles(String column, String value) {
        String query = "SELECT * FROM Artikel WHERE " + column + " = '" + value + "'";
        loadArticles(query);
    }

    public void showArticleSelection() {
        loadArticles("SELECT * FROM Artikel");
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton && !comp.equals(confirmButton)) {
                comp.setVisible(false);
            }
        }
        confirmButton.setVisible(true);
        zweiWochen.setVisible(true);
        vierWochen.setVisible(true);
    }

    // Auswahl auswerten
    private void confirmSelection() {
        List<Integer> artikelIds = new ArrayList<>();
        for (int i = 0; i < artikelTable.getRowCount(); i++) {
            Boolean isSelected = (Boolean) artikelTable.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                artikelIds.add(i);
            }
        }

        if (artikelIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie mindestens einen Artikel aus.");
            return;
        }

        int ausleihzeitraum;
        if (zweiWochen.isSelected()) {
            ausleihzeitraum = 2;
        } else if (vierWochen.isSelected()) {
            ausleihzeitraum = 4;
        } else {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Ausleihzeitraum.");
            return;
        }

        // Kunden-ID dynamisch ermitteln
        verleihService.auswahlBestätigen(artikelIds, kunde.getKundenId(), ausleihzeitraum);
        JOptionPane.showMessageDialog(this, "Auswahl gespeichert!");

        // Häkchen aus den Checkboxen entfernen
        for (int i = 0; i < artikelTable.getRowCount(); i++) {
            artikelTable.setValueAt(false, i, 0);
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
