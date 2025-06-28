package serviceJpa;

import daoJpa.GenericDao;

import java.util.List;

public abstract class GenericService <Entity, Key>{

    protected GenericDao<Entity, Key> dao; // Make dao generic

    public GenericService (GenericDao<Entity, Key> dao){ // Make constructor parameter generic
        this.dao = dao;
    }
    public void ajouter(Entity entite){
        dao.ajouter(entite);
    }
    public void modifier(Entity entite){
        dao.modifier(entite);
    }
    public void supprimer(Entity entite){
        dao.supprimer(entite);

    }
    public Entity trouver(Key id){
        return dao.trouver(id); // Call dao's trouver method
    }
    public List<Entity> listerTous(){
        return dao.listerTous(); // Call dao's listerTous method
    }
}