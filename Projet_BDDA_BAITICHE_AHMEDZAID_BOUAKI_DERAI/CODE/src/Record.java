import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Record {
    private RelationInfo relInfo;
    public ArrayList<String> values;
    public int sizeValeur;

    public Record(RelationInfo relation) {
        this.relInfo = relation;
        values = new ArrayList<>();
        sizeValeur=0;
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

            buff.putInt(relInfo.getNbColonnes() * 4 + 4 + sizeValeur);
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
            System.out.println("buffer : " + Arrays.toString(buff.array()));

        }
        buff.position(k);
        buff.putInt(relInfo.getNbColonnes() * 4 + 4 + sizeValeur);
        System.out.println("buffer : " + Arrays.toString(buff.array()));

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
            buff.position(pos+getWrittenSize());
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
                    tailleChaine = Integer.parseInt(type.substring(7));
                    tempVarchar = new char[tailleChaine];
                    for (i = pos+getWrittenSize(), j = 0; i < pos+tailleChaine*2 ; i+=2, j++) {
                        // System.out.println(tempVarchar);
                        tempVarchar[j] = buff.getChar();
                    }
                    chaine = new String(tempVarchar);
                    values.add(chaine);
                    break;
            }

        }

    }


    public int getWrittenSize(){
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
                    tailleChaine = Integer.parseInt(type.substring(7));
                    writtenSize +=  tailleChaine*2;
                    break;

            }
        }
        return writtenSize;
    }
    
}
