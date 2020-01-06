package com.niesen.qsomaster;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

@Component
@Command(name = "qsomaster", mixinStandardHelpOptions = true, description = "\nNetLoggerElf takes the CSV file from NetLogger to create QSL Cards and an ADIF file for importing into other logging software\n", footer = "\nhttps://github.com/cniesen/NetLoggerElf")
public class NetLoggerElf implements Callable<Integer> {

    private boolean hasErrors = false;

    private QslCardService qslCardService;

    @Option(names = {"-i", "--qso-input"}, defaultValue = "qso.csv", paramLabel ="<qso.csv>", description = "Filename of the exported csv file from NetLogger (default: ${DEFAULT-VALUE})")
    private File qsos;

    @Option(names = {"-t", "--qsl-template"}, defaultValue = "qsl-template.jpg", paramLabel ="<qsl-template.jpg>", description = "Filename of the 5.5 x 3.5 inch image template of the QSL card (default: ${DEFAULT-VALUE})")
    private File qslTemplate;

    @Option(names = {"-q", "--qsl-output"}, paramLabel ="<qsl-cards.pdf>", description = "Filename of the PDF file of the QSL cards (default: qsl-cards-ccyy-mm-dd-hh-mm-ss.pdf)")
    private File qslOutput;

    @Option(names = {"-m"}, description = "Print multiple cards on a Letter sized page")
    private boolean multipleCardsPerPage;

    @Option(names = {"-p"}, defaultValue = "qslCard", description = "Paper size on which the QSL cards are printed. Valid options are: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})")
    private PaperSize paperSize;

    private Font fontText;
    private Font fontCallsign;

    @Override
    public Integer call() throws IOException, CsvValidationException {
        validate();
        qslCardService = new QslCardService(qslTemplate, paperSize, fontText, fontCallsign);

        new CsvToBeanBuilder<NetLoggerQso>(new FileReader(qsos))
                .withType(NetLoggerQso.class)
                .build()
                .stream()
                .forEach(qslCardService::printCard);

        qslCardService.save(qslOutput);
        return 33;
    }

    private boolean validate() throws IOException {
        boolean valid = true;
        fontText = new Font(ResourceUtils.getFile("src/main/resources/fonts/trim.ttf"), 10, 12, 0, false);
        fontCallsign = new Font(ResourceUtils.getFile("src/main/resources/fonts/cruft.ttf"), 16, 16, 2, true);
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