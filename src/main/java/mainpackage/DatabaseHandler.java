package mainpackage;

import entities.Passenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler extends Configs{
    Connection dbConnection;//создание объекта класса Connection
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {//выдача исключений вслучае неполадок
        Class.forName("oracle.jdbc.driver.OracleDriver");//драйвер
        dbConnection = DriverManager.getConnection(jdbcURL, dbUser, dbPass);//указана строка подключения, пользователь и пароль
        return dbConnection;
    }

    public ResultSet getPassenger(Passenger passenger){
        ResultSet resSet = null;
        try {
            resSet = dbConnection.createStatement().executeQuery("SELECT * FROM " + Const.PASSENGERS_TABLE + " WHERE " + Const.PASSENGER_MAIL + "=" + passenger.getMail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
}
