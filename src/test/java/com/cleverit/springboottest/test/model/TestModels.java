package com.cleverit.springboottest.test.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.cleverit.springboottest.model.LicensePlate;
import com.cleverit.springboottest.model.User;

public class TestModels {

	@Test
	public void testUserModel() {
		User user = new User("id", "nombre", "name", "apellido", "lastname", "profesion", "email");
		User user2 = new User("id", "nombre", "name", "apellido", "lastname", "profesion", "email");
		User user3 = new User("id", "nombre", "name", "apellido", "lastname", "profesion", "email");
		
		assertThat(user.getId(), equalTo("id"));
		assertThat(user.getNombre(), equalTo("nombre"));
		assertThat(user.getName(), equalTo("name"));
		assertThat(user.getApellido(), equalTo("apellido"));
		assertThat(user.getLastname(), equalTo("lastname"));
		assertThat(user.getProfesion(), equalTo("profesion"));
		assertThat(user.getEmail(), equalTo("email"));
		
		assertThat(user.toString(), equalTo("User(id=id, nombre=nombre, name=name, apellido=apellido, lastname=lastname, profesion=profesion, email=email)"));
		
		user.setId("id2");
		assertThat(user.getId(), equalTo("id2"));
		
		assertThat(user.equals(user2), equalTo(false));
		assertThat(user2.equals(user3), equalTo(true));
		
		assertThat(user.hashCode(),not (equalTo(user2.hashCode())));
		assertThat(user2.hashCode(), equalTo(user3.hashCode()));

		
	}
	
	@Test
	public void testLicensePlateModel() {
		LicensePlate lp = new LicensePlate("id", "patente", "tipoAuto", "color");
		LicensePlate lp2 = new LicensePlate("id2", "patente2", "tipoAuto2", "color2");
		LicensePlate lp3 = new LicensePlate("id2", "patente2", "tipoAuto2", "color2");
		
		assertThat(lp.getId(), equalTo("id"));
		assertThat(lp.getPatente(), equalTo("patente"));
		assertThat(lp.getTipoAuto(), equalTo("tipoAuto"));
		assertThat(lp.getColor(), equalTo("color"));

		
		assertThat(lp.toString(), equalTo("LicensePlate(id=id, patente=patente, tipoAuto=tipoAuto, color=color)"));
		
		lp.setId("id2");
		assertThat(lp.getId(), equalTo("id2"));
		
		assertThat(lp.equals(lp2), equalTo(false));
		assertThat(lp2.equals(lp3), equalTo(true));
		
		assertThat(lp.hashCode(),not (equalTo(lp2.hashCode())));
		assertThat(lp2.hashCode(), equalTo(lp3.hashCode()));
		
	}

}
