public class AccountDaoFactory {
    private static AccountDao accountDao;

    private AccountDaoFactory(){
    }

    public static AccountDao getAccountDao(){
        if(accountDao == null){
            accountDao = new AccountDaoImpl();
        }
        return accountDao;
    }
}
