import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
    public static BufferManager leBufferManager = new BufferManager();
    private Frame[] buffPool;
    private DiskManager discManager;
    ArrayList<Frame> lru = new ArrayList<Frame>();
    Frame pRemplacee = new Frame();
    private static int tsGlobal=0;

    private BufferManager() {
        
        this.buffPool = new Frame[DBParams.frameCount];       
        this.discManager = DiskManager.leDiskManager;

    }
    

    // public static BufferManager getLeBufferManager() {
    //     return leBufferManager;
    // }

    public void init(){}
    public void initBuffPool(){
        for(int i = 0 ; i<buffPool.length ; i++){
            buffPool[i] = new Frame();
        }
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
                System.out.println("La page existe");
                buffPool[i].incrementerPinCount();
                return buffPool[i].getBuff();
            }
        }
        for (int i = 0; i < buffPool.length; i++) {
            if (buffPool[i].estVide()) {
                buffPool[i].setPageId(pageId);
                buffPool[i].setPinCount(1);
                buffPool[i].setFlagDirty(false);
                discManager.readPage(pageId, buffPool[i].getBuff());
                buffPool[i].toString();
                return buffPool[i].getBuff();
            }
        }
        for (int i = 0; i < buffPool.length; i++) {
            if (buffPool[i].getPinCount() == 0) {
                lru.add(buffPool[i]);
            }
        }
        /*On cherche le plus petit TS
         * On suppose que la premiere case a le plus petit TS 
         * On change dans la boucle si on trouve un plus petit
         */
        int min = lru.get(0).getTs();
        pRemplacee=lru.get(0);
        for (int j = 1; j < lru.size(); j++) {
            if (min > lru.get(j).getTs()) {
                min = lru.get(j).getTs();
                pRemplacee = lru.get(j);
            }
        }
        if (pRemplacee.getFlagDirty() == true) {
            this.discManager.writePage(pRemplacee.getPageId(), pRemplacee.getBuff());
        }
        int i=0;
        boolean trouvee=false;
        while(i<buffPool.length && !trouvee){
            if(buffPool[i].getTs()==pRemplacee.getTs()){
                buffPool[i].setPageId(pageId);
                buffPool[i].setPinCount(1);
                buffPool[i].setFlagDirty(false);
                discManager.readPage(pageId, buffPool[i].getBuff());  
                buffPool[i].toString();
                trouvee=true; 
            }
            i++;
        }
        return buffPool[i-1].getBuff();
    }

    /**
     * @param pageId
     * @param valdirty
     *                 Méthode qui permet de liberer une page
     *                 On parcourt le buffPool pour trouver le num de page que nous
     *                 voulons décharger
     *                 une fois trouvé on incrémente son Ts et son pinCount
     *                 On teste le valdirty
     *                 Si valdirty = true on le met true sinon false
     */
    public void freePage(PageId pageId, boolean valdirty) {
        for(int i=0 ; i<buffPool.length ; i++){
            if(buffPool[i].getPageId().pageIdx == pageId.pageIdx){
                tsGlobal++;
                buffPool[i].setTs(tsGlobal);
                buffPool[i].decrementerPinCount();
                buffPool[i].toString();
                if(valdirty){
                    buffPool[i].setFlagDirty(true);
                } else {
                    buffPool[i].setFlagDirty(false);
                }
            }
        }
    }

    /*
     * ecriture des pages modifiees sur le disque
     * remise a 0 des flags/ informartions et contenus des buffers
     */
    public void flushAll() throws  IOException {
        for (Frame frame : this.buffPool) {
            if (frame.getFlagDirty()) {
                this.discManager.writePage(frame.getPageId(), frame.getBuff());
            }
            frame.setPageId(new PageId(0, -1));
            frame.setPinCount(0);
            frame.setFlagDirty(false); 
        }

    }

    public void buffPoolContenu(){
        System.out.println("Contenu du buffer manager");
        for(int i= 0 ; i<buffPool.length; i++){
            if(buffPool[i].getPageId().pageIdx == -1){
                System.out.println("Case vide\n");
            }else{
                System.out.println(buffPool[i]+"\n");
            }
        }
    }

    
}
