import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao{
    Connection connection;
    public TransactionDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addTransaction(Transaction trans) throws SQLException {
        String sql = "insert into transaction (type, madeBy, amount, fromAcc, toAcc) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, trans.getType());
        preparedStatement.setString(2, trans.getMadeBy());
        preparedStatement.setInt(3, trans.getAmount());
        preparedStatement.setString(4, trans.getFromAcc());
        preparedStatement.setString(5, trans.getToAcc());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("New transaction added.");
        }else{
            System.out.println("Something went wrong with adding transaction...");
        }
    }

    @Override
    public List<Transaction> getTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transaction";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String type = resultSet.getString(2);
            String madeBy = resultSet.getString(3);
            int amount = resultSet.getInt(4);
            String fromAcc = resultSet.getString(5);
            String toAcc = resultSet.getString(6);
            Transaction trans = new Transaction(id, type, madeBy, amount, fromAcc, toAcc);
            transactions.add(trans);
        }
        return transactions;
    }
}
