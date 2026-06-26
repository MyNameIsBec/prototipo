# TODO — Portfolio AFAM

## CTRL mancanti (RAD §4.3.2)
- [ ] `AperturaLinkCTRL` — accessoesterno, validazione scadenza link, recupero contenuti condivisi
- [ ] `LasciaRiscontroCTRL` — accessoesterno, form riscontro, salvataggio valutazione
- [ ] `VisualizzaCartellaCTRL` — visualizzastudente, vista cartella per utente esterno
- [ ] `VisualizzaContenutoCTRL` — visualizzastudente, vista contenuto per utente esterno
- [ ] `ScaricaContenutoStudenteCTRL` — visualizzastudente, download contenuto con verifica permessi
- [ ] `ScaricaCartellaStudenteCTRL` — visualizzastudente, download cartella con verifica permessi

## BND mancanti (RAD §4.3.1) — priorità alta
- [ ] `FormNuovaPasswordBND` — nuova password dopo recupero (flusso REC_PASS)
- [ ] `RichiestaConsensoBND` — consenso GDPR per recupero account
- [ ] `SchermataRiscontriBND` — elenco riscontri ricevuti su link
- [ ] `SchermataContenutoBND` — vista singolo contenuto pubblico
- [ ] `ContenutiCondivisiBND` — contenuti resi disponibili tramite link
- [ ] `FormRiscontroBND` — form valutazione contenuti condivisi
- [ ] `FormCondivisioneEmailBND` — form invio link via email a destinatario
- [ ] `FormScadenzaLinkBND` — form impostazione data scadenza
- [ ] `FormModificaValiditaBND` — modifica validità link esistente
- [ ] `ListaFiltriBND` — filtri ricerca profili (corso, anno, laureato)

## Service mancanti (ODD §2.2.4)
- [ ] `IdentitaDigitaleService` — integrazione SPID/eIDAS (ora logica in CTRL)
- [ ] `LinkService` — gestione centralizzata link (ora distribuita nei CTRL)

## Schema DB — correzioni SDD
- [ ] Allineare `email2fa` VARCHAR(255) nel SDD (ora dice VARCHAR(20))
- [ ] Correggere typo SDD: `id_contenuto → cartelle.id_contenuto` → `contenuti.id_contenuto`
- [ ] Documentare `ON DELETE CASCADE / SET NULL` nel SDD

## GDPR / Requisiti non funzionali (RAD §3.3)

### Affidabilità (§3.3.2)
- [ ] TLS 1.3 per connessioni JDBC (parametro `ssl=true` + certificati)
- [ ] AES-256 per dati sensibili (BYTEA cifrati)
- [ ] Transazioni atomiche (BEGIN/COMMIT/ROLLBACK nei repository)
- [ ] Controllo concorrenza (lock ottimistici/pessimistici)

### Supportabilità (§3.3.4)
- [ ] Aggiungere logging SLF4J + Logback
- [ ] Creare directory `src/test` con test unitari (JUnit 5)
- [ ] Test di integrazione (DB reale o H2 in-memory)
- [ ] Script backup periodico PostgreSQL

### Aspetti legali (§3.3.8)
- [ ] **Soft-delete 14 giorni**: aggiungere colonna `data_eliminazione` a `studenti`
- [ ] `EliminaAccountCTRL`: flag eliminazione invece di DELETE
- [ ] Cancellazione automatica dopo 14 giorni (job/scheduler)
- [ ] Validazione dominio email AFAM in registrazione
- [ ] Funzione esportazione dati (portabilità GDPR)
- [ ] Meccanismo revoca consenso
- [ ] Validazione OTP 60 secondi (hardcodare in `OTPUtil`)

### Packaging (§3.3.7)
- [ ] Profile Maven per OS (Windows, Linux, macOS)
- [ ] Creazione immagine nativa con `jpackage`

## Integrazione esterna
- [ ] SPID: dipendenza reale (es. spid-java-sdk) o simulazione completa con `DatiCondivisi`
- [ ] eIDAS: dipendenza reale o simulazione
- [ ] OCR: verificare installazione Tesseract su Windows/macOS (path in `OCRUtil`)
- [ ] SMTP: configurare `EmailService` per invio reale OTP e link
