package votes.jani;

import static org.junit.Assert.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import votes.jani.domain.User;
import votes.jani.domain.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VotesRepositoryTest {
	
	@Autowired
	private UserRepository urepository;

	//Test of finding a specific user from the repository 
	@Test
	public void findByRoleShouldReturnUser(){		
		List<User> users = urepository.findByRole("USER");		
		assertThat(users).hasSize(1);
		assertThat(users.get(0).getUsername()).isEqualTo("testrunneruser");		
	}
	
	//Test of new user creation and removal
	@Test
	public void createAndDeleteNewUser(){
		User user= new User("testxxxy111", "testxxxy@jeemail.com", "hashofpassword","user");//last param = user role
		urepository.save(user);
		assertThat(user.getId()).isNotNull();
		urepository.delete(user);
		assertFalse(urepository.exists(user.getId()));
	}
		
	
}