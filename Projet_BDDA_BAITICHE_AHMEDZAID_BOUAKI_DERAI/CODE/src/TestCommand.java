public class TestCommand {
    public static void main(String[] args){
        String commande = "CREATE TABLE R (X:INTEGER,C2:REAL";
        CreateTableCommand c = new CreateTableCommand(commande);
        System.out.println(c);
    }
}
