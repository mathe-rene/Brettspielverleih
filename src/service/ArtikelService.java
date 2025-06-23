package service;

import model.Artikel;
import model.Kunde;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtikelService {
    public void artikelAnlegen(Artikel artikel) {
    	 
    	        String sql = "INSERT INTO Artikel (Bezeichnung, Kategorie, Spielerzahl, Empfohlenes_Alter, Beschreibung, Status) VALUES (?,?,?,?,?,?)";
    	        try (Connection conn = DatabaseConnection.getConnection();
    	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    	            pstmt.setString(1, artikel.getBezeichnung());
    	            pstmt.setString(2, artikel.getKategorie());
    	            pstmt.setString(3, artikel.getSpielerzahl());
    	            pstmt.setString(4, artikel.getAlter());
    	            pstmt.setString(5, artikel.getBeschreibung());
    	            pstmt.setBoolean(6, artikel.getStatus());
    	            pstmt.executeUpdate();
//    	            pstmt.close();
//    	            conn.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }
    

    public void artikelÄndern(Artikel artikel) {
        // Artikel ändern
    }

    public void artikelLöschen(int artikelId) {
    	 
			    String sql = "DELETE FROM Artikel WHERE Artikel_Id = ?";
			    try (Connection conn = DatabaseConnection.getConnection();
			         PreparedStatement pstmt = conn.prepareStatement(sql)) {
			        pstmt.setInt(1, artikelId);
			        pstmt.executeUpdate();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
    }
    
 // Artikelliste aufrufen
    public List<Artikel> getArtikelListe() {
    	
            List<Artikel> artikelListe = new ArrayList<>();
            String sql = "SELECT * FROM Artikel";
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Artikel artikel = new Artikel(
                           
                            rs.getString("Bezeichnung"),
                            rs.getString("Kategorie"),
                            rs.getString("Spielerzahl"),
                            rs.getString("Empfohlenes_Alter"),
                            rs.getString("Beschreibung"),
                            rs.getBoolean("Status")
                            
                    );
                    artikelListe.add(artikel);
                    
//    	            conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return artikelListe;
        }
        
    }

