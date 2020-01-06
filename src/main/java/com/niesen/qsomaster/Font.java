package com.niesen.qsomaster;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

import java.io.File;
import java.io.IOException;

public class Font {
    private File fontFile;
    private PDFont font;
    private int fontSize;
    private int leading;
    private int characterSpacing;
    private boolean bold;

    public Font(File fontFile, int fontSize, int leading, int characterSpacing, boolean bold) {
        this.fontFile = fontFile;
        this.fontSize = fontSize;
        this.leading = leading;
        this.characterSpacing = characterSpacing;
        this.bold = bold;
    }

    public Font init(PDDocument document) throws IOException {
        font = PDType0Font.load(document, fontFile);
        return this;
    }

    public void useFor(PDPageContentStream contentStream) throws IOException {
        if (font == null) {
            throw new RuntimeException("Font " + fontFile.getName() + " needs to be initialized before use.");
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


}
