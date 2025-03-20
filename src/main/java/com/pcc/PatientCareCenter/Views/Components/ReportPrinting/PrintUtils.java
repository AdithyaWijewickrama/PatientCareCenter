package com.pcc.PatientCareCenter.Views.Components.ReportPrinting;

import net.sf.jasperreports.engine.JasperPrint;

import javax.print.attribute.standard.MediaSizeName;

public class PrintUtils {

    public static MediaSizeName getMediaSize(JasperPrint jasperPrint) {
        int width = jasperPrint.getPageWidth();
        int height = jasperPrint.getPageHeight();

        // Normalize width and height (sometimes orientation changes them)
        int w = Math.min(width, height);
        int h = Math.max(width, height);

        if (w == 595 && h == 842) return MediaSizeName.ISO_A4;
        if (w == 842 && h == 1191) return MediaSizeName.ISO_A3;
        if (w == 420 && h == 595) return MediaSizeName.ISO_A5;
        if (w == 297 && h == 420) return MediaSizeName.ISO_A6;
        if (w == 210 && h == 297) return MediaSizeName.ISO_A7;
        if (w == 148 && h == 210) return MediaSizeName.ISO_A8;
        if (w == 105 && h == 148) return MediaSizeName.ISO_A9;
        if (w == 74 && h == 105) return MediaSizeName.ISO_A10;

        if (w == 729 && h == 1032) return MediaSizeName.ISO_B4;
        if (w == 516 && h == 729) return MediaSizeName.ISO_B5;
        if (w == 364 && h == 516) return MediaSizeName.ISO_B6;

        if (w == 612 && h == 792) return MediaSizeName.NA_LETTER;
        if (w == 612 && h == 1008) return MediaSizeName.NA_LEGAL;
        if (w == 792 && h == 1224) return MediaSizeName.NA_LETTER;
        if (w == 612 && h == 936) return MediaSizeName.EXECUTIVE;

        if (w == 419 && h == 595) return MediaSizeName.JAPANESE_POSTCARD;
        if (w == 323 && h == 472) return MediaSizeName.JAPANESE_DOUBLE_POSTCARD;

        // Custom or unknown size
        return null;
    }
}