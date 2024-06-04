package ressources;

public interface Document {

    void reservation(Abonne ab) ;
    void emprunt(Abonne ab) ;
    void retour(Abonne ab);

    int getNumero();
}
