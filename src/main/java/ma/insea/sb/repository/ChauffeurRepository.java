package ma.insea.sb.repository;

import ma.insea.sb.domain.Chauffeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Chauffeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    @Query(value = "select distinct chauffeur from Chauffeur chauffeur left join fetch chauffeur.vehicules",
        countQuery = "select count(distinct chauffeur) from Chauffeur chauffeur")
    Page<Chauffeur> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct chauffeur from Chauffeur chauffeur left join fetch chauffeur.vehicules")
    List<Chauffeur> findAllWithEagerRelationships();

    @Query("select chauffeur from Chauffeur chauffeur left join fetch chauffeur.vehicules where chauffeur.id =:id")
    Optional<Chauffeur> findOneWithEagerRelationships(@Param("id") Long id);

}
