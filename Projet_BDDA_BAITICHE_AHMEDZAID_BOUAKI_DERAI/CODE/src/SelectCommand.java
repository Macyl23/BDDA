import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class SelectCommand{


    public  String[] commande;
    public  ArrayList<Record> records;
    public  String nomRelation;
    public SelectCommand(String saisie){
        records=new ArrayList<>();
        parse(saisie);
    }

    private void parse(String cmd){
        commande = cmd.split(" ");
        this.nomRelation=commande[3];
        
    }
    public  void execute() throws IOException{
        if(commande.length > 5){
            /* Des critères ont été entrées  */
            ArrayList<SelectCondition> criteres = listeCriteres();
            
        }else{
            records=FileManager.leFileManager.getAllRecords(Catalog.leCatalog.getRelationInfo(nomRelation));
            afficherRecords();
        }
    }

    public void afficherRecords(){
        for(int i=0 ; i<records.size();i++){
            System.out.println(records.get(i).toString());
        }
    }

    
    private ArrayList<SelectCondition> listeCriteres(){
        String[] criteres = commande[5].split("AND");
            ArrayList<SelectCondition>  sc = new ArrayList<>();
            for(int i=0 ; i< criteres.length ; i++){
                sc.add(parseCmd(criteres[i]));
            }
            return sc;
    }
    private SelectCondition parseCmd(String str){
        int indice=0; 
        String op="";
        String val="";
        for(int i=0 ; i< SelectCondition.getOperateur().length;i++){
            if(str.contains(SelectCondition.getOperateur()[i])){
                String[] condition = str.split(SelectCondition.getOperateur()[i]);
                indice = getIndexColumn(nomRelation, condition[0]); 
                op = SelectCondition.getOperateur()[i];
                val = condition[1];
            }
        }
        return new SelectCondition(indice,op , val);
    }

    private int getIndexColumn(String str,String nomColonne){
        RelationInfo r = Catalog.leCatalog.getRelationInfo(str);
        for(int i=0 ; i<r.getNbColonnes() ; i++){
            if(r.getInfoColonne().get(i).getNom().equals(nomColonne)){
                return i;
            }
        }
        return -1;
    }

    private boolean testCondition(SelectCondition sc,String valeurRecord){
        RelationInfo r = Catalog.leCatalog.getRelationInfo(nomRelation);
        String type = r.getInfoColonne().get(sc.getIndiceColonne()).getType();

        switch(type){
            case "INTEGER":
                return sc.verifConditionNombre(Float.parseFloat(valeurRecord), Float.parseFloat(sc.getValeurComparaison()),sc.getOp());
            case "REAL":
                return sc.verifConditionNombre(Float.parseFloat(valeurRecord), Float.parseFloat(sc.getValeurComparaison()),sc.getOp());
            default:
                return sc.verifConditionString(valeurRecord, valeurRecord, type);
        }

    }
    
    // private ArrayList<Record> getFilteredRecordsInDataPage(RelationInfo r,ArrayList<SelectCondition> sc) throws IOException{
    //     ArrayList<PageId> allDataPage = FileManager.leFileManager.getAllDataPage(r);
    //     ArrayList<Record> records = new ArrayList<>();
    //     for(int i=0 ; i<allDataPage.size() ; i++){
    //         ByteBuffer bufferDataPage = BufferManager.leBufferManager.getPage(allDataPage.get(i));
    //         int nbSlots = bufferDataPage.getInt(DBParams.pageSize-8);
    //         for(int j=0 ; j<nbSlots ; j++){
    //             for(int k=0 ; k<sc.size() ; k++){
    //                 int posDebutValeur = bufferDataPage.getInt(4*sc.get(k).getIndiceColonne());
    //                 int valeur = 
    //                 if(testCondition(sc.get(k), nomRelation)){
    //                     records.add(null)
    //                 }
    //             }
    //         }
    //     }
    // }
}
