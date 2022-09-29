import java.io.IOException;

public class BufferManager{
    static BufferManager leBufferManager=new BufferManager();

    private BufferManager(){};
    public static BufferManager getLeBufferManager(){
    return leBufferManager;
}


}