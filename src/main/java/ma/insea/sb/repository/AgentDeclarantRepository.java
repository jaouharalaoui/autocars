package ma.insea.sb.repository;

import ma.insea.sb.domain.AgentDeclarant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgentDeclarant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentDeclarantRepository extends JpaRepository<AgentDeclarant, Long> {

}
