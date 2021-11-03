package com.azhar.pendaftaranvaksin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Azhar Rivaldi on 19-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class Constant {

    public static String namaRS;

    public static int cekUmurPeserta(String strTanggal) {
        int umurPeserta = 0;
        SimpleDateFormat formatDefault = new SimpleDateFormat("dd-MM-yyyy");
        try {
            int dateNow = Calendar.getInstance().get(Calendar.YEAR);
            Date dateFormat = formatDefault.parse(strTanggal);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat);
            int umurSekarang = calendar.get(Calendar.YEAR);
            umurPeserta = dateNow - umurSekarang;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return umurPeserta;
    }

}
