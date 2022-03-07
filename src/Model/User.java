package Model;

/**
 * @author Joshua Gibson
 */
public class User {

    private int userId;
    private String userName;
    private String password;

    /**
     * Creates an object containing user information.
     * @param userId Id of the user.
     * @param userName Name of the user.
     * @param password Password of the user.
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the user's id.
     * @return User's id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user.
     * @param userId ID to be set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves name of user.
     * @return User's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets name of the user.
     * @param userName Name to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password associated with the user.
     * @return Password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password Password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    //override string to string method to display user in combo boxes

    /**
     * Overrides to string method to allow user names to be displayed in a combo box.
     * @return User's name.
     */
    @Override
    public String toString(){
        return(userName);
    }

}
