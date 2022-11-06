import java.io.Serializable;
import java.util.ArrayList;

public class RelationInfo implements Serializable {
    private static final long serialVersionUID = 1234L;

    private String nomRelation;
    private int nbColonnes;
    private ArrayList<ColInfo> infoColonne;
    private PageId headerPageId;


    public ArrayList<ColInfo> getInfoColonne() {
        return infoColonne;
    }
    public void setInfoColonne(ArrayList<ColInfo> infoColonne) {
        this.infoColonne = infoColonne;
    }
   
    public RelationInfo(String nomRelation, int nbColonnes, ArrayList<ColInfo> a, PageId pId){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.infoColonne= a;
        this.headerPageId=pId;
    }
    public RelationInfo(String nomRelation, int nbColonnes, ArrayList<ColInfo> a){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.infoColonne= a;
    }
    // recuperer les valeurs present dans le vector infoColonne
    public String getInfoCol() {
        StringBuffer cI = new StringBuffer();
      
        for(ColInfo c : infoColonne) {
            cI.append("le nom de la relation" + nomRelation + " Type: " + c.getType()) ;
        }
        return cI.toString();
    }
    public String toString() {
       
        StringBuffer sb = new StringBuffer("nom relation : " + this.nomRelation +  "\nnbColonnes " + this.nbColonnes +"\n"+ getInfoCol());
        return sb.toString();
    }
    public void afficher() {
        System.out.println(toString());
    }
    public String getNomRelation() {
        return nomRelation;
    }
    public int getNbColonnes() {
        return nbColonnes;
    }


    public PageId getHeaderPageId() {
        return headerPageId;
    }
    
    


}
