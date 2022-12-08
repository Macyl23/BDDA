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
    // signaler qu'on utilise plu itératuer car 
    //y'a plu de record present dans le buffer
    // on libere la page auprès du buffermanager
    public void close() throws IOException{
       boolean valDirty = false;

        if(getNextRecord() == null) {
            // a verifier la partie avec valdirty xD
            valDirty = true;
            ByteBuffer b = BufferManager.leBufferManager.getPage(pageId);
            // on libere la page auprès du buffermanager
            BufferManager.leBufferManager.freePage(pageId,valDirty);
        }
        // message disant qu'on a pas finit ou finis
        else {
            System.out.println("il reste des recors dans la page");
        }

    }
    // remet au debut de la page le curseur de l'iterateur 
    // a verifier
    public void reset(){
       int pos=DBParams.pageSize-8;

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

