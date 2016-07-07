package ua.epamcourses.natalia_markova.classwork22;

import java.sql.*;

/**
 * Created by natalia_markova on 06.07.2016.
 */
public class TransactionExample {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("", "", "");
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        Savepoint savepoint = null;
        try {
            statement.execute("INSERT ..... ");
            savepoint = connection.setSavepoint("savepoint1");
            statement.execute("INSERT ..... ");
        } catch (SQLException e) {
            connection.rollback(savepoint);
        } finally {
            connection.commit();
        }
        connection.close();
    }

}
