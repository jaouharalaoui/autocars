package ma.insea.sb.repository;

import ma.insea.sb.domain.FicheIncident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FicheIncident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FicheIncidentRepository extends JpaRepository<FicheIncident, Long> {

}
