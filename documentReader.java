import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class documentReader {
	String input;
	BufferedReader br;
	Scanner scan;
	String current;
	public documentReader(){
		try {
		String pre = System.getProperty("user.dir");
		File file = new File(pre + "/welcome.scl"); 
		br = new BufferedReader(new FileReader(file));
		input = br.readLine();
		scan = new Scanner(input);
		} catch (Exception E) {
			System.out.println(E);
		}
	}
	public boolean hasNext() {
		if (scan.hasNext()) {
			return true;
		} else {
			return false;
		}
	}
	public String getWord() {
		try {
			//comment finder #1
			Pattern commentPatt = Pattern.compile("//");
			Matcher commentMatch;
			//comment finder #2
			Pattern commentPatt2 = Pattern.compile("\\/*");
			Matcher commentMatch2;
			//if ((input = br.readLine()) != null) {
				//Updates current word and checks for the start of comments
				if (scan.hasNext()) {
					current = scan.next();
					
					
					//Description Finder
					Pattern descPatt = Pattern.compile("description");
					Matcher descMatch = descPatt.matcher(current);
					//Description State
					if (descMatch.find()) {
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						boolean loop = true;
						String descriptionSentence = "";
						while (loop) {
							if (scan.hasNext()) {
								current = scan.next();
								} else {
								while (!scan.hasNext()) {
									//Checks for end of program
									if ((input = br.readLine()) == null) {
										System.out.println("Concluding scan of document.");
										System.exit(0);
									} else {
										scan = new Scanner(input);
									}	
								}
								current = scan.next();
								}
						//Compiles all of the description as a lexeme
						Pattern endDescPatt = Pattern.compile("\\*/");
						Matcher endDescMatch = endDescPatt.matcher(current);
						if (endDescMatch.find()) {
							if (scan.hasNext()) {
								current = scan.next();
								} else {
								while (!scan.hasNext()) {
									//Checks for end of program
									if ((input = br.readLine()) == null) {
										System.out.println("Concluding scan of document.");
										System.exit(0);
									} else {
										scan = new Scanner(input);
									}	
								}
								current = scan.next();
								}
							loop = false;
						} else {
							descriptionSentence += " " + current;
						}
						}
						//Outputs the description token and lexeme
						System.out.println("TOKEN: Definition, Lexeme: " + descriptionSentence);
					}
					
					
					//checking for // comments. When found, jumps to next line
					commentMatch = commentPatt.matcher(current);
					while(commentMatch.find()) {
						input = br.readLine();
						scan = new Scanner(input);
						//if at end of the line jump to next
						while (!scan.hasNext()) {
							//Checks for end of program
							if ((input = br.readLine()) == null) {
								System.out.println("Concluding scan of document.");
								System.exit(0);
							} else {
								scan = new Scanner(input);
							}	
						}
						current = scan.next();
						}
					commentMatch2 = commentPatt2.matcher(current);
					List<String> commentList = new ArrayList<String>();
					commentList.add("/*");
					List<String> endCommentList = new ArrayList<String>();
					endCommentList.add("*/");
					boolean loop5 = commentList.contains(current);
					while(loop5) {
						if (endCommentList.contains(current)) {
							if (scan.hasNext()) {
								current = scan.next();
								} else {
								while (!scan.hasNext()) {
									//Checks for end of program
									if ((input = br.readLine()) == null) {
										System.out.println("Concluding scan of document.");
										System.exit(0);
									} else {
										scan = new Scanner(input);
									}	
								}
								current = scan.next();
								}
							
							if (!commentList.contains(current)) {
								commentMatch = commentPatt.matcher(current);
								while(commentMatch.find()) {
									input = br.readLine();
									scan = new Scanner(input);
									//if at end of the line jump to next
									while (!scan.hasNext()) {
										//Checks for end of program
										if ((input = br.readLine()) == null) {
											System.out.println("Concluding scan of document.");
											System.exit(0);
										} else {
											scan = new Scanner(input);
										}
									}
									//updates current word
									current = scan.next();
									//Ensures the updated word is not a comment
									commentMatch = commentPatt.matcher(current);
									}
								loop5 = false;
								break;
							}
						}
						if (scan.hasNext()) {
							current = scan.next();
							} else {
							while (!scan.hasNext()) {
								//Checks for end of program
								if ((input = br.readLine()) == null) {
									System.out.println("Concluding scan of document.");
									System.exit(0);
								} else {
									scan = new Scanner(input);
								}
							}
							current = scan.next();
							}
						}
					//Ensures the updated word is not a comment
					commentMatch = commentPatt.matcher(current);
				//if there are no more words on line, move to next line
				} else {
					while (!scan.hasNext()) {
						if ((input = br.readLine()) == null) {
							System.out.println("Concluding scan of document.");
							System.exit(0);
						} else {
							scan = new Scanner(input);
						}
					}
					current = scan.next();
					
					
					
					
					
					//checking for // comments. When found, jumps to next line
					commentMatch = commentPatt.matcher(current);
					while(commentMatch.find()) {
						input = br.readLine();
						scan = new Scanner(input);
						//if at end of the line jump to next
						while (!scan.hasNext()) {
							//Checks for end of program
							if ((input = br.readLine()) == null) {
								System.out.println("Concluding scan of document.");
								System.exit(0);
							} else {
								scan = new Scanner(input);
							}
						}
						//updates current word
						current = scan.next();
						//Ensures the updated word is not a comment
						commentMatch = commentPatt.matcher(current);
						}
					commentMatch2 = commentPatt2.matcher(current);
					List<String> commentList = new ArrayList<String>();
					commentList.add("/*");
					List<String> endCommentList = new ArrayList<String>();
					endCommentList.add("*/");
					// If /* is encountered, the code will loop until */ is encountered
					boolean loop5 = commentList.contains(current);
					while(loop5) {
						if (endCommentList.contains(current)) {
							if (scan.hasNext()) {
								current = scan.next();
								} else {
								while (!scan.hasNext()) {
								//Checks for end of program
								if ((input = br.readLine()) == null) {
										System.out.println("Concluding scan of document.");
										System.exit(0);
									} else {
										scan = new Scanner(input);
									}
								}
								current = scan.next();
								}
							if (!commentList.contains(current)) {
								commentMatch = commentPatt.matcher(current);
								while(commentMatch.find()) {
									input = br.readLine();
									scan = new Scanner(input);
									//if at end of the line jump to next
									while (!scan.hasNext()) {
										//Checks for end of program
										if ((input = br.readLine()) == null) {
												System.out.println("Concluding scan of document.");
												System.exit(0);
											} else {
												scan = new Scanner(input);
											}	
									}
									//updates current word
									current = scan.next();
									//Ensures the updated word is not a comment
									commentMatch = commentPatt.matcher(current);
									}
								loop5 = false;
								break;
							}
						}
						
						if (scan.hasNext()) {
							current = scan.next();
							} else {
							while (!scan.hasNext()) {
							input = br.readLine();
							scan = new Scanner(input);	
							}
							current = scan.next();
							}
						}
				}
			//}
			return current;
		} catch (Exception E) {
			System.out.println("SECOND SCRIPT " + E);
			E.printStackTrace(System.out);
			System.out.println("\n");
			return current;
		}
		
	}
	
	
}
