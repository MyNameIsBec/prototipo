CREATE TABLE studenti (
    CF             VARCHAR(16)  PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    cognome        VARCHAR(100) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    hash_password  VARCHAR(255) NOT NULL,
    telefono       VARCHAR(20),
    email2fa       VARCHAR(255) NOT NULL UNIQUE,
    immagine_profilo BYTEA,
    dati_accademici TEXT,
    visibilita_profilo VARCHAR(30) NOT NULL DEFAULT 'PRIVATO'
);

CREATE TABLE amministratori (
    id_amministratore BIGSERIAL    PRIMARY KEY,
    email             VARCHAR(255) NOT NULL UNIQUE,
    hash_password     VARCHAR(255) NOT NULL,
    email2fa          VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE cartelle (
    id_cartella   BIGSERIAL    PRIMARY KEY,
    CF            VARCHAR(16)  NOT NULL REFERENCES studenti(CF)
                                   ON DELETE CASCADE,
    nome_cartella VARCHAR(100) NOT NULL,
    privacy       VARCHAR(30)  NOT NULL DEFAULT 'PRIVATO'
);

CREATE TABLE contenuti (
    id_contenuto     BIGSERIAL    PRIMARY KEY,
    CF               VARCHAR(16)  NOT NULL REFERENCES studenti(CF)
                                      ON DELETE CASCADE,
    id_cartella      BIGINT       REFERENCES cartelle(id_cartella)
                                      ON DELETE SET NULL,
    nome             VARCHAR(255) NOT NULL,
    tipo             VARCHAR(20)  NOT NULL,
    file_dati        BYTEA        NOT NULL,
    dimensione       BIGINT       NOT NULL,
    data_caricamento TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    privacy          VARCHAR(30)  NOT NULL DEFAULT 'PRIVATO'
);

CREATE TABLE link (
    id_link       BIGSERIAL   PRIMARY KEY,
    token         VARCHAR(64) NOT NULL UNIQUE,
    CF            VARCHAR(16) NOT NULL REFERENCES studenti(CF)
                                  ON DELETE CASCADE,
    id_cartella   BIGINT      REFERENCES cartelle(id_cartella)
                                  ON DELETE SET NULL,
    id_contenuto  BIGINT      REFERENCES contenuti(id_contenuto)
                                  ON DELETE SET NULL,
    tipo          VARCHAR(20) NOT NULL,
    data_scadenza DATE        NOT NULL,
    stato         VARCHAR(20) NOT NULL DEFAULT 'VALIDO',
    data_creazione TIMESTAMP  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE utenti_esterni (
    id_utente_esterno BIGSERIAL    PRIMARY KEY,
    nome              VARCHAR(100) NOT NULL,
    cognome           VARCHAR(100) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    ruolo             VARCHAR(20)  NOT NULL,
    riscontro         INTEGER
);

CREATE TABLE segnalazioni (
    id_segnalazione    BIGSERIAL   PRIMARY KEY,
    id_utente_esterno  BIGINT      NOT NULL REFERENCES utenti_esterni(id_utente_esterno)
                                       ON DELETE CASCADE,
    id_contenuto       BIGINT      NOT NULL REFERENCES contenuti(id_contenuto)
                                       ON DELETE CASCADE,
    id_amministratore  BIGINT      REFERENCES amministratori(id_amministratore)
                                       ON DELETE SET NULL,
    motivo             VARCHAR(30) NOT NULL,
    descrizione        TEXT,
    data               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);
