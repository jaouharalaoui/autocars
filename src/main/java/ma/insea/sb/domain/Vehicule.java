package ma.insea.sb.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_intern", nullable = false)
    private String codeIntern;

    @NotNull
    @Column(name = "immatriculation", nullable = false)
    private String immatriculation;

    @NotNull
    @Column(name = "date_de_mise_en_circulation", nullable = false)
    private LocalDate dateDeMiseEnCirculation;

    @ManyToMany(mappedBy = "vehicules")
    @JsonIgnore
    private Set<Chauffeur> chauffeurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeIntern() {
        return codeIntern;
    }

    public Vehicule codeIntern(String codeIntern) {
        this.codeIntern = codeIntern;
        return this;
    }

    public void setCodeIntern(String codeIntern) {
        this.codeIntern = codeIntern;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public Vehicule immatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
        return this;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public LocalDate getDateDeMiseEnCirculation() {
        return dateDeMiseEnCirculation;
    }

    public Vehicule dateDeMiseEnCirculation(LocalDate dateDeMiseEnCirculation) {
        this.dateDeMiseEnCirculation = dateDeMiseEnCirculation;
        return this;
    }

    public void setDateDeMiseEnCirculation(LocalDate dateDeMiseEnCirculation) {
        this.dateDeMiseEnCirculation = dateDeMiseEnCirculation;
    }

    public Set<Chauffeur> getChauffeurs() {
        return chauffeurs;
    }

    public Vehicule chauffeurs(Set<Chauffeur> chauffeurs) {
        this.chauffeurs = chauffeurs;
        return this;
    }

    public Vehicule addChauffeur(Chauffeur chauffeur) {
        this.chauffeurs.add(chauffeur);
        chauffeur.getVehicules().add(this);
        return this;
    }

    public Vehicule removeChauffeur(Chauffeur chauffeur) {
        this.chauffeurs.remove(chauffeur);
        chauffeur.getVehicules().remove(this);
        return this;
    }

    public void setChauffeurs(Set<Chauffeur> chauffeurs) {
        this.chauffeurs = chauffeurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicule vehicule = (Vehicule) o;
        if (vehicule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", codeIntern='" + getCodeIntern() + "'" +
            ", immatriculation='" + getImmatriculation() + "'" +
            ", dateDeMiseEnCirculation='" + getDateDeMiseEnCirculation() + "'" +
            "}";
    }
}
