package com.portfolioafam.autenticazione;

import com.portfolioafam.util.SessionManager;

public class LogoutCTRL {

    public void eseguiLogout() {
        SessionManager.getInstance().terminaSessione();
    }
}
