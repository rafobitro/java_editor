import java.util.ArrayList;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

class WordNode{
    public StringBuilder word;
    public int spaces;

    public WordNode(String word , int spaces){
	    this.word=new StringBuilder(word);
	    this.spaces=spaces;
    }
}



public class editor{
    //constants
	private static final int ESC = 27;
    private static final int BACKSPACE = 127;

    
    //main data structure of a editor
    static ArrayList<ArrayList<WordNode>> text = new ArrayList<>();

    //courser codinates 
    static int X=0;
    static int Y=0;
    static int wordIndex=0;
    static int charIndex=0;

	static void init(String file_name[]){
        ArrayList<WordNode> l1 = new ArrayList<>();
        l1.add(new WordNode("first", 0));
        l1.add(new WordNode("second", 4));
	l1.add(new WordNode("third", 4));
        l1.add(new WordNode("forth", 4));
	l1.add(new WordNode ("",1));
        text.add(l1);

        ArrayList<WordNode> l2 = new ArrayList<>();
        l2.add(new WordNode("L2_first", 0));
        l2.add(new WordNode("L2_second", 4));
	l2.add(new WordNode("L2_third", 4));
        l2.add(new WordNode("L2_forth", 4));
        l2.add(new WordNode ("",1));
        text.add(l2);

	ArrayList<WordNode> l3 = new ArrayList<>();
        l3.add(new WordNode("L3_first", 0));
        l3.add(new WordNode("L3_second", 4));
	l3.add(new WordNode("L3_third", 4));
        l3.add(new WordNode("L3_forth", 4));
        l3.add(new WordNode ("",1));
        text.add(l3);

	ArrayList<WordNode> l4 = new ArrayList<>();
        l4.add(new WordNode("L4_first", 0));
        l4.add(new WordNode("L4_second", 4));
	l4.add(new WordNode("L4_third", 4));
        l4.add(new WordNode("L4_forth", 4));
        l4.add(new WordNode ("",1));
	text.add(l4);
     
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
		        if(secondInput==91){
		            int thirdInput = terminal.reader().read();
                    if(thirdInput == 65){ 	
			            movement_logic_UP();
			            change=true;
		            }
		            else if(thirdInput ==66){ 
		                movement_logic_DAWN();	
			            change=true;
		            }
		            else if(thirdInput ==67){ 
			            movement_logic_RIGHT();
			            change=true;
		            }
		            else if(thirdInput ==68){ 
			            movement_logic_LEFT();
			            change=true;
					}
		            //for testing //System.out.println("kay: " + input +" and " + secondInput + " and " + thirdInput + " ("+ (char)input + ")");break;
		        }
		        else
		            break;
            }
	        else{
                insert(input);
		        change=true;
	        }
	     //for testing //System.out.println("kay: " + input + " (" + (char)input + ")");break;
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
	System.out.print("\033[H\033[2J");
	System.out.flush();

	System.out.println("X " + X + " real_Y " + Y + " wordIndex " + wordIndex + " charIndex " + charIndex);

	for (int line_index = 0; line_index < text.size(); line_index++) {
		int wIndex = 0;
		for (WordNode word : text.get(line_index)) {
			String spaces = " ".repeat(word.spaces);
			
			if (line_index == X && wIndex == wordIndex && charIndex < word.spaces){ 
				System.out.print(spaces.substring(0, charIndex) +"\u001b[43m" + spaces.charAt(charIndex) + "\u001b[0m" +spaces.substring(charIndex + 1));
			} 
			else {
				System.out.print(spaces);
			}

			if (line_index == X && wIndex == wordIndex && charIndex >= word.spaces) {
				int pos = charIndex - word.spaces;
				System.out.print(
				word.word.substring(0, pos) +"\u001b[43m" + word.word.charAt(pos) + "\u001b[0m" +word.word.substring(pos + 1));
			} 
			else {
				System.out.print(word.word);
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
