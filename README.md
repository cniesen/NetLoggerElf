Claus' NetLogger Elf
====================

Program to create QSL cards from NetLogger QSOs.

This is a java command line application which reads the CSV exported QSO file from NetLogger. The program can then 
  - Create creates a PDF file with QSL cards.
  - Create an ADIF file with your station information.
  - Send ADIF records with your station information via UDP to other Logging programs.

Download the latest JAR at  https://github.com/cniesen/NetLoggerElf/releases/latest

To run the program Java needs to be installed.  Then use the command line `java -jar NetLoggerElf.jar`.  In most cases
double clicking the JAR file will run the program as well.  

During the first run the "config.yml" file is being created and nothing else.  This allows the configuration of the 
program to your personal needs.

Most likely you want to create your own qsl-template.jpg file.  Use your favorite image editor to create an image 
with the same dimension.


Configuration
----------
config.yml
```yaml
##
## Claus' NetLogger Elf Configuration File
## more info at https://github.com/cniesen/NetLoggerElf
##

# When quiet is enabled informational messages will not be displayed
quiet: false

# Exported contacts CSV file name from NetLogger (default: contacts.csv)
inputFileName: contacts.csv

# Configuration for printing QSL cards to PDF
qslCard:
  # Enable printing of cards (true/false, default: true)
  enabled: true
  # Template file for the QSL card. A 3300 x 2100 JPG file. (default: qsl-template.jpg)
  template: qsl-template.jpg
  # Paper size on which the QSL cards are printed (sqlCard/letter, default: letter)
  paperSize: letter
  # Filename of the PDF file of the QSL cards (leave empty for default: qsl-cards-ccyy-mm-dd-hh-mm-ss.pdf)
  pdfName:

  # Text customization (advanced settings)
  text:
    # File name of the font to be used for the text (leave empty for default font)
    fontFile:
    # Font size of callsign on QSL Card (integer, default: 16)
    size: 10
    # Font leading / line height of text on QSL Card (integer, default: 16)
    leading: 12
    # Character spacing for text on QSL Card (integer, default: 0)
    characterSpacing: 0
    # Use bold font for text on QSL card (true/false, default: false)
    bold: false
  # Callsign customization (advanced settings)
  callsign:
    # File name of the font to be used for the callsign (leave empty for default font)
    fontFile:
    # Font size of callsign on QSL Card (integer, default: 16)
    size: 16
    # Font leading / line height of callsign on QSL Card (integer, default: 16)
    leading: 16
    # Character spacing for callsign on QSL Card (integer, default: 0)
    characterSpacing: 0
    # Use bold font for callsign on QSL card (true/false, default: false)
    bold: false

# Configuration for ADIF logging
adif:
  #
  file:
    # Enable saving of ADIF log file (true/false, default true)
    enabled: true
    # Filename of the ADIF file (leave empty for default: net-logger-qso-ccyy-mm-dd-hh-mm-ss.adif)
    name:
  #
  udp:
    # Enable sending of ADIF log file to other applications (true/false, default: false)
    enabled: false
    # Internet address of the other application (default: localhost)
    address: localhost
    # UDP port of the other application (default: 8899)
    port: 8899

  # Additional ADIF info appended
  station:
    # Your callsign (STATION_CALLSIGN)
    call:
    # Your grid square (MY_GRIDSQUARE)
    grid:
    # Your county (MY_CNTY)
    county:
    # Your state (MY_STATE)
    state:
    # Your country (MY_COUNTRY)
    country:
    # Your ITU zone ()
    itu:
    # YOur CQ zone ()
    cq:
```

License
-------
The code of Claus' Pota Logger has been Licensed under [GNU GPL 3.0](https://github.com/cniesen/NetLoggerElf/blob/master/COPYING.md).
