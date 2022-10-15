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
    private ArrayList<RelationInfo> tableauRelationInfo;
    private int compteRelation;

    public void init() throws ClassNotFoundException, IOException {
        File f = new File(DBParams.DBPath + "/Catalog.sy");
        FileInputStream fInput = new FileInputStream(f);
        ObjectInputStream in = new ObjectInputStream(fInput);
        in.readObject();
        fInput.close();
        in.close();
    }

    public void finish() throws IOException {
        sauvegarder();
    };
    // CONSTRUCTEUR static de catalog
    public static Catalog leCatalog = new Catalog();
// constructeur de catalog
    private Catalog() {
    }
// ajout d'une relation dans le tableauRelation
    public void addRelationInfo(RelationInfo ri) {
        tableauRelationInfo.add(ri);
        compteRelation++;
    }
// reucperer l'indice ou la relaton passer en parametres est presente dans le tableau tableauRelation
    public RelationInfo getRelationInfo(String relation) {
        for (int i = 0; i < tableauRelationInfo.size(); i++) {
            if (tableauRelationInfo.get(i).getNomRelation().equals(relation)) {
                return tableauRelationInfo.get(i);
            }
        }
        return null;
    }
    // sauvegarder un catalog
    private void sauvegarder() throws IOException {
        File f = new File(DBParams.DBPath + "/Catalog.sy");
        FileOutputStream fOutput = new FileOutputStream(f);
        ObjectOutputStream out = new ObjectOutputStream(fOutput);
        out.writeObject(this);
        out.flush();
        fOutput.close();
        out.close();
    }

}
