import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

    Connection connection;
    public AccountDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addAccount(Account account) throws SQLException {
        String sql = "insert into account (owner, name, balance, status) values (?, ?, ?, 'pending')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getOwner());
        preparedStatement.setString(2, account.getName());
        preparedStatement.setInt(3, account.getBalance());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("New account added.");
        }else{
            System.out.println("Something went wrong with adding account...");
        }
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = "Update account set balance = ?, status = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getBalance());
        preparedStatement.setString(2, account.getStatus());
        preparedStatement.setInt(3, account.getId());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Account updated.");
        } else {
            System.out.println("Something went wrong with updating account...");
        }
    }

    @Override
    public Account getAccountById(int accId) throws SQLException {
        Account account = new Account();
        String sql = "select * from account where id = " + accId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            String owner = resultSet.getString(2);
            String name = resultSet.getString(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            account = new Account(id, owner, name, balance, status);
        }else{
            System.out.println("No account found...");
        }
        return account;
    }

    @Override
    public List<Account> getPendingAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account where status = 'pending'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String owner = resultSet.getString(2);
            String name = resultSet.getString(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            Account account = new Account(id, owner, name, balance, status);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public List<Account> getAccountsByOwner(String accOwner) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account where owner = '" + accOwner + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String owner = resultSet.getString(2);
            String name = resultSet.getString(3);
            int balance = resultSet.getInt(4);
            String status = resultSet.getString(5);
            Account account = new Account(id, owner, name, balance, status);
            accounts.add(account);
        }
        return accounts;
    }
}
