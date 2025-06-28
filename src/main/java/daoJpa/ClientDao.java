package daoJpa;

import entite.Client;

import java.util.List;

public class ClientDao extends GenericDao<Client, Integer> {

    public ClientDao() {
        super();
    }

    @Override
    public Client trouver(Integer id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> listerTous() {
        return em.createQuery("SELECT c FROM Client c ORDER BY c.nom, c.prenom", Client.class).getResultList();
    }
}