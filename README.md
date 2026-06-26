# Portfolio AFAM

Applicazione desktop JavaFX per la gestione dell'identità digitale
e del portfolio artistico-formativo degli studenti delle istituzioni
AFAM (Alta Formazione Artistica, Musicale e Coreutica).

---

## Prerequisiti

| Strumento | Versione minima | Download |
|---|---|---|
| JDK | 17 LTS | https://adoptium.net |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| PostgreSQL | 14+ | https://www.postgresql.org/download/windows/ |

---

## Setup rapido (Windows)

```powershell
# 1. Clona il repository
git clone <repo-url>
cd prototipo

# 2. Crea il database PostgreSQL
psql -U postgres -c "CREATE DATABASE portfolioafam;"

# 3. Esegui lo schema
psql -U postgres -d portfolioafam -f src/main/resources/sql/schema.sql

# 4. Popola i dati di test
mvn exec:java -Dexec.mainClass="com.portfolioafam.app.DatabaseSeeder"

# 5. Avvia l'applicazione
mvn javafx:run
```

---

## Credenziali di test

| Ruolo | Email | Password |
|---|---|---|
| Studente | mario.rossi@afam.it | Password1! |
| Amministratore | admin@afam.it | Admin123!@# |

---

## Struttura del progetto

```
src/main/java/com/portfolioafam/
├── app/              Entry point, wiring dipendenze, DatabaseSeeder
├── model/            Entity (Studente, Contenuto, Cartella, Link, ...)
├── repository/       Pattern Repository + DatabaseManager (JDBC)
├── service/          Logica business (Auth, OTP, Email, ...)
├── util/             SessionManager, Argon2id, Validazione, Config, ...
├── autenticazione/        7 Boundary + 9 Controller
├── gestioneprofilo/       8 Boundary + 9 Controller
├── gestionecontenuti/     1 Boundary + 12 Controller
├── gestioneprivacy/       1 Boundary + 3 Controller
├── gestionecondivisioneesterna/ 2 Boundary + 8 Controller
├── visualizzastudente/   3 Boundary + 4 Controller
├── accessoesterno/       2 Boundary
├── gestionesegnalazioni/ 3 Boundary + 2 Controller
src/main/resources/
├── fxml/            File FXML (interfacce utente)
├── images/          Logo e risorse grafiche
└── sql/             Schema database
```

---

## Architettura

Il sistema segue un'architettura **Three Tier - Repository** (SDD §3.1):

| Layer | Descrizione |
|---|---|
| **Interface** | JavaFX + FXML (classi *BND) |
| **Logic** | Controller (*CTRL) + Service |
| **Data** | Repository JDBC + PostgreSQL |

Il pattern Repository (ODD §2.2.5) incapsula l'accesso ai dati tramite
`DatabaseManager`, disaccoppiando la logica applicativa dalla persistenza.

---

## Documentazione

I documenti di progetto sono nella cartella `Portfolio AFAM/`:

| Documento | Pagine | Contenuto |
|---|---|---|
| `RAD.pdf` | 186 | Requirements Analysis, use case, mockup |
| `ODD.pdf` | 21 | Object Design, package, classi, librerie |
| `SDD.pdf` | 24 | System Design, architettura, schema E/R |

---

## Configurazione

| Variabile d'ambiente | Default | Descrizione |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/portfolioafam` | URL connessione PostgreSQL |
| `DB_USER` | `postgres` | Utente database |
| `DB_PASSWORD` | `postgres` | Password database |

| System property | Default | Descrizione |
|---|---|---|
| `portfolio.2fa.enabled` | `true` | Abilita/disabilita autenticazione a due fattori |

Per attivare la 2FA, rimuovere l'opzione `<option>-Dportfolio.2fa.enabled=false</option>`
dal `pom.xml`.

---

## Contribuire

1. Leggi la documentazione (`RAD.pdf`, `ODD.pdf`, `SDD.pdf`)
2. I mockup di riferimento sono in RAD §4.6
3. Ogni modifica deve essere coerente con la documentazione
4. I testi e la disposizione devono corrispondere ai mockup
5. Esegui `mvn compile` prima di ogni commit
6. I package seguono la struttura descritta in ODD §2.2

### Convenzioni

- Le classi Boundary hanno suffisso `BND`
- Le classi Controller hanno suffisso `CTRL`
- Le classi Entity hanno suffisso `Entity`
- Ogni schermata ha un file FXML corrispondente in `resources/fxml/`
- Le dipendenze sono iniettate via `App.java` (composition root)

---

## Comandi utili

```powershell
# Compilare
mvn compile

# Eseguire
mvn javafx:run

# Popolare il database
mvn exec:java -Dexec.mainClass="com.portfolioafam.app.DatabaseSeeder"

# Pulire e ricompilare
mvn clean compile
```
