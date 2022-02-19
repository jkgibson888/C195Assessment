package DAO;

import Controller.LogInFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.User;
import DAO.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

/**

 */
public class UserDaoImpl {
    static boolean act;
    public static User getUser(String userName) throws SQLException, Exception{
        // type is name or phone, value is the name or the phone #
        JDBC.openConnection();
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String password=result.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }
        JDBC.closeConnection();
        return null;
    }
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        JDBC.openConnection();
        String sqlStatement="select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String password=result.getString("Password");
            User userResult= new User(userid, userNameG, password);
            allUsers.add(userResult);

        }
        JDBC.closeConnection();
        return allUsers;
    }

    //method to add users
    public static void insertUser(User user){

        JDBC.openConnection();
        String sqlStatement = "INSERT INTO users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(\'" + user.getUserName() + "\', \'" + user.getPassword() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName()+ "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\')";
        //String sqlStatement = "INSERT INTO users VALUES(3, 'admin2', 'admin2', NOW(), 'script', NOW(), 'script')";
        Query.makeQuery(sqlStatement);
        JDBC.closeConnection();

    }

    //method to update users password

    public static void updatePassword(User user) throws Exception {
        JDBC.openConnection();
        String table = "UPDATE users ";
        String set = "SET Password = \'" + user.getPassword() + "\'";
        String where = "WHERE User_Name = \'" + user.getUserName() +"\'";
        String sqlStatement = table + set + where;
        Query.makeQuery(sqlStatement);
        JDBC.closeConnection();
    }

    //method to delete user
    public static void deleteUser(User user){
        JDBC.openConnection();

        String sqlStatement = "DELETE FROM users WHERE User_Name = \'" + user.getUserName() +"\'";
        Query.makeQuery(sqlStatement);

        JDBC.closeConnection();
    }

}
