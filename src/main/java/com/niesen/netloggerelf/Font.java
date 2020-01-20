package com.niesen.netloggerelf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class Font {
    private Resource fontFile;
    private PDFont font;
    private int fontSize;
    private int leading;
    private int characterSpacing;
    private boolean bold;

    public Font(Resource fontFile, int fontSize, int leading, int characterSpacing, boolean bold) {
        this.fontFile = fontFile;
        this.fontSize = fontSize;
        this.leading = leading;
        this.characterSpacing = characterSpacing;
        this.bold = bold;
    }

    public boolean validFontFile() {
        if (fontFile.isReadable()) {
            return true;
        } else {
            System.err.println("ERROR: Font file " + fontFile.getFilename() + " not found");
            return false;
        }
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
