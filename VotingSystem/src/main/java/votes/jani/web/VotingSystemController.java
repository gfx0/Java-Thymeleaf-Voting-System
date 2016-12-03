package votes.jani.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import votes.jani.domain.Register;
import votes.jani.domain.User;
import votes.jani.domain.UserRepository;
import votes.jani.domain.VotingTopic;

@Controller
public class VotingSystemController {
	@Autowired
    private UserRepository repository; 
	
	private VotingTopic mVotingTopicX = null;
	
	private ArrayList<VotingTopic> mVotingTopics; //TODO: All voting topics here.
	
	private int mCurrentVotingIndex = 0;
	
	private void userChangedResetUserVotingSettings()
	{
		mCurrentVotingIndex = 0; //Start the newly logged users votes from the first voting topic again.
	}

	private void initTopics()
	{
		mCurrentVotingIndex = 0;
		System.out.println("Initialized mVotingTopics with the default topics!");
		mVotingTopics = new ArrayList<VotingTopic>();
		VotingTopic topic1 = new VotingTopic("Do you prefer Cats or Dogs?", "Cats", "Dogs", 2, 6);	
		VotingTopic topic2 = new VotingTopic("Do you like Apples or Oranges?", "Apples", "Oranges", 4, 6);
		VotingTopic topic3 = new VotingTopic("Do you like the Moon or the Sun?", "Moon", "Sun", 3, 7);
		VotingTopic topic4 = new VotingTopic("Is Chocolate Good?", "Yes", "No", 0, 1);
		VotingTopic topic5 = new VotingTopic("Would you like to visit Australia?", "Yes", "No", 90, 44);
		mVotingTopics.add(topic1);
		mVotingTopics.add(topic2);
		mVotingTopics.add(topic3);
		mVotingTopics.add(topic4);
		mVotingTopics.add(topic5);
	}
	
    @RequestMapping("/testarea")
    public String testArea(@ModelAttribute VotingTopic votingTopicX, Model model) {
    	model.addAttribute("test", "Testing Attribute 55");
    	model.addAttribute("numberTest", 13);
    	
    	int votesForTest = 25;
    	int votesAgainstTest = 75;
    	int totalVotes = (votesForTest + votesAgainstTest); //Plus one is the control value, this makes sure that the divider is never zero.
    	int votesForPercentage = (int) ( ((float)votesForTest / (float)totalVotes) * 100.0f );
    	int votesAgainstPercentage = (int) ( ((float)votesAgainstTest / (float)totalVotes) * 100.0f );
    	
    	System.out.println("totalVotes: "+totalVotes);
    	System.out.println("votesForTest: "+votesForTest);
    	System.out.println("votesAgainstTest: "+votesAgainstTest);
    	
    	System.out.println("votesForPercentage: "+votesForPercentage);
    	System.out.println("votesAgainstPercentage: "+votesAgainstPercentage);
    	
    	model.addAttribute("votesForPercentage",  votesForPercentage);
    	model.addAttribute("votesAgainstPercentage",  votesAgainstPercentage);

    	//Test vote
    	if ( mVotingTopics != null && mVotingTopics.size() > 0 )
    		model.addAttribute("voteTitle", mVotingTopics.get(0).getTitle());
    	else
    		model.addAttribute("voteTitle", "Dummy title, votingtopics not initialized..");
    	model.addAttribute("votesFor", 25);
    	model.addAttribute("votesAgainst", 75);
    	
		VotingTopic testVotingTopic = new VotingTopic("Test voting topic, do you prefer option 1 or 2?", "Yes", "No", 2, 6);
		
		model.addAttribute("testTopic", testVotingTopic);

		mVotingTopicX = new VotingTopic("Is Chocolate Good?", "Yes", "No", 2, 6);	

		model.addAttribute("votingTopicX", mVotingTopicX);
		
        return "testarea";
    }
    
    @RequestMapping("/")
    public String mainMenu(Model model) {  	
    	
    	//NOTE: Here we initialize the voting topics in case they haven't been initialized yet.
    	if ( mVotingTopics == null )
    	{
    		this.initTopics();
    	} else {
    		System.out.println("Avoided re-initialization of mVotingTopics with default topics since mVotingTopics is already initialized.");
    	}
	
    	model.addAttribute("votingTopicsAvailable", mVotingTopics);
    	
        return "index";
    }
    
    @RequestMapping("/login")
    public String logInNow(Model model) {

    	//This resets the state of the votes of that user, so he can vote again,
    	//this is so for testing the application.
    	userChangedResetUserVotingSettings();
    	
        return "login";
    }
    
    @RequestMapping(value = "register")
    public String addStudent(Model model){
    	model.addAttribute("register", new Register());
        return "register";
    }
    
    @RequestMapping(value = "/submitvote", method = RequestMethod.POST)
    public String submitVote(@ModelAttribute VotingTopic usersVoteResult, Model model)
    {
    	System.out.println("Vote result of user: " + usersVoteResult.toString());
    	
    	//TODO: Multiple votes on one page.
    	//for ( int x = 0; x < votingTopics.size(); x++ )
    	{
    		//System.out.println("x: "+x);
    		//votingTopics.toString();
    	}
    	
    	if ( mVotingTopics == null )
    		return "index";

    	if ( usersVoteResult== null )
    	{
    		System.out.println("userVoteResult was null!");
    		return "allvotesdone";
    	}
    		

		//Make sure we're not at the end of voting, if we are, return to the allvotesdone part.
		if ( mCurrentVotingIndex >= mVotingTopics.size() )
		{
			return "allvotesdone";
		}
		
    	/************************************************************
    	 * 
    	 * Handle the users choice, 0 is yes, 1 is no, usually, 
    	 * but voting topics can have custom choice names.
    	 * 
    	 ************************************************************/
    	//Handle users choice 
    	boolean successfulVote = false; 


    	if ( usersVoteResult.getChoiceMade().equals("0") )
    	{
    		successfulVote = true;
    		mVotingTopics.get(mCurrentVotingIndex).setVotesFor(mVotingTopics.get(mCurrentVotingIndex).getVotesFor() + 1);
    	} else if ( usersVoteResult.getChoiceMade().equals("1") )
    	{
    		successfulVote = true;
    		mVotingTopics.get(mCurrentVotingIndex).setVotesAgainst(mVotingTopics.get(mCurrentVotingIndex).getVotesAgainst() + 1);
    	} else {
    		return "error";
    	}

    	int votesForTest =  mVotingTopics.get(mCurrentVotingIndex).getVotesFor();
    	int votesAgainstTest = mVotingTopics.get(mCurrentVotingIndex).getVotesAgainst();
    	int totalVotes = (votesForTest + votesAgainstTest); //Plus one is the control value, this makes sure that the divider is never zero.
    	int votesForPercentage = (int) ( ((float)votesForTest / (float)totalVotes) * 100.0f );
    	int votesAgainstPercentage = (int) ( ((float)votesAgainstTest / (float)totalVotes) * 100.0f );
    	
    	mVotingTopics.get(mCurrentVotingIndex).setVotesForPercentage(votesForPercentage);
    	mVotingTopics.get(mCurrentVotingIndex).setVotesAgainstPercentage(votesAgainstPercentage);
    	
    	System.out.println("totalVotes: "+totalVotes);
    	System.out.println("votesForTest: "+votesForTest);
    	System.out.println("votesAgainstTest: "+votesAgainstTest);
    	
    	System.out.println("votesForPercentage: "+votesForPercentage);
    	System.out.println("votesAgainstPercentage: "+votesAgainstPercentage);
    	
    	model.addAttribute("votesForPercentage",  votesForPercentage);
    	model.addAttribute("votesAgainstPercentage",  votesAgainstPercentage);

   		model.addAttribute("voteTitle",  mVotingTopics.get(mCurrentVotingIndex).getTitle());
    	model.addAttribute("choiceName1", mVotingTopics.get(mCurrentVotingIndex).getChoice1());
    	model.addAttribute("choiceName2", mVotingTopics.get(mCurrentVotingIndex).getChoice2());
    	model.addAttribute("votesForNum", votesForTest);
    	model.addAttribute("votesAgainstNum", votesAgainstTest);
    	
		//Prepare for the next vote.
		mCurrentVotingIndex++;

    	//End of voting was not reached, go vote again.
    	return "voteresults";
    }
       
    @RequestMapping(value = "allvotesdone")
    public String allVotesDone(@ModelAttribute VotingTopic votingTopic, Model model){

    	/************************************************************
    	 * 
    	 * Thymeleaf code digs up the results to present from this.
    	 * 
    	 ************************************************************/
    	model.addAttribute("votingTopics", mVotingTopics);
    	
        return "allvotesdone";
    }
    
    @RequestMapping(value = "adduser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("register") Register registerForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (registerForm.getPassword().equals(registerForm.getPasswordConfirmation()))
    		{		
	    		String pwd = registerForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(registerForm.getUsername());
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(registerForm.getUsername()) == null)
		    	{
		    		repository.save(newUser);//Make user the user exists
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "register";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordConfirmation", "err.passCheck", "Passwords does not match");    	
    			return "register";
    		}
    	}
    	else {
    		return "register";
    	}
    	return "redirect:/login";    	
    }
    
    @RequestMapping("/votingsystem")
    public String votingSystem(@ModelAttribute VotingTopic votingTopic, Model model)
    {
    	/*
		if ( mCurrentVotingIndex > mVotingTopics.size() )
		{
			return "voteresults";
		} else 
		{
			model.addAttribute("votingTopicX", mVotingTopics.get(mCurrentVotingIndex));
		}
		*/
    	if ( mVotingTopics == null )
    	{
    		this.initTopics();
    	}
    	
		//Make sure we're not at the end of voting, if we are, return to the allvotesdone part.
		if ( mCurrentVotingIndex >= mVotingTopics.size() )
		{
			//NOTE: A full redirect is necessary here!
			return "redirect:/allvotesdone";
		}

    	if ( mCurrentVotingIndex < mVotingTopics.size() )
    		model.addAttribute("votingTopicX", mVotingTopics.get(mCurrentVotingIndex));
    	else
    	{
    		if ( mVotingTopics.size() > 0 )
    			model.addAttribute("votingTopicX", mVotingTopics.get(0));
    		else
    		{    			
    			return "index";
    		}
    	}
        return "votingsystem";
    }
  
    @RequestMapping("/resetmyvotes")
    public String votingSystem(Model model)
    {
    	mCurrentVotingIndex = 0;
    	return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin/deletethisquestion")
    public String adminDeleteThisQuestion(Model model)
    {
    	System.out.println("Removing question from the voting topics: " + mVotingTopics.get(mCurrentVotingIndex).getTitle());
    	if ( mCurrentVotingIndex < mVotingTopics.size() )
    		mVotingTopics.remove(mCurrentVotingIndex);
    	mCurrentVotingIndex = 0;    	
    	return "redirect:/votingsystem";
    }
    
    @RequestMapping("/loginerror")
    public String loginFailed(Model model)
    {
    	//Yeah, user failed to input good credentials, maybe do something special like login timeouts etc.
    	return "loginerror";
    }
    @RequestMapping("/logout")
    public String logout(Model model)
    {

    	return "login";
    }

    /*
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
    */
}
