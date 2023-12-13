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

import com.niesens.netloggerelf.enumerations.PaperSize;
import com.niesens.netloggerelf.options.Options;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

@Service
public class QslCardService {
    private static final float POINTS_PER_INCH = 72;
    private final Options options;

    private static final PDDocument document = new PDDocument();
    private static Font fontText;
    private static Font fontBureau;
    private static Font fontCallsign;
    private static PDImageXObject pdImage;

    private int cardOnPage = 0;

    private PDPage page = null;


    public QslCardService(Options options) {
        this.options = options;
    }

    public void initialize() throws IOException {
        QslCardService.pdImage = PDImageXObject.createFromFile(options.getQslCard().getTemplate(), document);
        addPdfMetaData();

        if(StringUtils.isNotBlank(options.getQslCard().getText().getFontFile())) {
            fontText = new Font(new FileSystemResource(options.getQslCard().getText().getFontFile()), options.getQslCard().getText().getSize(), options.getQslCard().getText().getLeading(), options.getQslCard().getText().getCharacterSpacing(), options.getQslCard().getText().getBold());
            fontBureau = new Font(new FileSystemResource(options.getQslCard().getText().getFontFile()), 6, 10, 0, false);
        } else {
            fontText = new Font(new ClassPathResource("fonts/trim.ttf"), options.getQslCard().getText().getSize(), options.getQslCard().getText().getLeading(), options.getQslCard().getText().getCharacterSpacing(), options.getQslCard().getText().getBold());
            fontBureau = new Font(new ClassPathResource("fonts/trim.ttf"), 6, 10, 0, false);
        }

        if (StringUtils.isNotBlank(options.getQslCard().getCallsign().getFontFile())) {
            fontCallsign = new Font(new FileSystemResource(options.getQslCard().getCallsign().getFontFile()), options.getQslCard().getCallsign().getSize(), options.getQslCard().getCallsign().getLeading(), options.getQslCard().getCallsign().getCharacterSpacing(), options.getQslCard().getCallsign().getBold());
        } else {
            fontCallsign = new Font(new ClassPathResource("fonts/cruft-bold.ttf"), options.getQslCard().getCallsign().getSize(), options.getQslCard().getCallsign().getLeading(), options.getQslCard().getCallsign().getCharacterSpacing(), options.getQslCard().getCallsign().getBold());
        }

        QslCardService.fontText = fontText.init(document);
        QslCardService.fontBureau = fontBureau.init(document);
        QslCardService.fontCallsign = fontCallsign.init(document);
   }

    private void addPdfMetaData() {
        PDDocumentInformation documentInformation = QslCardService.document.getDocumentInformation();
        documentInformation.setTitle("QSL Cards");
        String application = NetLoggerElfMetaData.getApplicationName() + " "
                + NetLoggerElfMetaData.getApplicationVersion()  + " ("
                + NetLoggerElfMetaData.getApplicationHomePage() +")";
        documentInformation.setCreator(application);
        documentInformation.setProducer(application);
        documentInformation.setCreationDate(NetLoggerElfMetaData.getApplicationStartCalendar());
        documentInformation.setModificationDate(NetLoggerElfMetaData.getApplicationStartCalendar());
    }

    public void printCard(NetLoggerQso netLoggerQso) {
        if (netLoggerQso.isNoCardNeeded()) {
            return;
        }

        int cardPositionX;
        int cardPositionY;

        if (options.getQslCard().getPaperSize() == PaperSize.letter) {
            switch (cardOnPage) {
                case 0:
                    page = new PDPage(new PDRectangle(11f * POINTS_PER_INCH, 8f * POINTS_PER_INCH));
                    document.addPage(page);
                    cardPositionX = 0;
                    cardPositionY = 72 + 252;
                    cardOnPage = 1;
                    break;
                case 1:
                    cardPositionX = 396;
                    cardPositionY = 72 + 252;
                    cardOnPage = 2;
                    break;
                case 2:
                    cardPositionX = 0;
                    cardPositionY = 72 + 0;
                    cardOnPage = 3;
                    break;
                case 3:
                default:
                    cardPositionX = 396;
                    cardPositionY = 72 + 0;
                    cardOnPage = 0;
                    break;
            }
        } else {
            page = new PDPage(new PDRectangle(5.5f * POINTS_PER_INCH, 3.5f * POINTS_PER_INCH));
            document.addPage(page);
            cardPositionX = 0;
            cardPositionY = 0;
        }

        try (PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contents.drawImage(pdImage, cardPositionX, cardPositionY, 5.5f * POINTS_PER_INCH, 3.5f * POINTS_PER_INCH);

            contents.beginText();
            contents.newLineAtOffset(cardPositionX + 10, cardPositionY + 120);
            fontText.useFor(contents);
            fontCallsign.nextFor(contents);
            contents.showText("Confirming QSO with");
            contents.newLine();
            fontCallsign.useFor(contents);
            fontText.nextFor(contents);
            contents.showText(" " + netLoggerQso.getCallsign());
            contents.newLine();
            fontText.useFor(contents);
            contents.showText("Date: " + netLoggerQso.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            contents.newLine();
            contents.showText("Time: " + netLoggerQso.getTime().format(new DateTimeFormatterBuilder()
                    .appendValue(HOUR_OF_DAY, 2)
                    .appendLiteral(':')
                    .appendValue(MINUTE_OF_HOUR, 2).toFormatter()) + " UTC");
            contents.newLine();
            contents.showText("Freq: " + netLoggerQso.getFrequency() + " MHz");
            contents.newLine();
            contents.showText("Mode: " + netLoggerQso.getMode());
            contents.newLine();
            contents.showText("RTS: " + netLoggerQso.getRstSent() + " sent / " + netLoggerQso.getRstReceived() + " received");
            contents.endText();

            contents.beginText();
            contents.newLineAtOffset(cardPositionX + 200, cardPositionY + 120);
            fontText.useFor(contents);
            fontText.nextFor(contents);
            if (StringUtils.isNotBlank(netLoggerQso.getQslMessage())) {
                showWrappedText(netLoggerQso.getQslMessage(), 186, contents,fontText);
                contents.newLine();
            }
            contents.showText("Thanks for the contact on the");
            contents.newLine();
            showWrappedText(netLoggerQso.getNetName(), 186, contents,fontText);
            contents.newLine();
            contents.newLine();
            contents.showText("73,");
            contents.endText();

            if (netLoggerQso.isBureau()) {
                contents.beginText();
                contents.newLineAtOffset(cardPositionX + 355, cardPositionY + 25);
                fontBureau.useFor(contents);
                fontBureau.nextFor(contents);
                contents.showText("Bureau");
                contents.endText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Credit goes to:
     * https://github.com/mkl-public/testarea-pdfbox2/blob/master/src/test/java/mkl/testarea/pdfbox2/content/BreakLongString.java#L39
     */
    public void showWrappedText(String text, float width,PDPageContentStream contents, Font font) throws IOException {
        List<String> lines = new ArrayList<>();

        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                spaceIndex = text.length();
            }
            String subString = text.substring(0, spaceIndex);
            if (font.textWidthOf(subString) > width) {
                if (lastSpace < 0) {
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }

        boolean isLinebreakNeeded = false;
        for (String line: lines)
        {
            if (isLinebreakNeeded) {
                contents.newLine();
            }
            contents.showText(line);
            isLinebreakNeeded = true;
        }
    }

    public void save() throws IOException {
        if (!options.getQslCard().getEnabled()) {
            return;
        }

        String fileName = options.getQslCard().getPdfName();
        if (StringUtils.isBlank(fileName)) {
            fileName = "qsl-cards-" + NetLoggerElfMetaData.getFormattedApplicationStartTimestamp() + ".pdf";
        }
        document.save(fileName);

        if (!options.getQuiet()) {
            System.out.println("Wrote ADIF file: " + fileName);
        }
    }

}
