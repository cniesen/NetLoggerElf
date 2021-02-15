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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;


@Service
public class AdifService {

    private final Adif adif;
    private final Options options;

    public AdifService(Adif adif, Options Options) {
        this.adif = adif;
        this.options = Options;
    }

    public void addAdifRecord(NetLoggerQso netLoggerQso) {
        adif.addQso(netLoggerQso);
    }

    public void save() throws IOException {
        if (!options.getAdif().getFile().getEnabled()) {
            return;
        }

        File file;
        if (StringUtils.isBlank(options.getAdif().getFile().getName())) {
            file = new File("qso-" + NetLoggerElfMetaData.getFormattedApplicationStartTimestamp() + ".adi");
        } else {
            file = new File(options.getAdif().getFile().getName());
        }

        FileUtils.writeStringToFile(file, adif.toString() , StandardCharsets.UTF_8);
    }

    public void send() throws IOException {
        if (!options.getAdif().getUdp().getEnabled()) {
            return;
        }

        DatagramSocket socket = new DatagramSocket();
        byte[] buf = adif.toString().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length,
                InetAddress.getByName(options.getAdif().getUdp().getAddress()), options.getAdif().getUdp().getPort());
        socket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.setSoTimeout(15000); //15 seconds
        socket.receive(receivePacket);

        if(!options.getQuiet()) {
            System.out.println("Sent ADIF records to: " + options.getAdif().getUdp().getAddress() + ":"
                    + options.getAdif().getUdp().getPort());
        }
    }

}
