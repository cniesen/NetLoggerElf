/*
	Claus' NetLogger Elf
	Copyright (C) 2020  Claus Niesen
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.niesens.netloggerelf;

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
