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

package com.niesens.netloggerelf.options;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySources({
        @PropertySource(value = "classpath:config.yml", factory = YamlPropertySourceFactory.class),
        @PropertySource(value = "file:config.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
})
public class Options {
    private Boolean quiet;
    private String inputFileName;
    private AdifOptions adif = new AdifOptions();
    private QslCardOptions qslCard = new QslCardOptions();

    public Boolean getQuiet() {
        return quiet;
    }

    public void setQuiet(Boolean quiet) {
        this.quiet = quiet;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public AdifOptions getAdif() {
        return adif;
    }

    public void setAdif(AdifOptions adif) {
        this.adif = adif;
    }

    public QslCardOptions getQslCard() {
        return qslCard;
    }

    public void setQslCard(QslCardOptions qslCard) {
        this.qslCard = qslCard;
    }
}
