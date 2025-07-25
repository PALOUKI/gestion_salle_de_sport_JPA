package gui_admin.view.abonnements;

import entite.Abonnement;
import entite.Membre;
import entite.TypeAbonnement;
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

public class AbonnementEdit extends GenericEdit<Abonnement> {

    private JTextField idField;
    private JComboBox<Membre> membreComboBox;
    private JComboBox<TypeAbonnement> typeAbonnementComboBox;
    private JTextField dateDebutField;
    private JTextField dateFinField;

    private JTextField searchField;
    private JButton searchButton;

    private JButton modifyButton = new ActionButton("Modifier", ActionButton.ButtonType.MODIFY);
    private JButton deleteButton = new ActionButton("Supprimer", ActionButton.ButtonType.DELETE);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public AbonnementEdit(List<List<Object>> tableData, List<String> columnNames, List<Membre> allMembres, List<TypeAbonnement> allTypes) {
        super(tableData, columnNames);

        idField = new JTextField();
        idField.setEnabled(false);

        membreComboBox = new JComboBox<>(allMembres.toArray(new Membre[0]));
        membreComboBox.setRenderer(new PlaceholderComboBoxRenderer<>("Sélectionnez un membre..."));

        typeAbonnementComboBox = new JComboBox<>(allTypes.toArray(new TypeAbonnement[0]));
        typeAbonnementComboBox.setRenderer(new PlaceholderComboBoxRenderer<>("Sélectionnez un type..."));

        dateDebutField = new JTextField();
        dateFinField = new JTextField();
        searchField = new JTextField(20);
        searchButton = new ActionButton("Rechercher", ActionButton.ButtonType.SEARCH);

        setupMainLayout();
    }

    private void setupMainLayout() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(240, 242, 245));

        JLabel globalTitleLabel = new JLabel("Gestion des Abonnements", SwingConstants.LEFT);
        globalTitleLabel.setFont(new Font("Goldman", Font.BOLD, 25));
        globalTitleLabel.setForeground(new Color(32, 64, 128));
        globalTitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(globalTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.add(createDetailsPanel(), BorderLayout.NORTH);

        JPanel listSectionPanel = new JPanel(new BorderLayout(0, 10));
        listSectionPanel.setBackground(new Color(240, 242, 245));
        JLabel listTitleLabel = new JLabel("Liste des abonnements", SwingConstants.LEFT);
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
                "Détails de l'Abonnement", TitledBorder.LEFT, TitledBorder.TOP,
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
        form.add(new JLabel("Membre :"), gbc);
        gbc.gridx = 1;
        membreComboBox.setPreferredSize(new Dimension(250, 30));
        form.add(membreComboBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Type d'abonnement :"), gbc);
        gbc.gridx = 1;
        typeAbonnementComboBox.setPreferredSize(new Dimension(250, 30));
        form.add(typeAbonnementComboBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Date de début (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1;
        dateDebutField.setPreferredSize(new Dimension(250, 30));
        form.add(dateDebutField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Date de fin (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1;
        dateFinField.setPreferredSize(new Dimension(250, 30));
        form.add(dateFinField, gbc);

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
            setEntity(new Abonnement());
        }
        Abonnement entite = (Abonnement) getEntity();

        if (membreComboBox.getSelectedItem() == null) throw new IllegalArgumentException("Veuillez sélectionner un membre.");
        if (typeAbonnementComboBox.getSelectedItem() == null) throw new IllegalArgumentException("Veuillez sélectionner un type d'abonnement.");

        entite.setMembre((Membre) membreComboBox.getSelectedItem());
        entite.setTypeAbonnement((TypeAbonnement) typeAbonnementComboBox.getSelectedItem());

        try {
            LocalDateTime dateDebut = LocalDateTime.parse(dateDebutField.getText(), DATE_FORMATTER);
            entite.setDateDebut(dateDebut);

            if (dateFinField.getText() != null && !dateFinField.getText().trim().isEmpty()) {
                LocalDateTime dateFin = LocalDateTime.parse(dateFinField.getText(), DATE_FORMATTER);
                if (dateFin.isBefore(dateDebut)) {
                    throw new IllegalArgumentException("La date de fin doit être après la date de début.");
                }
                entite.setDateFin(dateFin);
            } else {
                entite.setDateFin(null); // Permet une date de fin vide
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez jj/mm/aaaa hh:mm.");
        }
    }

    @Override
    public void initFormWithEntity(Abonnement abonnement) {
        if (abonnement != null) {
            idField.setText(abonnement.getId() != null ? String.valueOf(abonnement.getId()) : "");
            membreComboBox.setSelectedItem(abonnement.getMembre());
            typeAbonnementComboBox.setSelectedItem(abonnement.getTypeAbonnement());
            dateDebutField.setText(abonnement.getDateDebut() != null ? abonnement.getDateDebut().format(DATE_FORMATTER) : "");
            dateFinField.setText(abonnement.getDateFin() != null ? abonnement.getDateFin().format(DATE_FORMATTER) : "");
        } else {
            clearForm();
        }
    }

    @Override
    public void clearForm() {
        idField.setText("");
        membreComboBox.setSelectedItem(null);
        typeAbonnementComboBox.setSelectedItem(null);
        dateDebutField.setText("");
        dateFinField.setText("");
        setEntity(null);
        searchField.setText("");
    }

    @Override public JButton getModifyButton() { return this.modifyButton; }
    @Override public JButton getDeleteButton() { return this.deleteButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
}