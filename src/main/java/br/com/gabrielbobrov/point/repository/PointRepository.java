package br.com.gabrielbobrov.point.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gabrielbobrov.point.model.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
	
	@Query("select p from PointEntity p where p.dataHora between ?1 and ?2 order by p.status desc")
	List<PointEntity> findByDateInterval(String initialDate, String finalDate);
	

}
