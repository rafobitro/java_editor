import java.util.ArrayList;
import java.util.LinkedList;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

class WordNode{

    private String word;
    private int spaces;


    public WordNode(String string , int integer){
	word=string;
	spaces=integer;
    }

    public void changeWord(String newWord){
        word=newWord;
    }
    public void changeSpaces(int newSpaces){
        spaces=newSpaces;
    }
    
    public String getWord(){
        return word;
    }

    public int getSpaces(){
        return spaces;
    }
}



public class editor{

    
    //declar array of links to strings
    static ArrayList<ArrayList<WordNode>> text = new ArrayList<>();

    //line
    static int x=1;
    //word
    static int y=0;
    //leter of word
    static int z=0;



    public static void movement_logic_DAWN(){
        if(x<text.size()-1)
	    x++;
	else
	    x=0;
    }

    public static void movement_logic_UP(){
       if(x>0)
           x--;
       else
	   x=text.size()-1;
    }

    public static void movement_logic_LEFT(){
           if(z>0)
	       z--;
	   else{
	       if(y==0){
	           movement_logic_UP();
                   y=text.get(x).size()-1;
	       }
	       else 
	           y--;
	       z=text.get(x).get(y).getWord().length()-1;
	   }
    }

    public static void movement_logic_RIGHT(){
           if(z<text.get(x).get(y).getWord().length()-1)
	       z++;
	   else{
	       if(y==text.get(x).size()-1){
                   movement_logic_DAWN();
		   y=0;
	       }
	       else 
	           y++;
	       z=0;
	   }
    }

    public static void editor_loop(Terminal terminal)throws Exception{
        boolean change = true;
	while(true){
	    	    
            if(change){
                change=false;
		print();
	    }
            
            int input= terminal.reader().read();


	    if(input==27){
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
		     System.out.println("kay: " + input +" and " + secondInput + " and " + thirdInput + " ("+ (char)input + ")");
		}
		else
		    break;
	    }
	   // else    // System.out.println("kay: " + input + " (" + (char)input + ")");

	    //print
            //wait response
            //if esceape brake
            //if charcter add
            //if movement change cordinates
            //reprint
        }
    }

    public static void init(String file_name[]){
    
        //linked list test
	ArrayList<WordNode> line1 = new ArrayList<>();
        WordNode word= new WordNode("first",0);
	line1.add(word);
        word=new WordNode("second",4);
	line1.add(word);
        text.add(line1);
        ArrayList<WordNode> line2 = new ArrayList<>();
        word=new WordNode("L2_first",0);
	line2.add(word);
        word=new WordNode("L2_second",4);
	line2.add(word);
        text.add(line2);
	ArrayList<WordNode> line3 = new ArrayList<>();
        word=new WordNode("L3_first",0);
	line3.add(word);
        word=new WordNode("L3_second",4);
	line3.add(word);
        text.add(line3);	//open file
        //copy into array of links to string
        //close file
    }
    
    public static void print(){
	System.out.print("\033[H\033[2J");
        System.out.flush();


	System.out.println("x "+ x +" y "+ y + " z " + z);
	for(int line_index=0; line_index<text.size();line_index++){
            int word_index=0;
	    for(WordNode word : text.get(line_index)){
                String spaces="";
		for(int i=0;i<word.getSpaces();i++)
			spaces+=" ";
                    if(line_index==x && word_index==y && z<word.getSpaces())
  	                System.out.print(spaces.substring(0, z) +"\u001b[43m" + spaces.charAt(z) +"\u001b[0m" + spaces.substring(z+ 1));
		    else
		        System.out.print(spaces);
                if(line_index==x && word_index==y && z>=word.getSpaces())
		    System.out.print(word.getWord().substring(0, z) +"\u001b[43m" + word.getWord().charAt(z) +"\u001b[0m" + word.getWord().substring(z+ 1));
		else
		    System.out.print(word.getWord());
	        word_index++;
	    }

	    System.out.println();
	}
        //print array of links to string
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
