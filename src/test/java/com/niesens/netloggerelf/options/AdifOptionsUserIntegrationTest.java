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

package com.niesens.netloggerelf.options;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AdifOptions with user configuration
 */
@SpringBootTest(properties = {
        "adif.file.name=net-logger-qso-ccyy-mm-dd-hh-mm-ss.adif",
        "adif.udp.enabled=true",
        "adif.station.call=EF73BUX",
        "adif.station.grid=AB12",
        "adif.station.county=HAM",
        "adif.station.state=LT",
        "adif.station.country=SAMPLE LAND",
        "adif.station.itu=2",
        "adif.station.cq=9"
})
class AdifOptionsUserIntegrationTest {

    @Autowired
    private Options options;

    @Test
    public void getFile_getEnabled() {
        assertThat(options.getAdif().getFile().getEnabled()).isTrue();
    }

    @Test
    public void getFile_getName() {
        assertThat(options.getAdif().getFile().getName()).isEqualTo("net-logger-qso-ccyy-mm-dd-hh-mm-ss.adif");
    }

    @Test
    public void getUdp_getEnabled() {

        assertThat(options.getAdif().getUdp().getEnabled()).isTrue();
    }

    @Test
    public void geUdp_getAddress() {
        assertThat(options.getAdif().getUdp().getAddress()).isEqualTo("localhost");
    }

    @Test
    public void getUdp_getPort() {
        assertThat(options.getAdif().getUdp().getPort()).isEqualTo(8899);

    }

    @Test
    public void getStation_getCall() {
        assertThat(options.getAdif().getStation().getCall()).isEqualTo("EF73BUX");
    }

    @Test
    public void getStation_getGrid() {
        assertThat(options.getAdif().getStation().getGrid()).isEqualTo("AB12");
    }

    @Test
    public void getStation_getCounty() {
        assertThat(options.getAdif().getStation().getCounty()).isEqualTo("HAM");
    }

    @Test
    public void getStation_getState() {
        assertThat(options.getAdif().getStation().getState()).isEqualTo("LT");
    }

    @Test
    public void getStation_getCountry() {
        assertThat(options.getAdif().getStation().getCountry()).isEqualTo("SAMPLE LAND");
    }

    @Test
    public void getStation_getItu() {
        assertThat(options.getAdif().getStation().getItu()).isEqualTo("2");
    }

    @Test
    public void getStation_getCq() {
        assertThat(options.getAdif().getStation().getCq()).isEqualTo("9");
    }

}