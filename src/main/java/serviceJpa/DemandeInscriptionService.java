package serviceJpa;

import entite.DemandeInscription;
import daoJpa.DemandeInscriptionDao;

import java.util.List;
import java.util.stream.Collectors;

public class DemandeInscriptionService extends GenericService<DemandeInscription, Integer> {

    private DemandeInscriptionDao demandeInscriptionDao;

    public DemandeInscriptionService() {
        super(new DemandeInscriptionDao());
        this.demandeInscriptionDao = (DemandeInscriptionDao) dao;
    }

    @Override
    public DemandeInscription trouver(Integer id) {
        return dao.trouver(id);
    }

    @Override
    public java.util.List<DemandeInscription> listerTous() {
        return dao.listerTous();
    }

    /**
     * Recherche les demandes d'inscription en fonction du nom ou du prénom du client associé.
     * Si le terme est vide, retourne toutes les demandes.
     * @param searchTerm Le nom ou prénom du client à rechercher.
     * @return Une liste de DemandeInscription correspondant au critère.
     */
    public List<DemandeInscription> rechercher(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return listerTous();
        }

        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();

        return listerTous().stream()
                .filter(di -> di.getClient() != null &&
                        (di.getClient().getNom().toLowerCase().contains(lowerCaseSearchTerm) ||
                                di.getClient().getPrenom().toLowerCase().contains(lowerCaseSearchTerm)))
                .collect(Collectors.toList());
    }
}