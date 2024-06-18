package ressources;

import ressources.Documents.DVD;
import ressources.Documents.Document;
import server.DataBase.BDController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mediatheque {
    private static Mediatheque instance;
    private HashMap<Integer, Document> documents;
    private List<Abonne> abonnes;

    private Mediatheque() {
        documents = new HashMap<>();
        abonnes = new ArrayList<>();
        BDController bd = new BDController();
        for (DVD dvd : bd.getDVDs()) {
            documents.put(dvd.getNumero(), dvd);
        }
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
        for (Map.Entry<Integer, Document> entry : documents.entrySet()) {
            sb.append("Document Number: ").append(entry.getKey()).append("\n");
            sb.append("Document Details: ").append(entry.getValue().toString()).append("\n");
        }
        sb.append("Abonnes : \n");
        for (Abonne a : abonnes) {
            sb.append(a.toString() + "\n");
        }
        return sb.toString();

    }
    public List<Document> getDocuments() {
        return new ArrayList<>(documents.values());
    }

    public Document getDocument(int numero) {
        return documents.get(numero);
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


