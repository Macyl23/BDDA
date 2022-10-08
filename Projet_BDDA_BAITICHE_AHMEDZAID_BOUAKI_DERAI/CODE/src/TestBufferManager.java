import java.io.IOException;



public class TestBufferManager {

    /* TESTS UNITAIRES */
    public static void testGetPageWhenAllFrameEmpty() throws IOException{
        BufferManager.leBufferManager.init();
        BufferManager.leBufferManager.initBuffPool();
        PageId p = new PageId(0,0);
        BufferManager.leBufferManager.getPage(p);
        System.out.println("Test de buffPoolVide");
        BufferManager.leBufferManager.buffPoolContenu();
    }

    public static void testFreePage() throws IOException{
        PageId p = new PageId(0,0);
        BufferManager.leBufferManager.freePage(p, false);
        System.out.println("Test de freePage");
        BufferManager.leBufferManager.buffPoolContenu();
    }

    public static void testFlushAll() throws IOException{
        BufferManager.leBufferManager.flushAll();

        BufferManager.leBufferManager.buffPoolContenu();
    }


    /*TESTS D'INTEGRATION */
    /*
     * Test qui permet de vérifier si une page existe dans le buffPool
     * En affichant La page existe 
     */
    public static void testGetPageWhenPageAlreadyExists() throws IOException{
        BufferManager.leBufferManager.init();
        BufferManager.leBufferManager.initBuffPool();
        PageId p = new PageId(0,0);
        BufferManager.leBufferManager.getPage(p);
        BufferManager.leBufferManager.getPage(p);
    }
    /*
     * Test qui permet de remplacer une frame dans le buffPool
     * En affichant le contenu du buffPool
     */
    public static void testGetPageWhenAllFramesAreFull() throws IOException{
        BufferManager.leBufferManager.init();
        BufferManager.leBufferManager.initBuffPool();
        PageId p = new PageId(0,0);
        PageId p1 = new PageId(0,1);
        PageId p2 = new PageId(0,2);
        BufferManager.leBufferManager.getPage(p);
        BufferManager.leBufferManager.getPage(p1);
        BufferManager.leBufferManager.freePage(p,false);
        BufferManager.leBufferManager.getPage(p2);
        BufferManager.leBufferManager.buffPoolContenu();

    }
    public static void main(String[] args) throws IOException{
        DBParams.pageSize=4;
        DBParams.DBPath = args[0];
        DBParams.frameCount= 2;

        testGetPageWhenAllFrameEmpty();
        testFreePage();
        System.out.println("Apres le flushAll");
        testFlushAll();
        System.out.println("Test que la page existe dans le buffer\n");
        testGetPageWhenPageAlreadyExists();
        System.out.println("Test de remplacement de page");
        testGetPageWhenAllFramesAreFull();
        

    }
}
