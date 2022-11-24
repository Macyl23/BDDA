import java.io.IOException;
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
            for(int i=0; i<commande[5].length(); i++){

            }
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


    
}