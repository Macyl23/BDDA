import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManager  {
    // CONSTRUCTEUR FileManager PRIVATE 
   private FileManager() {} 
   // instance de ma classe
   public static FileManager leFileManager = new FileManager();
    //    • L’allocation d’une nouvelle page via AllocPage du DiskManager.
    //C’est le PageId rendu par AllocPage qui devra aussi être rendu par createNewHeaderPage
   public  PageId createNewHeaderPage() throws IOException {
        ByteBuffer buffer  = ByteBuffer.allocate(4);
        buffer.putInt(0);
        PageId pageIdFile = DiskManager.leDiskManager.allocPage();
        BufferManager.leBufferManager.getPage(pageIdFile);
        DiskManager.leDiskManager.writePage(pageIdFile,buffer);
        BufferManager.leBufferManager.freePage(pageIdFile,true);
        return pageIdFile;
   }
}
