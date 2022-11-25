import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
class LireCsv {
    //attributs
    private   File f;
    //tableau ou on enregistre chaque ligne delivrer par la methode readLine()
    private ArrayList<String>tableauDeLigne;
    // constructeur
    public LireCsv(String nomFichier) throws FileNotFoundException{
        //instanciation de fichiers
        f = new File(nomFichier);
        //instanciation du tableau
        tableauDeLigne = new ArrayList<String>();

    }
    //methode qui nosu permet de creer notre fichier ainsi que de lire le fichier
    // cette methode permet aussi d'ajouter a notre tableau de ligne chaque ligne
    public ArrayList<String> lireFichier() throws IOException {
        System.out.println("nom fichier dans la methode lire :" + this.f);
        FileReader fR = new FileReader(f);
        BufferedReader bR = new BufferedReader(fR);
        String line = "";

        while((line = bR.readLine()) != null) {
            tableauDeLigne.add(line);  
        }
        bR.close();
        return tableauDeLigne;
    }
    
    // method qui
    public void afficheTab(){
        for(int i = 0 ; i < tableauDeLigne.size();i++) {
            System.out.println("ligne "+ i+1 + " : "+ tableauDeLigne.get(i));
        }
    }
}