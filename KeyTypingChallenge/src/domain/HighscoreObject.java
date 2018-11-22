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
