package model;

public class Artikel {
    private int artikelId;
    private String bezeichnung;
    private String kategorie;
    private String spielerzahl;
    private String alter;
    private String beschreibung;
    private boolean status;

    // Konstruktor, Getter und Setter
    public Artikel(String bezeichnung, String kategorie, String spielerzahl, String alter, String beschreibung, boolean status) {
    	
    	this.bezeichnung = bezeichnung;
    	this.kategorie = kategorie;
    	this.spielerzahl = spielerzahl;
    	this.alter = alter;
    	this.beschreibung = beschreibung;
    	this.status = status;
    }
    
    
    public int getArtikelId() {
    	return artikelId;
    }
    public void setArtikelId(int artikelId) {
    	this.artikelId = artikelId;
    }
    
    public String getBezeichnung() {
    	return bezeichnung;
    }
    public void setBezeichnung(String bezeichnung) {
    	this.bezeichnung = bezeichnung;
    }
    
    public String getKategorie() {
    	return kategorie;
    }
    public void setKategorie(String kategorie) {
    	this.kategorie = kategorie;
    }
    
    public String getSpielerzahl() {
    	return spielerzahl;
    }
    public void setSpielerzahl(String spielerzahl) {
    	this.spielerzahl = spielerzahl;
    }
    
    public String getAlter() {
    	return alter;
    }
    public void setAlter(String alter) {
    	this.alter = alter;
    }
    
    public String getBeschreibung() {
    	return beschreibung;
    }
    
    public void setBeschreibung(String beschreibung) {
    	this.beschreibung = beschreibung;
    }
    
    public boolean getStatus() {
    	return status;
    }
    public void setStatus(boolean status) {
    	this.status = status;
    }
}
