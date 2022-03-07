package Model;

import DAO.CountryDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**
 * @author Joshua Gibson
 */
public class Customer {

    //private fields for a customer object
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String division;
    private String fullAddress;
    private String countryName;

    //constructor for a customer object

    /**
     * Constructs an object containing information about a customer.
     * @param customerId Id of the customer.
     * @param customerName Name of the customer.
     * @param address Address of the customer.
     * @param postalCode Postal code of the customer.
     * @param phoneNumber Phone number of the customer.
     * @param createDate Date the customer record was created.
     * @param createdBy Who created the customer record.
     * @param lastUpdate Last time the customer record was updated.
     * @param lastUpdatedBy Who last updated the customer record.
     * @param divisionId Division id associated with customer.
     * @param division Division name associated with the customer.
     * @param fullAddress Full address of the customer.
     * @throws Exception
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId, String division, String fullAddress) throws Exception {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.division = division;
        this.fullAddress = fullAddress;

        ObservableList<Country> allCountries = CountryDaoImpl.getAllCountries();
        ObservableList<FirstLevelDivision> flDivisions = FirstLevelDivisionDaoImpl.getAllFirstLevelDivisions();

        int countryID = 0;

        for(FirstLevelDivision fl: flDivisions){
            if(divisionId == fl.getDivisionId()){
                countryID = fl.getCountryId();
            }
        }
        for(Country country: allCountries){
            if(countryID == country.getCountryId()){
                countryName = country.getCountry();
            }

        }
    }


    //getter and setters for private fields


    /**
     * Retrieves the customer id.
     * @return The customer id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer id.
     * @param customerId Customer id to be set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the name of the customer.
     * @return Name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     * @param customerName Name to be set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Retrieves the address of the customer.
     * @return Address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address Address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the postal code of the customer.
     * @return Postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer.
     * @param postalCode Postal code to be set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retrieves the phone number of the customer.
     * @return Phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     * @param phoneNumber Phone number to be set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the date the customer record was created.
     * @return Date the customer record was created.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the day the customer record was created.
     * @param createDate Date to be set.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves who created the customer record.
     * @return The name of the person who created the customer record.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets who created the customer record.
     * @param createdBy Name to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the date that the customer record was last updated.
     * @return Date the customer record was last updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date that the customer record was last updated.
     * @param lastUpdate Date to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the name of who last updated the customer record.
     * @return Name of who last updated the customer record.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the name of who last updated the customer record.
     * @param lastUpdatedBy Name to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Retrieves the division id associated with the customer record.
     * @return Division id associated with the customer record.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division id associated with the customer record.
     * @param divisionId Division id to be set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the name of the division associated with the customer record.
     * @return Name of the division associated with the customer record.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division associated with the customer record.
     * @param division Name of the division to be set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Retrieves the name of the country that is associated with the customer record.
     * @return Country name.
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * Overrides to string method to allow country names to be displayed in a combo box.
     * @return Country name.
     */
    //override string to string method to display country in combo boxes
    @Override
    public String toString(){
        return(customerName);
    }
}
