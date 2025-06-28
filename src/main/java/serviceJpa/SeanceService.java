package serviceJpa;

import entite.Seance;
import daoJpa.SeanceDao;
import java.util.List;
import java.util.stream.Collectors;

public class SeanceService extends GenericService<Seance, Integer> {

    private SeanceDao seanceDao;

    public SeanceService() {
        super(new SeanceDao());
        this.seanceDao = (SeanceDao) dao;
    }

    @Override
    public Seance trouver(Integer id) {
        return dao.trouver(id);
    }

    @Override
    public List<Seance> listerTous() {
        return dao.listerTous();
    }

    /**
     * Recherche les séances en fonction du libellé de la salle associée.
     * @param searchTerm Le nom de la salle à rechercher.
     * @return Une liste de Seance correspondant au critère.
     */
    public List<Seance> rechercher(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return listerTous();
        }

        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();

        return listerTous().stream()
                .filter(seance -> seance.getSalle() != null &&
                        seance.getSalle().getLibelle().toLowerCase().contains(lowerCaseSearchTerm))
                .collect(Collectors.toList());
    }
}