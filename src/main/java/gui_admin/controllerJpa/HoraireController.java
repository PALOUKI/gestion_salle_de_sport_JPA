/*package gui_admin.view.horaires;

import entite.Horaire;
import gui_admin.gui_util.ActionButton;
import gui_admin.gui_util.GenericEdit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HoraireEdit extends GenericEdit<Horaire> {

    private JTextField idField;
    private JTextField debutField;
    private JTextField finField;
    private JTextField searchField;
    private JButton searchButton;
    private JButton modifyButton = new ActionButton("Modifier", ActionButton.ButtonType.MODIFY);
    private JButton deleteButton = new ActionButton("Supprimer", ActionButton.ButtonType.DELETE);
    private static finaledit);
    prepareForAdd();

        return edit;
}

@Override
public void setEditPanel(gui_admin.gui_util.GenericEdit<Horaire> editPanel) {
    super.setEditPanel(editPanel);
    if (this.editPanel instanceof HoraireEdit) {
        HoraireEdit horaireEdit = (HoraireEdit) this.editPanel;
        horaireEdit.getSearchButton().addActionListener(e -> performSearchOperation());
        horaireEdit.getSearchField().addActionListener(e -> performSearchOperation());
    }
}

@Override
protected void performSearchOperation() {
    String searchTerm = ((HoraireEdit)editPanel).getSearchField().getText();
    try {
        List<Horaire> searchResults = horaireService.rechercher(searchTerm);
        editPanel.getCustomTablePanel().updateTableData(convertEntitiesToTableData(searchResults));
        if (searchResults.isEmpty() && !searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(editPanel, "Aucun horaire trouvé pour le terme : '" + searchTerm + "'", "Résultats de recherche", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(editPanel, "Erreur lors de la recherche : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

@Override
protected List<List<Object>> convertEntitiesToTableData(List<Horaire> horaires) {
    List<List<Object>> data = new ArrayList<>();
    if (horaires != null) {
        for (Horaire h : horaires) {
            String debutStr = h.getDebut() != null ? h.getDebut().format(TABLE_DATE_FORMATTER) : "N/A";
            String finStr = h.getFin() != null ? h.getFin().format(TABLE_DATE_FORMATTER) : "N/A";

            String sallesStr = h.getSalles().stream()
                    .map(Salle::getLibelle)
                    .collect(Collectors.joining(", "));
            if (sallesStr.isEmpty()) {
                sallesStr = "Aucune";
            }

            data.add(Arrays.asList(h.getId(), debutStr, finStr, sallesStr));
        }
    }
    return data;
}

@Override
protected Integer getEntityKey(Horaire entity) {
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

 */