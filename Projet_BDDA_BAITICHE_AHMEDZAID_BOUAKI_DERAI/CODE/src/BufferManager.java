import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferManager{
    static BufferManager leBufferManager=new BufferManager();

    private BufferManager(){};
    public static BufferManager getLeBufferManager(){
    return leBufferManager;
}
//retourner un buffer associe a une frame 
public ByteBuffer GetPage(PageId pageId){
    return null;}

//actualisation du flag dirty, decrementation du pin_count
public void FreePage(PageId pageId, boolean valdirty){}

/*ecriture des pages modifiees sur le disque 
remise a 0 des flags/ informartions et contenus des buffers
*/
public void FlushhAll(){}



}