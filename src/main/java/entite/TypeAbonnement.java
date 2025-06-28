package entite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_abonnements")
public class TypeAbonnement extends GenericEntity {

    @Id
    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "montant", nullable = false)
    private Integer montant;

    @Column(name = "libelle", nullable = false, length = 255)
    private String libelle;

    // Relation bidirectionnelle
    @OneToMany(mappedBy = "typeAbonnement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Abonnement> abonnements = new ArrayList<>();

    public TypeAbonnement(){

    }

    public TypeAbonnement(String code, String libelle, int montant){
        this.code = code;
        this.libelle = libelle;
        this.montant = montant;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public List<Abonnement> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<Abonnement> abonnements) {
        this.abonnements = abonnements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeAbonnement that = (TypeAbonnement) o;
        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}