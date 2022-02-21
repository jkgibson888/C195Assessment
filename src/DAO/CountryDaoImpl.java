package DAO;

import Model.Country;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CountryDaoImpl {

    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{

        //ObservableList to be returned containing all the countries
        ObservableList<Country> allCountries= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String sqlStatement="select * from countries";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int countryId = result.getInt("Country_ID");
            String countryName = result.getString("Country");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");

            Country userResult = new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allCountries.add(userResult);
        }

        //close database connection
        JDBC.closeConnection();
        return allCountries;
    }
}
