package Model;

/**
 * @author Joshua Gibson
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Creates an object containing contact information.
     * @param contactId The id of the contact.
     * @param contactName The name of the contact.
     * @param email The email of the contact.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Retrieves the contact id.
     * @return The contact id.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id.
     * @param contactId Contact id to be set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the name of the contact.
     * @return The name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the name of the name of the contact.
     * @param contactName Name of the contact to be set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Retrieves the email of the contact.
     * @return Email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the contact.
     * @param email Email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Allows the contact name to be displayed in a combo box.
     * @return The contact name to be displayed in a combo box.
     */
    //override string to string method to display country in combo boxes
    @Override
    public String toString(){
        return(contactName);
    }
}
