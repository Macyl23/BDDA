import java.nio.ByteBuffer;

public class Frame {
    private PageId pageId;
    private int pinCount;
    private boolean flagDirty;
    private ByteBuffer buff;
    private  int ts;
    //constructeur class Frame
    public Frame(){
        pageId= new PageId(-1, 0);
        pinCount=0;
        flagDirty=false;
        buff= ByteBuffer.allocate(DBParams.pageSize);
        ts=-1;
    }
    //retourner les differents attributs
    //retourner pageId
    public PageId getPageId(){
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
    public ByteBuffer getBuff(){
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
    //incrementer du pinCount lors de chaque demande de page dans une frame
    public void decrementerPinCount(){
        pinCount-=1;
    }
    public void incrementerPinCount(){
        pinCount+=1;
    }
    public int getTs(){
        return this.ts;
    }
    public void setTs(int ts){
        this.ts=ts;
    }

    public void incrementTs(){
        ts++;
    }

}
