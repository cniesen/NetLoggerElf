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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Configuration
public class NetLoggerElfMetaData {

    private static ApplicationContext applicationContext;
    private static BuildProperties buildProperties;
    private static String homePage;

    @Autowired
    public NetLoggerElfMetaData(ApplicationContext applicationContext, BuildProperties buildProperties, @Value("${netLoggerElf.homePage}") String homePage) {
        NetLoggerElfMetaData.applicationContext = applicationContext;
        NetLoggerElfMetaData.buildProperties = buildProperties;
        NetLoggerElfMetaData.homePage = homePage;
    }

    public static String getApplicationName() {
        return buildProperties.getName();
    }

    public static String getApplicationVersion() {
        return buildProperties.getVersion();
    }

    public static Instant getApplicationStartTimestamp() {
        return Instant.ofEpochMilli(applicationContext.getStartupDate());
    }

    public static Calendar getApplicationStartCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(applicationContext.getStartupDate());
        return calendar;
    }

    public static String getFormattedApplicationStartTimestamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").withZone(ZoneId.of("UTC")).format(getApplicationStartTimestamp());
    }

    public static String getApplicationHomePage() {
        return homePage;
    }

}