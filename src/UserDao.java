import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException;
    List<User> getUsers() throws SQLException;
    User getUserByUsername(String username) throws SQLException;
}
