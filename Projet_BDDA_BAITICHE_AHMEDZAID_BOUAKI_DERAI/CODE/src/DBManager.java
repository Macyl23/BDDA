import java.io.IOException;

public class DBManager {
    
    public DBManager(){}

    public static DBManager leDBManager = new DBManager();

    public void init() throws IOException, ClassNotFoundException{
        Catalog.leCatalog.init();
        BufferManager.leBufferManager.init(); 

    }

    public void finish() throws IOException{
        Catalog.leCatalog.finish();
        BufferManager.leBufferManager.flushAll();
    }

    public void processCommand(String  commande) throws IOException{
        String mots[] = commande.split(" ");
        switch(mots[0]){
            case "CREATE":
                CreateTableCommand c = new CreateTableCommand(commande);
                c.execute();
            break;
            case "DROPDB":
                DropDB.execute();
            break;
            case "INSERT":
                InsertCommand i = new InsertCommand(commande);
                i.execute();
            break;
            case "SELECT":
            break;
        }
    }
}
