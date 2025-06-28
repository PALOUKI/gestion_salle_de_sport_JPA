package gui_admin.view.demande_inscriptions;

import entite.Client;
import entite.DemandeInscription;
import gui_admin.gui_util.ActionButton;
import gui_admin.gui_util.GenericEdit;
import gui_admin.gui_util.PlaceholderComboBoxRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DemandeInscriptionEdit extends GenericEdit<DemandeInscription> {

    // ... (champs inchangés)
    private JTextField idField;
    private JComboBox<Client> clientComboBox;
    private JTextField dateDemandeField;
    private JTextField dateTraitementField;
    private JTextField searchField;
    private JButton searchButton;
    private JButton modifyButton = new ActionButton("Modifier", ActionButton.ButtonType.MODIFY);
    private JButton deleteButton = new ActionButton("Supprimer", ActionButton.ButtonType.DELETE);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public DemandeInscriptionEdit(List<List<Object>> tableData, List<String> columnNames, List<Client> allClients) {
        super(tableData, columnNames);

        idField = new JTextField();
        idField.setEnabled(false);
        clientComboBox = new JComboBox<>(allClients.toArray(new Client[0]));
        clientComboBox.setRenderer(new PlaceholderComboBoxRenderer<>("Veuillez sélectionner un client..."));

        dateDemandeField = new JTextField();
        dateTraitementField = new JTextField();
        searchField = new JTextField(20);
        searchButton = new ActionButton("Rechercher", ActionButton.ButtonType.SEARCH);

        setupMainLayout();
        clearForm();
    }

    private void setupMainLayout() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(240, 242, 245));

        JLabel globalTitleLabel = new JLabel("Gestion des Demandes d'Inscription", SwingConstants.LEFT);
        globalTitleLabel.setFont(new Font("Goldman", Font.BOLD, 25));
        globalTitleLabel.setForeground(new Color(32, 64, 128));
        globalTitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(globalTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.add(createDetailsPanel(), BorderLayout.NORTH);

        // --- CORRECTION : Ajout du panneau de section pour la liste ---
        JPanel listSectionPanel = new JPanel(new BorderLayout(0, 10));
        listSectionPanel.setBackground(new Color(240, 242, 245));

        JLabel listTitleLabel = new JLabel("Liste des demandes d'inscription", SwingConstants.LEFT);
        listTitleLabel.setFont(new Font("Goldman", Font.BOLD, 20));
        listTitleLabel.setForeground(new Color(32, 64, 128));
        listTitleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        listSectionPanel.add(listTitleLabel, BorderLayout.NORTH);

        JPanel searchAndTablePanel = createSearchAndTablePanel();
        listSectionPanel.add(searchAndTablePanel, BorderLayout.CENTER);

        contentPanel.add(listSectionPanel, BorderLayout.CENTER);
        // --- FIN DE LA CORRECTION ---

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(createGlobalActionButtonsPanel(), BorderLayout.SOUTH);
    }

    // ... (Le reste du fichier reste identique)
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        ));

        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(32, 64, 128), 2),
                "Détails de la Demande", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Goldman", Font.BOLD, 16), new Color(32, 64, 128)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("ID :"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        idField.setPreferredSize(new Dimension(250, 30));
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Client :"), gbc);
        gbc.gridx = 1;
        clientComboBox.setPreferredSize(new Dimension(250, 30));
        form.add(clientComboBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Date demande (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1;
        dateDemandeField.setPreferredSize(new Dimension(250, 30));
        form.add(dateDemandeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Date traitement (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1;
        dateTraitementField.setPreferredSize(new Dimension(250, 30));
        form.add(dateTraitementField, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSearchAndTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JPanel searchControlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchControlsPanel.setBackground(Color.WHITE);
        searchControlsPanel.add(new JLabel("Rechercher par Client :"));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchControlsPanel.add(searchField);
        searchControlsPanel.add(searchButton);
        panel.add(searchControlsPanel, BorderLayout.NORTH);

        panel.add(this.customTablePanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGlobalActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(this.modifyButton);
        panel.add(this.deleteButton);
        panel.add(this.buttonPanel.getSaveButton());
        panel.add(this.buttonPanel.getCancelButton());
        return panel;
    }

    @Override
    public void initEntityFromForm() {
        if (currentEntity == null) {
            setEntity(new DemandeInscription());
        }
        DemandeInscription entite = (DemandeInscription) getEntity();
        Object selectedClient = clientComboBox.getSelectedItem();
        if (selectedClient == null) {
            throw new IllegalArgumentException("Veuillez sélectionner un client.");
        }
        entite.setClient((Client) selectedClient);
        try {
            String dateDemandeText = dateDemandeField.getText();
            if (dateDemandeText == null || dateDemandeText.trim().isEmpty()) {
                throw new IllegalArgumentException("La date de demande ne peut pas être vide.");
            }
            entite.setDateDeDemande(LocalDateTime.parse(dateDemandeText, DATE_FORMATTER));
            if (dateTraitementField.getText() != null && !dateTraitementField.getText().trim().isEmpty()) {
                entite.setDateDeTraitement(LocalDateTime.parse(dateTraitementField.getText(), DATE_FORMATTER));
            } else {
                entite.setDateDeTraitement(null);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez jj/mm/aaaa hh:mm.");
        }
    }

    @Override
    public void initFormWithEntity(DemandeInscription di) {
        if (di != null) {
            idField.setText(di.getId() != null ? String.valueOf(di.getId()) : "");
            clientComboBox.setSelectedItem(di.getClient());
            dateDemandeField.setText(di.getDateDeDemande() != null ? di.getDateDeDemande().format(DATE_FORMATTER) : "");
            dateTraitementField.setText(di.getDateDeTraitement() != null ? di.getDateDeTraitement().format(DATE_FORMATTER) : "");
        } else {
            clearForm();
        }
    }

    @Override
    public void clearForm() {
        idField.setText("");
        clientComboBox.setSelectedItem(null);
        dateDemandeField.setText("");
        dateTraitementField.setText("");
        setEntity(null);
        searchField.setText("");
    }

    @Override public JButton getModifyButton() { return this.modifyButton; }
    @Override public JButton getDeleteButton() { return this.deleteButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
}