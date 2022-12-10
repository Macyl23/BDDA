import java.io.IOException;
import java.nio.ByteBuffer;

public class RecordIterator {
    //attributs
    private RelationInfo relInfo;
    private PageId pageId;
    private int pos;
    private int nbRecordsLus;

    public RecordIterator(RelationInfo relInfo,PageId pageId){
        this.relInfo = relInfo;
        this.pageId = pageId;
        pos=DBParams.pageSize-16;
        nbRecordsLus=0;

    }
    // METHODE 
    
    // signaler qu'on utilise plu itératuer car 
    //y'a plu de record present dans le buffer
    // on libere la page auprès du buffermanager
    public void close() throws IOException{
        BufferManager.leBufferManager.freePage(pageId,false);
    }
    // remet au debut de la page le curseur de l'iterateur 
    // a verifier
    public void reset(){
        pos=DBParams.pageSize-16;
        nbRecordsLus=0;
    }
    public Record getNextRecord() throws IOException {
        ByteBuffer b =  BufferManager.leBufferManager.getPage(pageId);
        Record record = new Record(relInfo);
        int nbSlot = b.getInt(DBParams.pageSize-8);
        if(nbRecordsLus<nbSlot){
            record.readFromBuffer(b,b.getInt(pos));
            nbRecordsLus++;
            pos -= 8; 
            return record;
        }
        return null;
    }
}

