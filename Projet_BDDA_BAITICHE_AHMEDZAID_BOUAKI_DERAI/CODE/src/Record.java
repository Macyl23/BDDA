import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {
    private RelationInfo relInfo;
    public ArrayList<String> values;
    public int sizeValeur;
    public RecordId rid;

    public Record(RelationInfo relation) {
        this.relInfo = relation;
        values = new ArrayList<>();
        sizeValeur=0;
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

        for (int i = 0; i < values.size() && k <= relInfo.getNbColonnes() * 4; i++, k += 4) {
            type = relInfo.getInfoColonne().get(i).getType();
            buff.position(k);
            buff.putInt((1+relInfo.getNbColonnes()) * 4  + sizeValeur);
            buff.position(pos + sizeValeur);
            switch (type) {
                case "INTEGER":
                    tempInt = Integer.parseInt(values.get(i));
                    buff.putInt(tempInt);
                    sizeValeur += 4;
                    break;

                case "REAL":
                    tempFloat = Float.parseFloat(values.get(i));
                    buff.putFloat(tempFloat);
                    sizeValeur += 4;
                    break;
                default:
                    for (int j = 0;   j < values.get(i).length() ; j++) {
                        buff.putChar(values.get(i).charAt(j));
                    }
                    sizeValeur += 2 * values.get(i).length();
                    break;
            }

        }
        buff.position(k);
        buff.putInt((1+relInfo.getNbColonnes()) * 4  + sizeValeur);
        sizeValeur += (relInfo.getNbColonnes()+1)*4;
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        String type;
        int tempInt, tailleChaine, i, j,k;
        float tempFloat;
        char[] tempVarchar;
        String chaine;
        values.clear();
        // switch
        for (k = 0; k < relInfo.getNbColonnes(); k++) {
            type = relInfo.getInfoColonne().get(k).getType();
            buff.position(pos+recordSizeFromValues());
            switch (type) {
                case "INTEGER":
                    tempInt = buff.getInt();
                    values.add(String.valueOf(tempInt));
                    break;

                case "REAL":
                    tempFloat = buff.getFloat();
                    values.add(String.valueOf(tempFloat));
                    break;

                default:
                    tailleChaine = buff.getInt((k+1)*4) - buff.getInt(k*4);
                    tempVarchar = new char[tailleChaine/2];
                    for (i = pos+recordSizeFromValues(), j = 0; i < pos+recordSizeFromValues()+ tailleChaine ; i+=2, j++) {
                        // System.out.println(tempVarchar);
                        tempVarchar[j] = buff.getChar(i);
                    }
                    chaine = new String(tempVarchar);
                    values.add(chaine);
                    System.out.println(values.get(k).length());

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
}
