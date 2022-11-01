import java.util.ArrayList;

public class RelationInfo {
    private String nomRelation;
    private int nbColonnes;
    private ArrayList<ColInfo> infoColonne;


    public ArrayList<ColInfo> getInfoColonne() {
        return infoColonne;
    }
    public void setInfoColonne(ArrayList<ColInfo> infoColonne) {
        this.infoColonne = infoColonne;
    }
    public RelationInfo(String nomRelation, int nbColonnes){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.infoColonne= new ArrayList<ColInfo>();
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
       
        StringBuffer sb = new StringBuffer("nom relation : " + this.nomRelation +  " nbColonnes " + this.nbColonnes + getInfoCol());
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
    
    


}
