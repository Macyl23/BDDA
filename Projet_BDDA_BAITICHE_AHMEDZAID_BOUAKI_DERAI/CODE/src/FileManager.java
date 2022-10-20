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
// Cette méthode doit retourner, pour la relation désignée par relInfo, le PageId d’une page de données
//sur laquelle il reste assez de place pour insérer le record ; si une telle page n’existe pas, la méthode
//retournera null.
   public PageId getFreeDataPageId(RelationInfo r, int sizeRecord) throws IOException{
    // recupere byteBuffer de la relation 
    PageId pageId= new PageId();
    int j = 0;
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(r.getHeaderPageId());
    // verifier que le ByteBuffer contienw²t page  tel que sizeRecord correspondant à la taille du record à insérer de la page
    for(int i = 12 ; i < bufferHeaderPage.capacity() ; i += 12) {
      if(bufferHeaderPage.getInt(i) >= sizeRecord) {
      // return la page trouver qui assez de place tel que sizeRecord correspondant à la taille du record à insérer.
        j = i-8 ;
        pageId.pageIdx = bufferHeaderPage.getInt(j);
        j = i + 4 ;
        pageId.fileIdx = bufferHeaderPage.getInt(j);
        return pageId;
      }
     
    }
    return null;
   }
    /* Cette méthode doit écrire l’enregistrement record dans la page de données identifiée par pageId, et
    renvoyer son RecordId ! Utilisez une des méthodes du TP4 pour écrire le Record.

    IMPORTANT : Nous supposons que la page dispose d’assez d’espace disponible pour l’insertion
    (pas besoin de vérifier!)

    Une fois la page de données actualisée, n’oubliez pas de la libérer auprès du BufferManager et
    d’actualiser ségalement la HeaderPage de la relation, car l’espace disponible a changé !
    Comment trouve-t-on de quelle relation il s’agit 
   */
   public RecordId writeRecordToDataPage(Record r, PageId pid) throws IOException{
     RecordId rid = new RecordId();
     // ecrire record dans la pageId
     //écrire l’enregistrement record dans la page de données identifiée par pageId
     ByteBuffer buffer = BufferManager.leBufferManager.getPage(pid);
     ByteBuffer bufferPid = BufferManager.leBufferManager.getPage(r.getRelInfo().getHeaderPageId());
     // utilisation de writtensize afin d'avoir la size anisi que writte buffer afin d'ecrire dans le record
    return rid;
   }

   public ArrayList<Record> getRecordsInDataPage(RelationInfo r , PageId pid){
     return null;
   }

   public ArrayList<PageId> getAllDataPage(RelationInfo r){
     return null;
   }


   public RecordId insertRecordIntoRelation(Record r) throws IOException{
     int tailleOffsetDirectory = r.getWrittenSize()+(r.getRelInfo().getNbColonnes()*4+4);
     return  writeRecordToDataPage(r, getFreeDataPageId(r.getRelInfo(), tailleOffsetDirectory ));
   }
}
