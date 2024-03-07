import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String jdbcURL = "jdbc:postgresql://192.168.0.120:5432/admin";;
    private String jdbcUsername = "admin";
    private String jdbcPassword = "admin";
    private Connection jdbcConnection;

    public UserDAO() {
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

//    public List<User> listAllUsers() throws SQLException {
//        List<User> listUsers = new ArrayList<>();
//
//        String sql = "SELECT * FROM users";
//
//        connect();
//
//        Statement statement = jdbcConnection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//
//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            String surname = resultSet.getString("surname");
//            int age = resultSet.getInt("age");
//
//            User user = new User(id, name, surname, age);
//            listUsers.add(user);
//        }
//
//        resultSet.close();
//        statement.close();
//        disconnect();
//        return listUsers;
//    }

    public List<User> listAllUsers() throws SQLException {
        List<User> listUsers = new ArrayList<>();

        // Use a CallableStatement to call the stored procedure
        String storedProcedureCall = "{ call select_all_users() }";

        connect();

        CallableStatement callableStatement = jdbcConnection.prepareCall(storedProcedureCall);
        ResultSet resultSet = callableStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            int age = resultSet.getInt("age");

            User user = new User(id, name, surname, age);
            listUsers.add(user);
        }

        resultSet.close();
        callableStatement.close();
        disconnect();

        return listUsers;
    }

    public User getUser(int id) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            int age = resultSet.getInt("age");

            user = new User(id, name, surname, age);
        }

        resultSet.close();
        statement.close();

        return user;
    }

    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, surname, age) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setFloat(3, user.getAge());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, surname = ?, age = ?";
        sql += " WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setFloat(3, user.getAge());
        statement.setInt(4, user.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }

    public boolean deleteUser(User user) throws SQLException {
        String sql = "DELETE FROM users where id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, user.getId());

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

}
