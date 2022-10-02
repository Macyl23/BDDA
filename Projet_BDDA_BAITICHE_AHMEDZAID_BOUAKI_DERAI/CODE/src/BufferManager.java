import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BufferManager{
    private static BufferManager leBufferManager=new BufferManager();
    private Frame[] buffPool;
    private DiskManager discManager;
    private BufferManager(){
        this.buffPool= new Frame[DBParams.frameCount];
        this.discManager=DiskManager.leDiskManager;
    };
    public static BufferManager getLeBufferManager(){
    return leBufferManager;
}
//retourner un buffer associe a une frame 
public ByteBuffer getPage(PageId pageId) throws IOException{
    //verifier si pageId se trouve deja dans le buffPool
    for (int i=0; i<buffPool.length; i++){
        if(buffPool[i].getPageId().equals(pageId)){
            buffPool[i].incrementerPinCount();
            return buffPool[i].getBuff();
        }
        else{
            if(buffPool[i].estVide()){
                buffPool[i].setPageId(pageId);
                buffPool[i].setPinCount(1);
                buffPool[i].setFlagDirty(false);
                discManager.readPage(pageId, buffPool[i].getBuff());
            }
            else{

            

                }

            }
        }

    }
    return null;
}

//actualisation du flag dirty, decrementation du pin_count
public void freePage(PageId pageId, boolean valdirty){
    if (buffPool[i].getPinCount() == 0){
        
        }

}

/*ecriture des pages modifiees sur le disque 
remise a 0 des flags/ informartions et contenus des buffers
*/
public void flushhAll(){}



}