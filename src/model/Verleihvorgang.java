package model;

import java.sql.Timestamp;

public class Verleihvorgang {
    private int verleihvorgangId;
    private int artikelId;
    private int kundenId;
    private Timestamp verleihdatum;
    private Timestamp rückgabedatum;

    public Verleihvorgang(int verleihvorgangId, int artikelId, int kundenId, Timestamp verleihdatum, Timestamp rückgabedatum) {
        this.verleihvorgangId = verleihvorgangId;
        this.artikelId = artikelId;
        this.kundenId = kundenId;
        this.verleihdatum = verleihdatum;
        this.rückgabedatum = rückgabedatum;
    }

    public int getVerleihvorgangId() {
        return verleihvorgangId;
    }

    public int getArtikelId() {
        return artikelId;
    }

    public int getKundenId() {
        return kundenId;
    }

    public Timestamp getVerleihdatum() {
        return verleihdatum;
    }

    public Timestamp getRückgabedatum() {
        return rückgabedatum;
    }
}
