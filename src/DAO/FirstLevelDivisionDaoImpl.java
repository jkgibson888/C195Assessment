package DAO;

import Model.Country;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FirstLevelDivisionDaoImpl {

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

}
