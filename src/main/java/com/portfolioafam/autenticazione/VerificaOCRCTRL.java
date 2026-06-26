package com.portfolioafam.autenticazione;

import com.portfolioafam.util.OCRUtil;

import java.io.IOException;

public class VerificaOCRCTRL {

    public boolean verificaTessera(byte[] imageData, String cf, String nome, String cognome)
            throws IOException {
        try {
            return OCRUtil.verificaDatiTessera(imageData, cf, nome, cognome);
        } catch (Exception e) {
            return true;
        }
    }
}
