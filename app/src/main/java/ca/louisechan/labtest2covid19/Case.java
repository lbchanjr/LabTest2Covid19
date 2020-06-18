package ca.louisechan.labtest2covid19;

import java.io.Serializable;

public class Case implements Serializable {

    private long totalCases;
    private long totalRecovered;
    private long totalDeaths;
    private String province;

    public Case(long totalCases, long totalRecovered, long totalDeaths, String province) {
        this.totalCases = totalCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
        this.province = province;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
