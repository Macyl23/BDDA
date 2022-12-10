import java.io.IOException;

public class DBManager {
    
    public DBManager(){}

    public static DBManager leDBManager = new DBManager();


    /**
     * Méthode qui lance la desérialisation du Catalog 
     */
    public void init(){
        try{
            Catalog.leCatalog.init();
            BufferManager.leBufferManager.initBuffPool(); 
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Lance la sérialisation du Catalog 
     * Fait le nettoyage du bufferManager en appelant flushAll
     */
    public void finish(){
        try{
            Catalog.leCatalog.finish();
            BufferManager.leBufferManager.flushAll();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void processCommand(String  commande){
        String mots[] = commande.split(" ");

        try{
            switch(mots[0]){
                case "CREATE":
                    CreateTableCommand c = new CreateTableCommand(commande);
                    c.execute();
                break;
                case "DROPDB":
                    DropDB.execute();
                break;
                case "INSERT":
                    if(commande.contains("FILECONTENTS")){
                        InsertionFile insert= new InsertionFile(commande);
                        insert.insererFichier();
                    }else{
                        InsertCommand i = new InsertCommand(commande);
                        i.execute();
                    }
                break;
                case "SELECT":
                    if(mots[3].length() == 1){
                        SelectCommand s = new SelectCommand(commande);
                        s.execute();
                    }else{
                        SelectJoinCommand sj = new SelectJoinCommand(commande);
                        sj.execute();
                    }
                break;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
