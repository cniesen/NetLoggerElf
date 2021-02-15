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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class Font {
    private final Resource fontFile;
    private PDFont font;
    private final int fontSize;
    private final int leading;
    private final int characterSpacing;
    private final boolean bold;

    public Font(Resource fontFile, int fontSize, int leading, int characterSpacing, boolean bold) {
        this.fontFile = fontFile;
        this.fontSize = fontSize;
        this.leading = leading;
        this.characterSpacing = characterSpacing;
        this.bold = bold;
    }

    public Font init(PDDocument document) throws IOException {
        font = PDType0Font.load(document, fontFile.getInputStream());
        return this;
    }

    public void useFor(PDPageContentStream contentStream) throws IOException {
        if (font == null) {
            throw new RuntimeException("Font " + fontFile.getFilename() + " needs to be initialized before use.");
        }

        contentStream.setFont(font, fontSize);
        contentStream.setCharacterSpacing(characterSpacing);
        if (bold) {
            contentStream.setRenderingMode(RenderingMode.FILL_STROKE);
        } else {
            contentStream.setRenderingMode(RenderingMode.FILL);
        }
    }

    /**
     *  Font size changes aren't handled well in regards to line spacing.  Setting the leading size according to the next
     *  line's font is a workaround for this.
     */
    public void nextFor(PDPageContentStream contentStream) throws IOException {
        contentStream.setLeading(leading);
    }

    public float textWidthOf(String text) throws IOException {
        return fontSize * font.getStringWidth(text) / 1000;
    }

}
