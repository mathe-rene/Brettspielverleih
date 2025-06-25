package model;

public class Kunde {
    private int kundenId;
    private String name;
    private String email;

    // Konstruktor
    public Kunde( String name, String email, Integer id) {
        this.name = name;
        this.email = email;
        this.kundenId= id;
    }

    // Getter und Setter
    public  int getKundenId() {
        return kundenId;
    }

    public void setKundenId(int kundenId) {
    	System.out.println("Kunden id gesetzt "+ kundenId);
        this.kundenId = kundenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
