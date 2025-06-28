/*package gui_admin.view.horaires;

import entite.Horaire;
import gui_admin.gui_util.GenericEdit;

public class HoraireEdit  extends GenericEdit<Horaire> {


    private JPanel createSearchAndTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        JPanel searchControlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchControlsPanel.setBackground(List<List<Object>> tableData, List<String> columnNames) {
            super(tableData, columnNames);

            idField = new JTextField();
            idField.setEnabled(false);
            debutField = new JTextField();
            finField = new JTextField();

            searchField = new JTextField(20);
            searchButton = new ActionButton("Rechercher", ActionButton.ButtonType.SEARCH);

            setupMainLayout();
        }

    private void setupMainLayout() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(240, 242, 245));

        JLabel globalTitleLabel = new JLabel("Gestion des Horaires", SwingConstants.LEFT);
        globalTitleLabel.setFont(new Font("Goldman", Font.BOLD, 25));
        globalTitleLabel.setForeground(new Color(32, 64, 128));
        globalTitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        this.add(globalTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.add(createDetailsPanel(), BorderLayout.NORTH);

        JPanel listSectionPanel = new JPanel(new BorderLayout(0, 10));
        listSectionPanel.Color.WHITE);
        searchControlsPanel.add(new JLabel("Rechercher :"));
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
            setEntity(new Horaire());
        }
        Horaire entite = (Horaire) getEntity();

        try {
            LocalDateTime debut = LocalDateTime.parse(debutField.getText(), DATE_FORMATTER);
            LocalDateTime fin = LocalDateTime.parse(finField.getText(), DATE_FORMATTER);

            if (fin.isBefore(debut) || fin.isEqual(debut)) {
                throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début.");
            }
            entite.setDebut(debut);
            entite.setFin(fin);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez jj/mm/aaaa hh:mm.");
        }
    }

    @Override
    public void initFormWithEntity(Horaire horaire) {
        if (horaire != null) {
            setBackground(new Color(240, 242, 245));
            JLabel listTitleLabel = new JLabel("Liste des horaires", SwingConstants.LEFT);
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
                    "Détails de l'Horaire", TitledBorder.LEFT, TitledBorder.TOP,
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
            form.add(id            idField.setText(horaire.getId() != null ? String.valueOf(horaire.getId()) : "");
            debutField.setText(horaire.getDebut() != null ? horaire.getDebut().format(DATE_FORMATTER) : "");
            finField.setText(horaire.getFin() != null ? horaire.getFin().format(DATE_FORMATTER) : "");
        } else {
            clearForm();
        }
    }

    @Override
    public void clearForm() {
        idField.setText("");
        debutField.setText("");
        finField.setText("");
        setEntity(null);
        searchField.setText("");
    }

    @Override public JButton getModifyButton() { return this.modifyButton; }
    @Override public JButton getDeleteButton() { return this.deleteButton; }
    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
}

 */
