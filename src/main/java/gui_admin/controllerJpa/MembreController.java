package gui_admin.controllerJpa;

import entite.Client;
import entite.Membre;
import gui_admin.view.membres.MembreEdit;
import serviceJpa.ClientService;
import serviceJpa.MembreService;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MembreController extends GenericCrudController<Membre, Integer> {

    private MembreService membreService;
    private ClientService clientService;

    private static final DateTimeFormatter TABLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MembreController() {
        super(new MembreService(), Membre.class);
        this.membreService = (MembreService) service;
        this.clientService = new ClientService();
    }

    public MembreEdit createAndConfigureEditPanelForAdd() {
        List<String> columnNames = Arrays.asList("ID Membre", "Client", "Date d'Inscription");
        List<List<Object>> tableData = convertEntitiesToTableData(service.listerTous());

        // Au départ, on ne fournit qu'une liste vide à la vue, elle sera peuplée par prepareForAdd()
        MembreEdit edit = new MembreEdit(tableData, columnNames, new ArrayList<>());

        setEditPanel(edit);

        // Prépare le formulaire pour un ajout, ce qui inclut le remplissage de la ComboBox
        prepareForAdd();

        return edit;
    }

    @Override
    public void prepareForAdd() {
        super.prepareForAdd(); // Appelle clearForm() et met l'entité à null

        // Logique spécifique pour peupler la ComboBox avec les clients non-membres
        List<Client> allClients = clientService.listerTous();
        List<Membre> allMembers = membreService.listerTous();
        List<Integer> memberClientIds = allMembers.stream()
                .map(membre -> membre.getClient().getId())
                .collect(Collectors.toList());

        List<Client> availableClients = allClients.stream()
                .filter(client -> !memberClientIds.contains(client.getId()))
                .collect(Collectors.toList());

        // Met à jour la ComboBox dans la vue avec la liste filtrée
        if (editPanel instanceof MembreEdit) {
            ((MembreEdit) editPanel).updateClientComboBox(availableClients);
        }
    }

    @Override
    public void setEditPanel(gui_admin.gui_util.GenericEdit<Membre> editPanel) {
        super.setEditPanel(editPanel);
        if (this.editPanel instanceof MembreEdit) {
            MembreEdit membreEditPanel = (MembreEdit) this.editPanel;
            membreEditPanel.getSearchButton().addActionListener(e -> performSearchOperation());
            membreEditPanel.getSearchField().addActionListener(e -> performSearchOperation());
        }
    }

    @Override
    protected void performSearchOperation() {
        String searchTerm = ((MembreEdit)editPanel).getSearchField().getText();
        try {
            List<Membre> searchResults = membreService.rechercher(searchTerm);
            editPanel.getCustomTablePanel().updateTableData(convertEntitiesToTableData(searchResults));
            if (searchResults.isEmpty() && !searchTerm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(editPanel, "Aucun membre trouvé pour : '" + searchTerm + "'", "Résultats de recherche", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(editPanel, "Erreur lors de la recherche : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected List<List<Object>> convertEntitiesToTableData(List<Membre> membres) {
        List<List<Object>> data = new ArrayList<>();
        if (membres != null) {
            for (Membre m : membres) {
                String clientStr = (m.getClient() != null) ? m.getClient().getNom() + " " + m.getClient().getPrenom() : "N/A";
                String dateInscriptionStr = (m.getDateInscription() != null) ? m.getDateInscription().format(TABLE_DATE_FORMATTER) : "";
                data.add(Arrays.asList(m.getId(), clientStr, dateInscriptionStr));
            }
        }
        return data;
    }

    @Override
    protected Integer getEntityKey(Membre entity) {
        return entity.getId();
    }

    @Override
    protected Integer convertRawKeyToKeyType(Object rawKey) throws ClassCastException {
        if (rawKey instanceof Integer) {
            return (Integer) rawKey;
        }
        throw new ClassCastException("La clé attendue est de type Integer, mais a reçu " + rawKey.getClass().getName());
    }
}