package Model;

import java.sql.Timestamp;

public class FirstLevelDivision {

    private int divisionId;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int countryId;

    /**
     * Creates a first level division object.
     * @param divisionId ID of the division.
     * @param division Name of the division.
     * @param createDate Date the division was created.
     * @param createdBy Who created the division.
     * @param lastUpdate Last time the division was updated.
     * @param updatedBy Last person to update the division.
     * @param countryId Country id associated with the division.
     */
    public FirstLevelDivision(int divisionId, String division, Timestamp createDate, String createdBy, Timestamp lastUpdate, String updatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.countryId = countryId;
    }

    /**
     * Retrieves the division id.
     * @return Division id.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division id.
     * @param divisionId Division id to be set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the name of the division.
     * @return Name of the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division.
     * @param division Name to be set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Retrieves the date the division was created.
     * @return Date the division was created.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date the division was created.
     * @param createDate Date to be set.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves who created the division.
     * @return Name of who created the division.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of who created the division.
     * @param createdBy Name to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the date of when the division was last updated.
     * @return Date the division was last updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date of when the division was last updated.
     * @param lastUpdate Date to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the name of who last updated the division.
     * @return Name of who last updated the division.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the name of who last updated the division.
     * @param updatedBy Name to be set.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Retrieves the country id associated with the division.
     * @return Country id associated with the division.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country id.
     * @param countryId Country id to be set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    //override string to string method to display FirstLevelDivision in combo boxes

    /**
     * Overrides to string method to display the division name in a combo box.
     * @return Name of the division.
     */
    @Override
    public String toString(){
        return(division);
    }
}
