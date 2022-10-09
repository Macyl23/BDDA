import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {
    private RelationInfo relInfo;
    private ArrayList<String> values;

    public Record(RelationInfo relation) {
        this.relInfo = relation;
        values = new ArrayList<>();
    }

    public RelationInfo getRelInfo() {
        return relInfo;
    }

    public void setRelInfo(RelationInfo relInfo) {
        this.relInfo = relInfo;
    }

    /**
     * Méthode qui permet d'écrire un record dans le buffer
     * @param buff
     * @param pos
     * On position le curseur du buffer 
     * On parcourt la liste de values 
     * On convertit la valeur de chaque type et on écrit 
     * Sinon si une on trouve un String on écrit char par char
     */
    public void writeToBuffer(ByteBuffer buff, int pos) {
        String type;
        int tempInt;
        float tempFloat;
        buff.position(pos);
        for (int i = 0; i < values.size(); i++) {
            type = relInfo.getInfoColonne().get(i).getType();

            switch (type) {
                case "INTEGER":
                    tempInt = Integer.parseInt(values.get(i));
                    buff.putInt(tempInt);
                    break;

                case "REAL":
                    tempFloat = Float.parseFloat(values.get(i));
                    buff.putFloat(tempFloat);
                    break;
               default:
                    for(int j=0 ; j<values.get(i).length() ; j++){
                        buff.putChar(values.get(i).charAt(j));
                    }
                    break;

            }
        }
    }

}
