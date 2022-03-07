package Model;

import Controller.LogInFormController;
import DAO.ContactDaoImp;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private String createdBy;
    private Timestamp createDate;
    private int customerId;
    private int contactId;
    private int userId;

    private String customerName;
    private String userName;
    private String contactName;
    private String start;
    private String stop;


    /**
     * Constructs an appointment object.
     * @param appointmentId The appointments ID number.
     * @param title The appointments title.
     * @param description The appointments description.
     * @param location The appointments location.
     * @param type The appointments type.
     * @param startTime The start time of the appointment.
     * @param endTime The end time of the appointment.
     * @param createdBy Who created the appointment.
     * @param createDate The date the appointment was created.
     * @param customerId The customer id the appointment is associated with.
     * @param contactId The id of the contact associated with the appointment.
     * @param userId The user id associated with the appointment.
     * @throws Exception
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, String createdBy, Timestamp createDate, int customerId, int contactId, int userId) throws Exception {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.customerId = customerId;
        this.contactId = contactId;
        this.createdBy = LogInFormController.getCurrentUser().getUserName();
        this.userId = userId;

        ObservableList<Customer> allCustomers = CustomerDaoImpl.getAllCustomers();
        for(Customer customer: allCustomers){
            if(customer.getCustomerId() == customerId){
                customerName = customer.getCustomerName();
            }
        }

        ObservableList<User> allUsers = UserDaoImpl.getAllUsers();
        for(User user: allUsers){
            if(user.getUserId() == userId){
                userName = user.getUserName();
            }
        }

        ObservableList<Contact> allContacts = ContactDaoImp.getAllContacts();
        for(Contact contact: allContacts){
            if(contact.getContactId() == contactId){
                contactName = contact.getContactName();
            }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM-dd-yyyy hh:mm a");
        //String s = dtf.format(startTime.toLocalDateTime());
        start = dtf.format(startTime.toLocalDateTime());
        stop = dtf.format(endTime.toLocalDateTime());

        System.out.println(stop);
    }

    /**
     * Retrieves the title of the appointment.
     * @return The title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment.
     * @param title Title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the appointment.
     * @return The description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointment.
     * @param description Description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the location of the appointment.
     * @return The location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment.
     * @param location Location to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the type of appointment.
     * @return The type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of appointment.
     * @param type Type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the start time of the appointment.
     * @return The start time of the appointment.
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the appointment.
     * @param startTime Start time to be set.
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieves the end time of the appointment.
     * @return The end time of the appointment.
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the appointment.
     * @param endTime End time to be set.
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * Retrieves the date the appointment was created.
     * @return The date the appointment was created.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date the appointment was created.
     * @param createDate The date to be set.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the customer id associated with the appointment.
     * @return The customer id associated with the appointment.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer id of the appointment.
     * @param customerId Customer id to be set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the contact id of the appointment.
     * @return The contact id of the appointment.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id of the appointment.
     * @param contactId Contact id to be set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the appointment id.
     * @return The appointment id.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment id.
     * @param appointmentId Id to be set.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Retrieves the username of the appointment.
     * @return The username of the appointment.
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Retrieves the user id of the appointment.
     * @return The user id of the appointment.
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
