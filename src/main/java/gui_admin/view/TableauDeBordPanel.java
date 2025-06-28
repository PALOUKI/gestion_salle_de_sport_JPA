package gui_admin.view;

import entite.Client;
import entite.Paiement;
import gui_admin.gui_util.CustomTablePanel;
import gui_admin.gui_util.MyWindow1;
import serviceJpa.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableauDeBordPanel extends JPanel {

    private final MembreService membreService = new MembreService();
    private final PaiementService paiementService = new PaiementService();
    private final ClientService clientService = new ClientService();
    private final AbonnementService abonnementService = new AbonnementService();
    private final DemandeInscriptionService demandeInscriptionService = new DemandeInscriptionService();

    private final JLabel titre;
    private final String fullTitleText = "Bienvenue sur le tableau de bord !";
    private int charIndex = 0;
    private Timer typingTimer = null;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TableauDeBordPanel(MyWindow1 parentWindow) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        // Titre animé
        titre = new JLabel();
        titre.setFont(new Font("Goldman", Font.BOLD, 30));
        titre.setForeground(new Color(32, 64, 128));
        titre.setBorder(new EmptyBorder(25, 30, 15, 0));
        this.add(titre, BorderLayout.NORTH);
        typingTimer = new Timer(120, e -> {
            if (charIndex < fullTitleText.length()) {
                titre.setText(fullTitleText.substring(0, charIndex + 1));
                charIndex++;
            } else {
                typingTimer.stop();
            }
        });
        typingTimer.start();

        // Panneau central avec défilement
        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBorder(new EmptyBorder(10, 30, 30, 30));
        centre.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(centre);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);

        // Panneau des statistiques
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        statsPanel.setOpaque(false);

        // --- Carte Membres inscrits ---
        JPanel membresPanel = createStatsCard("👤", "Membres inscrits", String.valueOf(membreService.listerTous().size()), new Color(235, 240, 255));
        addNavigationListener(membresPanel, parentWindow, "page_membres", new Color(220, 230, 255));
        statsPanel.add(membresPanel);

        // --- Carte Abonnements ---
        JPanel abonnementsPanel = createStatsCard("🗓️", "Abonnements Actifs", String.valueOf(abonnementService.listerTous().size()), new Color(255, 245, 230));
        addNavigationListener(abonnementsPanel, parentWindow, "page_abonnements", new Color(255, 235, 210));
        statsPanel.add(abonnementsPanel);

        // --- Carte Demandes d'Inscription ---
        JPanel inscriptionsPanel = createStatsCard("✉️", "Demandes d'Inscription", String.valueOf(demandeInscriptionService.listerTous().size()), new Color(230, 255, 235));
        addNavigationListener(inscriptionsPanel, parentWindow, "page_demande_inscription", new Color(215, 250, 220));
        statsPanel.add(inscriptionsPanel);

        centre.add(statsPanel);
        centre.add(Box.createVerticalStrut(30));

        // --- Section des tables ---
        List<Paiement> recentPaiements = paiementService.listerTous().stream().limit(5).collect(Collectors.toList());
        centre.add(createTableSection("Historique des paiements récents",
                new String[]{"ID", "Membre", "Montant", "Date Paiement", "Moyen"},
                convertPaiementsToTableData(recentPaiements)));
        centre.add(Box.createVerticalStrut(30));

        List<Client> latestClients = clientService.listerTous().stream().limit(5).collect(Collectors.toList());
        centre.add(createTableSection("Nouveaux clients",
                new String[]{"ID", "Nom", "Prénom", "Date Naissance", "Email"},
                convertClientsToTableData(latestClients)));
    }

    /**
     * Crée une carte de statistique visuelle avec une bordure par défaut.
     */
    private JPanel createStatsCard(String iconText, String labelText, String valueText, Color bgColor) {
        JPanel cardPanel = new JPanel(new BorderLayout(15, 5));
        cardPanel.setBackground(bgColor);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 48));

        JLabel valueLabel = new JLabel(valueText);
        valueLabel.setFont(new Font("Goldman", Font.BOLD, 36));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Goldman", Font.PLAIN, 15));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cardPanel.add(iconLabel, BorderLayout.WEST);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        cardPanel.add(textLabel, BorderLayout.SOUTH);
        return cardPanel;
    }

    /**
     * Ajoute un listener à un panneau pour le rendre cliquable et naviguer vers une page.
     * Gère les effets visuels de survol (couleur de fond ET bordure).
     */
    private void addNavigationListener(JPanel panel, MyWindow1 parentWindow, String cardName, Color hoverColor) {
        // Sauvegarde des états d'origine
        Color originalColor = panel.getBackground();
        Border originalBorder = panel.getBorder();

        // Création de la nouvelle bordure de survol
        Border hoverBorder = BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 90, 180), 2, true), // Bordure bleue plus épaisse
                // On garde le même padding intérieur
                new EmptyBorder(19, 19, 19, 19) // 19 au lieu de 20 pour compenser l'épaisseur de la bordure
        );

        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentWindow.navigateTo(cardName);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(hoverColor);
                panel.setBorder(hoverBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(originalColor);
                panel.setBorder(originalBorder);
            }
        });
    }

    /**
     * Crée une section de tableau avec un titre.
     */
    private JPanel createTableSection(String title, String[] columns, List<List<Object>> data) {
        JPanel tableSectionPanel = new JPanel(new BorderLayout());
        tableSectionPanel.setBackground(Color.WHITE);
        tableSectionPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel tableTitleLabel = new JLabel(title);
        tableTitleLabel.setFont(new Font("Goldman", Font.BOLD, 20));
        tableTitleLabel.setForeground(new Color(32, 64, 128));
        tableTitleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        CustomTablePanel customTable = new CustomTablePanel(data, Arrays.asList(columns));
        tableSectionPanel.add(tableTitleLabel, BorderLayout.NORTH);
        tableSectionPanel.add(customTable, BorderLayout.CENTER);
        return tableSectionPanel;
    }

    private List<List<Object>> convertClientsToTableData(List<Client> clients) {
        List<List<Object>> data = new ArrayList<>();
        for (Client client : clients) {
            data.add(Arrays.asList(
                    client.getId(),
                    client.getNom(),
                    client.getPrenom(),
                    client.getDateNaissance() != null ? client.getDateNaissance().format(DATE_FORMATTER) : "N/A",
                    client.getEmail()
            ));
        }
        return data;
    }

    private List<List<Object>> convertPaiementsToTableData(List<Paiement> paiements) {
        List<List<Object>> data = new ArrayList<>();
        for (Paiement p : paiements) {
            String membreStr = (p.getAbonnement() != null && p.getAbonnement().getMembre() != null && p.getAbonnement().getMembre().getClient() != null)
                    ? p.getAbonnement().getMembre().getClient().toString() : "N/A";
            data.add(Arrays.asList(
                    p.getId(),
                    membreStr,
                    p.getMontant(),
                    p.getDateDePaiement() != null ? p.getDateDePaiement().format(DATE_FORMATTER) : "N/A",
                    p.getMoyenDePaiement() != null ? p.getMoyenDePaiement().getLibelle() : "N/A"
            ));
        }
        return data;
    }
}