package com.portfolioafam.app;

import com.portfolioafam.repository.*;
import com.portfolioafam.service.*;
import com.portfolioafam.util.*;
import com.portfolioafam.autenticazione.*;
import com.portfolioafam.gestioneprofilo.*;
import com.portfolioafam.gestionecontenuti.*;
import com.portfolioafam.gestioneprivacy.*;
import com.portfolioafam.gestionecondivisioneesterna.*;
import com.portfolioafam.visualizzastudente.*;
import com.portfolioafam.accessoesterno.*;
import com.portfolioafam.gestionesegnalazioni.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application {

    private DatabaseManager dbManager;
    private StudenteRepository studenteRepository;
    private AmministratoreRepository amministratoreRepository;
    private CartellaRepository cartellaRepository;
    private ContenutoRepository contenutoRepository;
    private LinkRepository linkRepository;
    private UtenteEsternoRepository utenteEsternoRepository;
    private SegnalazioneRepository segnalazioneRepository;

    private AuthService authService;
    private OTPService otpService;
    private PasswordResetService passwordResetService;
    private EmailService emailService;

    private LoginCTRL loginCtrl;
    private Verifica2faCTRL verifica2faCtrl;
    private RegistrazioneCTRL registrazioneCtrl;
    private RecuperoPasswordCTRL recuperoPasswordCtrl;
    private SchermataVerifica2FABND verifica2faBnd;

    private VisualizzaProfiloCTRL visualizzaProfiloCtrl;
    private ModificaImmagineCTRL modificaImmagineCtrl;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Portfolio AFAM");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        SceneManager.setPrimaryStage(primaryStage);

        initDataLayer();
        initServices();
        initCoreCtrls();
        registerAllFactories();

                Parent homeRoot = loadFxml(HomePageBND.class, new HomePageBND());
        SceneManager.registerScene("HomePage", homeRoot);
        primaryStage.setScene(SceneManager.getScene("HomePage"));
        primaryStage.show();

    }

    private void initDataLayer() {
        dbManager = new DatabaseManager();
        studenteRepository = new StudenteRepository(dbManager);
        amministratoreRepository = new AmministratoreRepository(dbManager);
        cartellaRepository = new CartellaRepository(dbManager);
        contenutoRepository = new ContenutoRepository(dbManager);
        linkRepository = new LinkRepository(dbManager);
        utenteEsternoRepository = new UtenteEsternoRepository(dbManager);
        segnalazioneRepository = new SegnalazioneRepository(dbManager);
    }

    private void initServices() {
        authService = new AuthService(studenteRepository, amministratoreRepository);
        otpService = new OTPService();
        passwordResetService = new PasswordResetService(studenteRepository);
        emailService = new EmailService(Config.getSmtpHost(), Config.getSmtpPort(),
                Config.getSmtpUsername(), Config.getSmtpPassword(), Config.getSmtpFrom());
    }

    private void initCoreCtrls() {
        loginCtrl = new LoginCTRL(authService);
        verifica2faCtrl = new Verifica2faCTRL(otpService);
        registrazioneCtrl = new RegistrazioneCTRL(authService, otpService);
        recuperoPasswordCtrl = new RecuperoPasswordCTRL(passwordResetService);

        verifica2faBnd = new SchermataVerifica2FABND();
        verifica2faBnd.setVerifica2faCtrl(verifica2faCtrl);

        visualizzaProfiloCtrl = new VisualizzaProfiloCTRL(studenteRepository);
        modificaImmagineCtrl = new ModificaImmagineCTRL(studenteRepository);
    }

    private void registerAllFactories() {
        reg("SchermataAccesso", () -> {
            SchermataAccessoBND b = new SchermataAccessoBND();
            b.setLoginCtrl(loginCtrl);
            b.setVerifica2faBnd(verifica2faBnd);
            return loadFxml(SchermataAccessoBND.class, b);
        });
        reg("SchermataVerifica2FA", () -> loadFxml(SchermataVerifica2FABND.class, verifica2faBnd));

                reg("SchermataSPID", () -> {
            SchermataSPIDBND b = new SchermataSPIDBND();
            DatiCondivisiBND d = new DatiCondivisiBND();
            b.setDatiCondivisiBnd(d);
            return loadFxml(SchermataSPIDBND.class, b);
        });
                reg("SchermataEIDAS", () -> {
            SchermataEIDASBND b = new SchermataEIDASBND();
            DatiCondivisiBND d = new DatiCondivisiBND();
            b.setDatiCondivisiBnd(d);
            return loadFxml(SchermataEIDASBND.class, b);
        });
        reg("DatiCondivisi", () -> loadFxml(DatiCondivisiBND.class, new DatiCondivisiBND()));

        FormPasswordBND formPasswordBnd = new FormPasswordBND();
        formPasswordBnd.setRegistrazioneCtrl(registrazioneCtrl);
        reg("FormPassword", () -> loadFxml(FormPasswordBND.class, formPasswordBnd));

        reg("FormRegistrazione", () -> {
            FormRegistrazioneBND b = new FormRegistrazioneBND();
            b.setRegistrazioneCtrl(registrazioneCtrl);
            b.setFormPasswordBnd(formPasswordBnd);
            return loadFxml(FormRegistrazioneBND.class, b);
        });

        reg("SchermataVerificaOcr", () -> loadFxml(SchermataVerificaOcrBND.class, new SchermataVerificaOcrBND()));

        reg("SchermataOTP", () -> {
            SchermataOTPBND b = new SchermataOTPBND();
            b.setVerifica2faCtrl(verifica2faCtrl);
            return loadFxml(SchermataOTPBND.class, b);
        });

        reg("FormRecuperoPassword", () -> {
            FormRecuperoPasswordBND b = new FormRecuperoPasswordBND();
            b.setRecuperoPasswordCtrl(recuperoPasswordCtrl);
            return loadFxml(FormRecuperoPasswordBND.class, b);
        });

        reg("SchermataAttiva2fa", () -> {
            SchermataAttiva2faBND b = new SchermataAttiva2faBND();
            Attiva2faCTRL c = new Attiva2faCTRL(studenteRepository, otpService);
            b.setAttiva2faCtrl(c);
            return loadFxml(SchermataAttiva2faBND.class, b);
        });

        reg("SchermataProfilo", () -> {
            ProfiloBND b = new ProfiloBND();
            b.setVisualizzaProfiloCtrl(visualizzaProfiloCtrl);
            b.setModificaImmagineCtrl(modificaImmagineCtrl);
            return loadFxml(ProfiloBND.class, b);
        });

        reg("ModificaEmail", () -> {
            ModificaEmailBND b = new ModificaEmailBND();
            b.setModificaEmailCtrl(new ModificaEmailCTRL(studenteRepository));
            return loadFxml(ModificaEmailBND.class, b);
        });
        reg("ModificaPassword", () -> {
            ModificaPasswordBND b = new ModificaPasswordBND();
            b.setModificaPasswordCtrl(new ModificaPasswordCTRL(studenteRepository));
            return loadFxml(ModificaPasswordBND.class, b);
        });
        reg("ModificaTelefono", () -> {
            ModificaTelefonoBND b = new ModificaTelefonoBND();
            b.setModificaTelefonoCtrl(new ModificaTelefonoCTRL(studenteRepository));
            return loadFxml(ModificaTelefonoBND.class, b);
        });
        reg("ModificaDatiAccademici", () -> {
            ModificaDatiAccademiciBND b = new ModificaDatiAccademiciBND();
            b.setModificaDatiAccademiciCtrl(new ModificaDatiAccademiciCTRL(studenteRepository));
            return loadFxml(ModificaDatiAccademiciBND.class, b);
        });

        reg("ConfermaEliminazione", () -> {
            ConfermaEliminazioneBND b = new ConfermaEliminazioneBND();
            b.setEliminaAccountCtrl(new EliminaAccountCTRL(studenteRepository));
            return loadFxml(ConfermaEliminazioneBND.class, b);
        });
        reg("Informativa", () -> {
            InformativaBND b = new InformativaBND();
            b.setEliminaAccountCtrl(new EliminaAccountCTRL(studenteRepository));
            return loadFxml(InformativaBND.class, b);
        });
        reg("RecuperaAccount", () -> {
            RecuperaAccountBND b = new RecuperaAccountBND();
            b.setRecuperaAccountCtrl(new RecuperaAccountCTRL(studenteRepository));
            return loadFxml(RecuperaAccountBND.class, b);
        });

        reg("IMieiContenuti", () -> {
            IMieiContenutiBND b = new IMieiContenutiBND();
            b.setDependencies(new VisualizzaContenutiCTRL(contenutoRepository),
                    new EliminaContenutoCTRL(contenutoRepository),
                    new CaricaContenutiCTRL(contenutoRepository),
                    new RicercaDocumentoCTRL(contenutoRepository),
                    new CreaCartellaCTRL(cartellaRepository),
                    cartellaRepository);
            b.setExtraDependencies(new EliminaCartellaCTRL(cartellaRepository),
                    new ScaricaContenutoCTRL(contenutoRepository),
                    new ScaricaCartellaCTRL(cartellaRepository, contenutoRepository),
                    new SpostaContenutoCTRL(contenutoRepository),
                    new DuplicaContenutoCTRL(contenutoRepository),
                    new ModificaContenutoCTRL(contenutoRepository),
                    new ModificaCartellaCTRL(cartellaRepository),
                    new ModificaPrivacyContenutoCTRL(contenutoRepository),
                    new ModificaPrivacyCartellaCTRL(cartellaRepository, contenutoRepository),
                    new GeneraLinkCartellaCTRL(linkRepository),
                    new GeneraLinkContenutoCTRL(linkRepository),
                    contenutoRepository);
            return loadFxml(IMieiContenutiBND.class, b);
        });

        reg("SchermataVisibilitaProfilo", () -> {
            SchermataVisibilitaProfiloBND b = new SchermataVisibilitaProfiloBND();
            b.setModificaVisibilitaCtrl(new ModificaVisibilitaCTRL(studenteRepository));
            return loadFxml(SchermataVisibilitaProfiloBND.class, b);
        });

        reg("SchermataCondivisioni", () -> {
            SchermataCondivisioniBND b = new SchermataCondivisioniBND();
            b.setDependencies(new CondivisioniCTRL(linkRepository),
                    new GeneraLinkPortfolioCTRL(linkRepository),
                    new RevocaLinkCTRL(linkRepository));
            return loadFxml(SchermataCondivisioniBND.class, b);
        });
        reg("PopupCondivisione", () -> loadFxml(PopupCondivisioneBND.class, new PopupCondivisioneBND()));

        reg("RisultatiRicerca", () -> {
            RisultatiRicercaBND b = new RisultatiRicercaBND();
            b.setRicercaProfiloCtrl(new RicercaProfiloCTRL(studenteRepository));
            return loadFxml(RisultatiRicercaBND.class, b);
        });
        reg("SchermataProfiloStudente", () -> {
            SchermataProfiloStudenteBND b = new SchermataProfiloStudenteBND();
            b.setRepositories(studenteRepository, cartellaRepository, contenutoRepository);
            return loadFxml(SchermataProfiloStudenteBND.class, b);
        });

        reg("InviaSegnalazione", () -> {
            InviaSegnalazioneBND b = new InviaSegnalazioneBND();
            b.setInviaSegnalazioneCtrl(new InviaSegnalazioneCTRL(segnalazioneRepository));
            return loadFxml(InviaSegnalazioneBND.class, b);
        });

                reg("SchermataAperturaLink", () -> {
            SchermataAperturaLinkBND b = new SchermataAperturaLinkBND();
            b.setRepositories(linkRepository, contenutoRepository);
            return loadFxml(SchermataAperturaLinkBND.class, b);
        });
                reg("SchermataIdentificazione", () -> {
            SchermataIdentificazioneBND b = new SchermataIdentificazioneBND();
            b.setIdentificazioneCtrl(new IdentificazioneCTRL(utenteEsternoRepository));
            return loadFxml(SchermataIdentificazioneBND.class, b);
        });

        reg("AdminDashboard", () -> loadFxml(AdminDashboardBND.class, new AdminDashboardBND()));

        reg("ListaSegnalazioni", () -> {
            ListaSegnalazioniBND b = new ListaSegnalazioniBND();
            b.setSegnalazioniCtrl(new SegnalazioniCTRL(segnalazioneRepository));
            return loadFxml(ListaSegnalazioniBND.class, b);
        });

        reg("ContenutoSegnalato", () -> {
            DettagliSegnalazioneBND b = new DettagliSegnalazioneBND();
            b.setEliminaContenutiCtrl(new EliminaContenutiCTRL(contenutoRepository));
            return loadFxml(DettagliSegnalazioneBND.class, b);
        });
    }

    private void reg(String name, java.util.function.Supplier<Parent> factory) {
        SceneManager.registerSceneFactory(name, factory);
    }

    private Parent loadFxml(Class<?> bndClass, Object controller) {
        try {
            String path = "/fxml/" + bndClass.getSimpleName().replace("BND", "") + ".fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            loader.setController(controller);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Cannot load " + bndClass.getSimpleName(), e);
        }
    }
}
