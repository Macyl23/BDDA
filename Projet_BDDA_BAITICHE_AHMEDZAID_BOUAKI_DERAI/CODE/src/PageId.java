import java.io.Serializable;

public class PageId implements  Serializable {
    private static final long serialVersionUID = 1234L;

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
