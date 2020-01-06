package com.niesen.qsomaster;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

public class QslCardService {
    private static final float POINTS_PER_INCH = 72;

    private static PDDocument doc = new PDDocument();
    private static Font fontText;
    private static Font fontCallsign;
    private static PDImageXObject pdImage;
    private static PaperSize paperSize;

    private int cardOnPage = 0;

    private PDPage page = null;


    public QslCardService(File qslTemplate, PaperSize paperSize, Font fontText, Font fontCallsign) throws IOException {
        QslCardService.pdImage = PDImageXObject.createFromFileByExtension(qslTemplate, doc);
//            pdImage = JPEGFactory.createFromStream(doc, new FileInputStream(new File(qslTemplate)));
        QslCardService.paperSize = paperSize;
        QslCardService.fontText = fontText.init(doc);
        QslCardService.fontCallsign = fontCallsign.init(doc);
    }

    public void printCard(NetLoggerQso netLoggerQso) {
        int cardPositionX;
        int cardPositionY;

        if (paperSize == PaperSize.letter) {
            switch (cardOnPage) {
                case 0:
                    page = new PDPage(new PDRectangle(11f * POINTS_PER_INCH, 8f * POINTS_PER_INCH));
                    doc.addPage(page);
                    cardPositionX = 0;
                    cardPositionY = 252;
                    cardOnPage = 1;
                    break;
                case 1:
                    cardPositionX = 396;
                    cardPositionY = 252;
                    cardOnPage = 2;
                    break;
                case 2:
                    cardPositionX = 0;
                    cardPositionY = 0;
                    cardOnPage = 3;
                    break;
                case 3:
                default:
                    cardPositionX = 396;
                    cardPositionY = 0;
                    cardOnPage = 0;
                    break;
            }
        } else {
            page = new PDPage(new PDRectangle(5.5f * POINTS_PER_INCH, 3.5f * POINTS_PER_INCH));
            doc.addPage(page);
            cardPositionX = 0;
            cardPositionY = 0;
        }

        try (PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contents.drawImage(pdImage, cardPositionX, cardPositionY, 5.5f * POINTS_PER_INCH, 3.5f * POINTS_PER_INCH);

            contents.beginText();
            contents.newLineAtOffset(cardPositionX + 10, cardPositionY + 100);
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
            contents.newLineAtOffset(cardPositionX + 200, cardPositionY + 100);
            fontText.useFor(contents);
            fontText.nextFor(contents);
//                    netLoggerQso.setQslMessage("Something really long to see what happens when we print this on the card.  Does it wrap lines or just drop off?");
//                    netLoggerQso.setQslMessage("Something really long to see what happens when we print this on the card.  Does it wrap lines");
//                    System.out.println("size " + fontCalibri.getStringWidth(netLoggerQso.getQslMessage()) + " " + fontCalibri.getStringWidth(netLoggerQso.getQslMessage()) * 10 / 1000 );
            if (StringUtils.isNotBlank(netLoggerQso.getQslMessage())) {
                //ToDo: wrap test via https://github.com/mkl-public/testarea-pdfbox2/blob/master/src/test/java/mkl/testarea/pdfbox2/content/BreakLongString.java#L39
                contents.showText(netLoggerQso.getQslMessage());
                contents.newLine();
            }
            contents.showText("Thanks for the contact on the");
            contents.newLine();
            contents.showText(netLoggerQso.getNetName());
            contents.newLine();
            contents.newLine();
            contents.showText("73,");
            contents.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(File qslOutput) throws IOException {
        if (qslOutput == null) {
            Instant utcTimestamp = Instant.now();
            doc.save("qsl-cards-" + DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").withZone(ZoneId.of("UTC")).format(utcTimestamp) + "pdf");
        } else {
            doc.save(qslOutput);
        }
    }
}
