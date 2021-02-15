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

import com.niesens.netloggerelf.options.Options;
import com.niesens.netloggerelf.options.OptionsValidator;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class NetLoggerElf {

    private static final String CONFIG_FILE_NAME = "config.yml";
    private final Options options;
    private final AdifService adifService;
    private final QslCardService qslCardService;

    public NetLoggerElf(Options options, AdifService adifService, QslCardService qslCardService) {
        this.options = options;
        this.adifService = adifService;
        this.qslCardService = qslCardService;
    }

    public int execute() throws IOException {
        printBanner();

        if (createConfigFile()) {
            return 1;
        }
        if (!OptionsValidator.valid(options)) {
            return -1;
        }

        qslCardService.initialize();

        new CsvToBeanBuilder<NetLoggerQso>(new FileReader(options.getInputFileName()))
                .withType(NetLoggerQso.class)
                .build()
                .stream()
                .forEach(this::processQso);

        qslCardService.save();
        adifService.save();
        adifService.send();
        createConfigFile();

        return 0;
    }

    private void processQso(NetLoggerQso netLoggerQso) {
        qslCardService.printCard(netLoggerQso);
        adifService.addAdifRecord(netLoggerQso);
    }

    private void printBanner() {
        if (options.getQuiet()) {
            return;
        }

        String secondLine = "more info at " + NetLoggerElfMetaData.getApplicationHomePage();
        int firstLineLength = NetLoggerElfMetaData.getApplicationName().length() + NetLoggerElfMetaData.getApplicationVersion().length();
        int lineLength = Math.max( firstLineLength + 3, secondLine.length());
        String firstLine = NetLoggerElfMetaData.getApplicationName() + StringUtils.repeat(" ", lineLength - firstLineLength)
                + NetLoggerElfMetaData.getApplicationVersion();
        secondLine = StringUtils.center(secondLine, lineLength);

        System.out.println("  .-" + StringUtils.repeat("-", lineLength)+ "-.");
        System.out.println("  | " + firstLine + " |");
        System.out.println("  | " + secondLine + " |");
        System.out.println("  `-" + StringUtils.repeat("-", lineLength)+ "-'");
    }

    /**
     * This method checks if a config file already exists.  If not the default config file is being created.
     * Check for build.grade to determine if run from within IDE.
     */
    private boolean createConfigFile() throws IOException {
        if (Files.isRegularFile(Paths.get("build.gradle"))
                || Files.isRegularFile(Paths.get(CONFIG_FILE_NAME))) {
            return false;
        }

        FileUtils.copyURLToFile(new ClassPathResource(CONFIG_FILE_NAME).getURL(), new File(CONFIG_FILE_NAME));
        System.out.println("Configuration missing.  Created config.yml file with defaults.");
        System.out.println("Please open the file with a text editor and make necessary changes before running the program again.");
        return true;
    }

}