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

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class OptionsValidator {

    public static boolean valid(Options options) {
        boolean valid = true;

        if (!Files.isRegularFile(Paths.get(options.getInputFileName()))) {
            System.err.println("ERROR: Contacts CSV file " + options.getInputFileName() + " not found." +
                    "[inputFile]");
            valid = false;
        }

        if (!Files.isRegularFile(Paths.get(options.getQslCard().getTemplate()))) {
            System.err.println("ERROR: QSL template file " + options.getQslCard().getTemplate() + " not found. "
                    + "[qslCard.template]");
            valid = false;
        }

        if (StringUtils.isNotBlank(options.getQslCard().getText().getFontFile())
                && !Files.isRegularFile(Paths.get(options.getQslCard().getText().getFontFile()))) {
            System.err.println("ERROR: Font file " + options.getQslCard().getText().getFontFile() + " for text not found. "
                    + "[qslCard.text.fontFile]");
            valid = false;
        }

        if (StringUtils.isNotBlank(options.getQslCard().getCallsign().getFontFile())
                && !Files.isRegularFile(Paths.get(options.getQslCard().getCallsign().getFontFile()))) {
            System.err.println("ERROR: Font file " + options.getQslCard().getCallsign().getFontFile() + " for callsign not found. "
                    + "[qslCard.callsign.fontFile]");
            valid = false;
        }

        return valid;
    }
}
