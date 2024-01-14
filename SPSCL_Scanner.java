import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SPSCL_Scanner {
	public static void main(String[] args) {
		try {
		String current = "";
		documentReader codeDoc = new documentReader();
		boolean updateWord = true;
		
		//Arrays 
		String[] keywordArray = {"define","type", "of", "char", "integer", "long", "short", "double", "mvoid",
				"float","real","tstring","tbool","tbyte","function","return", "parameters", "pointer", "is", 
				"call", "set", "destroy", "call", "using", "if", "for", 
				"endif", "endfor", "to", "then", "create", "input", "exit"};
		List<String> keywordList = Arrays.asList(keywordArray);
		String[] symbolArray = {"(",")","{","}","[","]"};
		List<String> symbolList = Arrays.asList(symbolArray);
		String[] operatorsArray = {"+","-","/","=","*","<",">","!=", "<=", ">="};
		List<String> operatorList = Arrays.asList(operatorsArray);
	
		
		//import Finder
		Pattern importPatt = Pattern.compile("import");
		Matcher importMatch;
		//import Finder
		Pattern symbolPatt = Pattern.compile("symbol");
		Matcher symbolMatch;
		//Forward Declarations Finder
		Pattern forPatt = Pattern.compile("forward");
		Matcher forMatch;
		//Global Declarations Finder
		Pattern globPatt = Pattern.compile("global");
		Matcher globMatch;
		//implementations Finder
		Pattern impPatt = Pattern.compile("implementations");
		Matcher impMatch;
		//specifications Finder
		Pattern specPatt = Pattern.compile("specifications");
		Matcher specMatch;
		//forward Finder
		Pattern forPat = Pattern.compile("forward");
		Matcher forMatcher;
		//global Finder
		Pattern GlobPat = Pattern.compile("global");
		Matcher GlobMatcher;
		
		while (true) {
				if (updateWord) {
					current = codeDoc.getWord();
				} else {
					updateWord = true;
				}

				//update matchers to current word
				importMatch = importPatt.matcher(current);
				symbolMatch = symbolPatt.matcher(current);
				forMatch = forPatt.matcher(current);
				globMatch = globPatt.matcher(current);
				impMatch = impPatt.matcher(current);
				specMatch = specPatt.matcher(current);
				forMatcher = forPat.matcher(current);
				GlobMatcher = GlobPat.matcher(current);


// Import & Symbol State
				//IMPORT
				//Outputs the token for import and the library
				if (importMatch.find()) {
					System.out.println("TOKEN: Keyword, Lexeme: " + current);
					current = codeDoc.getWord();
					System.out.println("TOKEN: Identifier, Lexeme: " + current);
					}
				//SYMBOL
				else if (symbolMatch.find()) {
					System.out.println("TOKEN: Keyword, Lexeme: " + current);
					current = codeDoc.getWord();
					String[] nextSectionArray = {"symbol","import","specifications","forward","global","implementations"};
					boolean loop = true;
					while (loop) {
						if (checkForNextSection(current, nextSectionArray)) {
							loop = false;
							updateWord = false;
						} else {
							System.out.println("TOKEN: Identifier, Lexeme: " + current);
							current = codeDoc.getWord();
						}
						
					}
					
					}
//Specifications State
				
				else if (specMatch.find()) {
					System.out.println("TOKEN: Keyword, Lexeme: " + current);
					current = codeDoc.getWord();
					String[] nextSectionArray = {"forward","global","implementations"};
					boolean loop = true;
					while (loop) {
					if (checkForNextSection(current, nextSectionArray)) {
						updateWord = false;
						loop = false;
					} else {
						switch (current) {
						case "definetype":
			//definetype
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
			// <type>
						current = codeDoc.getWord();
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						
			// type name
						current = codeDoc.getWord();
						System.out.println("TOKEN: Identifier, Lexeme: " + current);	
			//new type name
						current = codeDoc.getWord();
						System.out.println("TOKEN: Identifier, Lexeme: " + current);
						break;
						case "struct":
			//struct
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
			//struct name
						current = codeDoc.getWord();
						System.out.println("TOKEN: Identifier, Lexeme: " + current);		
			//is
						current = codeDoc.getWord();
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						current = codeDoc.getWord();
						boolean loop2 = true;
						while (loop2) {
						Pattern endStrPatt = Pattern.compile("endstruct");
						Matcher endStrMat = endStrPatt.matcher(current);
						if (endStrMat.find()) {
							loop2 = false;
			//endstruct
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
			//struct name
							current = codeDoc.getWord();
							System.out.println("TOKEN: Identifier, Lexeme: " + current);
						} else {
							switch (current) {
							case "define":
			//define
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							break;
							case "of":
			//of
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
			//type
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
			//datatype
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							break;
							case "pointer":
			//of
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							break;
							default:
			//identifiers
							System.out.println("TOKEN: Identifier, Lexeme: " + current);
							}
							current = codeDoc.getWord();
						}
						}	
						break;
						case "enum":
			//enum
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						current = codeDoc.getWord();
			//enum name
						System.out.println("TOKEN: Identifier, Lexeme: " + current);
						current = codeDoc.getWord();
			//is
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						current = codeDoc.getWord();
			//possible values
						String[] endSection = {"endenum"};
						boolean loop4 = true;
						while (loop4) {
							if (checkForNextSection(current,endSection)) {
								loop4 = false;
							} else if (keywordList.contains(current)){
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
							} else if (symbolList.contains(current)) {
								System.out.println("TOKEN: Symbol, Lexeme: " + current);
								current = codeDoc.getWord();
							} else if (operatorList.contains(current)) {
								System.out.println("TOKEN: Operator, Lexeme: " + current);
								current = codeDoc.getWord();
							} else {
								System.out.println("TOKEN: Identifier, Lexeme: " + current);
								current = codeDoc.getWord();
							}
							
						}
			//endenum
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						current = codeDoc.getWord();
			// enum name
						System.out.println("TOKEN: Identifier, Lexeme: " + current);
						break;
						}
						current = codeDoc.getWord();
					}
				}
			}
				
//forward declarations Sections
				else if (forMatcher.find()) {
					String temp = current;
					current = codeDoc.getWord();
					Pattern declPatt = Pattern.compile("declarations");
					Matcher declarationMatch = declPatt.matcher(current);
					
					if (declarationMatch.find()) {
			//Forward Declarations
						System.out.println("TOKEN: Keyword, Lexeme: " + temp + " " + current);
						current = codeDoc.getWord();
						String[] sectionArray = {"global","implementations"};
					//contents of section
					boolean loop = true;
					while (loop) {
						//checks for next sections
						if (checkForNextSection(current, sectionArray)) {
							loop = false;
							updateWord = false;
						} else {
							Pattern t = Pattern.compile("type");
							Matcher typeMatcher = t.matcher(current);
							Pattern t2 = Pattern.compile("of");
							Matcher ofMatcher = t2.matcher(current);
							if (ofMatcher.find()) {
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
							} else if (typeMatcher.find()) {
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
							} else if (keywordList.contains(current)) {
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
							} else {
								System.out.println("TOKEN: Identifier, Lexeme: " + current);
							}
							current = codeDoc.getWord();
						}
					}
				}
				}	
				
//Global Declarations 
				else if (GlobMatcher.find()) {
					String temp = current;
					current = codeDoc.getWord();
					Pattern declPatt = Pattern.compile("declarations");
					Matcher declarationMatch = declPatt.matcher(current);
					
					if (declarationMatch.find()) {
			//Global Declarations
						System.out.println("TOKEN: Keyword, Lexeme: " + temp + " " + current);
						
						boolean loop = true;
						while (loop) {
						String[] constSection = {"constant"};
						String[] varSection = {"variables"};
						String[] structSection = {"structures"};
						String[] endFunSection = {"implementations"};
						
						//Checks for condition to end global declarations
						if (checkForNextSection(current,endFunSection)) {
							loop = false;
					//constants
						} else if (checkForNextSection(current,constSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							while(loop2) {
								//Checks each word for a list of keywords
								String[] sects = {"variable","structures","implementations"};
								if (checkForNextSection(current, sects)) {
									loop2 = false;
								} else if (keywordList.contains(current)){
									System.out.println("TOKEN: Keyword, Lexeme: " + current);
									current = codeDoc.getWord();
								} else if (symbolList.contains(current)) {
									System.out.println("TOKEN: Symbol, Lexeme: " + current);
									current = codeDoc.getWord();
								} else if (operatorList.contains(current)) {
									System.out.println("TOKEN: Operator, Lexeme: " + current);
									current = codeDoc.getWord();
								} else {
									System.out.println("TOKEN: Identifier, Lexeme: " + current);
									current = codeDoc.getWord();
								}
							}
					//variables
						} else if (checkForNextSection(current,varSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							String[] catList = {"structures","implementations"};
							while (loop2 == true) {
								if (checkForNextSection(current,catList)) {
									loop2 = false;
								} else {
									//Variables section only contains two types of tokens
									if (keywordList.contains(current)) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
									} else {
										System.out.println("TOKEN: Identifier, Lexeme: " + current);
									}
									current = codeDoc.getWord();
								}
							}
					//structures	
						} else if (checkForNextSection(current,structSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							String[] catList = {"begin","endfun"};
							while (loop2 == true) {
								if (checkForNextSection(current,catList)) {
									loop2 = false;
								} else {
									switch (current) {
								case "define":
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								break;
								case "of":
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								break;
								default:
								System.out.println("TOKEN: Identifier, Lexeme: " + current);
								}
								current = codeDoc.getWord();	
								}
							}
						} else {
							current = codeDoc.getWord();
						}	
						
						}

					}
					}
				
				
				
//implementations
				
				else if (impMatch.find()) {
				System.out.println("TOKEN: Keyword, Lexeme: " + current);
				
				boolean loop = true;
				while (loop) {
					//Next word block adjusted to terminate loop if at end of text
					current = codeDoc.getWord();
					Pattern funcPat = Pattern.compile("function");
					Matcher funcMatch = funcPat.matcher(current);
					if (funcMatch.find()) {
						System.out.println("TOKEN: Keyword, Lexeme: " + current);
						current = codeDoc.getWord();
//MAIN FUNCTION STATEMENT
						Pattern mainPat = Pattern.compile("main");
						Matcher mainMatch = mainPat.matcher(current);
						if (mainMatch.find()) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
// FUNCTION STATEMENT (EXCLUDING MAIN)
						} else {
							//function name
							System.out.println("TOKEN: Identifier, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							String[] sections = {"variables","structures","begin","endfun"};
							while (loop2) {
								if (checkForNextSection(current, sections)) {
									loop2 = false;
								} else {
									Pattern typePat = Pattern.compile("type");
									Matcher typeMatcher = typePat.matcher(current);
									Pattern ofPat = Pattern.compile("of");
									Matcher ofMatcher = ofPat.matcher(current);
									if (current.contains(",")) {
										current.replace(",", "");
									}
									if (ofMatcher.find()) {
										String tempLine = ("TOKEN: Keyword, Lexeme: " + current);
										current = codeDoc.getWord();
										tempLine += (" " + current);
										System.out.println(tempLine);
									}
									else if (typeMatcher.find()) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
										current = codeDoc.getWord();
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
									}
									else if (keywordList.contains(current)) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
									}
									else {
										System.out.println("TOKEN: Identifier, Lexeme: " + current);
									}
									current = codeDoc.getWord();
								}
							}
						}
						//Keeps the program in the main state
						
//ADDRESSING CONTENTS OF FUNCTIONS
						boolean loop3 = true;
						while (loop3 == true) {
							
							String[] varSection = {"variables"};
							String[] constSection = {"constants"};
							String[] structSection = {"structures"};
							String[] beginSection = {"begin"};
							String[] endFunSection = {"endfun"};
							//Checks for condition to end main state
//END OF FUNCTION
							if (checkForNextSection(current,endFunSection)) {
								loop3 = false;
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								Pattern mainPat2 = Pattern.compile("main");
								Matcher mainMatch2 = mainPat2.matcher(current);
								if (mainMatch2.find()) {
									System.out.println("TOKEN: Keyword, Lexeme: " + current);
								} else {
									System.out.println("TOKEN: Identifier, Lexeme: " + current);
								}
								
//CONSTANTS SECTION
							} else if (checkForNextSection(current,constSection)) {
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								boolean loop2 = true;
								while(loop2) {
									//Checks each word for a list of keywords
									String[] sects = {"variable","structures","begin","endfun"};
									if (checkForNextSection(current, sects)) {
										loop2 = false;
									} else if (keywordList.contains(current)){
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
									} else if (symbolList.contains(current)) {
										System.out.println("TOKEN: Symbol, Lexeme: " + current);
									} else if (operatorList.contains(current)) {
										System.out.println("TOKEN: Operator, Lexeme: " + current);
									} else {
										System.out.println("TOKEN: Identifier, Lexeme: " + current);
									}
									current = codeDoc.getWord();
								}
								
//VARIABLE SECTION
							} else if (checkForNextSection(current,varSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							String[] catList = {"structures","begin","endfun"};
							while (loop2 == true) {
								if (checkForNextSection(current,catList)) {
									loop2 = false;
								} else {
									if (keywordList.contains(current)) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
									} else {
										System.out.println("TOKEN: Identifier, Lexeme: " + current);
									}
									current = codeDoc.getWord();
								}
							}
//STRUCTURE SECTION	
						} else if (checkForNextSection(current,structSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							String[] catList = {"begin","endfun"};
							while (loop2 == true) {
								if (checkForNextSection(current,catList)) {
									loop2 = false;
								} else {
									switch (current) {
								case "define":
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								break;
								case "of":
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								current = codeDoc.getWord();
								System.out.println("TOKEN: Keyword, Lexeme: " + current);
								break;
								default:
								System.out.println("TOKEN: Identifier, Lexeme: " + current);
								}
								current = codeDoc.getWord();	
								}
							}
//BEGIN SECTION
						} else if (checkForNextSection(current,beginSection)) {
							System.out.println("TOKEN: Keyword, Lexeme: " + current);
							current = codeDoc.getWord();
							boolean loop2 = true;
							while (loop2) {
								Pattern displayPat = Pattern.compile("display");
								Matcher displayMatch = displayPat.matcher(current);
								if (checkForNextSection(current,endFunSection)) {
									loop2 = false;
								} else {
									if (keywordList.contains(current)) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
										current = codeDoc.getWord();
									} else if (displayMatch.find()) {
										System.out.println("TOKEN: Keyword, Lexeme: " + current);
										current = codeDoc.getWord();
										Pattern quotePat = Pattern.compile("\"");
										Matcher quoteMatch = quotePat.matcher(current);
										if (quoteMatch.find()) {
											boolean loop4 = true;
											String word = current.replace("\"", "");
											current = codeDoc.getWord();
											while (loop4 == true) {
												quotePat = Pattern.compile("\"");
												quoteMatch = quotePat.matcher(current);
												if (quoteMatch.find()) {
													loop4 = false;
													word += (" " + current);
													System.out.println("TOKEN: Identifier, Lexeme: \"" + word);
													current = codeDoc.getWord();
												} else {
													word += (" " + current);
													current = codeDoc.getWord();
												}
											}
										}		
									} else if (symbolList.contains(current)){
										System.out.println("TOKEN: Symbol, Lexeme: " + current);
									} else {
										Pattern quotePat = Pattern.compile("\"");
										Matcher quoteMatch = quotePat.matcher(current);
										if (quoteMatch.find()) {
											boolean loop4 = true;
											String word = current.replace("\"", "");
											current = codeDoc.getWord();
											while (loop4 == true) {
												quotePat = Pattern.compile("\"");
												quoteMatch = quotePat.matcher(current);
												
												if (quoteMatch.find()) {
													loop4 = false;
													word += (" " + current);
													System.out.println("TOKEN: Identifier, Lexeme: \"" + word);
													current = codeDoc.getWord();
												} else {
													word += (" " + current);
													current = codeDoc.getWord();
												}
											}
										} else if (checkForSymbol(current)) {
											String currentLine = current;
											while (codeDoc.hasNext()) {
												current = codeDoc.getWord();
												currentLine += current;
											}
											String outSentence = "Equation Breakdown: ";
											String identifierWord = "";
											boolean symbolTest= false;
											char[] c = currentLine.toCharArray();
											for (int counter = 0; counter < c.length; counter++) {
												char letter = c[counter];
												Character[] operatorArray = {'+','-','/','=','*','<','>','!'};
												Character[] symbolsArray = {'(',')','{','}','[',']'};
												boolean stringOperator = false;
												char secondOperator = 't';
												
												//iterates a character through all symbols
												for (Character s : symbolsArray) {
													if (letter == s) {
														if (identifierWord != "") {
															outSentence += "Identifier- " + identifierWord + ", ";
															identifierWord = "";
															}
														symbolTest = true;
														outSentence += ("Symbol- " + letter + ", ");
														break;
													}
												}
												//iterates a character through all operators
												for (Character i : operatorArray) {
												if (letter == i) {
													//if there is a match, checks if the symbol is two characters long
													
													//Skips the check if current character is at the end of the line
													if (counter == (c.length - 1)) {
													} else {
													char nextValue = c[counter + 1];
													//iterates second character through all operators
													for (Character j : operatorArray) {
														if (nextValue == j) {
														//if there is a second character operator, marks a boolean
														stringOperator = true;
														secondOperator = nextValue;
														counter++;
														} 
													} }
													symbolTest = true;
													//adds current identifier before marking operator
													if (identifierWord != "") {
													outSentence += "Identifier- " + identifierWord + ", ";
													identifierWord = "";
													}
													if (stringOperator) {
													stringOperator = false;
													outSentence += ("Operator- " + letter + secondOperator + ", ");
													} else {
													outSentence += ("Operator- " + letter + ", ");
													}
													break;
												} }
												
												
												
												//prevents operator from being added to identifier word
												if (symbolTest == true) {
													symbolTest = false;
												} else {
													identifierWord += letter;
												}	
											}
											//After line is processed, prints the equation analysis
											if (identifierWord != "") {
												outSentence += "Identifier- " + identifierWord;
											}
											System.out.println(outSentence);
											current = codeDoc.getWord();
										} else {
											System.out.println("TOKEN: Identifier, Lexeme: " + current);
											current = codeDoc.getWord();
										}
									}
								}
							}
						} else {
							current = codeDoc.getWord();
						}		
						}
					}
				}
			} else {
				System.out.println("Error identifying word: " + current);
			}
			
		}
		
		}catch (Exception E) {
			System.out.println("HIT: " + E);
			E.printStackTrace(System.out);
		}
	}
	private static boolean checkForNextSection(String word, String[] categoryList) {
		List<String> catList = Arrays.asList(categoryList);
		if (catList.contains(word)) {
			return true;
		} else {
			return false;
		}		
	}
	private static boolean checkForSymbol(String word) {
		char[] c = {'+','-','/','=','*'};
		
		for (char i : word.toCharArray()) {
			for (int counter = 0; counter < c.length; counter++) {
				if (i == c[counter]) {
					return true;
				}
			}
			
		}
		return false;	
	}
	
}