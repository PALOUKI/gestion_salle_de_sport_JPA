package daoJpa;

import entite.TypeAbonnement;

import java.util.List;

public class TypeAbonnementDao extends GenericDao<TypeAbonnement, String> { // Key changed to String

    public TypeAbonnementDao() {
        super();
    }

    @Override
    public TypeAbonnement trouver(String code) { // Parameter type changed to String
        return em.find(TypeAbonnement.class, code);
    }

    @Override
    public List<TypeAbonnement> listerTous() {
        return em.createQuery("SELECT t FROM TypeAbonnement t", TypeAbonnement.class).getResultList();
    }
}