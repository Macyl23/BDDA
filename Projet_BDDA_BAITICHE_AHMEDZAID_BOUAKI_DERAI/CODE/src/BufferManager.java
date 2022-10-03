import java.io.IOException;
import java.nio.ByteBuffer;


public class BufferManager {
    private static BufferManager leBufferManager = new BufferManager();
    private Frame[] buffPool;
    private DiskManager discManager;

    private BufferManager() {
        this.buffPool = new Frame[DBParams.frameCount];
        this.discManager = DiskManager.leDiskManager;
    };

    public static BufferManager getLeBufferManager() {
        return leBufferManager;
    }

    // retourner un buffer associe a une frame
    public ByteBuffer getPage(PageId pageId) throws IOException {
        // verifier si pageId se trouve deja dans le buffPool
        for (int i = 0; i < buffPool.length; i++) {
            if (buffPool[i].getPageId().equals(pageId)) {
                buffPool[i].incrementerPinCount();
                return buffPool[i].getBuff();
            } else {
                if (buffPool[i].estVide()) {
                    buffPool[i].setPageId(pageId);
                    buffPool[i].setPinCount(1);
                    buffPool[i].setFlagDirty(false);
                    discManager.readPage(pageId, buffPool[i].getBuff());
                } else {

                }

            }
        }
        return null;
    }

    /**
     * @param pageId
     * @param valdirty
     * Méthode qui permet de liberer une page 
     * On parcourt le buffPool pour trouver le num de page que nous voulons décharger
     * une fois trouvé on incrémente son Ts et son pinCount
     * On teste le valdirty
     * Si valdirty = true on le met true sinon false
     */
    public void freePage(PageId pageId, boolean valdirty) {
        for(int i=0 ; i<buffPool.length ; i++){
            if(buffPool[i].getPageId().pageIdx == pageId.pageIdx){
                buffPool[i].incrementTs();;
                buffPool[i].incrementerPinCount();
                if(valdirty){
                    buffPool[i].setFlagDirty(true);
                }else{
                    buffPool[i].setFlagDirty(false);
                }
            }
        }
    }

    /*
     * ecriture des pages modifiees sur le disque
     * remise a 0 des flags/ informartions et contenus des buffers
     */
    public void flushhAll() {
    }

}
