
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args ) throws IOException, ClassNotFoundException {   
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.frameCount=2;
        DBParams.maxPagesPerFile=4;
        
        DBManager.leDBManager.init();
        
        Scanner sc= new Scanner(System.in);
        String cmnd;
        do{
            System.out.println("Entrez une commande");
            cmnd= sc.nextLine();
            switch (cmnd){
                case "EXIT": DBManager.leDBManager.finish();
                break;
                default: DBManager.leDBManager.processCommand(cmnd);
                break;
            }
        }while (!cmnd.equals("EXIT"));
        DBManager.leDBManager.finish();


        sc.close();



    }

}
