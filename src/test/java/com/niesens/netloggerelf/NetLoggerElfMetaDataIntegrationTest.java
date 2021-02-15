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

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NetLoggerElfMetaDataIntegrationTest {

    @Test
    public void getApplicationName() {
        assertThat(NetLoggerElfMetaData.getApplicationName()).isEqualTo("Claus' NetLogger Elf");
    }

    @Test
    public void getApplicationVersion() {
        assertThat(NetLoggerElfMetaData.getApplicationVersion()).isNotBlank();
    }

    @Test
    public void getApplicationStartTimestamp() {
        assertThat(Duration.between(Instant.now(), NetLoggerElfMetaData.getApplicationStartTimestamp())).isLessThan(Duration.ofMinutes(5));
    }

    @Test
    public void getApplicationStartCalendar() {
        assertThat(NetLoggerElfMetaData.getApplicationStartCalendar().toInstant()).isEqualTo(NetLoggerElfMetaData.getApplicationStartTimestamp());
    }

    @Test
    public void getFormattedApplicationStartTimestamp() {
        assertThat(NetLoggerElfMetaData.getFormattedApplicationStartTimestamp()).isEqualTo(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").withZone(ZoneId.of("UTC")).format(NetLoggerElfMetaData.getApplicationStartTimestamp()));
    }

}