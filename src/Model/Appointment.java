package Model;

import Controller.LogInFormController;
import DAO.ContactDaoImp;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import Utility.Timezone;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        //String s = dtf.format(startTime.toLocalDateTime());
        start = dtf.format(startTime.toLocalDateTime());
        stop = dtf.format(endTime.toLocalDateTime());

        System.out.println(stop);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

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
}
