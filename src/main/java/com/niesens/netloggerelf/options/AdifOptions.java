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

public class AdifOptions {
    private final File file = new File();
    private final Udp udp = new Udp();
    private Station station = new Station();

    public File getFile() {
        return file;
    }
    public Udp getUdp() {
        return udp;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public static class File {
        private Boolean enabled;
        private String name;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Udp {
        private Boolean enabled;
        private String address;
        private Integer port;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    public static class Station {
        private String call;
        private String grid;
        private String county;
        private String state;
        private String country;
        private Integer itu;
        private Integer cq;

        public String getCall() {
            return call;
        }

        public void setCall(String call) {
            this.call = call;
        }

        public String getGrid() {
            return grid;
        }

        public void setGrid(String grid) {
            this.grid = grid;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getItu() {
            return itu == null ? null : itu.toString();
        }

        public void setItu(Integer itu) {
            this.itu = itu;
        }

        public String getCq() {
            return cq == null ? null : cq.toString();
        }

        public void setCq(Integer cq) {
            this.cq = cq;
        }
    }

}
