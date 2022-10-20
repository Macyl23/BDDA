import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManager  {

    // CONSTRUCTEUR FileManager PRIVATE 
   private FileManager() {} 
   // instance de ma classe
   public static FileManager leFileManager = new FileManager();

    //    • L’allocation d’une nouvelle page via AllocPage du DiskManager.
    //C’est le PageId rendu par AllocPage qui devra aussi être rendu par createNewHeaderPage
   public  PageId createNewHeaderPage() throws IOException {
        
        
        PageId pageIdFile = DiskManager.leDiskManager.allocPage();
        ByteBuffer b = BufferManager.leBufferManager.getPage(pageIdFile);
        DiskManager.leDiskManager.writePage(pageIdFile,b.putInt(0));
        BufferManager.leBufferManager.freePage(pageIdFile,true);
        return pageIdFile;
   }

   public PageId addDataPage(RelationInfo r){
     return null;
   }

   public PageId getFreeDataPageId(RelationInfo r, int sizeRecord){
     return null;
   }

   public RecordId writeRecordToDataPage(Record r, PageId pid){
     return null;
   }

   public ArrayList<Record> getRecordsInDataPage(RelationInfo r , PageId pid){
     return null;
   }

   public ArrayList<PageId> getAllDataPage(RelationInfo r){
     return null;
   }


   public RecordId insertRecordIntoRelation(Record r){
     int tailleOffsetDirectory = r.getWrittenSize()+(r.getRelInfo().getNbColonnes()*4+4);
     return  writeRecordToDataPage(r, getFreeDataPageId(r.getRelInfo(), tailleOffsetDirectory ));
   }
}
