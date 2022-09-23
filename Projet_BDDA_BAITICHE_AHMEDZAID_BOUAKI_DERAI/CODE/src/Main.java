import java.io.File;

public class Main {
	public static void main(String[] args ) {
        System.out.println("Hello world");
        DBParams.DBPath= new File("/~/BDDA/Projet_BDDA_BAITICHE_AHMEDZAID_BOUAKI_DERAI/DB");
        DBParams.pageSize=4096;
        DBParams.maxPagesPerFile=4;
    }

}
