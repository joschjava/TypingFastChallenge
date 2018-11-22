package domain;

public class HighscoreObject {
	private String name;
	private int score;
	/**
	 * @param name
	 * @param score
	 */
	public HighscoreObject(String name, int score) {
		this.name = name;
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	@Override
	public String toString() {
		return name+" " + score;
	}
	
	
	
}
