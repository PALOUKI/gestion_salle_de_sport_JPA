package entite;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends GenericEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDateTime dateNaissance; // Utilisation de LocalDate pour DATE

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    // Relations bidirectionnelles
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DemandeInscription> demandeInscriptions = new ArrayList<>();

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Membre membre; // Un client peut devenir un membre

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    public Client(){

    }

    public Client(String nom, String prenom, LocalDateTime dateNaissance, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
    }

    public Client(int id, String nom, String prenom, LocalDateTime dateNaissance, String email){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }



    public List<DemandeInscription> getDemandeInscriptions() {
        return demandeInscriptions;
    }

    public void setDemandeInscriptions(List<DemandeInscription> demandeInscriptions) {
        this.demandeInscriptions = demandeInscriptions;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    // NOUVEAU : Override de toString() pour un affichage lisible dans le JComboBox
    @Override
    public String toString() {
        return nom + " " + prenom;
    }

    // Il est également bon d'implémenter equals() et hashCode() si vous utilisez des objets Client
    // dans des collections comme des HashSet ou si vous comparez des objets Client.
    // Pour JComboBox.setSelectedItem(), equals() est important pour la comparaison d'objets.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        // On compare par l'ID car c'est la clé primaire
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }


}
