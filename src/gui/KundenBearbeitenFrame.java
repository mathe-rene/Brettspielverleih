package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Kunde;
import service.KundeService;

class KundenBearbeitenFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private KundeService kundeService;
    private DefaultTableModel model;
    private JTable table;

    public KundenBearbeitenFrame() {
        setTitle("Kunden bearbeiten");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        kundeService = new KundeService();

        // Tabelle initialisieren
        String[] columnNames = {"Kunden ID", "Name", "eMail"};
        model = new DefaultTableModel(columnNames, 0) {
            
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Nur die Spalten "Name" und "eMail" sind editierbar
                return column != 0;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Kundendaten laden
        loadKundenData();

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
                dispose();
            }
        });
    }

    private void loadKundenData() {
        List<Kunde> kundenListe = kundeService.getKundenListe();
        for (Kunde kunde : kundenListe) {
            if (!kunde.getName().equalsIgnoreCase("Chef")) { // Filtert den Chef heraus
                model.addRow(new Object[]{kunde.getKundenId(), kunde.getName(), kunde.getEmail()});
            }
        }
    }

    private void saveChanges() {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int kundenId = (int) model.getValueAt(i, 0);
            String name = (String) model.getValueAt(i, 1);
            String email = (String) model.getValueAt(i, 2);
            kundeService.updateKunde(new Kunde(name, email, kundenId));
        }
        JOptionPane.showMessageDialog(this, "Änderungen gespeichert.");
    }
}
