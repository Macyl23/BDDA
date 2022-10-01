import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferManager{
    private static BufferManager leBufferManager=new BufferManager();
    private Frame[] buffPool;
    private DiskManager discManager;
    private BufferManager(){
        this.buffPool=new Frame[DBParams.frameCount];
        discManager=DiskManager.leDiskManager;
    };
    public static BufferManager getLeBufferManager(){
    return leBufferManager;
}
//retourner un buffer associe a une frame 
public ByteBuffer GetPage(PageId pageId) throws IOException{
    //verifier si pageId se trouve deja dans le buffPool
    for (int i=0; i<buffPool.length; i++){
        if(buffPool[i].GetPageId().equals(pageId)){
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
    return null;
}

//actualisation du flag dirty, decrementation du pin_count
public void FreePage(PageId pageId, boolean valdirty){}

/*ecriture des pages modifiees sur le disque 
remise a 0 des flags/ informartions et contenus des buffers
*/
public void FlushhAll(){}



}