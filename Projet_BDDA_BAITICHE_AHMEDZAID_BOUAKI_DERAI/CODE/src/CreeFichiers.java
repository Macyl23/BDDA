import java.io.File;
import java.io.IOException;

public class 	CreeFichiers {
    public static void main( String[] args ){ 
     try {
       
       File file = new File("C:\\\\Users\\\\204BO\\\\OneDrive\\\\Bureau\\\\BDDA\\\\Projet_BDDA_BAITICHE_AHMEDZAID_BOUAKI_DERAI\\\\DB");
       
       if (file.createNewFile()){
         System.out.println("Fichier cr�e dans DB!");
       }else{
         System.out.println("Fichier existe d�j� dans Db.");
       }
       
     } catch (IOException e) {
       e.printStackTrace();
 }
    }
}