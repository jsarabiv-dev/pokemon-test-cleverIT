package com.cleverit.springboottest.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleverit.springboottest.SpringboottestApplication;
import com.cleverit.springboottest.dao.LicensePlateDAOImpl;
import com.cleverit.springboottest.model.LicensePlate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringboottestApplication.class)
public class TestLicensePlateDAOImpl {

//	@Autowired
//	LicensePlateDAOImpl licensePlateDAOImpl;
	
	@Spy
	LicensePlateDAOImpl licensePlateDAOImpl;
	
	@Test
	
	public void TestsaveLicenses() {

		List<LicensePlate> listLP = new ArrayList<>();
		LicensePlate lp = new LicensePlate("id", "patente", "tipoAuto", "color");
		LicensePlate lp2 = new LicensePlate("id2", "patente2", "tipoAuto2", "color2");
		listLP.add(lp);
		listLP.add(lp2);
		
		licensePlateDAOImpl.saveLicenses(listLP);
		

		// Se verifica si fue llamado 1 vez el metodo deleteUser()
		Mockito.verify(licensePlateDAOImpl, Mockito.times(1)).saveLicenses(listLP);
				

	}

}
