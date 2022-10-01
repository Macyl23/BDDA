import java.nio.ByteBuffer;

public class Frame {
    private PageId pageId;
    private int pinCount;
    private boolean flagDirty;
    private ByteBuffer buff;
    
    //constructeur class Frame
    public Frame(){
        pageId= new PageId(-1, 0);
        pinCount=0;
        flagDirty=false;
        buff= ByteBuffer.allocate(DBParams.pageSize);
    }
    //retourner les differents attributs
    //retourner pageId
    public PageId GetPageId(){
        return this.pageId;
    }
    //retourner le pinCount
    public int getPinCount(){
        return this.pinCount;
    }
    //retourner le flagDirty
    public boolean getFlagDirty(){
        return this.flagDirty;
    }
    //retourner le buffer
    public ByteBuffer buff(){
        return this.buff;
    }
    //methodes qui permettent de modifier les differents attribut de la classe
    //setter de pageId
    public void setPageId(PageId pId){
        this.pageId=pId;
    }
    //setter de pinCount
    public void setPinCount (int pc){
        this.pinCount=pc;
    }
    //setter de flagDirty
    public void setFlagDirty(boolean fDirty){
        this.flagDirty=fDirty;
    }
    //seter du buffer 
    public void setBuff(ByteBuffer buffer){
        buff=buffer;
    }

    //verifier si une case est libre
    public boolean estVide(){
        if(pageId.pageIdx==-1){
            return false;
        }
        return true;
    }
}
