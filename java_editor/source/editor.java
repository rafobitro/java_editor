import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


class WordNode{
    public StringBuilder word;
    public int spaces;

    public WordNode(String word , int spaces){
	    this.word=new StringBuilder(word);
	    this.spaces=spaces;
    }

    public WordNode(StringBuilder word , int spaces){
	    this.word=word;
	    this.spaces=spaces;
    }


    public WordNode(WordNode wordNode){
           word=wordNode.word;
	   spaces=wordNode.spaces;
    }
}



public class editor{
    //constants
    static final int ESC = 27;
    static final int BACKSPACE = 127;
    static final int BRACKET = 91;
    static final int ARROW_UP = 65;
    static final int ARROW_DOWN = 66;
    static final int ARROW_RIGHT = 67;
    static final int ARROW_LEFT = 68;
    
    static final String CLEAR_SCREEN = "\033[H\033[2J";
    static final String RESET = "\u001b[0m";
    static final String ANSI_BG_YELLOW = "\u001b[43m";

    //main data structure of a editor
    static ArrayList<ArrayList<WordNode>> text = new ArrayList<>();

    //main data structor for coloring 
    static ArrayList<ArrayList<String>> colors = new ArrayList<>();

    //unordered map for sintax coloring
    static HashMap<String, String> word_to_color = new HashMap<>();
    
    static String defoultColor="\u001b[37m";

    //courser codinates 
    static int X=0;
    static int Y=0;
    static int wordIndex=0;
    static int charIndex=0;

static void init(String file_name[]){
   initSyntaxColoring("../configs/jav.txt");     
   try (BufferedReader reader = new BufferedReader(new FileReader(file_name[0]))) {
        String line;
        while ((line = reader.readLine()) != null) {
	    int n=line.length();

	    
	    ArrayList<WordNode>   wordLine = new ArrayList<>();
	    ArrayList<String> colorLine = new ArrayList<>();
	    String word="";
	    int spaces=0;

	    for(int i=0;i<n;i++){
               
		if(line.charAt(i)==' '){
		    if(word.length()!=0){
			if(word_to_color.containsKey(word))
			    colorLine.add(new String(word_to_color.get(word)));    
			else
			    colorLine.add(new String(defoultColor));    
			wordLine.add(new WordNode(word,spaces));
			spaces=1;
			word="";
		    }	    
		    else spaces++;
		}
		else{
                    word+=line.charAt(i);
		}
		

	    }

	    if(word.length()!=0){
		if(word_to_color.containsKey(word))
		    colorLine.add(new String(word_to_color.get(word)));    
		else
		    colorLine.add(new String(defoultColor));    
                wordLine.add(new WordNode(word, spaces));
	        wordLine.add(new WordNode ("",1));
		colorLine.add(new String(defoultColor));    
            }
	    else{
                wordLine.add(new WordNode("",spaces+1));
		colorLine.add(new String(defoultColor));    
	    }
	        colors.add(colorLine);
	        text.add(wordLine);
	}
	
    } catch (IOException e) {
        e.printStackTrace();
    }
}

static void initSyntaxColoring(String filename){
    word_to_color.put("int","\u001b[31m");
    word_to_color.put("try","\u001b[34m");
    try(BufferedReader reader =  new BufferedReader(new FileReader(filename))){
        String line;
	while((line = reader.readLine())!= null){
	    String[] parts=line.split("\\s+");
	    if (parts.length == 2) {
                String keyword   = parts[0];
                String colorCode = parts[1];

                // convert "\u001b" (text) → real ESC char
                colorCode = colorCode.replace("\\u001b", "\u001b");

                word_to_color.put(keyword, colorCode);

	    }

	}


    }catch(IOException e) {
	e.printStackTrace();
    }
}
    
    static void colibrateY(){
	Y=charIndex;
	    for(int word_index=0;word_index<wordIndex;word_index++){
	        Y+=text.get(X).get(word_index).word.length();
	        Y+=text.get(X).get(word_index).spaces;
	    }
    }

    static void colibrateZY(){
        int line_length=0;
	    boolean breaked_loop=false;
		for(int word_index=0;word_index<text.get(X).size();word_index++){
	        //word length equals string.length+spaces
			int word_length=text.get(X).get(word_index).word.length();
	        word_length+=text.get(X).get(word_index).spaces;
			
	        if(line_length+word_length>Y){
	            wordIndex=word_index;
		        charIndex=Y-line_length;
		        breaked_loop=true;
		        break;
	        }
	        else{
                line_length+=word_length;
	        }
	    }

		//if Y is longer then line set maxposibale value
	    if (!breaked_loop){
	        wordIndex=text.get(X).size()-1;
     	    charIndex=text.get(X).get(wordIndex).word.length()-1+text.get(X).get(wordIndex).spaces;
	    }
    }

    static void movement_logic_DAWN(){
        if(X<text.size()-1){
	        X++;
	        colibrateZY();
	    }
	    else{
	        X=0;
	        colibrateZY();
	    }
    }

    static void movement_logic_UP(){
        if(X>0){
            X--;
	        colibrateZY();
        }
        else{
	        X=text.size()-1;
	        colibrateZY();
        }
    }

    static void movement_logic_LEFT(){
        if(charIndex>0){
	        charIndex--;
	        Y--;
	    }
	    else{
	        if(wordIndex==0){
	            movement_logic_UP();
                wordIndex=text.get(X).size()-1;
	        }
	        else{ 
	            wordIndex--;
	            Y--;
	        }
			//move last character
	        charIndex=text.get(X).get(wordIndex).word.length()-1+text.get(X).get(wordIndex).spaces;
	        colibrateY();
	    }
    }

    static void movement_logic_RIGHT(){
        if(charIndex<text.get(X).get(wordIndex).word.length()-1+text.get(X).get(wordIndex).spaces){
	        charIndex++;
	        Y++;
	    }
	    else{
	        if(wordIndex==text.get(X).size()-1){ 
		        movement_logic_DAWN();
		        wordIndex=0;
		    }
	        else{ 
	            wordIndex++;
		        Y++;
	        }
	        charIndex=0;
	        colibrateY();
	    }
    }

    static void editor_loop(Terminal terminal)throws Exception{
        boolean change = true;
	    while(true){	    
            if(change){
                change=false;
		        print();
	        }
            
            int input= terminal.reader().read();
            //excape sequiance
            if(input==ESC){
	            int secondInput = terminal.reader().read();
		    if(secondInput==BRACKET){
		        int thirdInput = terminal.reader().read();
                            if(thirdInput == ARROW_UP){ 	
			            movement_logic_UP();
			            change=true;
		            }
		            else if(thirdInput ==ARROW_DOWN){ 
		                movement_logic_DAWN();	
			            change=true;
		            }
		            else if(thirdInput ==ARROW_RIGHT){ 
			            movement_logic_RIGHT();
			            change=true;
		            }
		            else if(thirdInput ==ARROW_LEFT){ 
			            movement_logic_LEFT();
			            change=true;
				}
		            //System.out.println("kay: " + input +" and " + secondInput + " and " + thirdInput + " ("+ (char)input + ")");break;
		        }
		        else
		            break;
            }
	    else{
                    insert(input);
		    change=true;
	        }
	    // System.out.println("kay: " + input + " (" + (char)input + ")");break;
        }
    }


	//helper functions for insert function
	static boolean isInWord(){
		return (charIndex>text.get(X).get(wordIndex).spaces);
	}

public static void insert(int input){
	if(input==BACKSPACE){
		if(charIndex==0){
	                if(wordIndex==0){
			   if(X!=0){
				text.get(X).get(wordIndex).spaces+=text.get(X-1).get(text.get(X-1).size()-1).spaces;
				int old_size=text.get(X-1).size()-1;
				text.get(X-1).remove(old_size);
				for(int wordI=0;wordI<text.get(X).size();wordI++){
			            text.get(X-1).add(new WordNode(text.get(X).get(wordI)));
				}
				text.remove(X);

				X--;
				wordIndex=old_size;
				charIndex=0;
				colibrateY();
				
                           }
		
			}
			else{
				text.get(X).get(wordIndex - 1).word.deleteCharAt(text.get(X).get(wordIndex - 1).word.length() - 1);
				if(text.get(X).get(wordIndex-1).word.length() ==0){
					text.get(X).get(wordIndex).spaces+=text.get(X).get(wordIndex-1).spaces;
					text.get(X).remove(wordIndex-1);}
					Y--;
				
				    colibrateZY();
			    }
		    }
		else if (isInWord()){
			text.get(X).get(wordIndex).word.deleteCharAt(charIndex-text.get(X).get(wordIndex).spaces-1);
			Y--;
			charIndex--;
		}
		else if(!isInWord()){
			if(text.get(X).get(wordIndex).spaces>1 || wordIndex==0 && Y!=0){
				text.get(X).get(wordIndex).spaces--;
				Y--;
				charIndex--;
			}
			else if(wordIndex!=0){
				text.get(X).get(wordIndex-1).word.append(text.get(X).get(wordIndex).word);
				text.get(X).remove(wordIndex);
				Y--;
				colibrateZY();
			}
		}
	}
	else if(input == 13){
            if(isInWord()){
	         text.get(X).add(wordIndex+1,new WordNode("",0));
		        
		int cut_size=0;
		for(int i=charIndex+1;i<=text.get(X).get(wordIndex).word.length()+text.get(X).get(wordIndex).spaces;i++){
                    text.get(X).get(wordIndex + 1).word.append(text.get(X).get(wordIndex).word.charAt(i - text.get(X).get(wordIndex).spaces - 1));
	            cut_size++;
			}

			int start = text.get(X).get(wordIndex).word.length() - cut_size;
                        text.get(X).get(wordIndex).word.delete(start, text.get(X).get(wordIndex).word.length());
				
		    Y++;
		    wordIndex++;
		    charIndex=0;
	    }
            text.add(X+1,new ArrayList<WordNode>());
	    for(int wordI=wordIndex;wordI<text.get(X).size();wordI++){
		text.get(X+1).add(new WordNode(text.get(X).get(wordI)));
	    }
	    for(int wordI=text.get(X).size()-2;wordI>=wordIndex;wordI--)
		text.get(X).remove(wordI);
	    text.get(X).get(wordIndex).spaces+=charIndex;
	    text.get(X+1).get(0).spaces-=charIndex;
	    X++;
	    wordIndex=0;
            charIndex=0;
	    Y=0;
	    
	}
        else if((char) input==' '){
	    if(isInWord()){
                text.get(X).add(wordIndex+1,new WordNode("",1));
		        
		int cut_size=0;
		for(int i=charIndex+1;i<=text.get(X).get(wordIndex).word.length()+text.get(X).get(wordIndex).spaces;i++){
                    text.get(X).get(wordIndex + 1).word.append(text.get(X).get(wordIndex).word.charAt(i - text.get(X).get(wordIndex).spaces - 1));
	            cut_size++;
			}

			int start = text.get(X).get(wordIndex).word.length() - cut_size;
            text.get(X).get(wordIndex).word.delete(start, text.get(X).get(wordIndex).word.length());
				
		    Y++;
		    wordIndex++;
		    charIndex=1;
		}
	    else{
            text.get(X).get(wordIndex).spaces++;
	        charIndex++;
	        Y++;
	    }
	}
	else /* character */{
        if (charIndex == 0 && wordIndex!=0) {
            text.get(X).get(wordIndex - 1).word.append((char) input);
            Y++;
        } 
	//isInWord or next to word 
        else if (charIndex>=text.get(X).get(wordIndex).spaces ) {
            text.get(X).get(wordIndex).word.insert(charIndex-text.get(X).get(wordIndex).spaces,(char) input);
            Y++;
            charIndex++;
	} 
	    else {
	        text.get(X).get(wordIndex).spaces -= charIndex;
	        text.get(X).add(
	        wordIndex,
	        new WordNode(new StringBuilder().append((char) input).toString(), charIndex));
	        Y++;
	        wordIndex++;
	        charIndex = 0;
	    }
	}
}
	
public static void print(){
	System.out.print(CLEAR_SCREEN);
	System.out.flush();

	System.out.println("X " + X + " real_Y " + Y + " wordIndex " + wordIndex + " charIndex " + charIndex);

	for (int line_index = 0; line_index < text.size(); line_index++) {
		int wIndex = 0;
		for (int word_index=0; word_index<text.get(line_index).size();word_index++) {
			WordNode word= text.get(line_index).get(word_index);
			String color=colors.get(line_index).get(word_index);
			String spaces = " ".repeat(word.spaces);
		        	
			if (line_index == X && wIndex == wordIndex && charIndex < word.spaces){ 
				System.out.print(spaces.substring(0, charIndex) +ANSI_BG_YELLOW + spaces.charAt(charIndex) + RESET +spaces.substring(charIndex + 1));
			} 
			else {
				System.out.print(spaces);
			}

			if (line_index == X && wIndex == wordIndex && charIndex >= word.spaces) {
				int pos = charIndex - word.spaces;
				System.out.print(color+word.word.substring(0, pos) +ANSI_BG_YELLOW + word.word.charAt(pos) +RESET+ color +word.word.substring(pos + 1)+RESET);
			} 
			else {
				System.out.print(color+word.word+RESET);
			}
			wIndex++;
		}
		System.out.println();
	}
}


    public static void main (String args[])throws Exception{

        /*test
        java.io.File file= new java.io.File("test.txt");
	System.out.println("dose exist? "+file.exists());
        */
        init(args);
       // print();
        Terminal terminal = TerminalBuilder.terminal();
        terminal.enterRawMode();
	    editor_loop(terminal);
	//reed args file
        //copy into memmory
        //editor_loop
        //exit 
    }
}
