package daoJpa;

import entite.DemandeInscription;

import java.util.List;

public class DemandeInscriptionDao extends GenericDao<DemandeInscription, Integer> {

    public DemandeInscriptionDao() {
        super();
    }

    @Override
    public DemandeInscription trouver(Integer id) {
        return em.find(DemandeInscription.class, id);
    }

    @Override
    public List<DemandeInscription> listerTous() {
        // Trie les demandes de la plus récente à la plus ancienne
        return em.createQuery("SELECT d FROM DemandeInscription d ORDER BY d.dateDeDemande DESC", DemandeInscription.class).getResultList();
    }
}