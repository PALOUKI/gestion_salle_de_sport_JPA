package daoJpa;

import entite.Paiement;
import java.util.List;

public class PaiementDao extends GenericDao<Paiement, Integer> {

    public PaiementDao() {
        super();
    }

    @Override
    public Paiement trouver(Integer id) {
        return em.find(Paiement.class, id);
    }

    @Override
    public List<Paiement> listerTous() {
        // Trie les paiements par date, du plus récent au plus ancien
        return em.createQuery("SELECT p FROM Paiement p ORDER BY p.dateDePaiement DESC", Paiement.class).getResultList();
    }
}