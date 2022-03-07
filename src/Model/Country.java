package Model;

import java.sql.Timestamp;

/**
 * @author Joshua Gibson
 */
public class Country {

    private int countryId;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;

    /**
     * Creates a country object.
     * @param countryId Id of the country.
     * @param country Name of the country.
     * @param createDate Date the country was created.
     * @param createdBy Who created the country.
     * @param lastUpdate Last time the country was updated.
     * @param updatedBy Who updated the country.
     */
    public Country(int countryId, String country, Timestamp createDate, String createdBy, Timestamp lastUpdate, String updatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }

    /**
     * Retrieve the country id.
     * @return The country id.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Set the country id.
     * @param countryId Country id to be set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieve country name.
     * @return Name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     * @param country Name of the country to be set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the date that the country was created.
     * @return The date the country was created.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date the country was created.
     * @param createDate Date the country was created.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the name of who created the country.
     * @return Name of who created the country.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of who created the country.
     * @param createdBy Name of who created teh country.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieve when the country was last updated.
     * @return Timestamp of when the country was last updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets when the country was last updated.
     * @param lastUpdate Timestamp to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the name of who last updated the country.
     * @return Name of who last updated the country.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the name of who last updated the country.
     * @param updatedBy Name to be set.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    //override string to string method to display country in combo boxes

    /**
     * Overrides to string method to display country name in combo boxes.
     * @return Name of the country to be displayed in a combo box.
     */
    @Override
    public String toString(){
        return(country);
    }
}
