
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {
	public static void main(String[] args ) throws IOException {   
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4;
        DBParams.frameCount=2;
        
        DBManager.leDBManager.init();
        
        ArrayList<String> commandes= new ArrayList <String> ("EXIT", "CREATE TABLE NomRelation (NomCol_1:TypeCol_1, NomCol_2:TypeCol_2,...)", "DROPDB", "INSERT INTO nomRelation VALUES (val1, val2,...)", "SELECT * FROM nomRelation WHERE nomColonne1OPvaleur1 AND nomColonne2OPvaleur2 AND...");
        Scanner sc= new Scanner(System.in);
        do{
            System.out.println("Entrez une commande");
            String cmnd= sc.nextLine();
            String []mots= cmd.split(" ");
            switch (mots[0]){
                case "EXIT": DBManager.leDBManager.finish();
                break;
                default: DBManager.leDBManager.processCommand(cmnd);
                break;
            }
        }while (cmnd[0].compareTo("EXIT") != 0);


        in.close();



    }

}
