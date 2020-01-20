package com.niesen.netloggerelf;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;
import java.time.LocalTime;

public class NetLoggerQso {
    @CsvBindByName(column = "Date")
    @CsvDate("yyyy/MM/dd")
    private LocalDate date;

    @CsvBindByName(column = "Time")
    @CsvDate("HH:mm:ss")
    private LocalTime time;

    @CsvBindByName(column = "Callsign")
    private String callsign;

    @CsvBindByName(column = "Frequency")
    private String frequency;

    @CsvBindByName(column = "Mode")
    private String mode;

    @CsvBindByName(column = "Band")
    private String band;

    @CsvBindByName(column = "His_RST")
    private String RstReceived;

    @CsvBindByName(column = "My_RST")
    private String RstSent;

    @CsvBindByName(column = "Net Name")
    private String netName;

    @CsvBindByName(column = "QSL Message")
    private String qslMessage;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getRstReceived() {
        return RstReceived;
    }

    public void setRstReceived(String rstReceived) {
        RstReceived = rstReceived;
    }

    public String getRstSent() {
        return RstSent;
    }

    public void setRstSent(String rstSent) {
        RstSent = rstSent;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getQslMessage() {
        return qslMessage;
    }

    public void setQslMessage(String qslMessage) {
        this.qslMessage = qslMessage;
    }
}
