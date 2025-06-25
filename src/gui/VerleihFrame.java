package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Verleihvorgang;
import service.VerleihService;

public class VerleihFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private VerleihService verleihService;
    private DefaultTableModel model;
    private JTable table;

    public VerleihFrame() {
        setTitle("Verleihvorgänge anzeigen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        verleihService = new VerleihService();

        // Tabelle initialisieren
        String[] columnNames = {"Verleihvorgang Id", "Artikel Id", "Kunden Id", "Verleihdatum", "Rückgabedatum"};
        model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                // Keine Zellen sind editierbar
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Verleihvorgänge laden
        loadVerleihvorgängeData();

        // Panel für die Buttons
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Schließen");
        JButton overdueButton = new JButton("Überfällige anzeigen");
        buttonPanel.add(closeButton);
        buttonPanel.add(overdueButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener für den Schließen-Button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // ActionListener für die Überfällige anzeigen-Button
        overdueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0); // Tabelle leeren
                loadÜberfälligeVerleihvorgängeData(); // Überfällige Daten laden
            }
        });
    }

    private void loadVerleihvorgängeData() {
        List<Verleihvorgang> verleihvorgängeListe = verleihService.getVerleihListe();
        for (Verleihvorgang verleihvorgang : verleihvorgängeListe) {
            model.addRow(new Object[]{verleihvorgang.getVerleihvorgangId(), verleihvorgang.getArtikelId(), verleihvorgang.getKundenId(), verleihvorgang.getVerleihdatum(), verleihvorgang.getRückgabedatum()});
        }
    }

    private void loadÜberfälligeVerleihvorgängeData() {
        List<Verleihvorgang> verleihvorgängeListe = verleihService.getVerleihListe();
        Date heute =new Date();
        

        for (Verleihvorgang verleihvorgang : verleihvorgängeListe) {
        	 Date t = new Date( verleihvorgang.getRückgabedatum().getTime());
            if  ( heute.after(t) )   {
                model.addRow(new Object[]{verleihvorgang.getVerleihvorgangId(), verleihvorgang.getArtikelId(), verleihvorgang.getKundenId(), verleihvorgang.getVerleihdatum(), verleihvorgang.getRückgabedatum()});
            }
        }
    }
}
