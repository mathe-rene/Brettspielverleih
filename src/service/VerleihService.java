package service;

import model.Verleihvorgang;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class VerleihService {

    // Methode zum Starten eines Verleihvorgangs
    public String verleihStarten(Verleihvorgang verleihvorgang) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //System.out.println(timeStamp);
        return timeStamp;
    }

    // Methode zum Beenden eines Verleihvorgangs
    public void verleihBeenden(Verleihvorgang verleihvorgang) {
        // Verleihvorgang beenden
    }

    // Methode zum Abrufen überfälliger Rückgaben
    public List<Verleihvorgang> getÜberfälligeRückgaben() {
        return null;
        // Überfällige Rückgaben abrufen
    }

    // Methode zum Bestätigen der Auswahl und Speichern der Verleihvorgänge
    public void auswahlBestätigen(List<Integer> artikelIds, int kundenId) {
        Date verleihDatum = new Date();
        Date rückgabeDatum = null; // Setze das Rückgabedatum auf null 

        String sql = "INSERT INTO Verleihvorgang (Artikel_Id, Kunden_Id, Verleihdatum, Rückgabedatum) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int artikelId : artikelIds) {
                Verleihvorgang verleihvorgang = new Verleihvorgang(0, artikelId, kundenId, verleihDatum, rückgabeDatum);
                pstmt.setInt(1, verleihvorgang.getArtikelId());
                pstmt.setInt(2, kundenId);
                pstmt.setTimestamp(3, new Timestamp(verleihvorgang.getVerleihDatum().getTime()));
                pstmt.setTimestamp(4, verleihvorgang.getRückgabeDatum() != null ? new Timestamp(verleihvorgang.getRückgabeDatum().getTime()) : null);
                pstmt.addBatch();
                System.out.println("Verleihvorgang vorbereitet: " + verleihvorgang);
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Alle Verleihvorgänge gespeichert.");
    }
}
