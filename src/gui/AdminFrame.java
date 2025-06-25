package gui;

//import model.Kunde;
//import service.KundeService;

import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.List;

public class AdminFrame extends JDialog {

    private static final long serialVersionUID = 1L;
    
    private final MainFrame mainFrame;

    public AdminFrame(MainFrame mainFrame) {
    	
    	this.mainFrame = mainFrame;
    	
        setTitle("Admin Panel");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton kundeBearbeiten = new JButton("Kunde bearbeiten");
        kundeBearbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KundenBearbeitenFrame().setVisible(true);
            }
        });
        add(kundeBearbeiten);

        JButton artikelBearbeiten = new JButton("Artikel bearbeiten");
        artikelBearbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ArtikelBearbeitenFrame(mainFrame).setVisible(true);
            }
        });
        add(artikelBearbeiten);

        JButton verleihZeigen = new JButton("Verleihvorgänge");
        verleihZeigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VerleihFrame().setVisible(true);
            }
        });
        add(verleihZeigen);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AdminFrame().setVisible(true));
//    }
}

