package service;

import model.Kunde;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KundeService {
    public void registrieren(Kunde kunde) {
        String sql = "INSERT INTO Kunde (Name, eMail, Passwort) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kunde.getName());
            pstmt.setString(2, kunde.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Kunde anmelden(String email, String password) {
        String sql = "SELECT * FROM Kunde WHERE eMail = ? AND Passwort = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Kunde(
                        rs.getString("Name"),
                        rs.getString("eMail"),
                        rs.getInt("Kunden_Id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Kunde> getKundenListe() {
        List<Kunde> kundenListe = new ArrayList<>();
        String sql = "SELECT * FROM Kunde";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kunde kunde = new Kunde(
                        rs.getString("Name"),
                        rs.getString("eMail"),
                        rs.getInt("Kunden_Id")
                );
                kundenListe.add(kunde);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kundenListe;
    }

    public void kundeLöschen(int kundenId) {
        String sql = "DELETE FROM Kunde WHERE Kunden_Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kundenId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKunde(Kunde kunde) {
        String sql = "UPDATE Kunde SET Name = ?, eMail = ? WHERE Kunden_Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kunde.getName());
            pstmt.setString(2, kunde.getEmail());
            pstmt.setInt(3, kunde.getKundenId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
