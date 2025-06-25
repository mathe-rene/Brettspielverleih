package service;

import util.DatabaseConnection;
import model.Verleihvorgang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VerleihService {

    // Methode zum Starten eines Verleihvorgangs
    public String verleihStarten() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //System.out.println(timeStamp);
        return timeStamp;
    }

    // Methode zum Beenden eines Verleihvorgangs
    public void verleihBeenden() {
        // Verleihvorgang beenden
    }

    // Methode zum Bestätigen der Auswahl und Speichern der Verleihvorgänge
    public void auswahlBestätigen(List<Integer> artikelIds, int kundenId, int ausleihzeitraum) {
        Date verleihDatum = new Date();

        // Rückgabedatum berechnen
        Calendar cal = Calendar.getInstance();
        cal.setTime(verleihDatum);
        cal.add(Calendar.WEEK_OF_YEAR, ausleihzeitraum);
        Date rückgabeDatum = cal.getTime();
        System.out.println(rückgabeDatum);

        String sql = "INSERT INTO Verleihvorgang (Artikel_Id, Kunden_Id, Verleihdatum, Rückgabedatum) VALUES (?,?,?,?)";
        String updateSql = "UPDATE Artikel SET Status = ? WHERE Artikel_Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
            for (int artikelId : artikelIds) {
                pstmt.setInt(1, artikelId);
                pstmt.setInt(2, kundenId);
                pstmt.setTimestamp(3, new Timestamp(verleihDatum.getTime()));
                pstmt.setTimestamp(4, new Timestamp(rückgabeDatum.getTime()));
                pstmt.addBatch();

                // Status des Artikels auf false setzen
                updatePstmt.setBoolean(1, false);
                updatePstmt.setInt(2, artikelId);
                updatePstmt.addBatch();
            }
            pstmt.executeBatch();
            updatePstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Alle Verleihvorgänge gespeichert und Artikelstatus aktualisiert.");
    }

    // Methode zum Abrufen aller Verleihvorgänge
    public List<Verleihvorgang> getVerleihListe() {
        List<Verleihvorgang> verleihListe = new ArrayList<>();
        String sql = "SELECT * FROM Verleihvorgang";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int verleihvorgangId = rs.getInt("Verleihvorgangs_Id");
                int artikelId = rs.getInt("Artikel_Id");
                int kundenId = rs.getInt("Kunden_Id");
                Timestamp verleihdatum = rs.getTimestamp("Verleihdatum");
                Timestamp rückgabedatum = rs.getTimestamp("Rückgabedatum");
                Verleihvorgang verleihvorgang = new Verleihvorgang(verleihvorgangId, artikelId, kundenId, verleihdatum, rückgabedatum);
                verleihListe.add(verleihvorgang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verleihListe;
    }
}
