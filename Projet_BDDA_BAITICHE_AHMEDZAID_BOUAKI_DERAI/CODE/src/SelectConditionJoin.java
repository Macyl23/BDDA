import java.util.ArrayList;

public class SelectConditionJoin {
    
    public ArrayList<Integer> idxcolonneRelation;
    
    private String op;
    public SelectConditionJoin(ArrayList<Integer> idxColonne,String op){
        this.op=op;
        this.idxcolonneRelation=idxColonne;
    }

    public boolean compareValues( String type, Record record1,Record record2){
        switch (type) {
            case "INTEGER":
                int valeurRecordInt1 = Integer.parseInt(record1.values.get(this.idxcolonneRelation.get(0)));
                int valeurRecordInt2 = Integer.parseInt(record2.values.get(this.idxcolonneRelation.get(1)));
                return verifConditionInt(valeurRecordInt1, valeurRecordInt2);
            case "REAL":
                Float valeurRecordFloat1 = Float.parseFloat(record1.values.get(this.idxcolonneRelation.get(0)));
                Float valeurRecordFloat2 = Float.parseFloat(record2.values.get(this.idxcolonneRelation.get(1)));
                return verifConditionReal(valeurRecordFloat1, valeurRecordFloat2);
            default:
                return verifConditionString(record1.values.get(idxcolonneRelation.get(0)), record1.values.get(idxcolonneRelation.get(1)));
        }
    }
    
    public boolean verifConditionInt(int valeurRecord1,int valeurRecord2){
        switch (this.op){
            case "=":
			return Integer.valueOf(valeurRecord1)==valeurRecord2;
            case "<":
                return valeurRecord1 < Integer.valueOf(valeurRecord2);
            case ">":
                return valeurRecord1 > Integer.valueOf(valeurRecord2);
            case "<=":
                return valeurRecord1 <= Integer.valueOf(valeurRecord2);
            case ">=":
                return valeurRecord1 >= Integer.valueOf(valeurRecord2);
            case "<>":
                return valeurRecord1 != Integer.valueOf(valeurRecord2);
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public boolean verifConditionReal(Float valeurRecord1,Float valeurRecord2){
        switch (this.op){
            case "=":
			    return Float.compare(valeurRecord1, Float.valueOf(valeurRecord2)) == 0;
            case "<":
                return  Float.compare(valeurRecord1, Float.valueOf(valeurRecord2))<0;
            case ">":
                return Float.compare(valeurRecord1, Float.valueOf(valeurRecord2))>0;
            case "<=":
                return Float.compare(valeurRecord1, Float.valueOf(valeurRecord2))<=0;
            case ">=":
                return Float.compare(valeurRecord1, Float.valueOf(valeurRecord2))>=0;
            case "<>":
                return Float.compare(valeurRecord1, Float.valueOf(valeurRecord2))!=0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    /**
     * Méthode qui permet de comparer la valeur de la condition et la valeur du record
     * @param valeurRecord la valeur du record r
     * @return TRUE si la condition est satisfaite false sinon
     */
    public  boolean verifConditionString(String valeurRecord1,String valeurRecord2){
        
        switch (this.op){
            case "=":
			    return valeurRecord2.equals(valeurRecord1);
            case "<>":
                return valeurRecord1.compareTo(valeurRecord2) != 0;
            case "<":
                return valeurRecord1.compareTo(valeurRecord2) < 0;
            case ">":
                return valeurRecord1.compareTo(valeurRecord2) > 0;
            case "<=":
                return valeurRecord1.compareTo(valeurRecord2) <= 0;
            case ">=":
                return valeurRecord1.compareTo(valeurRecord2)>=0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public String toString(){
        return idxcolonneRelation.get(0)+" "+idxcolonneRelation.get(1)+" "+op;
    }
}
