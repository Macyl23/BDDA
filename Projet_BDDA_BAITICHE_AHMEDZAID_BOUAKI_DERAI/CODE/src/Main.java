
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args ) throws IOException, ClassNotFoundException {   
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4;
        DBParams.frameCount=2;
        
        DBManager.leDBManager.init();
        
        Scanner sc= new Scanner(System.in);
        String [] mots;
        do{
            System.out.println("Entrez une commande");
            String cmnd= sc.nextLine();
            mots= cmnd.split(" ");
            switch (mots[0]){
                case "EXIT": DBManager.leDBManager.finish();
                break;
                default: DBManager.leDBManager.processCommand(mots[0]);
                break;
            }
        }while (mots[0].compareTo("EXIT") != 0);


        sc.close();



    }

}
