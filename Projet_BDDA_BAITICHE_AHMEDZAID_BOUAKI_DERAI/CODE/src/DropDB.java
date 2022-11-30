import java.io.File;

public class DropDB{

    /**
     * Reinitialise le bufferManager
     * Supprime tout ce qui a dans le Catalog
     * Réinitialise le disk Manager
     * SUpprime les fichiers dans le dossier DB
     */
    public static void execute(){
        BufferManager.leBufferManager.reinitialiser();
        Catalog.leCatalog.reinitialiser();
        DiskManager.leDiskManager.menageDiscManager();
        supprimerFichiers();

    }
    /**
     * Supprime les fichiers dans le dossier DB
     */
    public static void supprimerFichiers(){
        File lesFic = new File(DBParams.DBPath);
        File [] liste = lesFic.listFiles();
        for(int i=0; i<liste.length; i++){
            liste[i].delete();
        }
    }
}