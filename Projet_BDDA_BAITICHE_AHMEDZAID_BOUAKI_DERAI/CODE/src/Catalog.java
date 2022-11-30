import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalog implements Serializable {
    // tableau de type RelationInfo
    private static final long serialVersionUID = 1234L;
    private ArrayList<RelationInfo> listeRelationInfo=new ArrayList<>();

    private Catalog() {}
    public static Catalog leCatalog = new Catalog();
    


    /**
     * Méthode qui permet de faire de la désérialisation en récupérant les données 
     * stockés dans le Catalog
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void init() throws ClassNotFoundException, IOException {
        File f = new File(DBParams.DBPath + "/Catalog.sy");
        if (f.exists()) {
            FileInputStream fInput = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fInput);
            Catalog.leCatalog = (Catalog)in.readObject();
            fInput.close();
            in.close();
        }
    }

    /*
     * Méthode qui s'éxecute a chaque fin de programme pour faire de la sérialisation
     */
    public void finish() throws IOException {
        File f = new File(DBParams.DBPath + "/Catalog.sy");
        try{
            FileOutputStream fOutput = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fOutput);
            out.writeObject(this);
            out.flush();
            fOutput.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    };


    /**
     * Ajoute une relation dans le Catalog
     * @param ri la relation a rajouter
     */
    public void addRelationInfo(RelationInfo ri) {
        listeRelationInfo.add(ri);
    }

    
    /**
     * Permet de recuperer une relation a partir de son nom
     * @param relation nom de la relation
     * @return la relationInfo
     */
    public RelationInfo getRelationInfo(String relation) {
        for (int i = 0; i < listeRelationInfo.size(); i++) {
            if (listeRelationInfo.get(i).getNomRelation().equals(relation)) {
                return listeRelationInfo.get(i);
            }
        }
        return null;
    }

   
    /**
     * Supprime toutes les relations stockées dans l'arrayList
     */
    public void reinitialiser(){
        listeRelationInfo.clear();
    }
}
