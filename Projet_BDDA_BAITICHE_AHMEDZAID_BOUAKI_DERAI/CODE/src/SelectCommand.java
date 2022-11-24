import java.io.IOException;
import java.util.ArrayList;


public class SelectCommand{


    public  String[] commande;
    public  ArrayList<Record> recordResultat;
    public  String nomRelation;
    public SelectCommand(String saisie){
        recordResultat=new ArrayList<>();
        parse(saisie);
    }
    


    /**
     * Méthode qui permet de découper la commande en un tableau pour 
     * récuperer le nom de la relation
     * @param cmd la commande entré par l'utilisateur
     */
    private void parse(String cmd){
        commande = cmd.split(" ");
        this.nomRelation=commande[3];
        
    }

    /**
     * Lancer la méthode qui choisit les records a afficher 
     * Affiche les records
     * @throws IOException
     */
    public  void execute() throws IOException{
        selectedRecords();
        afficherRecords();
    }

    public void afficherRecords(){
        for(int i=0 ; i<recordResultat.size();i++){
            System.out.println(recordResultat.get(i).toString());
        }
    }


    /**
     * Méthode qui vérifie si la commande contient des critères ou pas 
     * Si des critères on été entrés on affiche les records qui respecte les conditions
     * Sinon on récupere tous les records
     * @throws IOException
     */
    private void selectedRecords() throws IOException{
        boolean resultat=true;
        if(commande.length > 5){
            /* Des critères ont été entrées  */
            ArrayList<SelectCondition> criteres = listeCriteres();
            ArrayList<Record> allRecords= FileManager.leFileManager.getAllRecords(Catalog.leCatalog.getRelationInfo(nomRelation));
            for (Record record : allRecords) {
                for(int i=0; i<criteres.size(); i++){
                    int indiceColumn= listeCriteres().get(i).getIndiceColonne();
                    resultat= listeCriteres().get(i).verifConditionString(record.values.get(indiceColumn)); 
                }
                if(resultat){
                    recordResultat.add(record);
                }
            }
        }else{
            recordResultat=FileManager.leFileManager.getAllRecords(Catalog.leCatalog.getRelationInfo(nomRelation));
        }
    }
     
    

    /**
     * Découpe les critères dans différentes cases d'une liste
     * @return l'arraylist contenant dans chaque case les critères a respecter
     */
    private ArrayList<SelectCondition> listeCriteres(){
        String[] criteres = commande[5].split("AND");
            ArrayList<SelectCondition>  sc = new ArrayList<>();
            for(int i=0 ; i< criteres.length ; i++){
                sc.add(parseCmd(criteres[i]));
            }
            return sc;
    }

    
    /**
     * Crée une instance de selectCondition contenant les critères
     * @param str la condition a découper 
     * @return l'instance de selectCondition
     */
    private SelectCondition parseCmd(String str){
        int indice=0; 
        String op="";
        String val="";
        for(int i=0 ; i< SelectCondition.getOperateur().length;i++){
            if(str.contains(SelectCondition.getOperateur()[i])){
                String[] condition = str.split(SelectCondition.getOperateur()[i]);
                indice = getIndexColumn( condition[0]); 
                op = SelectCondition.getOperateur()[i];
                val = condition[1];
            }
        }
        return new SelectCondition(indice,op,val);
    }

    /**
     * @param str
     * @param nomColonne
     * @return
     * la methode permet de recuperer l'indice d'une colonne 
     */
    private int getIndexColumn(String nomColonne){
        RelationInfo r = Catalog.leCatalog.getRelationInfo(nomRelation);
        for(int i=0 ; i<r.getNbColonnes() ; i++){
            if(r.getInfoColonne().get(i).getNom().equals(nomColonne)){
                return i;
            }
        }
        return -1;
    }
}
