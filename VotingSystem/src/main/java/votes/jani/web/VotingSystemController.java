package votes.jani.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private VotingTopic mVotingTopicX;
	  
    @RequestMapping("/testarea")
    public String testArea(@ModelAttribute VotingTopic votingTopicX, Model model) {
    	model.addAttribute("test", "Testing Attribute 55");
    	model.addAttribute("numberTest", 13);
    	model.addAttribute("progressBarPercentage", 50);
    	
		VotingTopic testVotingTopic = new VotingTopic("Test voting topic, do you prefer option 1 or 2?", "Yes", "No", 2, 6);
		
		model.addAttribute("testTopic", testVotingTopic);
		
		ArrayList<VotingTopic> votingTopics = new ArrayList<VotingTopic>();
		VotingTopic topic1 = new VotingTopic("Do you prefer 1: Cats or 2: Dogs?", "Cats", "Dogs", 2, 6);	
		votingTopics.add(topic1);
		VotingTopic topic2 = new VotingTopic("Do you like Apples or Oranges?", "Apples", "Oranges", 4, 6);
		votingTopics.add(topic2);
		VotingTopic topic3 = new VotingTopic("Do you like the Moon or the Sun?", "Moon", "Sun", 3, 3);
		votingTopics.add(topic3);
		
		model.addAttribute("votingTopics",votingTopics);

		mVotingTopicX = new VotingTopic("Is Chocolate Good?", "Yes", "No", 2, 6);	

		model.addAttribute("votingTopicX", mVotingTopicX);
		
        return "testarea";
    }
    
    @RequestMapping("/")
    public String mainMenu(Model model) {
    	model.addAttribute("test", "Voting System 1.0");	
        return "index";
    }
    
    @RequestMapping("/login")
    public String logInNow(Model model) {
    	//model.addAttribute("test", "Voting System 1.0");	
        return "login";
    }
    
    @RequestMapping(value = "register")
    public String addStudent(Model model){
    	model.addAttribute("register", new Register());
        return "register";
    }	
    
    @RequestMapping(value = "/submitvotes", method = RequestMethod.POST)
    public String submitVotes(@ModelAttribute VotingTopic votingTopic)
    {
    	System.out.println("Test XXX Monkeys");
    	//System.out.println("v s"+votingTopics.size());
    	System.out.println("v" + votingTopic.toString());
    	
    	int x = 0;
    	//for ( x = 0; x < votingTopics.size(); x++ )
    	{
    		//System.out.println("x: "+x);
    		//votingTopics.toString();
    	} 	
    	return "voteresults";
    }
    
    /*
    @RequestMapping(value = "/processvotes", method = RequestMethod.POST)
    public String save(@ModelAttribute(value = "votingTopics") ArrayList<VotingTopic> votingTopics)
    {
    	System.out.println("Test XXX Monkeys");
    	int x = 0;
    	for ( x = 0; x < votingTopics.size(); x++ )
    	{
    		System.out.println("x: "+x);
    		//votingTopics.toString();
    	} 		
    		
    		
        //for (String s : priorities.keySet()) {
            //System.out.println(s);
        //}
    	return "voteresults";
    }
    */
    
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
		ArrayList<VotingTopic> votingTopics = new ArrayList<VotingTopic>();
		VotingTopic topic1 = new VotingTopic("Do you prefer 1: Cats or 2: Dogs?", "Cats", "Dogs", 2, 6);	
		votingTopics.add(topic1);
		VotingTopic topic2 = new VotingTopic("Do you like Apples or Oranges?", "Apples", "Oranges", 4, 6);
		votingTopics.add(topic2);
		VotingTopic topic3 = new VotingTopic("Do you like the Moon or the Sun?", "Moon", "Sun", 3, 3);
		votingTopics.add(topic3);
		
		model.addAttribute("votingTopics",votingTopics);
		
		List<String> data = new ArrayList<String>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");

        model.addAttribute("datas", data);
        
        return "votingsystem";
    }
  
}
