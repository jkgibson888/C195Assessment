package DAO;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Joshua Gibson
 */
public class FirstLevelDivisionDaoImpl {
    /**
     * Retrieves all first level divisions from the database and places them into an observable list.
     * @return Observable list that contains all the first level divisions from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() throws SQLException, Exception{

        //ObservableList to be returned containing all the countries
        ObservableList<FirstLevelDivision> allFirst= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String sqlStatement="select * from first_level_divisions";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int divisionId = result.getInt("Division_ID");
            String division = result.getString("Division");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryId = result.getInt("Country_ID");

            FirstLevelDivision userResult = new FirstLevelDivision(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            allFirst.add(userResult);
        }

        //close database connection
        JDBC.closeConnection();
        return allFirst;
    }

    /**
     * Searches for a specific division in the database.
     * @param divisionId The search parameter to find the appropriate information in the database.
     * @return An object that contains the appropriate information retrieved from the database.
     * @throws SQLException
     */
    public static FirstLevelDivision searchDivision(int divisionId) throws SQLException {

        FirstLevelDivision userResult = null;
        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String select = "select * from first_level_divisions ";
        String where = "WHERE Division_ID = " + divisionId;
        String sqlStatement = select + where;
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();

        while(result.next()) {
            int newDivisionId = result.getInt("Division_ID");
            String divisionName = result.getString("Division");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryId = result.getInt("Country_ID");

            userResult = new FirstLevelDivision(newDivisionId, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);


        }
        return userResult;
    }

}
