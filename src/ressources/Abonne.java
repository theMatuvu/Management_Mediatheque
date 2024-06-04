package ressources;

import java.sql.Date;

public class Abonne {
    private int numero;
    private String nom;
    private Date dateNaissance;


    public Abonne(int numero, String nom, Date dateNaissance) {
        this.numero = numero;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String toString() {
        return "Abonne : " + numero + " " + nom + " " + dateNaissance;
    }
}
