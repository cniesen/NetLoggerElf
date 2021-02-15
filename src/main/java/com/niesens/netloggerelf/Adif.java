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

import com.niesens.netloggerelf.options.Options;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
public class Adif {
    private final StringBuilder adif = new StringBuilder();
    private final Options options;

    private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd").toFormatter();
    private final DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("HHmmss").toFormatter();

    public Adif(NetLoggerElfMetaData netLoggerElfMetaData, Options options) {
        this.options = options;
        appendAdifField("ADIF_VERS", "3.1.0");
        appendAdifField("PROGRAMID", "Claus' NetLoggerElf");
        appendAdifField("PROGRAMVERSION", netLoggerElfMetaData.getApplicationVersion());
        appendAdifEoh();
    }

    public Adif addQso(NetLoggerQso qso) {
        appendAdifField("QSO_DATE", qso.getDate());
        appendAdifField("TIME_ON", qso.getTime());
        appendAdifField("CALL", qso.getCallsign());
        appendAdifField("RST_RCVD", qso.getRstReceived());
        appendAdifField("RST_SENT", qso.getRstSent());
        appendAdifField("BAND", qso.getBand());
        appendAdifField("FREQ", qso.getFrequency());
        appendAdifField("MODE", qso.getMode());
        appendAdifField("COMMENT", qso.getNetName());
        appendAdifField("QSLMSG", qso.getQslMessage());
        appendAdifField("STATION_CALLSIGN", options.getAdif().getStation().getCall());
        appendAdifField("MY_COUNTRY", options.getAdif().getStation().getCountry());
        appendAdifField("MY_STATE", options.getAdif().getStation().getState());
        appendAdifField("MY_CNTY", options.getAdif().getStation().getCounty());
        appendAdifField("MY_GRIDSQUARE", options.getAdif().getStation().getGrid());
        appendAdifField("MY_ITU_ZONE", options.getAdif().getStation().getItu());
        appendAdifField("MY_CQ_ZONE", options.getAdif().getStation().getCq());
        appendAdifEor();
        return this;
    }

    private void appendAdifField(String fieldName, String fieldValue) {
        if (!StringUtils.isNotBlank(fieldValue)) {
            return;
        }

        adif.append("<");
        adif.append(fieldName);
        adif.append(":");
        adif.append(fieldValue.trim().length());
        adif.append(">");
        adif.append(fieldValue);
        adif.append(" ");
    }

    private void appendAdifField(String fieldName, LocalDate fieldValue) {
        appendAdifField(fieldName, fieldValue.format(dateFormatter));
    }

    private void appendAdifField(String fieldName, LocalTime fieldValue) {
        appendAdifField(fieldName, fieldValue.format(timeFormatter));
    }

    private void appendAdifEoh() {
        adif.append("<EOH>\n");
    }
    private void appendAdifEor() {
        adif.append("<EOR>\n");
    }

    public String toString() {
        return adif.toString();
    }
}
