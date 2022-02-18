package DAO;

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
}
