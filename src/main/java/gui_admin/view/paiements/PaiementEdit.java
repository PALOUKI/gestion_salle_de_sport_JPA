package gui_admin.view.paiements;

import entite.Abonnement;
import entite.MoyenDePaiement;
import entite.Paiement;
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

public class PaiementEdit extends GenericEdit<Paiement> {

    private JTextField idField;
    private JComboBox<Abonnement> abonnementComboBox;
    private JComboBox<MoyenDePaiement> moyenDePaiementComboBox;
    private JTextField datePaiementField;
    private JTextField montantField;

    private JTextField searchField;
    private JButton searchButton;

    private JButton modifyButton = new ActionButton("Modifier", ActionButton.ButtonType.MODIFY);
    private JButton deleteButton = new ActionButton("Supprimer", ActionButton.ButtonType.DELETE);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PaiementEdit(List<List<Object>> tableData, List<String> columnNames, List<Abonnement> allAbonnements, List<MoyenDePaiement> allMoyens) {
        super(tableData, columnNames);

        idField = new JTextField();
        idField.setEnabled(false);

        abonnementComboBox = new JComboBox<>(allAbonnements.toArray(new Abonnement[0]));
        abonnementComboBox.setRenderer(new PlaceholderComboBoxRenderer<>("Sélectionnez un abonnement..."));

        moyenDePaiementComboBox = new JComboBox<>(allMoyens.toArray(new MoyenDePaiement[0]));
        moyenDePaiementComboBox.setRenderer(new PlaceholderComboBoxRenderer<>("Sélectionnez un moyen de paiement..."));

        datePaiementField = new JTextField();
        montantField = new JTextField();
        searchField = new JTextField(20);
        searchButton = new ActionButton("Rechercher", ActionButton.ButtonType.SEARCH);

        setupMainLayout();
    }

    private void setupMainLayout() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(240, 242, 245));

        JLabel globalTitleLabel = new JLabel("Gestion des Paiements", SwingConstants.LEFT);
        globalTitleLabel.setFont(new Font("Goldman", Font.BOLD, 25));
        globalTitleLabel.setForeground(new Color(32, 64, 128));
        globalTitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(globalTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.add(createDetailsPanel(), BorderLayout.NORTH);

        JPanel listSectionPanel = new JPanel(new BorderLayout(0, 10));
        listSectionPanel.setBackground(new Color(240, 242, 245));
        JLabel listTitleLabel = new JLabel("Liste des paiements", SwingConstants.LEFT);
        listTitleLabel.setFont(new Font("Goldman", Font.BOLD, 20));
        listTitleLabel.setForeground(new Color(32, 64, 128));
        listTitleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        listSectionPanel.add(listTitleLabel, BorderLayout.NORTH);
        listSectionPanel.add(createSearchAndTablePanel(), BorderLayout.CENTER);
        contentPanel.add(listSectionPanel, BorderLayout.CENTER);

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(createGlobalActionButtonsPanel(), BorderLayout.SOUTH);
    }

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
                "Détails du Paiement", TitledBorder.LEFT, TitledBorder.TOP,
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
        form.add(new JLabel("Abonnement concerné :"), gbc);
        gbc.gridx = 1;
        abonnementComboBox.setPreferredSize(new Dimension(250, 30));
        form.add(abonnementComboBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Moyen de Paiement :"), gbc);
        gbc.gridx = 1;
        moyenDePaiementComboBox.setPreferredSize(new Dimension(250, 30));
        form.add(moyenDePaiementComboBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Date de paiement (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1;
        datePaiementField.setPreferredSize(new Dimension(250, 30));
        form.add(datePaiementField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Montant :"), gbc);
        gbc.gridx = 1;
        montantField.setPreferredSize(new Dimension(250, 30));
        form.add(montantField, gbc);

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
        searchControlsPanel.add(new JLabel("Rechercher par Membre :"));
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
            setEntity(new Paiement());
        }
        Paiement entite = (Paiement) getEntity();

        if (abonnementComboBox.getSelectedItem() == null) throw new IllegalArgumentException("Veuillez sélectionner un abonnement.");
        if (moyenDePaiementComboBox.getSelectedItem() == null) throw new IllegalArgumentException("Veuillez sélectionner un moyen de paiement.");

        entite.setAbonnement((Abonnement) abonnementComboBox.getSelectedItem());
        entite.setMoyenDePaiement((MoyenDePaiement) moyenDePaiementComboBox.getSelectedItem());

        try {
            entite.setDateDePaiement(LocalDateTime.parse(datePaiementField.getText(), DATE_FORMATTER));
            entite.setMontant(Integer.parseInt(montantField.getText()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez jj/mm/aaaa hh:mm.");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Le montant doit être un nombre entier valide.");
        }
    }

    @Override
    public void initFormWithEntity(Paiement paiement) {
        if (paiement != null) {
            idField.setText(paiement.getId() != null ? String.valueOf(paiement.getId()) : "");
            abonnementComboBox.setSelectedItem(paiement.getAbonnement());
            moyenDePaiementComboBox.setSelectedItem(paiement.getMoyenDePaiement());
            datePaiementField.setText(paiement.getDateDePaiement() != null ? paiement.getDateDePaiement().format(DATE_FORMATTER) : "");
            montantField.setText(String.valueOf(paiement.getMontant()));
        } else {
            clearForm();
        }
    }

    @Override
    public void clearForm() {
        idField.setText("");
        abonnementComboBox.setSelectedItem(null);
        moyenDePaiementComboBox.setSelectedItem(null);
        datePaiementField.setText("");
        montantField.setText("");
        setEntity(null);
        searchField.setText("");
    }

    @Override public JButton getModifyButton() { return this.modifyButton; }
    @Override public JButton getDeleteButton() { return this.deleteButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
}