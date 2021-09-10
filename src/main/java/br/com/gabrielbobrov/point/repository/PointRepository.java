package br.com.gabrielbobrov.point.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gabrielbobrov.point.model.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
	
	@Query("select p from PointEntity p where p.userId = ?1 and p.point between ?2 and ?3 order by p.status desc")
	List<PointEntity> findByDateInterval(Long userId,LocalDateTime initialDate, LocalDateTime finalDate);
	

}
