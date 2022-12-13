import java.io.IOException;
import java.util.ArrayList;


public class SelectJoinCommand {

    private final static int MAX_CRITERES = 20;
    public  String[] commande;
    private  ArrayList<Record> recordResultat;
    public  String relationUne;
    public  String relationDeux;

    public SelectJoinCommand(String saisie){
        recordResultat=new ArrayList<>();
        parseRelation(saisie);
    }
    public void execute() throws IOException{
        joinNestedLoops();
    }


    private void parseRelation(String saisie){
        commande = saisie.split("WHERE");
        String[] cmdSplit = commande[0].split(" ");
        String[] relations = cmdSplit[3].split(",");
        this.relationUne=relations[0];
        this.relationDeux=relations[1];
    }

    /**
     * Applique l'algorithme joinNestedLoops
     * @throws IOException
     */
    private void joinNestedLoops() throws IOException{
        RelationInfo r1 = Catalog.leCatalog.getRelationInfo(relationUne);
        RelationInfo r2 = Catalog.leCatalog.getRelationInfo(relationDeux);
        Record recordR1 = new Record(r1);
        Record recordR2 = new Record(r2);
        ArrayList<PageId> pagesR1 = FileManager.leFileManager.getAllDataPage(r1);
        ArrayList<PageId> pagesR2 = FileManager.leFileManager.getAllDataPage(r2);
        int i=0;
        boolean allConditionsTrue = true;
        
        ArrayList<SelectConditionJoin> conditions = listeConditions(commande[1]);

        for(int j=0 ; j<pagesR1.size();j++){
            RecordIterator recordIterateur = new RecordIterator(r1, pagesR1.get(j));
            for(int k=0 ; k<pagesR2.size();k++){
                RecordIterator recordIterateurDeux = new RecordIterator(r2, pagesR2.get(k));
                while((recordR1= recordIterateur.getNextRecord())!=null){
                    while((recordR2 = recordIterateurDeux.getNextRecord())!=null){
                        i=0;
                        allConditionsTrue=true;
                        while(i<conditions.size() && allConditionsTrue){
                            String type = recordR1.getRelInfo().getInfoColonne().get(conditions.get(i).idxcolonneRelation.get(0)).getType();
                            allConditionsTrue=conditions.get(i).compareValues(type, recordR1, recordR2);
                            i++;
                        }
                        if(allConditionsTrue){
                            combinerRecords(recordR2, recordR1);
                            recordResultat.add(recordR2);
                        } 
                    }
                    recordIterateurDeux.reset();
                }
                recordIterateurDeux.close();
                recordIterateur.reset();
            }
            recordIterateur.close();           
        }
        afficherRecords();
    }
    

    private void combinerRecords(Record r2, Record r1){
        for(int i=0 ; i<r1.values.size() ; i++){
            r2.values.add(r1.values.get(i));
        }
    }
    public ArrayList<SelectConditionJoin> listeConditions(String str){
        String[] joins = str.split("AND");
        String op="";
        int j=0;
        ArrayList<SelectConditionJoin> conditionJoins = new ArrayList<>();
        if(joins.length<=MAX_CRITERES){
            for(int k=0 ; k<joins.length ; k++){
                ArrayList<Integer> indices =new ArrayList<>();
                for(int i=0 ; i< SelectConditionJoin.getOperateur().length;i++){
                    if(joins[k].contains(SelectConditionJoin.getOperateur()[i])){
                        String[] condition = joins[k].split(SelectConditionJoin.getOperateur()[i]);
                        j=0;
                        while(j<2){
                            String[] criteres = condition[j].split("\\.");
                            retirerEspace(criteres);
                            indices.add(getIndexColumn(criteres[1], criteres[0]));
                            j++;
                        }
                        op = SelectCondition.getOperateur()[i];
                        i=SelectConditionJoin.getOperateur().length;
                    }
                }
                conditionJoins.add(new SelectConditionJoin(indices, op));
            }
        }
        return conditionJoins;
    }
    private void retirerEspace(String[] tab){
        for(int i=0 ; i<tab.length ; i++){
            String[] temp = tab[i].split(" ");
            for(int j=0 ; j<temp.length ;j++){
                if(!temp[j].contains(" ")){
                    tab[i]=temp[j];
                }
            }
        }
    }
    /**
     * @param str
     * @param nomColonne
     * @return
     * la methode permet de recuperer l'indice d'une colonne 
     */
    private int getIndexColumn(String nomColonne,String r){
        RelationInfo relation = Catalog.leCatalog.getRelationInfo(r);
        for(int i=0 ; i<relation.getNbColonnes() ; i++){
            if(relation.getInfoColonne().get(i).getNom().equals(nomColonne)){
                return i;
            }
        }
        return -1;
    }

    private void afficherRecords(){
        for(int i=0 ; i<recordResultat.size();i++){
            System.out.println(recordResultat.get(i).toString());
        }
        System.out.println("Total records = "+recordResultat.size());
    }

}