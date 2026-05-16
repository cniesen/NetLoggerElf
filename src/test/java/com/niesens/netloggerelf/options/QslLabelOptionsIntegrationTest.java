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

import static com.niesens.netloggerelf.enumerations.LabelType.AVERY8163;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AdifOptions with default/build in configuration
 */
@SpringBootTest()
class QslLabelOptionsIntegrationTest {

    @Autowired
    private Options options;

    @Test
    void getEnabled() {
        assertThat(options.getQslLabel().getEnabled()).isFalse();
    }

    @Test
    void getLabelType() {
        assertThat(options.getQslLabel().getLabelType()).isEqualTo(AVERY8163);
    }

    @Test
    void getPdfName() {
        assertThat(options.getQslLabel().getPdfName()).isBlank();
    }

    @Test
    void getCcnAwards() {
        assertThat(options.getQslLabel().getCcnAwards()).isBlank();
    }

    @Test
    void getCcnAwardsOffset() {
        assertThat(options.getQslLabel().getCcnAwardsOffset()).isEqualTo(20);
    }

    @Test
    void getBureauIndicator() {
        assertThat(options.getQslLabel().getBureauIndicator()).isEqualTo("Bureau");
    }

    @Test
    void getText_getFont() {
        assertThat(options.getQslLabel().getText().getFontFile()).isBlank();
    }
    @Test
    void getText_getSize() {
        assertThat(options.getQslLabel().getText().getSize()).isEqualTo(10);
    }
    @Test
    void getText_getLeading() {
        assertThat(options.getQslLabel().getText().getLeading()).isEqualTo(12);
    }
    @Test
    void getText_getCharacterSpacing() {
        assertThat(options.getQslLabel().getText().getCharacterSpacing()).isEqualTo(0);
    }
    @Test
    void getText_getBold() {
        assertThat(options.getQslLabel().getText().getBold()).isFalse();
    }

    @Test
    void getCallsign_getFont() {
        assertThat(options.getQslLabel().getCallsign().getFontFile()).isBlank();
    }
    @Test
    void getCallsign_getSize() {
        assertThat(options.getQslLabel().getCallsign().getSize()).isEqualTo(14);
    }
    @Test
    void getCallsign_getLeading() {
        assertThat(options.getQslLabel().getCallsign().getLeading()).isEqualTo(14);
    }
    @Test
    void getCallsign_getCharacterSpacing() {
        assertThat(options.getQslLabel().getCallsign().getCharacterSpacing()).isEqualTo(0);
    }
    @Test
    void getCallsign_getBold() {
        assertThat(options.getQslLabel().getCallsign().getBold()).isFalse();
    }

    @Test
    void getBureau_getFont() {
        assertThat(options.getQslLabel().getBureau().getFontFile()).isBlank();
    }
    @Test
    void getBureau_getSize() {
        assertThat(options.getQslLabel().getBureau().getSize()).isEqualTo(6);
    }
    @Test
    void getBureau_getLeading() {
        assertThat(options.getQslLabel().getBureau().getLeading()).isEqualTo(10);
    }
    @Test
    void getBureau_getCharacterSpacing() {
        assertThat(options.getQslLabel().getBureau().getCharacterSpacing()).isEqualTo(0);
    }
    @Test
    void getBureau_getBold() {
        assertThat(options.getQslLabel().getBureau().getBold()).isFalse();
    }
}