public class RecordId {
    private PageId pageId;
    private int soltIdx;

    public RecordId(){

    }

    public RecordId(PageId pageId, int soltIdx){
        this.pageId=pageId;
        this.soltIdx=soltIdx;
    }
}
