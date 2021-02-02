package com.niesen.netloggerelf;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

@Component
@Command(name = "NetLoggerElf", mixinStandardHelpOptions = true, defaultValueProvider = CommandLine.PropertiesDefaultProvider.class, description = "\nNetLoggerElf takes the CSV file from NetLogger to create QSL Cards and an ADIF file for importing into other logging software\n", footer = "\nhttps://github.com/cniesen/NetLoggerElf")
public class NetLoggerElf implements Callable<Integer> {
    private QslCardService qslCardService;

    @Option(names = {"-i", "--contacts-file"}, defaultValue = "contacts.csv", paramLabel = "<contacts.csv>", description = "Exported contacts CSV file from NetLogger. (default: ${DEFAULT-VALUE})")
    private File fileQsos;

    @Option(names = {"-t", "--qsl-template"}, defaultValue = "qsl-template.jpg", paramLabel = "<qsl-template.jpg>", description = "QSL card template. A 5.5 x 3.5 inch jpg image. (default: ${DEFAULT-VALUE})")
    private File fileQslTemplate;

    @Option(names = {"--qsl-font-text-file"}, hidden = true, paramLabel = "<trim.ttf>", description = "Custom text font on QSL card.")
    private File fontTextFile;

    @Option(names = {"--qsk-font-text-size"}, hidden = true, defaultValue = "10", description = "Custom text size on QSL card.")
    int fontTextSize;

    @Option(names = {"--qsl-font-text-leading"}, hidden = true, defaultValue = "12", description = "Custom text size on QSL card.")
    int fontTestLeading;

    @Option(names = {"--qsl-font-text-character-spacing"}, hidden = true, defaultValue = "0", description = "Custom text size on QSL card.")
    int fontTextCharacterSpacing;

    @Option(names = {"--qsl-font-text-bold"}, hidden = true, defaultValue = "false", description = "Custom text boldness on QSL card. (true/false)")
    boolean fontTextBold;

    @Option(names = {"--qsl-font-callsign-file"}, hidden = true, paramLabel = "<trim.ttf>", description = "Custom callsign font on QSL card.")
    private File fontCallsignFile;

    @Option(names = {"--qsl-font-callsign-size"}, hidden = true, defaultValue = "16", description = "Custom callsign size on QSL card.")
    int fontCallsignSize;

    @Option(names = {"--qsl-font-callsign-leading"}, hidden = true, defaultValue = "16", description = "Custom callsign size on QSL card.")
    int fontCallsignLeading;

    @Option(names = {"--qsl-font-callsign-character-spacing"}, hidden = true, defaultValue = "0", description = "Custom callsign size on QSL card.")
    int fontCallsignCharacterSpacing;

    @Option(names = {"--qsl-font-callsign-bold"}, hidden = true, defaultValue = "false", description = "Custom callsign boldness on QSL card. (true/false)")
    boolean fontCallsignBold;

    @Option(names = {"--qsl-paper-size"}, defaultValue = "letter", description = "Paper size on which the QSL cards are printed. Valid options are: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
    private PaperSize paperSize;

    @Option(names = {"-q", "--qsl-output"}, paramLabel ="<qsl-cards.pdf>", description = "Filename of the PDF file of the QSL cards (default: qsl-cards-ccyy-mm-dd-hh-mm-ss.pdf)")
    private File fileQslOutput;

    private Font fontText;
    private Font fontCallsign;

    public NetLoggerElf() {
    }

    @Override
    public Integer call() throws IOException {
        if (!valid()) {
            return -1;
        }

        qslCardService = new QslCardService(fileQslTemplate, paperSize, fontText, fontCallsign);

        new CsvToBeanBuilder<NetLoggerQso>(new FileReader(fileQsos))
                .withType(NetLoggerQso.class)
                .build()
                .stream()
                .forEach(qslCardService::printCard);

        qslCardService.save(fileQslOutput);
        return 33;
    }

    private boolean valid() {
        boolean valid = true;

        if (!fileQsos.exists()) {
            System.err.println("ERROR: Contacts CSV file " + fileQsos.getName() + " not found");
            valid = false;
        }

        if (!fileQslTemplate.exists()) {
            System.err.println("ERROR: QSL template file " + fileQslTemplate.getName() + " not found");
            valid = false;
        }

        if(fontTextFile != null) {
            fontText = new Font(new FileSystemResource(fontTextFile), fontTextSize, fontTestLeading, fontTextCharacterSpacing, fontTextBold);
        } else {
            fontText = new Font(new ClassPathResource("fonts/trim.ttf"), fontTextSize, fontTestLeading, fontTextCharacterSpacing, fontTextBold);
        }
        valid &= fontText.validFontFile();

        if (fontCallsignFile != null) {
            fontCallsign = new Font(new FileSystemResource(fontCallsignFile), fontCallsignSize, fontCallsignLeading, fontCallsignCharacterSpacing, fontCallsignBold);
        } else {
            fontCallsign = new Font(new ClassPathResource("fonts/cruft-bold.ttf"), fontCallsignSize, fontCallsignLeading, fontCallsignCharacterSpacing, fontCallsignBold);
        }
        valid &= fontCallsign.validFontFile();

        if (paperSize == null) {
            System.err.println("ERROR: No QSL paper size specified.");
            valid = false;
        }

        return valid;
    }

    private void processQso(NetLoggerQso netLoggerQso) {
        updateInfo(netLoggerQso);
        qslCardService.printCard(netLoggerQso);
        createAdifRecord(netLoggerQso);
    }
    private void updateInfo(NetLoggerQso netLoggerQso) {
        //ToDo: do a internet lookup of the call and add additional info to the NetLoggerQso bean
    }
    private void createAdifRecord(NetLoggerQso netLoggerQso) {

    }

}