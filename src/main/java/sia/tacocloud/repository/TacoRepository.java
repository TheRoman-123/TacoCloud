package sia.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.dto.Taco;

@Repository
public interface TacoRepository extends JpaRepository<Taco, Long> {

}
