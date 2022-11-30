import java.io.IOException;
import java.util.ArrayList;


public class SelectCommand{

    private final static int MAX_CRITERES = 20;
    private  String[] commande;
    private  ArrayList<Record> recordResultat;
    private  String nomRelation;
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
        commande = cmd.split("WHERE");
        String[] cmdTemp = commande[0].split(" ");
        this.nomRelation=cmdTemp[3];
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
        System.out.println("Total records = "+recordResultat.size());
    }


    /**
     * Méthode qui vérifie si la commande contient des critères ou pas 
     * Si des critères on été entrés on affiche les records qui respecte les conditions
     * Sinon on récupere tous les records
     * @throws IOException
     */
    private void selectedRecords() throws IOException{
        if(commande.length == 2){
            /* Des critères ont été entrées  */
            ArrayList<SelectCondition> criteres = listeCriteres();
            ArrayList<Record> allRecords= FileManager.leFileManager.getAllRecords(Catalog.leCatalog.getRelationInfo(nomRelation));
            for (Record record : allRecords) {
                int i=0;
                boolean resultat=true;
                while(i<criteres.size() && resultat){
                    int indiceColumn= listeCriteres().get(i).getIndiceColonne();
                    resultat= listeCriteres().get(i).verifConditionString(record.values.get(indiceColumn)); 
                    i++;
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
        String[] criteres = commande[1].split("AND");
        
        ArrayList<SelectCondition>  sc = new ArrayList<>();
        if(criteres.length <= MAX_CRITERES){
            for(int i=0 ; i< criteres.length ; i++){
                sc.add(parseCmd(criteres[i]));
            }
        }else{
            System.out.println("Vous avez entres plus de 20 criteres");
            System.exit(1);
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
                indice = getIndexColumn(condition[0].substring(1)); 
                op = SelectCondition.getOperateur()[i];
                if(condition[1].contains(" ")){
                    val = condition[1].substring(0,condition[1].length()-1);
                }else
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
