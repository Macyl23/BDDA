
public class PageId {
    public int fileIdx;
    public int pageIdx;

    public PageId(){}
    public PageId(int fIdx, int pIdx){
        this.fileIdx=fIdx;
        this.pageIdx=pIdx;
    }

    public String toString(){
        return pageIdx + " dans le fichier "+ fileIdx;
    }
}
