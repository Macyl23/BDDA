import java.util.ArrayList;
// class relationInfo qui contient le nom de la relation et le ,nbColonne et une arraylis de type colInfo 
public class RelationInfo {
    private String nomRelation;
    private int nbColonnes;
    private ArrayList<ColInfo> infoColonne;

    
    // constructeur 
    public RelationInfo(String nomRelation, int nbColonnes){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.infoColonne= new ArrayList<ColInfo>();
    }

    // recupere la tableau de type ColInfo
        public ArrayList<ColInfo> getInfoColonne() {
        return infoColonne;
        }
    // une fonction pas necessaire mais au cas ou je la mets
        public void setInfoColonne(ArrayList<ColInfo> infoColonne) {
        this.infoColonne = infoColonne;
     }
    // recuperer les valeurs present dans le vector infoColonne (nom colonne et typeColonne)
    public String getInfoCol() {
        StringBuffer cI = new StringBuffer();
      
        for(ColInfo c : infoColonne) {
            cI.append("le nom de la colonne" + c.getNom() + " Type: " + c.getType()) ;
        }
        return cI.toString();
    }
    // recuperer toutes les infos nom relation / nbColonne / infoColonnes
    public String toString() {       
        StringBuffer sb = new StringBuffer("nom relation : " + this.nomRelation +  " nbColonnes " + this.nbColonnes + getInfoCol());
        return sb.toString();
    }
    // void afficher
    public void afficher() {
        System.out.println(toString());
    }
    // recuperer le nom de la relation
    public String getNomRelation() {
        return nomRelation;
    }
    // recueprer nb de colonne
    public int getNbColonnes() {
        return nbColonnes;
    }
    
    


}
