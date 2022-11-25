import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
class InsertionFile {
    private String nomRelation;
    //tableau de String qui sert a enregistrer les lignes de Fichier
    private ArrayList<String>valeurRecords;
    public InsertionFile(String fichier){
        //variable ligne on obtient a l'apelle de la methode lireFichier()
      System.out.println("nous somme dans le constructeur" );
      //initialisation de l'objet qui sert à lire le fichier
        LireCsv lc;
        // LECURE DU FICHIER POUR RECUPERER ligne par ligne les values
        try {
            lc = new LireCsv(fichier);
            valeurRecords = new ArrayList<>();
            try {
                valeurRecords =  lc.lireFichier();
            } catch (IOException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //initialisation ma variable saisie pour instancier la class InsertCommand
        String chaine = "INSERT INTO " + this.nomRelation + "VALUES(";
        String saisie = chaine + valeurRecords+")";
        //instanciation de la commande insert
        try {
            InsertCommand is = new InsertCommand(saisie);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // affihce pour le main pour tester ma classe Insertion par lot
    public void afficheTab(){
        System.out.println("cc methode recup");
        for(int i = 0; i < valeurRecords.size(); i ++){
            System.out.println(valeurRecords.get(i));
        }
    }

public static void main(String[] args) {
    
        System.out.println("nomFichier : " + args[0]);
        InsertionFile ip = new InsertionFile(args[0]);
        ip.afficheTab();
    }
}
