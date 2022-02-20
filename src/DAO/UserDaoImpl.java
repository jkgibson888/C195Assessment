package DAO;

import Controller.LogInFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.User;
import DAO.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

 /**
 *
 */
public class UserDaoImpl {

    public static User getUser(String userName) throws SQLException, Exception{

        // Connect to database
        JDBC.openConnection();

        //sql statement and database query
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String password=result.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }

        //close database connection
        JDBC.closeConnection();
        return null;
    }
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{

        //ObservableList to be returned containing all the users
        ObservableList<User> allUsers= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String sqlStatement="select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userName=result.getString("User_Name");
            String password=result.getString("Password");
            User userResult= new User(userid, userName, password);
            allUsers.add(userResult);
        }

        //close database connection
        JDBC.closeConnection();
        return allUsers;
    }

    //method to add users
    public static void insertUser(User user){

        //connect to the database
        JDBC.openConnection();

        //sql statement and query to insert new user into database
        String insert = "INSERT INTO users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) ";
        String values = "VALUES(\'" + user.getUserName() + "\', \'" + user.getPassword() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName()+ "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\')";
        String sqlStatement = insert + values;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();

    }

    //method to update users password

    public static void updatePassword(User user) throws Exception {

        //connect to database
        JDBC.openConnection();

        //sql statement and query to change users password
        String table = "UPDATE users ";
        String set = "SET Password = \'" + user.getPassword() + "\'";
        String where = "WHERE User_Name = \'" + user.getUserName() +"\'";
        String sqlStatement = table + set + where;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();
    }

    //method to delete user
    public static void deleteUser(User user){

        //connect to database
        JDBC.openConnection();

        //sql statement and query to delete user from user table
        String sqlStatement = "DELETE FROM users WHERE User_Name = \'" + user.getUserName() +"\'";
        Query.makeQuery(sqlStatement);

        //close connection
        JDBC.closeConnection();
    }

}
