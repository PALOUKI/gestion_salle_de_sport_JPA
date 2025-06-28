package daoJpa;

import entite.Membre;
import java.util.List;

public class MembreDao extends GenericDao<Membre, Integer> {

    public MembreDao() {
        super();
    }

    @Override
    public Membre trouver(Integer id) {
        return em.find(Membre.class, id);
    }

    @Override
    public List<Membre> listerTous() {
        // Trie les membres par le nom de famille du client associé
        return em.createQuery("SELECT m FROM Membre m ORDER BY m.client.nom, m.client.prenom", Membre.class).getResultList();
    }
}