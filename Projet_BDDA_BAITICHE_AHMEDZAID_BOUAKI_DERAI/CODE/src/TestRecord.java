import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestRecord {
    public static void main(String[] args){
        ColInfo colonne =  new ColInfo("Age", "INTEGER");
        ColInfo colonneDe = new ColInfo("Points", "REAL");
        ColInfo colonneD = new ColInfo("Nom", "VARCHAR3");
        ArrayList<ColInfo> c = new ArrayList<>();
        c.add(colonneD);
        c.add(colonneDe);
        c.add(colonne);
        RelationInfo r = new RelationInfo("Joueur", 3,c);
        Record re =  new Record(r);
        re.values.add("ABC");
        re.values.add("30.5");
        re.values.add("31");
        System.out.println(re.recordSizeFromValues());
        ByteBuffer buff = ByteBuffer.allocate(r.getNbColonnes()*4+4+re.recordSizeFromValues());
        re.writeToBuffer(buff, r.getNbColonnes()*4+4);
        re.readFromBuffer(buff, r.getNbColonnes()*4+4);
        System.out.println(re.values);
    }
}
