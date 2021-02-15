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
 * Test AdifOptions with default/build in configuration
 */
@SpringBootTest()
class AdifOptionsIntegrationTest {

    @Autowired
    private Options options;

    @Test
    public void getFile_getEnabled() {
        assertThat(options.getAdif().getFile().getEnabled()).isTrue();
    }

    @Test
    public void getFile_getName() {
        assertThat(options.getAdif().getFile().getName()).isBlank();
    }

    @Test
    public void getUdp_getEnabled() {

        assertThat(options.getAdif().getUdp().getEnabled()).isFalse();
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
        assertThat(options.getAdif().getStation().getCall()).isBlank();
    }

    @Test
    public void getStation_getGrid() {
        assertThat(options.getAdif().getStation().getGrid()).isBlank();
    }

    @Test
    public void getStation_getCounty() {
        assertThat(options.getAdif().getStation().getCounty()).isBlank();
    }

    @Test
    public void getStation_getState() {
        assertThat(options.getAdif().getStation().getState()).isBlank();
    }

    @Test
    public void getStation_getCountry() {
        assertThat(options.getAdif().getStation().getCountry()).isBlank();
    }

    @Test
    public void getStation_getItu() {
        assertThat(options.getAdif().getStation().getItu()).isNull();
    }

    @Test
    public void getStation_getCq() {
        assertThat(options.getAdif().getStation().getCq()).isNull();
    }

}