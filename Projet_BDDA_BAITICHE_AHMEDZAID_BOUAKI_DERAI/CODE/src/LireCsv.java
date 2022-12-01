import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
class LireCsv {

    private   File f;
    private ArrayList<String>tableauDeLigne;

    public LireCsv(String nomFichier) throws FileNotFoundException{
        //instanciation de fichiers
        f = new File(nomFichier);
        //instanciation du tableau
        tableauDeLigne = new ArrayList<String>();

    }
    
    
    /**
     * Methode qui nous permet de creer notre fichier ainsi que de lire le fichier
     * cette methode permet aussi d'ajouter a notre tableau de ligne chaque ligne
     * @return Liste des differents records a inserer
     * @throws IOException
     */
    public ArrayList<String> lireFichier() throws IOException {
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