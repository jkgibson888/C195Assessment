package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Joshua Gibson
 */
public class CountryDaoImpl {
    /**
     * Retrieves all countries from the database and places them into an observable list.
     * @return Observable list that contains all the countries from the database.
     * @throws SQLException
     * @throws Exception
     */
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

    /**
     * Searches for a country in the database using a passed divisionId.
     * @param divisionId The search parameter that is used to select the appropriate country associated divisionId.
     * @return The country associated with the passed division id.
     * @throws SQLException
     */
    public static Country searchCountry(int divisionId) throws SQLException {

        Country userResult = null;
        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String select = "select * from countries ";
        String join = "INNER JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID ";
        String where = "WHERE Division_ID = " + divisionId;
        String sqlStatement = select + join + where;
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();

        while(result.next()) {
            int newCountryId = result.getInt("Country_ID");
            String countryName = result.getString("Country");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");

            userResult = new Country(newCountryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);


        }
        return userResult;
    }
}
