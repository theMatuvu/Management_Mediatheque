package ressources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DVD implements Document {
    private int numero;
    private String titre;
    private boolean adulte;
    private boolean emprunte;
    private long DateReservation;
    private Abonne abonne;

    public DVD(int numero, String titre, boolean adulte) {
        this.numero = numero;
        this.titre = titre;
        this.adulte = adulte;
    }


    @Override
    public synchronized void reservation(Abonne ab) throws ReservationException{
        if(DateReservation > System.currentTimeMillis()){
            DateReservation = 0;
            abonne = null;
        }

        if (DateReservation!=0)
            throw new ReservationException("DVD déjà réservé jusqu'a " + DateReservation);
        if(!emprunte)
            throw new ReservationException("DVD déjà emprunté");
        else{
            DateReservation = System.currentTimeMillis() + 5400000 ;
            abonne = ab;
        }

    }

    @Override
    public synchronized void emprunt(Abonne ab) throws EmpruntException {
        //gerer date de naissance si adulte
        Period periode = Period.between(ab.getDateNaissance().toLocalDate(),  LocalDate.now());
        int age = periode.getYears();
        if(adulte && (age) < 18){
            System.out.println("DVD adulte, abonné mineur");
            throw new EmpruntException("DVD adulte, abonné mineur");
        }
        if(!(DateReservation==0) && !emprunte){
            emprunte = true;
            DateReservation = 0;
            abonne = ab;
        }
        else if(!emprunte && (DateReservation==0) && abonne.equals(ab)){
            emprunte = true;
        }
        else{
            System.out.println("DVD réservé ou déjà emprunté");
            throw new EmpruntException("DVD réservé ou déjà emprunté");
        }

    }

    @Override
    public synchronized void retour() throws RetourException {
        if(emprunte){
            DateReservation = 0;
            emprunte = false;
            abonne = null;
        }
        else{
            System.out.println("DVD non emprunté");
            throw new RetourException("DVD non emprunté");

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
        StringBuilder sb = new StringBuilder("DVD : " + numero + " " + titre + " ");
        if(adulte){
            sb.append("Adulte ");
        }
        else{
            sb.append("Tout public ");
        }
        if(emprunte){
            sb.append("Emprunté ");
        }
        else if(DateReservation!=0){
            SimpleDateFormat dateFormatée = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
            sb.append("Réservé jusqu'a " + dateFormatée.format(new Date(DateReservation))+ " ");
        }
        return sb.toString();
    }
}
