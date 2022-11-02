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
        chaineSplit= saisie.split(" ");
        valeurs= chaineSplit[4].split(",");
        this.nomRelation= chaineSplit[2];

        for(String valeur: valeurs ){
            valeursRecords.add(valeur);
        }

    }

    public void execute() throws IOException{
        Record r = new Record(Catalog.leCatalog.getRelationInfo(nomRelation),valeursRecords);
        RecordId rid = FileManager.leFileManager.insertRecordIntoRelation(r);
        r.rid = rid;
    }
}
