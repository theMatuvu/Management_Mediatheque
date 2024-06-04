package ressources;

import server.BDController;

import java.util.ArrayList;
import java.util.List;

public class Mediatheque {
    private static Mediatheque instance;
    private List<Document> documents;
    private List<Abonne> abonnes;

    private Mediatheque() {
        documents = new ArrayList<>();
        abonnes = new ArrayList<>();
        BDController bd = new BDController();
        documents.addAll(bd.getDVDs());
        abonnes.addAll(bd.getAbonnes());

    }
    public static Mediatheque getInstance() {
        if (instance == null) {
            instance = new Mediatheque();
        }
        return instance;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("Documents : \n");
        for (Document d : documents) {
            sb.append(d.toString() + "\n");
        }
        sb.append("Abonnes : \n");
        for (Abonne a : abonnes) {
            sb.append(a.toString() + "\n");
        }
        return sb.toString();

    }

    public DVD getDVD(int numero) {
        for (Document d : documents) {
            if (d.getNumero() == numero) {
                return (DVD) d;
            }
        }
        return null;
    }
    public Abonne getAbonne(int numero) {
        for (Abonne a : abonnes) {
            if (a.getNumero() == numero) {
                return a;
            }
        }
        return null;
    }
}


