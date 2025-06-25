package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Artikel;
import service.ArtikelService;

public class ArtikelBearbeitenFrame extends JPanel {
    private static final long serialVersionUID = 1L;
    private ArtikelService artikelService;
    private DefaultTableModel model;
    private JTable table;

    public ArtikelBearbeitenFrame(MainFrame mainFrame) {
    	//setTitle("Artikel bearbeiten");
        setSize(600, 400);
      //  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setLayout(new BorderLayout());

        artikelService = new ArtikelService();

        // Tabelle initialisieren
        String[] columnNames = {"Artikel Id", "Bezeichnung", "Kategorie", "Spielerzahl", "Alter", "Beschreibung", "Status"};
        model = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Nur die Spalten außer "Artikel Id" sind editierbar
                return column != 0;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Artikeldaten laden
        loadArtikelData();

        // Panel für die Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Speichern");
        JButton cancelButton = new JButton("Abbrechen");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener für die Buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  dispose();
            	//TODO WEITEBERARBEITEN
            }
        });
        
        mainFrame.setVisible(false);
        mainFrame.setContentPane(this);
        mainFrame.setVisible(true);
    }

    private void loadArtikelData() {
        List<Artikel> artikelListe = artikelService.getArtikelListe();
        for (Artikel artikel : artikelListe) {
            model.addRow(new Object[]{artikel.getArtikelId(), artikel.getBezeichnung(), artikel.getKategorie(), artikel.getSpielerzahl(), artikel.getAlter(), artikel.getBeschreibung(), artikel.getStatus()});
        }
    }

    private void saveChanges() {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            try {
                int artikelId = (int) model.getValueAt(i, 0);
                String bezeichnung = (String) model.getValueAt(i, 1);
                String kategorie = (String) model.getValueAt(i, 2);
                String spielerzahl = (String) model.getValueAt(i, 3);
                String alter = (String) model.getValueAt(i, 4);
                String beschreibung = (String) model.getValueAt(i, 5);
                Boolean status = Boolean.valueOf((String) model.getValueAt(i, 6));

                artikelService.updateArtikel(new Artikel(artikelId, bezeichnung, kategorie, spielerzahl, alter, beschreibung, status));
            } catch (Exception e) {
                continue;
            }
        }
        JOptionPane.showMessageDialog(this, "Änderungen gespeichert.");
    }
}
