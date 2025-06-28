package gui_admin.gui_util;

import gui_admin.controllerJpa.*;
import gui_admin.view.TableauDeBordPanel;
import gui_admin.view.abonnements.AbonnementEdit;
import gui_admin.view.clients.ClientEdit;
import gui_admin.view.demande_inscriptions.DemandeInscriptionEdit;
import gui_admin.view.equipements.EquipementEdit;
//import gui_admin.view.horaires.HoraireEdit;
import gui_admin.view.membres.MembreEdit;
import gui_admin.view.moyen_de_paiements.MoyenDePaiementEdit;
import gui_admin.view.paiements.PaiementEdit;
import gui_admin.view.salles.SalleEdit;
import gui_admin.view.seances.SeanceEdit;
import gui_admin.view.tickets.TicketEdit;
import gui_admin.view.type_abonnements.TypeAbonnementEdit;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MyWindow1 extends MyWindow {

    private final TypeAbonnementController typeAbonnementController;
    private final ClientController clientController;
    private final MoyenDePaiementController moyenDePaiementController;
    private final DemandeInscriptionController demandeInscriptionController;
    private final MembreController membreController;
    private final SalleController salleController;
    private final TicketController ticketController;
    private final EquipementController equipementController;
    private final SeanceController seanceController;
    private final AbonnementController abonnementController;
    private final PaiementController paiementController;
    //private final HoraireController horaireController;

    private final CardLayout cardLayout;
    private final List<MyButton> menuButtons;
    private MyButton currentSelectedButton;

    public MyWindow1() {
        super();

        cardLayout = new CardLayout();
        center.setLayout(cardLayout);

        // --- Initialisation des Contrôleurs ---
        typeAbonnementController = new TypeAbonnementController();
        clientController = new ClientController();
        moyenDePaiementController = new MoyenDePaiementController();
        demandeInscriptionController = new DemandeInscriptionController();
        membreController = new MembreController();
        salleController = new SalleController();
        ticketController = new TicketController();
        equipementController = new EquipementController();
        seanceController = new SeanceController();
        abonnementController = new AbonnementController();
        paiementController = new PaiementController();
        //horaireController = new HoraireController();

        // --- Initialisation des Vues ---
        center.add(new TableauDeBordPanel(this), "page_dashboard"); // On passe 'this'
        center.add(typeAbonnementController.createAndConfigureEditPanelForAdd(), "page_type_abonnements");
        center.add(clientController.createAndConfigureEditPanelForAdd(), "page_clients");
        center.add(moyenDePaiementController.createAndConfigureEditPanelForAdd(), "page_moyen_paiements");
        center.add(demandeInscriptionController.createAndConfigureEditPanelForAdd(), "page_demande_inscription");
        center.add(membreController.createAndConfigureEditPanelForAdd(), "page_membres");
        center.add(salleController.createAndConfigureEditPanelForAdd(), "page_salles");
        center.add(ticketController.createAndConfigureEditPanelForAdd(), "page_tickets");
        center.add(equipementController.createAndConfigureEditPanelForAdd(), "page_equipements");
        center.add(seanceController.createAndConfigureEditPanelForAdd(), "page_seances");
        center.add(abonnementController.createAndConfigureEditPanelForAdd(), "page_abonnements");
        center.add(paiementController.createAndConfigureEditPanelForAdd(), "page_paiements");
       // center.add(horaireController.createAndConfigureEditPanelForAdd(), "page_horaires");

        // --- Définition des Boutons du Menu ---
        MyButton btn0_dashboard = new MyButton("Tableau de bord");
        MyButton btn1_typeAbonnements = new MyButton("Types abonnements");
        MyButton btn2_clients = new MyButton("Clients");
        MyButton btn3_moyenPaiements = new MyButton("Moyen de paiements");
        MyButton btn4_demandeInscription = new MyButton("Demande Inscription");
        MyButton btn5_membres = new MyButton("Membres");
        MyButton btn6_salles = new MyButton("Salles");
        MyButton btn7_tickets = new MyButton("Tickets");
        MyButton btn8_equipements = new MyButton("Equipements");
        MyButton btn9_seances = new MyButton("Séances");
        MyButton btn10_abonnements = new MyButton("Abonnements");
        MyButton btn11_paiements = new MyButton("Paiements");
        MyButton btn12_horaires = new MyButton("Horaires");

        menuButtons = Arrays.asList(
                btn0_dashboard, btn1_typeAbonnements, btn2_clients, btn3_moyenPaiements,
                btn4_demandeInscription, btn5_membres, btn6_salles, btn7_tickets,
                btn8_equipements, btn9_seances, btn10_abonnements, btn11_paiements, btn12_horaires
        );

        // --- Configuration des Panneaux ---
        setupNorthPanel();

        west.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.insets = new Insets(5, 0, 5, 0); gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.weighty = 0;
        west.add(new JPanel() {{ setOpaque(false); }}, gbc);

        int gridY = 1;
        addMenuButton(btn0_dashboard, "page_dashboard", gbc, gridY++);
        addMenuButton(btn1_typeAbonnements, "page_type_abonnements", gbc, gridY++);
        addMenuButton(btn2_clients, "page_clients", gbc, gridY++);
        addMenuButton(btn3_moyenPaiements, "page_moyen_paiements", gbc, gridY++);
        addMenuButton(btn4_demandeInscription, "page_demande_inscription", gbc, gridY++);
        addMenuButton(btn5_membres, "page_membres", gbc, gridY++);
        addMenuButton(btn6_salles, "page_salles", gbc, gridY++);
        addMenuButton(btn7_tickets, "page_tickets", gbc, gridY++);
        addMenuButton(btn8_equipements, "page_equipements", gbc, gridY++);
        addMenuButton(btn9_seances, "page_seances", gbc, gridY++);
        addMenuButton(btn10_abonnements, "page_abonnements", gbc, gridY++);
        addMenuButton(btn11_paiements, "page_paiements", gbc, gridY++);
        addMenuButton(btn12_horaires, "page_horaires", gbc, gridY++);

        gbc.gridy = gridY; gbc.weighty = 1.0;
        west.add(Box.createVerticalGlue(), gbc);

        MyLabel copyrightLabel = new MyLabel("@ copyright...chez root@hsa");
        copyrightLabel.setForeground(Color.WHITE);
        south.setLayout(new FlowLayout(FlowLayout.CENTER));
        south.add(copyrightLabel);

        if (!menuButtons.isEmpty()) {
            menuButtons.get(0).doClick();
        }
    }

    private void setupNorthPanel() {
        north.setLayout(new GridBagLayout());
        north.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 220)));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        titlePanel.setOpaque(false);

        URL iconUrl = MyWindow1.class.getResource("/gym_icon.png");
        if (iconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(iconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            titlePanel.add(new JLabel(new ImageIcon(scaledImage)));
        }

        MyLabel titleLabel = new MyLabel("CHEZ ROOT@HSA");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Goldman", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST; gbc.insets = new Insets(10, 20, 10, 20);
        north.add(titlePanel, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        north.add(Box.createHorizontalGlue(), gbc);

        MyLabel userLabel = new MyLabel("Connecté : Admin");
        userLabel.setForeground(new Color(220, 220, 255));
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        north.add(userLabel, gbc);
    }

    private void addMenuButton(MyButton button, String cardName, GridBagConstraints gbc, int gridY) {
        gbc.gridy = gridY;
        gbc.weighty = 0;
        west.add(button, gbc);
        button.setActionCommand(cardName); // Définit la commande pour la retrouver plus tard

        button.addActionListener(e -> {
            if (cardName.equals("page_dashboard")) {
                center.remove(0);
                center.add(new TableauDeBordPanel(this), "page_dashboard", 0);
            }
            else if (cardName.equals("page_type_abonnements")) { typeAbonnementController.prepareForAdd(); typeAbonnementController.updateTable(); }
            else if (cardName.equals("page_clients")) { clientController.prepareForAdd(); clientController.updateTable(); }
            else if (cardName.equals("page_moyen_paiements")) { moyenDePaiementController.prepareForAdd(); moyenDePaiementController.updateTable(); }
            else if (cardName.equals("page_demande_inscription")) { demandeInscriptionController.prepareForAdd(); demandeInscriptionController.updateTable(); }
            else if (cardName.equals("page_membres")) { membreController.prepareForAdd(); membreController.updateTable(); }
            else if (cardName.equals("page_salles")) { salleController.prepareForAdd(); salleController.updateTable(); }
            else if (cardName.equals("page_tickets")) { ticketController.prepareForAdd(); ticketController.updateTable(); }
            else if (cardName.equals("page_equipements")) { equipementController.prepareForAdd(); equipementController.updateTable(); }
            else if (cardName.equals("page_seances")) { seanceController.prepareForAdd(); seanceController.updateTable(); }
            else if (cardName.equals("page_abonnements")) { abonnementController.prepareForAdd(); abonnementController.updateTable(); }
            else if (cardName.equals("page_paiements")) { paiementController.prepareForAdd(); paiementController.updateTable(); }
            //else if (cardName.equals("page_horaires")) { horaireController.prepareForAdd(); horaireController.updateTable(); }

            cardLayout.show(center, cardName);

            if (currentSelectedButton != null) {
                currentSelectedButton.setSelected(false);
            }
            button.setSelected(true);
            currentSelectedButton = button;
        });
    }

    /**
     * Navigue vers une page spécifique en simulant un clic sur le bouton de menu correspondant.
     * @param cardName Le nom de la carte à afficher (ex: "page_demande_inscription").
     */
    public void navigateTo(String cardName) {
        for (MyButton button : menuButtons) {
            if (cardName.equals(button.getActionCommand())) {
                button.doClick();
                return;
            }
        }
    }
}