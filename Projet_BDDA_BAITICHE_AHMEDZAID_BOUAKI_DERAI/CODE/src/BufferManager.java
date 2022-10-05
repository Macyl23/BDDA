import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
    private static BufferManager leBufferManager = new BufferManager();
    private Frame[] buffPool;
    private DiskManager discManager;
    ArrayList<Frame> lru= new ArrayList<Frame>();
    Frame pRemplacee=null;


    private BufferManager() {
        this.buffPool = new Frame[DBParams.frameCount];
        this.discManager = DiskManager.leDiskManager;
    };

    public static BufferManager getLeBufferManager() {
        return leBufferManager;
    }

    /*
     * @param pageId
     * methode qui permet de chercher une page dans le bufferPool
     * cete methode implemente la politique de LRU: least recently used 
     * on cherche dans le buffer si la page deja existe 
     * si non on cherche une case vide 
     * si non on remplace en suivant la politique LRU
     */
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
               
                    if(buffPool[i].getPinCount()==0){
                        lru.add(buffPool[i]);
                    }
                    int min=lru.get(0).getTs();
                    for(int j=0; j<lru.size(); j++){
                        if(min>lru.get(j).getTs()){
                        min=lru.get(j).getTs();
                        pRemplacee.setBuff(lru.get(j).getBuff());
                        }
                    }
                        freePage(pRemplacee.getPageId(), pRemplacee.getFlagDirty());
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
    public void flushAll() throws FileNotFoundException, IOException {
        for(Frame frame: this.buffPool){
            if(frame.getFlagDirty()){
                this.discManager.writePage(frame.getPageId(), frame.getBuff());
            }
            frame.setPageId(new PageId(-1,0));
            frame.setPinCount(0);
            frame.setFlagDirty(false);    
        }

    }

}

