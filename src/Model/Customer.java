package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    //ObservableArray of all the customers in the database;
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    //private fields for a customer object
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String fullAddress;

    //constructor for a customer object

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String fullAddress) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.fullAddress = fullAddress;

    }

    //getter and setters for private fields

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public static ObservableList<Customer> getAllCustomers(){
        //FIX ME! Finish
        return allCustomers;
    }
}
