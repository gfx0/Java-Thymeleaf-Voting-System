package votes.jani;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ModelAttribute;

import votes.jani.domain.User;
import votes.jani.domain.UserRepository;

@SpringBootApplication
public class VotesApplication {

	@Autowired
	private UserRepository urepository;
	
	/***************
	 * 
	 * Note: Features required for this project work:
	 * 
	 * 		Admin login
	 * 			- Tools for adding new voting topics
	 * 			- 
	 * 			 
	 * 		User login
	 * 			- Ability to vote one time on a topic
	 * 			- Ability to see vote results 
	 * 
	 * 		General
	 * 
	 * 			- Nice graphical bars to show vote situation
	 * 			- Multiple votes on one page
	 * 
	 * 		Database
	 * 
	 * 			- Save user data to a database
	 * 			- Save vote data to database (persistent results)
	 * 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(VotesApplication.class, args);
		
		
	}
	@Bean
	public CommandLineRunner demo(){
		return(args) -> {
			//NOTE: It's critical to keep the name as "testrunneruser" since thats what the findByUserName test reflects to!
			User testRunnerDefaultUser = new User("testrunneruser", "testuserfortestrunner", "USER");
			urepository.save(testRunnerDefaultUser);
		};
	}

}
