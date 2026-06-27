package com.portfolioafam.util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class OCRUtil {

    private static final Tesseract tesseract = new Tesseract();

    static {
        String tessdata = System.getenv("TESSDATA_PREFIX");
        if (tessdata != null) {
            tesseract.setDatapath(tessdata);
        } else if (new File("/usr/share/tesseract-ocr/5/tessdata").exists()) {
            tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata");
        } else if (new File("C:/Program Files/Tesseract-OCR/tessdata").exists()) {
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        } else if (new File("C:/Program Files (x86)/Tesseract-OCR/tessdata").exists()) {
            tesseract.setDatapath("C:/Program Files (x86)/Tesseract-OCR/tessdata");
        } else if (new File("C:/ProgramData/Tesseract-OCR/tessdata").exists()) {
            tesseract.setDatapath("C:/ProgramData/Tesseract-OCR/tessdata");
        } else if (new File(System.getenv("LOCALAPPDATA") + "/Tesseract-OCR/tessdata").exists()) {
            tesseract.setDatapath(System.getenv("LOCALAPPDATA") + "/Tesseract-OCR/tessdata");
        }
        tesseract.setLanguage("ita");
    }

    private OCRUtil() {
    }

    public static String estraiTesto(byte[] dati) throws IOException, TesseractException {
        String ext = guessExtension(dati);
        File temp = File.createTempFile("ocr_", ext);
        try {
            Files.write(temp.toPath(), dati);
            String testo = tesseract.doOCR(temp);
            if (testo.trim().isEmpty()) {
                tesseract.setLanguage("eng");
                testo = tesseract.doOCR(temp);
                tesseract.setLanguage("ita");
            }
            return testo;
        } finally {
            temp.delete();
        }
    }

    public static boolean verificaDatiTessera(byte[] imageData, String cfAtteso, String nomeAtteso, String cognomeAtteso) throws IOException, TesseractException {
        String testo = estraiTesto(imageData).toUpperCase();
        boolean cfOK = cfAtteso != null && testo.contains(cfAtteso.toUpperCase());
        boolean nomeOK = nomeAtteso != null && testo.contains(nomeAtteso.toUpperCase());
        boolean cognomeOK = cognomeAtteso != null && testo.contains(cognomeAtteso.toUpperCase());
        return cfOK && nomeOK && cognomeOK;
    }

    private static String guessExtension(byte[] dati) {
        if (dati.length > 3 && dati[0] == (byte)0xFF && dati[1] == (byte)0xD8) return ".jpg";
        if (dati.length > 3 && dati[0] == (byte)0x89 && dati[1] == 0x50 && dati[2] == 0x4E && dati[3] == 0x47) return ".png";
        if (dati.length > 3 && dati[0] == 0x47 && dati[1] == 0x49 && dati[2] == 0x46) return ".gif";
        if (dati.length > 3 && dati[0] == 0x42 && dati[1] == 0x4D) return ".bmp";
        return ".png";
    }
}
