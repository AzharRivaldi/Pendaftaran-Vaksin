package com.azhar.pendaftaranvaksin.ui.main;

import java.io.Serializable;

/**
 * Created by Azhar Rivaldi on 16-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class ModelMain implements Serializable {

    String strName;
    String strVicinity;
    String strPhoto;
    double latLoc;
    double longLoc;

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrVicinity() {
        return strVicinity;
    }

    public void setStrVicinity(String strVicinity) {
        this.strVicinity = strVicinity;
    }

    public String getStrPhoto() {
        return strPhoto;
    }

    public void setStrPhoto(String strPhoto) {
        this.strPhoto = strPhoto;
    }

    public double getLatLoc() {
        return latLoc;
    }

    public void setLatLoc(double latLoc) {
        this.latLoc = latLoc;
    }

    public double getLongLoc() {
        return longLoc;
    }

    public void setLongLoc(double longLoc) {
        this.longLoc = longLoc;
    }
}
