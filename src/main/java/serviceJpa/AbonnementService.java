package serviceJpa;

import entite.Abonnement;
import daoJpa.AbonnementDao;
import java.util.List;
import java.util.stream.Collectors;

public class AbonnementService extends GenericService<Abonnement, Integer> {

    private AbonnementDao abonnementDao;

    public AbonnementService() {
        super(new AbonnementDao());
        this.abonnementDao = (AbonnementDao) dao;
    }

    @Override
    public Abonnement trouver(Integer id) {
        return dao.trouver(id);
    }

    @Override
    public List<Abonnement> listerTous() {
        return dao.listerTous();
    }


    public List<Abonnement> rechercher(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return listerTous();
        }

        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();

        return listerTous().stream()
                .filter(abonnement -> abonnement.getMembre() != null &&
                        (abonnement.getMembre().getClient().getNom().toLowerCase().contains(lowerCaseSearchTerm) ||
                                abonnement.getMembre().getClient().getPrenom().toLowerCase().contains(lowerCaseSearchTerm)))
                .collect(Collectors.toList());
    }
}