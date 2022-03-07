package Model;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Month;

public class MonthTypeApp {

    private String month;
    private String type;
    private String count;

    /**
     * Creates a month type object with appointment data to be used in a report.
     * @param month Month appointment occurs.
     * @param type Type of appointment.
     * @param count Count of similar records.
     */
    public MonthTypeApp(String month, String type, String count){
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Retrieves the month.
     * @return Month.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month.
     * @param month Month to be set.
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Retrieves type.
     * @return Type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     * @param type Type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves count.
     * @return Count.
     */
    public String getCount() {
        return count;
    }

    /**
     * Sets count.
     * @param count Count to be set.
     */
    public void setCount(String count) {
        this.count = count;
    }


}
