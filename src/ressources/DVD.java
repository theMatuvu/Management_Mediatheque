package ressources;

public class DVD implements Document {
    private int numero;
    private String titre;
    private boolean adulte;
    private boolean emprunte;
    private boolean reserve;
    private Abonne abonne;

    public DVD(int numero, String titre, boolean adulte) {
        this.numero = numero;
        this.titre = titre;
        this.adulte = adulte;
    }


    @Override
    public synchronized void reservation(Abonne ab)  {
        if(!reserve && !emprunte){
            reserve = true;
            abonne = ab;
        }
        else{
            System.out.println("DVD déjà réservé ou emprunté");
        }

    }

    @Override
    public synchronized void emprunt(Abonne ab) {
        if(!reserve && !emprunte){
            emprunte = true;
            reserve = false;
            abonne = ab;
        }
        else if(!emprunte && reserve && abonne.equals(ab)){
            emprunte = true;
        }
        else{
            System.out.println("DVD réservé ou déjà emprunté");
        }

    }

    @Override
    public synchronized void retour(Abonne ab)  {
        if(abonne.equals(ab)){
            reserve = false;
            emprunte = false;
            abonne = null;
        }
        else{
            System.out.println("DVD non emprunté par cet abonné");
        }

    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public boolean isAdulte() {
        return adulte;
    }

    public String toString() {
        return "DVD : " + numero + " " + titre + " " + adulte;
    }
}
