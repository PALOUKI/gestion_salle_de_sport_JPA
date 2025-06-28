package entite;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "demande_inscriptions")
public class DemandeInscription extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_de_demande", nullable = false)
    private LocalDateTime dateDeDemande; // Utilisation de LocalDateTime pour DATETIME

    @Column(name = "date_de_traitement")
    private LocalDateTime dateDeTraitement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id") // Clé étrangère dans la table demande_inscriptions
    private Client client;

    public DemandeInscription(){

    }

    public DemandeInscription(LocalDateTime dateDeDemande, LocalDateTime dateDeTraitement, Client client) {
        this.dateDeDemande = dateDeDemande;
        this.dateDeTraitement = dateDeTraitement;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateDeDemande() {
        return dateDeDemande;
    }

    public void setDateDeDemande(LocalDateTime dateDeDemande) {
        this.dateDeDemande = dateDeDemande;
    }

    public LocalDateTime getDateDeTraitement() {
        return dateDeTraitement;
    }

    public void setDateDeTraitement(LocalDateTime dateDeTraitement) {
        this.dateDeTraitement = dateDeTraitement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
