package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.Kunde;
import service.KundeService;
import util.DatabaseConnection;

public class TestKundeService {
    public static void main(String[] args) {
        KundeService kundeService = new KundeService();

        // Kunde registrieren
        Kunde neuerKunde = new Kunde("Max Mustermann", "max@example.com", "password123");
        kundeService.registrieren(neuerKunde);
        Kunde neuerKunde2 = new Kunde("Heinzi Schmollke", "heinzi@example.com", "123password");
        kundeService.registrieren(neuerKunde2);
 
               
        // Kunde anmelden
        
        // Kundenliste abrufen
        kundeService.getKundenListe().forEach(kunde -> System.out.println(kunde.getName())); 
        
//        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
//        System.out.println(timeStamp);
    }
}
