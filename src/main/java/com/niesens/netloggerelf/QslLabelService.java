/*
	Claus' NetLogger Elf
	Copyright (C) 2026  Claus Niesen
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

import com.niesens.netloggerelf.options.Options;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class QslLabelService {
    private static final float POINTS_PER_INCH = 72;
    private final Options options;

    private static final PDDocument document = new PDDocument();
    private static Font fontText;
    private static Font fontBureau;
    private static Font fontCallsign;

    private int labelOnPage = 0;

    private PDPage page = null;

    public QslLabelService(Options options) {
        this.options = options;
    }

    public void initialize() throws IOException {
        this.addPdfMetaData();
        if (StringUtils.isNotBlank(this.options.getQslLabel().getText().getFontFile())) {
            fontText = new Font(
                    new FileSystemResource(this.options.getQslLabel().getText().getFontFile()),
                    this.options.getQslLabel().getText().getSize(),
                    this.options.getQslLabel().getText().getLeading(),
                    this.options.getQslLabel().getText().getCharacterSpacing(),
                    this.options.getQslLabel().getText().getBold()
            );
        } else {
            fontText = new Font(
                    new ClassPathResource("fonts/Atkinson-Hyperlegible-Regular-102.ttf"),
                    this.options.getQslLabel().getText().getSize(),
                    this.options.getQslLabel().getText().getLeading(),
                    this.options.getQslLabel().getText().getCharacterSpacing(),
                    this.options.getQslLabel().getText().getBold()
            );
        }

        if (StringUtils.isNotBlank(this.options.getQslLabel().getCallsign().getFontFile())) {
            fontCallsign = new Font(
                    new FileSystemResource(this.options.getQslLabel().getCallsign().getFontFile()),
                    this.options.getQslLabel().getCallsign().getSize(),
                    this.options.getQslLabel().getCallsign().getLeading(),
                    this.options.getQslLabel().getCallsign().getCharacterSpacing(),
                    this.options.getQslLabel().getCallsign().getBold()
            );
        } else {
            fontCallsign = new Font(
                    new ClassPathResource("fonts/Atkinson-Hyperlegible-Bold-102.ttf"),
                    this.options.getQslLabel().getCallsign().getSize(),
                    this.options.getQslLabel().getCallsign().getLeading(),
                    this.options.getQslLabel().getCallsign().getCharacterSpacing(),
                    this.options.getQslLabel().getCallsign().getBold()
            );
        }

        if (StringUtils.isNotBlank(this.options.getQslLabel().getBureau().getFontFile())) {
            fontBureau = new Font(
                    new FileSystemResource(this.options.getQslLabel().getBureau().getFontFile()),
                    this.options.getQslLabel().getBureau().getSize(),
                    this.options.getQslLabel().getBureau().getLeading(),
                    this.options.getQslLabel().getBureau().getCharacterSpacing(),
                    this.options.getQslLabel().getBureau().getBold()
            );
        } else {
            fontBureau = new Font(
                    new ClassPathResource("fonts/Atkinson-Hyperlegible-Regular-102.ttf"),
                    this.options.getQslLabel().getBureau().getSize(),
                    this.options.getQslLabel().getBureau().getLeading(),
                    this.options.getQslLabel().getBureau().getCharacterSpacing(),
                    this.options.getQslLabel().getBureau().getBold()
            );
        }

        fontText = fontText.init(document);
        fontBureau = fontBureau.init(document);
        fontCallsign = fontCallsign.init(document);
    }

    private void addPdfMetaData() {
        PDDocumentInformation documentInformation = document.getDocumentInformation();
        documentInformation.setTitle("QSL Labels");
        String application = NetLoggerElfMetaData.getApplicationName()
                + " "
                + NetLoggerElfMetaData.getApplicationVersion()
                + " ("
                + NetLoggerElfMetaData.getApplicationHomePage()
                + ")";
        documentInformation.setCreator(application);
        documentInformation.setProducer(application);
        documentInformation.setCreationDate(NetLoggerElfMetaData.getApplicationStartCalendar());
        documentInformation.setModificationDate(NetLoggerElfMetaData.getApplicationStartCalendar());
    }

    public void skipLabels(int count) {
        this.labelOnPage = count % 10;
        if (this.labelOnPage > 0) {
            this.page = new PDPage(new PDRectangle(612.0F, 792.0F));
            document.addPage(this.page);
        }
    }

    public void printLabel(NetLoggerQso netLoggerQso) {
        if (!netLoggerQso.isNoCardNeeded()) {
            if (this.labelOnPage == 0) {
                this.page = new PDPage(new PDRectangle(612.0F, 792.0F));
                document.addPage(this.page);
            }

            int skipRows = this.labelOnPage / 2;
            float labelPositionY = this.page.getMediaBox().getUpperRightY() - 28.0F - skipRows * 2.0F * 72.0F - 30.0F;
            float labelPositionX;
            if (this.labelOnPage % 2 == 0) {
                labelPositionX = this.page.getMediaBox().getLowerLeftX() + 16.0F;
            } else {
                labelPositionX = this.page.getMediaBox().getLowerLeftX() + 314.0F;
            }

            this.labelOnPage++;
            if (this.labelOnPage == 10) {
                this.labelOnPage = 0;
            }

            try {
                PDPageContentStream contents = new PDPageContentStream(document, this.page, PDPageContentStream.AppendMode.APPEND, true);
                int ccnAwardsOffset = 0;

                try {
                    if (!this.options.getQslLabel().getCcnAwards().isEmpty()) {
                        contents.beginText();
                        contents.newLineAtOffset(labelPositionX, labelPositionY);
                        fontText.useFor(contents);
                        fontText.nextFor(contents);
                        for (String awardLine : this.options.getQslLabel().getCcnAwards()) {
                            contents.showText(awardLine);
                            contents.newLine();
                            ccnAwardsOffset = ccnAwardsOffset + fontText.getLeading();
                        }
                        ccnAwardsOffset = ccnAwardsOffset + fontText.getLeading();
                        contents.endText();
                    }

                    contents.beginText();
                    contents.newLineAtOffset(labelPositionX, labelPositionY - ccnAwardsOffset);
                    fontText.useFor(contents);
                    fontCallsign.nextFor(contents);
                    contents.showText("Confirming QSO with");
                    contents.newLine();
                    fontCallsign.useFor(contents);
                    fontText.nextFor(contents);
                    contents.showText("   " + netLoggerQso.getCallsign());
                    contents.newLine();
                    fontText.useFor(contents);
                    contents.showText("Date: " + netLoggerQso.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    contents.newLine();
                    contents.showText(
                            "Time: "
                                    + netLoggerQso.getTime()
                                    .format(
                                            new DateTimeFormatterBuilder()
                                                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                                                    .appendLiteral(':')
                                                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                                                    .toFormatter()
                                    )
                                    + " UTC"
                    );
                    contents.newLine();
                    contents.showText("Freq: " + netLoggerQso.getFrequency() + " MHz");
                    contents.newLine();
                    contents.showText("Mode: " + netLoggerQso.getMode());
                    contents.newLine();
                    contents.showText("RTS: " + netLoggerQso.getRstSent() + " sent / " + netLoggerQso.getRstReceived() + " received");
                    contents.endText();
                    contents.beginText();
                    contents.newLineAtOffset(labelPositionX + 130.0F, labelPositionY - ccnAwardsOffset);
                    fontText.useFor(contents);
                    fontText.nextFor(contents);
                    if (StringUtils.isNotBlank(netLoggerQso.getQslMessage())) {
                        this.showWrappedText(netLoggerQso.getQslMessage(), 144.0F, contents, fontText);
                        contents.newLine();
                    }

                    contents.showText("Thanks for the contact on the");
                    contents.newLine();
                    this.showWrappedText(netLoggerQso.getNetName(), 144.0F, contents, fontText);
                    contents.newLine();
                    contents.newLine();
                    contents.showText("73,");
                    contents.endText();
                    if (netLoggerQso.isBureau() && StringUtils.isNotBlank(this.options.getQslLabel().getBureauIndicator())) {
                        contents.beginText();
                        contents.newLineAtOffset(labelPositionX + 240.0F, labelPositionY - 110.0F);
                        fontBureau.useFor(contents);
                        fontBureau.nextFor(contents);
                        contents.showText(this.options.getQslLabel().getBureauIndicator());
                        contents.endText();
                    }
                } catch (Throwable var9) {
                    try {
                        contents.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                contents.close();
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }
    }

    public void showWrappedText(String text, float width, PDPageContentStream contents, Font font) throws IOException {
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;

        while (text.length() > 0) {
            int spaceIndex = text.indexOf(32, lastSpace + 1);
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

        for (String line : lines) {
            if (isLinebreakNeeded) {
                contents.newLine();
            }

            contents.showText(line);
            isLinebreakNeeded = true;
        }
    }

    public void save() throws IOException {
        String fileName = this.options.getQslLabel().getPdfName();
        if (StringUtils.isBlank(fileName)) {
            fileName = "qsl-labels-" + NetLoggerElfMetaData.getFormattedApplicationStartTimestamp() + ".pdf";
        }

        document.save(fileName);
        if (!this.options.getQuiet()) {
            System.out.println("Wrote QSL Label PDF file: " + fileName);
        }
    }
}
