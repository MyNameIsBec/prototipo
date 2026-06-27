package com.portfolioafam.util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        }
        tesseract.setLanguage("ita");
    }

    private OCRUtil() {
    }

    public static String estraiTesto(byte[] imageData) throws IOException, TesseractException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
        if (img == null) {
            throw new IOException("Impossibile leggere l'immagine");
        }
        return tesseract.doOCR(img);
    }

    public static boolean verificaDatiTessera(byte[] imageData, String cfAtteso, String nomeAtteso, String cognomeAtteso) throws IOException, TesseractException {
        String testo = estraiTesto(imageData).toUpperCase();
        boolean cfOK = cfAtteso != null && testo.contains(cfAtteso.toUpperCase());
        boolean nomeOK = nomeAtteso != null && testo.contains(nomeAtteso.toUpperCase());
        boolean cognomeOK = cognomeAtteso != null && testo.contains(cognomeAtteso.toUpperCase());
        return cfOK && nomeOK && cognomeOK;
    }
}
