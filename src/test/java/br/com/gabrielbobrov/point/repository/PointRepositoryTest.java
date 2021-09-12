package br.com.gabrielbobrov.point.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gabrielbobrov.point.model.PointEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PointRepositoryTest {
	
	@Autowired
	private PointRepository repository;
	
	@Test
	public void findByDateIntervalTest() {
		
		List<PointEntity> list = repository.findByDateInterval(Mockito.anyString(), Mockito.anyString());
		assertNotNull(list);
		
		
		
	}

}
