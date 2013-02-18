package com.dev.campus.analyser;
import java_cup.runtime.Symbol;

public class TimeKeeper {
	//private Reader content;
	private static Scanner scanner;
	private static Parser parser;
	public static Parser mParser = null;
	public static Scanner mScanner = null;

	public void parse(String[] args) {
		Symbol result = null;
		try {
			Reader  myFile = new StringReader(args[0]);
			mScanner = new Scanner(myFile);
			mParser = new Parser(mScanner);
		} catch (Exception e) {
			System.err.println("Invalid file");
		}

		try {
			result = mParser.parse();
		} catch (Exception e) {
			System.out.println("FAILED");
		}
	}
	
	/*public TimeParser(String s){
		content = new StringReader(s);
	}
	
	public ArrayList<String> getTimes() {
		ArrayList<String> al = new ArrayList<String>();
		return al;
	}*/

}
