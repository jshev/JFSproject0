import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    Connection connection;
    public UserDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "insert into user values (?, ?, 'customer')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("New user added.");
        }else{
            System.out.println("Something went wrong with adding user...");
        }

    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String role = resultSet.getString(3);
            User user = new User(username, password, role);
            users.add(user);
        }
        //System.out.println(users);
        return users;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        User user = new User();
        String sql = "select * from user where username = '" + username + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            String name = resultSet.getString(1);
            String password = resultSet.getString(2);
            String role = resultSet.getString(3);
            user = new User(name, password, role);
        }else{
            System.out.println("No user found...");
        }
        //System.out.println(user);
        return user;
    }
}
