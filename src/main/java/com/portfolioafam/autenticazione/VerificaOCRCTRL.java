package com.portfolioafam.autenticazione;

import com.portfolioafam.util.OCRUtil;

import java.io.IOException;

public class VerificaOCRCTRL {

    public boolean verificaTessera(byte[] imageData, String cf, String nome, String cognome)
            throws Exception {
        return OCRUtil.verificaDatiTessera(imageData, cf, nome, cognome);
    }

    public String estraiTesto(byte[] dati) throws IOException {
        try {
            return OCRUtil.estraiTesto(dati);
        } catch (Exception e) {
            return "[errore OCR: " + e.getMessage() + "]";
        }
    }
}
