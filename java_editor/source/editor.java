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
    static ArrayList<LinkedList<WordNode>> text = new ArrayList<>();

    //line
    static int x=1;
    //word
    static int y=1;
    //leter of word
    static int z=3;

    public static void editor_loop(Terminal terminal)throws Exception{
        while(true)
        {
            
	    int input= terminal.reader().read();
	    
	    if(input==27)
		break;
	    System.out.println("kay: " + input + " (" + (char)input + ")");

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
	LinkedList<WordNode> line1 = new LinkedList<>();
        WordNode word= new WordNode("first",4);
	line1.add(word);
        word=new WordNode("second",4);
	line1.add(word);
        text.add(line1);
        LinkedList<WordNode> line2 = new LinkedList<>();
        word=new WordNode("L2_first",8);
	line2.add(word);
        word=new WordNode("L2_second",0);
	line2.add(word);
        text.add(line2);	//open file
        //copy into array of links to string
        //close file
    }  
    
    public static void print(){
	for(int line_index=0; line_index<text.size();line_index++){
            int word_index=0;
	    for(WordNode word : text.get(line_index)){
		for(int i=0;i<word.getSpaces();i++)
                    System.out.print(" ");
                if(line_index==x && word_index==y)
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
