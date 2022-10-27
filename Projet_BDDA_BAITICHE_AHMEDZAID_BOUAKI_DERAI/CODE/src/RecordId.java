public class RecordId {
    private PageId pageId;
    private int slotIdx;

    public RecordId(PageId pid, int slot){
        this.pageId=pid;
        this.slotIdx=slot;
    }
    public PageId getPageId() {
        return pageId;
    }
    public void setPageId(PageId pageId) {
        this.pageId = pageId;
    }
    public int getSlotIdx() {
        return slotIdx;
    }
    public void setSlotIdx(int slotIdx) {
        this.slotIdx = slotIdx;
    }

    
}
