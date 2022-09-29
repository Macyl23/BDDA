
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class DiskManagerTest {

    /*
     * Test si on renvoie le bon page Id
     * en sachant que f0 existe et qu'il est vide
     * fileIdx = 0 pageIdx = 0
     */
    public static void TestAllocPageUn() throws IOException {
        
        RandomAccessFile file = new RandomAccessFile(DBParams.DBPath + "/f0.bdda", "rw");
        if (DiskManager.leDiskManager.allocPage().fileIdx == 0 && DiskManager.leDiskManager.allocPage().pageIdx == 0) {
            System.out.println("Allocation worked successfullly");
        }
        file.close();
    }

    public static void  TestAllocPageDeux() throws IOException {
        /*
         * Test si on crée un nouveau
         * en sacahnt qu"aucune page n'est disponible
         * Création de f1.bdda
         */
        if (DiskManager.leDiskManager.allocPage().fileIdx == 1 && DiskManager.leDiskManager.allocPage().pageIdx == 0) {
            System.out.println("File created successfully");
        }
    }

    /*
         * On alloue une pageSize au buffer
         * On exécute la fonction avec fileidx=0 et pageIdx=0 
         * On affiche le contenu du tableau et on vérifie qu'il a bien 
         * le meme contenu du fichier
         */
    public static void TestReadPage() throws IOException{
        ByteBuffer buff = ByteBuffer.allocate(4);
        DiskManager.leDiskManager.readPage(new PageId(0, 0), buff);
        System.out.println(Arrays.toString(buff.array()));
    }

    public static void TestGetCurrentCountAllocPages() throws IOException{
        DiskManager.leDiskManager.allocPage();
        // DiskManager.leDiskManager.deAllocPage(new PageId(0,0));
        System.out.println(DiskManager.leDiskManager.count);
    }
    public static void main(String[] args) throws IOException {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4;
        DBParams.maxPagesPerFile = 4;

        /* Pour les tests nous supposons que la taille d'une page s'agit de 4 octets */
        DiskManagerTest.TestAllocPageUn();
        DiskManagerTest.TestAllocPageDeux();
        DiskManagerTest.TestReadPage();
        DiskManagerTest.TestGetCurrentCountAllocPages();
        
        

    }
}
