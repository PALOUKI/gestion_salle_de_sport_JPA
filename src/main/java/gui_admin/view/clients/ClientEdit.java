package gui_admin.view.clients;

import entite.Client;
import gui_admin.gui_util.ActionButton;
import gui_admin.gui_util.GenericEdit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ClientEdit extends GenericEdit<Client> {

    private JTextField idField;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JTextField emailField;

    private JTextField searchField;
    private JButton searchButton;

    private JButton modifyButton = new ActionButton("Modifier", ActionButton.ButtonType.MODIFY);
    private JButton deleteButton = new ActionButton("Supprimer", ActionButton.ButtonType.DELETE);

    // Formatter pour la date
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public ClientEdit(List<List<Object>> tableData, List<String> columnNames) {
        super(tableData, columnNames);

        idField = new JTextField();
        idField.setEnabled(false); // L'ID n'est pas modifiable par l'utilisateur
        nomField = new JTextField();
        prenomField = new JTextField();
        dateNaissanceField = new JTextField();
        emailField = new JTextField();

        searchField = new JTextField(20);
        searchButton = new ActionButton("Rechercher", ActionButton.ButtonType.SEARCH);

        setupMainLayout();
    }

    public ClientEdit(Client entite, List<List<Object>> tableData, List<String> columnNames) {
        this(tableData, columnNames);
        setEntity(entite);
        initFormWithEntity(entite);
    }

    private void setupMainLayout() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(240, 242, 245));

        JLabel globalTitleLabel = new JLabel("Gestion des Clients", SwingConstants.LEFT);
        globalTitleLabel.setFont(new Font("Goldman", Font.BOLD, 25));
        globalTitleLabel.setForeground(new Color(32, 64, 128));
        globalTitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(globalTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 242, 245));

        JPanel detailsPanel = createDetailsPanel();
        contentPanel.add(detailsPanel, BorderLayout.NORTH);

        JPanel listSectionPanel = new JPanel(new BorderLayout(0, 10));
        listSectionPanel.setBackground(new Color(240, 242, 245));

        JLabel listTitleLabel = new JLabel("Liste des clients", SwingConstants.LEFT);
        listTitleLabel.setFont(new Font("Goldman", Font.BOLD, 20));
        listTitleLabel.setForeground(new Color(32, 64, 128));
        listTitleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        listSectionPanel.add(listTitleLabel, BorderLayout.NORTH);

        JPanel searchAndTablePanel = createSearchAndTablePanel();
        listSectionPanel.add(searchAndTablePanel, BorderLayout.CENTER);

        contentPanel.add(listSectionPanel, BorderLayout.CENTER);
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel globalActionButtonsPanel = createGlobalActionButtonsPanel();
        this.add(globalActionButtonsPanel, BorderLayout.SOUTH);
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
                "Détails du Client",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Goldman", Font.BOLD, 16),
                new Color(32, 64, 128)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;
        // Ligne ID
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        form.add(new JLabel("ID :"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        idField.setPreferredSize(new Dimension(250, 30));
        form.add(idField, gbc);
        row++;

        // Ligne Nom
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        form.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        nomField.setPreferredSize(new Dimension(250, 30));
        form.add(nomField, gbc);
        row++;

        // Ligne Prénom
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        form.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        prenomField.setPreferredSize(new Dimension(250, 30));
        form.add(prenomField, gbc);
        row++;

        // Ligne Date de Naissance
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        form.add(new JLabel("Date Naissance (jj/mm/aaaa hh:mm) :"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        dateNaissanceField.setPreferredSize(new Dimension(250, 30));
        form.add(dateNaissanceField, gbc);
        row++;

        // Ligne Email
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        form.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        emailField.setPreferredSize(new Dimension(250, 30));
        form.add(emailField, gbc);
        row++;

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
        searchControlsPanel.add(new JLabel("Rechercher (Nom/Prénom/Email) :"));
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
            setEntity(new Client());
        }

        Client entite = (Client) getEntity();
        entite.setNom(nomField.getText());
        entite.setPrenom(prenomField.getText());
        entite.setEmail(emailField.getText());
        try {
            // Ajout des secondes si elles ne sont pas fournies par l'utilisateur
            String dateText = dateNaissanceField.getText();
            if(dateText.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                dateText += ":00";
            }
            entite.setDateNaissance(LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Le format de la date est invalide. Utilisez jj/mm/aaaa hh:mm.");
        }
    }

    @Override
    public void initFormWithEntity(Client client) {
        if (client != null) {
            idField.setText(client.getId() != null ? String.valueOf(client.getId()) : "");
            nomField.setText(client.getNom());
            prenomField.setText(client.getPrenom());
            dateNaissanceField.setText(client.getDateNaissance() != null ? client.getDateNaissance().format(DATE_FORMATTER) : "");
            emailField.setText(client.getEmail());
        } else {
            clearForm();
        }
    }

    @Override
    public void clearForm() {
        idField.setText("");
        nomField.setText("");
        prenomField.setText("");
        dateNaissanceField.setText("");
        emailField.setText("");
        setEntity(null);
        searchField.setText("");
    }

    @Override
    public JButton getModifyButton() { return this.modifyButton; }
    @Override
    public JButton getDeleteButton() { return this.deleteButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
}