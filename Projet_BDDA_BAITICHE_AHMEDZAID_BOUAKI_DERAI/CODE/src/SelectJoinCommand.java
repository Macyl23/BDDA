// import java.util.ArrayList;

// public class SelectJoinCommand{
    
//     private String relationUne;
//     private String relationDeux;
//     private String[] commande;
//     private ArrayList<Record> recordResultat;
//     private final static int MAX_CRITERES=20;
//     private SelectCondition recordACompare;


//     public SelectJoinCommand(String saisie){
//         recordResultat = new ArrayList<>();
//         remplirNomRelation(saisie);
//     }

//     public void execute(){
//         selectRecords();
//         afficherRecords();
//     }

//     public void afficherRecords(){
//         for(int i=0 ; i<recordResultat.size();i++){
//             System.out.println(recordResultat.get(i).toString());
//         }
//         System.out.println("Total records = "+recordResultat.size());
//     }

//     private void remplirNomRelation(String cmd){
//         commande = cmd.split("WHERE");
//         String[] cmdTemp = commande[0].split(" ");
//         String[] nomRelations = cmdTemp[3].split(",");
//         this.relationUne=nomRelations[0];
//         this.relationDeux=nomRelations[1];
//     }

//     private void selectrRecords(){

//     }


//     /**
//      * Découpe les critères dans différentes cases d'une liste
//      * @return l'arraylist contenant dans chaque case les critères a respecter
//      */
//     private ArrayList<SelectCondition> listeCriteres(){
//         String[] criteres = commande[1].split("AND");
        
//         ArrayList<SelectCondition>  sc = new ArrayList<>();
//         if(criteres.length <= MAX_CRITERES){
//             for(int i=0 ; i< criteres.length ; i++){
//                 sc.add(parseCmd(criteres[i]));
//             }
//         }else{
//             System.out.println("Vous avez entres plus de 20 criteres");
//             System.exit(1);
//         }
        
//         return sc;
//     }

//     /**
//      * Crée une instance de selectCondition contenant les critères
//      * @param str la condition a découper 
//      * @return l'instance de selectCondition
//      */
//     private SelectCondition parseCmd(String str){
//         int indice=0; 
//         String op="";
//         String val="";
//         ArrayList<String> colonnesRelations = new ArrayList<>();
//         for(int i=0 ; i< SelectCondition.getOperateur().length;i++){
//             if(str.contains(SelectCondition.getOperateur()[i])){
//                 String[] condition = str.split(SelectCondition.getOperateur()[i]);
//                 colonnesRelations= parseCondition(condition);
//                 indice = getIndexColumn(condition[0].substring(1)); 
//                 op = SelectCondition.getOperateur()[i];
//                 if(condition[1].contains(" ")){
//                     val = condition[1].substring(0,condition[1].length()-1);
//                 }else
//                     val = condition[1];
//             }
//         }
//         return new SelectCondition(indice,op,val);
//     }

//     private ArrayList<String> parseCondition(String[] conditions){
//         ArrayList<String> joinConditions = new ArrayList<>();
//         for(int i=0 ; i<conditions.length ; i++){
//             String[] joinTemp = conditions[i].split(".");
//             joinConditions.add(joinTemp[1]); 
//         }
        
//         return joinConditions;
//     }

//     /**
//      * @param str
//      * @param nomColonne
//      * @return
//      * la methode permet de recuperer l'indice d'une colonne 
//      */
//     private int getIndexColumn(String nomColonne){
//         RelationInfo r = Catalog.leCatalog.getRelationInfo(nomRelation);

//         for(int i=0 ; i<r.getNbColonnes() ; i++){
//             if(r.getInfoColonne().get(i).getNom().equals(nomColonne)){
//                 return i;
//             }
//         }
//         return -1;
//     }
// }
