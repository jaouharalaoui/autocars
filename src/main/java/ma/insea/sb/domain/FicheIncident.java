package ma.insea.sb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FicheIncident.
 */
@Entity
@Table(name = "fiche_incident")
public class FicheIncident implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_incident", nullable = false)
    private LocalDate dateIncident;

    @NotNull
    @Column(name = "numero_fiche", nullable = false)
    private String numeroFiche;

    @NotNull
    @Column(name = "incident_critique", nullable = false)
    private Boolean incidentCritique;

    @NotNull
    @Column(name = "lieu_incident", nullable = false)
    private String lieuIncident;

    @NotNull
    @Column(name = "nombre_voyageur", nullable = false)
    private Integer nombreVoyageur;

    @Column(name = "description_incident")
    private String descriptionIncident;

    @ManyToOne
    @JsonIgnoreProperties("ficheIncidents")
    private AgentDeclarant agentDeclarant;

    @ManyToOne
    @JsonIgnoreProperties("ficheIncidents")
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateIncident() {
        return dateIncident;
    }

    public FicheIncident dateIncident(LocalDate dateIncident) {
        this.dateIncident = dateIncident;
        return this;
    }

    public void setDateIncident(LocalDate dateIncident) {
        this.dateIncident = dateIncident;
    }

    public String getNumeroFiche() {
        return numeroFiche;
    }

    public FicheIncident numeroFiche(String numeroFiche) {
        this.numeroFiche = numeroFiche;
        return this;
    }

    public void setNumeroFiche(String numeroFiche) {
        this.numeroFiche = numeroFiche;
    }

    public Boolean isIncidentCritique() {
        return incidentCritique;
    }

    public FicheIncident incidentCritique(Boolean incidentCritique) {
        this.incidentCritique = incidentCritique;
        return this;
    }

    public void setIncidentCritique(Boolean incidentCritique) {
        this.incidentCritique = incidentCritique;
    }

    public String getLieuIncident() {
        return lieuIncident;
    }

    public FicheIncident lieuIncident(String lieuIncident) {
        this.lieuIncident = lieuIncident;
        return this;
    }

    public void setLieuIncident(String lieuIncident) {
        this.lieuIncident = lieuIncident;
    }

    public Integer getNombreVoyageur() {
        return nombreVoyageur;
    }

    public FicheIncident nombreVoyageur(Integer nombreVoyageur) {
        this.nombreVoyageur = nombreVoyageur;
        return this;
    }

    public void setNombreVoyageur(Integer nombreVoyageur) {
        this.nombreVoyageur = nombreVoyageur;
    }

    public String getDescriptionIncident() {
        return descriptionIncident;
    }

    public FicheIncident descriptionIncident(String descriptionIncident) {
        this.descriptionIncident = descriptionIncident;
        return this;
    }

    public void setDescriptionIncident(String descriptionIncident) {
        this.descriptionIncident = descriptionIncident;
    }

    public AgentDeclarant getAgentDeclarant() {
        return agentDeclarant;
    }

    public FicheIncident agentDeclarant(AgentDeclarant agentDeclarant) {
        this.agentDeclarant = agentDeclarant;
        return this;
    }

    public void setAgentDeclarant(AgentDeclarant agentDeclarant) {
        this.agentDeclarant = agentDeclarant;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public FicheIncident vehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
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
        FicheIncident ficheIncident = (FicheIncident) o;
        if (ficheIncident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ficheIncident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FicheIncident{" +
            "id=" + getId() +
            ", dateIncident='" + getDateIncident() + "'" +
            ", numeroFiche='" + getNumeroFiche() + "'" +
            ", incidentCritique='" + isIncidentCritique() + "'" +
            ", lieuIncident='" + getLieuIncident() + "'" +
            ", nombreVoyageur=" + getNombreVoyageur() +
            ", descriptionIncident='" + getDescriptionIncident() + "'" +
            "}";
    }
}
