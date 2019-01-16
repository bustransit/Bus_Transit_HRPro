/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author NelsonDelaTorre
 */

public class DateTimeUtilities {
    private Date date;
    private Time time;
    private Timestamp dateTime;

    public DateTimeUtilities() {
        
    }
      
    public DateTimeUtilities(Date date, Time time, Timestamp dateTime) {
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
    }
        

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return the dateTime
     */
    public Timestamp getDateTime() {
        return dateTime;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
