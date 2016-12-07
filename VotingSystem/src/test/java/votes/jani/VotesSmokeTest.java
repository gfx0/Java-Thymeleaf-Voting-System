package votes.jani;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import votes.jani.web.VotingSystemController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VotesSmokeTest {
	@Autowired
	private VotingSystemController mVotingSystemController;
	
	@Test
	public void contexLoads() throws Exception{

		assertThat(mVotingSystemController).isNotNull();
		
	}
	

}