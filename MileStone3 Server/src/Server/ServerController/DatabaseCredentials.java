package Server.ServerController;

/**
 * Just an interface that holds the credentials needed to acesss the database
 * 
 * @author  Ryan Holts
 * @version 4.10.0
 * @since April 5, 2019
 */
public interface DatabaseCredentials {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ToolShopDataBase";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

}
