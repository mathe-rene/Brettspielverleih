package test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Artikel;
import service.ArtikelService;
import util.DatabaseConnection;

public class TestArtikelService {
    public static void main(String[] args) {
        ArtikelService artikelService = new ArtikelService();

        // Artikel anlegen
        Artikel neuerArtikel = new Artikel("Dominion","Deck-Building","2 bis 4 Spieler","0 bis 99 Jahre","ganz viel geklauter Text",true);
        artikelService.artikelAnlegen(neuerArtikel);
        Artikel neuerArtikel2 = new Artikel("Risiko","Strategie","2 bis 6 Spieler","6 bis 99 Jahre","noch viel mehr geklauter Text",true);
        artikelService.artikelAnlegen(neuerArtikel2);
        Artikel neuerArtikel3 = new Artikel("Schach","Strategie","2 Spieler","0 bis 99 Jahre","noch viel viel viel mehr geklauter Text",false);
        artikelService.artikelAnlegen(neuerArtikel3);
        Artikel neuerArtikel4 = new Artikel("Skat","Karten","2 Spieler","0 bis 99 Jahre","Text",true);
        artikelService.artikelAnlegen(neuerArtikel4);
        Artikel neuerArtikel5 = new Artikel("Mau Mau","Karten","2 Spieler","6 bis 99 Jahre","Text",true);
        artikelService.artikelAnlegen(neuerArtikel5);
        Artikel neuerArtikel6 = new Artikel("Kniffel","Würfel","2 bis 4 Spieler","0 bis 99 Jahre","noch mehr geklauter Text",false);
        artikelService.artikelAnlegen(neuerArtikel6);
        
        // Artikelliste abrufen
        artikelService.getArtikelListe().forEach(artikel -> System.out.println(artikel.getBezeichnung()));

       
        
        
    }
}

