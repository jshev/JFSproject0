import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    void addTransaction(Transaction trans) throws SQLException;
    List<Transaction> getTransactions() throws SQLException;
}
