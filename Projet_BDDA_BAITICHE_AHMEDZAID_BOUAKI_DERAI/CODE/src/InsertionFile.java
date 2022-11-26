import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InsertionFile {

    private String nomRelation;
    private ArrayList<String>valeurRecords;
    private String nomFichier;
    


    public InsertionFile(String cmd){
        recupererInfos(cmd);
        this.valeurRecords = new ArrayList<>();
    }

    public void insererFichier(){
        remplirValeurRecords();
        remplirCommande();
    }

    private void remplirValeurRecords(){
        LireCsv lc;
        try {
            lc = new LireCsv(nomFichier);
            valeurRecords = new ArrayList<>();
            try {
                valeurRecords =  lc.lireFichier();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void remplirCommande(){
        for(String value : valeurRecords){
            String chaine = "INSERT INTO " + this.nomRelation + " VALUES(";
            String saisie = chaine + value+")";
            try {
                InsertCommand is = new InsertCommand(saisie);
                is.execute();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void recupererInfos(String cmd){
        String[] cmdSplit = cmd.split(" ");
        this.nomRelation = cmdSplit[2];
        String[] fileSplit = cmdSplit[3].split("\\(");
        String nameFile = fileSplit[1].substring(0,fileSplit[1].length()-1);
        this.nomFichier = nameFile;
    }
    public void afficheTab(){
        for(int i = 0; i < valeurRecords.size(); i ++){
            System.out.println(valeurRecords.get(i));
        }
    }

    public String toString(){
        return this.nomRelation+" "+this.nomFichier;
    }


}
