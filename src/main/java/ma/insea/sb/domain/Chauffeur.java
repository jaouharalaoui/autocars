package ma.insea.sb.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chauffeur.
 */
@Entity
@Table(name = "chauffeur")
public class Chauffeur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @ManyToMany
    @JoinTable(name = "chauffeur_vehicule",
               joinColumns = @JoinColumn(name = "chauffeur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "vehicule_id", referencedColumnName = "id"))
    private Set<Vehicule> vehicules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Chauffeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Chauffeur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Chauffeur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Chauffeur vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Chauffeur addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.getChauffeurs().add(this);
        return this;
    }

    public Chauffeur removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.getChauffeurs().remove(this);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
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
        Chauffeur chauffeur = (Chauffeur) o;
        if (chauffeur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chauffeur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chauffeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
