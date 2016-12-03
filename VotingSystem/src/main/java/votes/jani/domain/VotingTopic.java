package votes.jani.domain;

public class VotingTopic {


	@Override
	public String toString() {
		return "VotingTopic [title=" + title + ", choice1=" + choice1 + ", choice2=" + choice2 + ", choiceMade="
				+ choiceMade + ", votesFor=" + votesFor + ", votesAgainst=" + votesAgainst + ", votesForPercentage="
				+ votesForPercentage + ", votesAgainstPercentage=" + votesAgainstPercentage + "]";
	}

	private String title;
	private String choice1;
	private String choice2;
	private String choiceMade;
	private int votesFor;
	private int votesAgainst;
	private int votesForPercentage;
	private int votesAgainstPercentage;
	
	public int getVotesForPercentage() {
		return votesForPercentage;
	}

	public void setVotesForPercentage(int votesForPercentage) {
		this.votesForPercentage = votesForPercentage;
	}

	public int getVotesAgainstPercentage() {
		return votesAgainstPercentage;
	}

	public void setVotesAgainstPercentage(int votesAgainstPercentage) {
		this.votesAgainstPercentage = votesAgainstPercentage;
	}

	public String getChoiceMade() {
		return choiceMade;
	}

	public void setChoiceMade(String choiceMade) {
		this.choiceMade = choiceMade;
	}

	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	public VotingTopic()
	{
		super();
		title = "Uninitialized Voting Topic Title";
		choice1 = "Uninitialized Choice 1";
		choice2 = "Uninitialized Choice 2";
		votesFor = 99;
		votesAgainst = 99;
		choiceMade = "99";
		votesForPercentage = 1;
		votesAgainstPercentage = 1;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String topicTitle) {
		this.title = topicTitle;
	}

	public VotingTopic(String title, String choice1, String choice2, int votesFor, int votesAgainst) {
		super();
		this.title 			= title;
		this.choice1		= choice1;
		this.choice2		= choice2;
		this.votesFor 		= votesFor;
		this.votesAgainst 	= votesAgainst;
		this.choiceMade		= "99";
		this.votesForPercentage = 1;
		this.votesAgainstPercentage = 1;
	}

	public int getVotesFor() {
		return votesFor;
	}

	public void setVotesFor(int votesFor) {
		this.votesFor = votesFor;
	}

	public int getVotesAgainst() {
		return votesAgainst;
	}

	public void setVotesAgainst(int votesAgainst) {
		this.votesAgainst = votesAgainst;
	}

}

