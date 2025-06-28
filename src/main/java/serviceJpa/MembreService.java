package serviceJpa;

import entite.Membre;
import daoJpa.MembreDao;
import java.util.List;
import java.util.stream.Collectors;

public class MembreService extends GenericService<Membre, Integer> {

    private MembreDao membreDao;

    public MembreService() {
        super(new MembreDao());
        this.membreDao = (MembreDao) dao;
    }

    @Override
    public Membre trouver(Integer id) {
        return dao.trouver(id);
    }

    @Override
    public List<Membre> listerTous() {
        return dao.listerTous();
    }

    /**
     * Recherche des membres en fonction du nom ou du prénom du client associé.
     * @param searchTerm Le terme à rechercher.
     * @return Une liste de Membre correspondant au critère.
     */
    public List<Membre> rechercher(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return listerTous();
        }

        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();

        return listerTous().stream()
                .filter(membre -> membre.getClient() != null &&
                        (membre.getClient().getNom().toLowerCase().contains(lowerCaseSearchTerm) ||
                                membre.getClient().getPrenom().toLowerCase().contains(lowerCaseSearchTerm)))
                .collect(Collectors.toList());
    }
}