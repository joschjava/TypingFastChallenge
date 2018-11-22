package domain;

import java.io.Serializable;

public class HighscoreObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448355170074940051L;
	/**
	 * 
	 */

	
	/**
	 * 
	 */

	private String name;
	private int score;
	private String language;
	
	/**
	 * @param name
	 * @param score
	 */
	public HighscoreObject(String name, int score, String language) {
		this.name = name;
		this.score = score;
		this.language = language;
	}
	public String getName() {
		return name;
	}
	public String getLanguage() {
		return language;
	}
	public int getScore() {
		return score;
	}
	@Override
	public String toString() {
		return name+"("+language+") " + score;
	}
	
	
	
}
