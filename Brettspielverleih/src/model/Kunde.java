package model;

public class Kunde {
    private int kundenId;
    private String name;
    private String email;
    private String password;

    // Konstruktor
    public Kunde( String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter und Setter
    public int getKundenId() {
        return kundenId;
    }

    public void setKundenId(int kundenId) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
