package serviceJpa;

import entite.Client;
import daoJpa.ClientDao;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService extends GenericService<Client, Integer> {

    private ClientDao clientDao;

    public ClientService() {
        super(new ClientDao());
        this.clientDao = (ClientDao) dao;
    }

    @Override
    public Client trouver(Integer id) {
        return dao.trouver(id);
    }

    @Override
    public java.util.List<Client> listerTous() {
        return dao.listerTous();
    }

    /**
     * Recherche les clients dont le nom, le prénom ou l'email contient le terme de recherche.
     * Si le terme est vide ou null, retourne tous les clients.
     * @param searchTerm Le terme à rechercher (non sensible à la casse).
     * @return Une liste de Clients correspondant au critère.
     */
    public List<Client> rechercher(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return listerTous(); // Si le terme est vide, on retourne tout
        }

        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();

        // Filtre en mémoire. Pour de grandes bases de données, une requête JPQL serait plus performante.
        return listerTous().stream()
                .filter(client -> client.getNom().toLowerCase().contains(lowerCaseSearchTerm) ||
                        client.getPrenom().toLowerCase().contains(lowerCaseSearchTerm) ||
                        client.getEmail().toLowerCase().contains(lowerCaseSearchTerm))
                .collect(Collectors.toList());
    }
}