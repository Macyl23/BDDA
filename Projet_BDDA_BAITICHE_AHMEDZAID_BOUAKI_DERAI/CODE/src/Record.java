import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {
    private RelationInfo relInfo;
    public ArrayList<String> values;
    public static int sizeValeur;

    public Record(RelationInfo relation) {
        this.relInfo = relation;
        this.values = new ArrayList<>();
    }
    
    public Record(RelationInfo relation, ArrayList<String> values){
        this.relInfo=relation;
        this.values=values;
    }

    public RelationInfo getRelInfo() {
        return relInfo;
    }

    public void setRelInfo(RelationInfo relInfo) {
        this.relInfo = relInfo;
    }

    /**
     * Méthode qui permet d'écrire un record dans le buffer
     * 
     * @param buff
     * @param pos
     *             On position le curseur du buffer
     *             On parcourt la liste de values
     *             On convertit la valeur de chaque type et on écrit
     *             Sinon si une on trouve un String on écrit char par char
     */
    public void writeToBuffer(ByteBuffer buff, int pos) {
        String type;
        int tempInt;
        float tempFloat;
        int  k = 0;
        int debutValeurs= (1+relInfo.getNbColonnes()) * 4;
        int tailleValeurs=0;
        for (int i = 0; i < values.size() && k <= relInfo.getNbColonnes() * 4; i++, k += 4) {
            type = relInfo.getInfoColonne().get(i).getType();
            buff.putInt(pos+k,(1+relInfo.getNbColonnes()) * 4  + tailleValeurs);
            switch (type) {
                case "INTEGER":
                    tempInt = Integer.parseInt(values.get(i));
                    buff.putInt(pos+debutValeurs+tailleValeurs,tempInt);
                    tailleValeurs+=4;
                    sizeValeur+=4;
                    break;

                case "REAL":
                    tempFloat = Float.parseFloat(values.get(i));
                    buff.putFloat(pos+debutValeurs+tailleValeurs,tempFloat);
                    tailleValeurs+=4;
                    sizeValeur+=4;
                    break;
                default:
                    for (int j = 0, h=debutValeurs+tailleValeurs;   j < values.get(i).length() ;h+=2, j++) {
                        buff.putChar(pos+h,values.get(i).charAt(j));
                    }
                    tailleValeurs += 2 * values.get(i).length();
                    sizeValeur+=2 * values.get(i).length();
                    break;
            }
            

        }
        
        buff.putInt(pos+k,(1+relInfo.getNbColonnes()) * 4  + sizeValeur);
        sizeValeur += (relInfo.getNbColonnes()+1)*4;
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        String type;
        int tempInt=0, tailleChaine, i, j,k;
        float tempFloat;
        char[] tempVarchar;
        String chaine;
        int debutValeurs= (1+relInfo.getNbColonnes()) * 4;
        int posLecture = 0;
        values.clear();
        // switch
        for (k = 0; k < relInfo.getNbColonnes(); k++) {
            type = relInfo.getInfoColonne().get(k).getType();
            posLecture = pos+debutValeurs;
            switch (type) {
                case "INTEGER":
                    tempInt = buff.getInt(posLecture);
                    values.add(String.valueOf(tempInt));
                    debutValeurs+=4;
                    break;

                case "REAL":
                    
                    tempFloat = buff.getFloat(pos+debutValeurs);
                    values.add(String.valueOf(tempFloat));
                    debutValeurs+=4;
                    break;

                default:
                    tailleChaine = buff.getInt(pos+(k+1)*4) - buff.getInt(pos+k*4);
                    tempVarchar = new char[tailleChaine/2];
                    for (i = pos+debutValeurs, j = 0; i < pos+debutValeurs+ tailleChaine ; i+=2, j++) {
                        // System.out.println(tempVarchar);
                        tempVarchar[j] = buff.getChar(i);
                    }
                    chaine = new String(tempVarchar);
                    values.add(chaine);
                    debutValeurs+=tailleChaine;

                    break;
            }


        }

    }


    public int getWrittenSize(){
        return sizeValeur;
    }

    public int recordSizeFromValues(){
        String type;
        int tailleChaine, writtenSize=0;
        for(int i=0 ; i<values.size() ; i++){
            type = relInfo.getInfoColonne().get(i).getType();
            switch (type) {
                case "INTEGER":
                    writtenSize += 4;
                    break;
                case "REAL":
                    writtenSize += 4;
                    break;
                default:
                    tailleChaine = values.get(i).length();
                    writtenSize +=  tailleChaine*2;
                    break;

            }
        }
        return writtenSize;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<values.size();i++){
            sb.append(values.get(i)+" ");
        }
        return sb.toString();
        
    }
    

}
