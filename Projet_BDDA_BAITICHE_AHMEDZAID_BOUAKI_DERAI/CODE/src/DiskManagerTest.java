
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManagerTest {

    public static void main(String[] args) throws IOException {

        /* Pour les tests nous supposons que la taille d'une page s'agit de 4 octets */
        DiskManager dm = new DiskManager();
        DBParams.pageSize = 4;
        DBParams.maxPagesPerFile = 4;

        /*
         * Test si on renvoie le bon page Id
         * en sachant que f0 existe et qu'il est vide
         * fileIdx = 0 pageIdx = 0
         */

        RandomAccessFile file = new RandomAccessFile(args[0] + "/f0.bdda", "rw");
        if (dm.allocPage().fileIdx == 0 && dm.allocPage().pageIdx == 0) {
            System.out.println("Allocation worked successfullly");
        }

        /*
         * Test si on crée un nouveau 
         * en sacahnt qu"aucune page n'est disponible
         * Création de f1.bdda
         */
        if(dm.allocPage().fileIdx==1 && dm.allocPage().pageIdx==0){
            System.out.println("File created successfully");
        }
        file.close();
    }
}
