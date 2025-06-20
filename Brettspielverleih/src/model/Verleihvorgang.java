package model;

import java.util.Date;

public class Verleihvorgang {
    private int verleihVorgangsId;
    private int artikelId;
    private int kundenId;
    private Date verleihDatum;
    private Date rückgabeDatum;
   

    // Konstruktor, Getter und Setter
    public Verleihvorgang(int verleihVorgangsId, int artikelId, int kundenId, Date verleihDatum, Date rückgabeDatum) {
    	this.verleihVorgangsId = verleihVorgangsId;
    	this.artikelId = artikelId;
    	this.kundenId = kundenId;
    	this.verleihDatum = verleihDatum;
    	this.rückgabeDatum = rückgabeDatum;
    
    }
    
    public int getVerleihVorgansId() {
    	return verleihVorgangsId;
    }
    public void setVerleihVorgansId(int verleihVorgangsId) {
    	this.verleihVorgangsId = verleihVorgangsId;
    }
    
    public int getArtikelId() {
    	return artikelId;
    }
    public void artikelId(int artikelId) {
    	this.artikelId = artikelId;
    }
    
    public int getKundenId() {
    	return kundenId;
    }
    public void kundenId(int kundenId) {
    	this.kundenId = kundenId;
    }
    
    public Date getVerleihDatum() {
    	return verleihDatum;
    }
    public void setVerleihdatum(Date verleihDatum) {
    	this.verleihDatum = verleihDatum;
    }
    
    public Date getRückgabeDatum() {
    	return rückgabeDatum;
    }
    public void setRückgabeDatum(Date rückgabeDatum) {
    	this.rückgabeDatum = rückgabeDatum;
    }
    
    
}

