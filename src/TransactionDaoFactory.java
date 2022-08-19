public class TransactionDaoFactory {
    private static TransactionDao transDao;

    private TransactionDaoFactory(){

    }

    public static TransactionDao getTransDao(){
        if(transDao == null){
            transDao = new TransactionDaoImpl();
        }
        return transDao;
    }
}
