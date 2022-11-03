import java.io.IOException;

public class DBManager {
    
    public DBManager(){}

    public static DBManager leDBManager = new DBManager();

    public void init() throws IOException, ClassNotFoundException{
        Catalog.leCatalog.init();

        //La methode init()  dans BufferManager est vide pour l'instant
        BufferManager.leBufferManager.init(); 

        //DiskManager n'a pas de methode init()
    }

    public void finish() throws IOException{
        Catalog.leCatalog.finish();
        BufferManager.leBufferManager.flushAll();

        //DiskManager n'a pas de methode finish()
    }

    public void processCommand(String  commande){
        //Pour l'instant, vide
        switch(commande){
            case "CREATE":
            break;
            case "DROPDB":
            break;
            case "INSERT":
            break;
            case "SELECT":
            break;
        }
    }
}
