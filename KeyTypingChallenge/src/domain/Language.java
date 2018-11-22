package domain;

public class Language {

	private String consoleString;
	private String sourceCode;
	private String name;
	
	
	
	/**
	 * @param consoleString
	 * @param sourceCode
	 */
	public Language() {
	}
	public void setConsoleString(String consoleString) {
		this.consoleString = consoleString;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getConsoleString() {
		return consoleString;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public String getName() {
		return name;
	}
	
	
}
