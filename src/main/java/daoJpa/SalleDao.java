package daoJpa;

import entite.Salle;
import java.util.List;

public class SalleDao extends GenericDao<Salle, Integer> {

    public SalleDao() {
        super();
    }

    @Override
    public Salle trouver(Integer id) {
        return em.find(Salle.class, id);
    }

    @Override
    public List<Salle> listerTous() {
        return em.createQuery("SELECT s FROM Salle s ORDER BY s.libelle", Salle.class).getResultList();
    }
}