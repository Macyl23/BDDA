import java.util.ArrayList;

public class RelationInfo {
    private String nomRelation;
    private int nbColonnes;
    private ArrayList<ColInfo> infoColonne;


    public RelationInfo(String nomRelation, int nbColonnes){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.infoColonne= new ArrayList<>();
    }


}
