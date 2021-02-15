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

public class QslCardOptions {
    private Boolean enabled;
    private String template;
    private PaperSize paperSize;
    private String pdfName;
    private Text text;
    private Text callsign;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public PaperSize getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(PaperSize paperSize) {
        this.paperSize = paperSize;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Text getCallsign() {
        return callsign;
    }

    public void setCallsign(Text callsign) {
        this.callsign = callsign;
    }


    public static class Text {
        private String fontFile;
        private Integer size;
        private Integer leading;
        private Integer characterSpacing;
        private Boolean bold;

        public String getFontFile() {
            return fontFile;
        }

        public void setFontFile(String fontFile) {
            this.fontFile = fontFile;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getLeading() {
            return leading;
        }

        public void setLeading(Integer leading) {
            this.leading = leading;
        }

        public Integer getCharacterSpacing() {
            return characterSpacing;
        }

        public void setCharacterSpacing(Integer characterSpacing) {
            this.characterSpacing = characterSpacing;
        }

        public Boolean getBold() {
            return bold;
        }

        public void setBold(Boolean bold) {
            this.bold = bold;
        }
    }
}
