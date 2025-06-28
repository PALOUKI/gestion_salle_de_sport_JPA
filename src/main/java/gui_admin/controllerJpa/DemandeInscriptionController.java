package gui_admin.controllerJpa;

import entite.Client;
import entite.DemandeInscription;
import gui_admin.view.demande_inscriptions.DemandeInscriptionEdit;
import serviceJpa.ClientService;
import serviceJpa.DemandeInscriptionService;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemandeInscriptionController extends GenericCrudController<DemandeInscription, Integer> {

    private DemandeInscriptionService demandeInscriptionService;
    private ClientService clientService; // Nécessaire pour peupler la ComboBox

    private static final DateTimeFormatter TABLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public DemandeInscriptionController() {
        super(new DemandeInscriptionService(), DemandeInscription.class);
        this.demandeInscriptionService = (DemandeInscriptionService) service;
        this.clientService = new ClientService(); // Initialisation du service client
    }

    public DemandeInscriptionEdit createAndConfigureEditPanelForAdd() {
        List<String> columnNames = Arrays.asList("ID", "Client", "Date de Demande", "Date de Traitement", "Statut");
        List<List<Object>> tableData = convertEntitiesToTableData(service.listerTous());

        // Récupérer tous les clients pour la ComboBox
        List<Client> allClients = clientService.listerTous();

        DemandeInscriptionEdit edit = new DemandeInscriptionEdit(tableData, columnNames, allClients);

        setEditPanel(edit);
        edit.setEntity(new DemandeInscription());
        edit.clearForm();

        return edit;
    }

    @Override
    public void setEditPanel(gui_admin.gui_util.GenericEdit<DemandeInscription> editPanel) {
        super.setEditPanel(editPanel);
        if (this.editPanel instanceof DemandeInscriptionEdit) {
            DemandeInscriptionEdit diEditPanel = (DemandeInscriptionEdit) this.editPanel;
            diEditPanel.getSearchButton().addActionListener(e -> performSearchOperation());
            diEditPanel.getSearchField().addActionListener(e -> performSearchOperation());
        }
    }

    @Override
    protected void performSearchOperation() {
        String searchTerm = ((DemandeInscriptionEdit)editPanel).getSearchField().getText();
        try {
            List<DemandeInscription> searchResults = demandeInscriptionService.rechercher(searchTerm);
            editPanel.getCustomTablePanel().updateTableData(convertEntitiesToTableData(searchResults));
            if (searchResults.isEmpty() && !searchTerm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(editPanel, "Aucune demande trouvée pour le client : '" + searchTerm + "'", "Résultats de recherche", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(editPanel, "Erreur lors de la recherche : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    protected List<List<Object>> convertEntitiesToTableData(List<DemandeInscription> demandes) {
        List<List<Object>> data = new ArrayList<>();
        if (demandes != null) {
            for (DemandeInscription di : demandes) {
                String clientStr = (di.getClient() != null) ? di.getClient().toString() : "N/A";
                String dateDemandeStr = (di.getDateDeDemande() != null) ? di.getDateDeDemande().format(TABLE_DATE_FORMATTER) : "";
                String dateTraitementStr = (di.getDateDeTraitement() != null) ? di.getDateDeTraitement().format(TABLE_DATE_FORMATTER) : "";
                String statut = (di.getDateDeTraitement() != null) ? "Traitée" : "En attente";

                data.add(Arrays.asList(di.getId(), clientStr, dateDemandeStr, dateTraitementStr, statut));
            }
        }
        return data;
    }

    @Override
    protected Integer getEntityKey(DemandeInscription entity) {
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