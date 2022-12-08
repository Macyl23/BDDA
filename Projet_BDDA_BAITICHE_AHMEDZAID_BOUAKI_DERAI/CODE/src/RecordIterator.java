import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class RecordIterator {
    private RelationInfo relInfo;
    private PageId pageId;
    private static int pos=DBParams.pageSize-8;
    private static int nbRecordsLus=0;

    public RecordIterator(RelationInfo relInfo,PageId pageId){
        this.relInfo = relInfo;
        this.pageId = pageId;

    }
    public void close(){

    }
    public void reset(){

    }
    public Record getNextRecord() throws IOException {
        ByteBuffer b =  BufferManager.leBufferManager.getPage(pageId);
        Record record = new Record(relInfo);
        int nbSlot = b.getInt(DBParams.pageSize-4);
        if(nbRecordsLus<nbSlot){
            record.readFromBuffer(b,pos);
            nbRecordsLus++;
            pos -= 8; 
            return record;
        }
        return null;
    }
}

