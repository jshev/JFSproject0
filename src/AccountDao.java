import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    void addAccount(Account account) throws SQLException;
    void updateAccount(Account account) throws SQLException;
    Account getAccountById(int id) throws SQLException;
    List<Account> getPendingAccounts() throws SQLException;
    List<Account> getAccountsByOwner(String owner) throws SQLException;
}
