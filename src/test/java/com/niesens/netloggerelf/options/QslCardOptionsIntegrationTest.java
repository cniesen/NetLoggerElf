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

import com.niesens.netloggerelf.enumerations.PaperSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AdifOptions with default/build in configuration
 */
@SpringBootTest()
class QslCardOptionsIntegrationTest {

    @Autowired
    private Options options;

    @Test
    void getEnabled() {
        assertThat(options.getQslCard().getEnabled()).isTrue();
    }

    @Test
    void getTemplate() {
        assertThat(options.getQslCard().getTemplate()).isEqualTo("qsl-template.jpg");
    }

    @Test
    void getPaperSize() {
        assertThat(options.getQslCard().getPaperSize()).isEqualTo(PaperSize.letter);
    }

    @Test
    void getPdfName() {
        assertThat(options.getQslCard().getPdfName()).isBlank();
    }

    @Test
    void getText_getFont() {
        assertThat(options.getQslCard().getText().getFontFile()).isBlank();
    }

    @Test
    void getText_getSize() {
        assertThat(options.getQslCard().getText().getSize()).isEqualTo(10);
    }
    @Test
    void getText_getLeading() {
        assertThat(options.getQslCard().getText().getLeading()).isEqualTo(12);
    }
    @Test
    void getText_getCharacterSpacing() {
        assertThat(options.getQslCard().getText().getCharacterSpacing()).isEqualTo(0);
    }
    @Test
    void getText_getBold() {
        assertThat(options.getQslCard().getText().getBold()).isFalse();
    }

    @Test
    void getCallsign_getFont() {
        assertThat(options.getQslCard().getCallsign().getFontFile()).isBlank();
    }

    @Test
    void getCallsign_getSize() {
        assertThat(options.getQslCard().getCallsign().getSize()).isEqualTo(16);
    }
    @Test
    void getCallsign_getLeading() {
        assertThat(options.getQslCard().getCallsign().getLeading()).isEqualTo(16);
    }
    @Test
    void getCallsign_getCharacterSpacing() {
        assertThat(options.getQslCard().getCallsign().getCharacterSpacing()).isEqualTo(0);
    }
    @Test
    void getCallsign_getBold() {
        assertThat(options.getQslCard().getCallsign().getBold()).isFalse();
    }

}