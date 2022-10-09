import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalog implements Serializable {
    // tableau de type RelationInfo
    private static final long serialVersionUID = 1234L;
    private ArrayList<RelationInfo> tableauRelationInfo;
    private int compteRelation;

    public void init() {
    }

    public void finish() throws IOException {
        sauvegarder();
    };

    public static Catalog leCatalog = new Catalog();

    private Catalog() {
    }

    public void addRelationInfo(RelationInfo ri) {
        tableauRelationInfo.add(ri);
        compteRelation++;
    }

    public RelationInfo getRelationInfo(String relation) {
        for (int i = 0; i < tableauRelationInfo.size(); i++) {
            if (tableauRelationInfo.get(i).getNomRelation().equals(relation)) {
                return tableauRelationInfo.get(i);
            }
        }
        return null;
    }

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
