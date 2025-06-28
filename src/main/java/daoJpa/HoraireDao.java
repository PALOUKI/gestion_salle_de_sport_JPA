package daoJpa;

import entite.Horaire;
import java.util.List;

public class HoraireDao extends GenericDao<Horaire, Integer> {

    public HoraireDao() {
        super();
    }

    @Override
    public Horaire trouver(Integer id) {
        // CORRECTION : "Horairee.class" remplacé par "Horaire.class"
        return em.find(Horaire.class, id);
    }

    @Override
    public List<Horaire> listerTous() {
        // Trie les horaires par heure de début
        return em.createQuery("SELECT h FROM Horaire h ORDER BY h.debut", Horaire.class).getResultList();
    }
}