import java.io.File;

public class DropDB{

    /**
     * execution de la commande DROPDB
     */
    public static void execute(){
        BufferManager.leBufferManager.reinitialiser();
        Catalog.leCatalog.reinitialiser();
        DiskManager.leDiskManager.menageDiscManager();
        supprimerFichiers();

    }
    /**
     * a verifier !!!
     */
    public static void supprimerFichiers(){
        File lesFic = new File(DBParams.DBPath);
        File [] liste = lesFic.listFiles();
        for(int i=0; i<liste.length; i++){
            liste[i].delete();
        }
    }
}