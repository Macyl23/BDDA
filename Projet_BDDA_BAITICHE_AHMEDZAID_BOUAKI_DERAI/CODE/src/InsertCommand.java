import java.io.IOException;
import java.util.ArrayList;


public class InsertCommand {
    
    private String nomRelation;
    private ArrayList<String> valeursRecords;

    public InsertCommand(String saisie) throws IOException{
        this.valeursRecords = new ArrayList<>();
        parse(saisie);
    }

    private void parse(String saisie) throws IOException {
        String [] chaineSplit;
        String [] valeurs;
        String chaineTemp;
        chaineSplit= saisie.split(" ");
        chaineTemp = chaineSplit[4].substring(1,chaineSplit[4].length()-1);
        valeurs= chaineTemp.split(",");
        this.nomRelation= chaineSplit[2];

        for(String valeur: valeurs ){
            valeursRecords.add(valeur);
        }

    }

    public void execute() throws IOException{
        Record r = new Record(Catalog.leCatalog.getRelationInfo(nomRelation),valeursRecords);
        r.rid = FileManager.leFileManager.insertRecordIntoRelation(r);
        viderValeursRecords();

    }

    private void viderValeursRecords(){
        for(int i=0 ; i<valeursRecords.size() ; i++){
            valeursRecords.remove(i);
        }
    }
}
