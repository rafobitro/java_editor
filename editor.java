import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

class WordNode{

    private String word;
    private int spaces;


    public WordNode(String string , int integer){
	word=string;
	spaces=integer;
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


    //point cordinate x-line y-word y2-character init to 0 0 0
    public static void editor_loop (){
        while(true)
        {
            //print
            //wait response
            //if esceape brake
            //if charcter add
            //if movement change cordinates
            //reprint
        }
    }

    public static void init(String file_name[])
    {
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
	for(LinkedList<WordNode> line : text){
            for(WordNode word : line){
		for(int i=0;i<word.getSpaces();i++)
                    System.out.print(" ");
		System.out.print(word.getWord());
		    
	    }
	    System.out.println();
	}
        //print array of links to string
    }  

    public static void main (String args[])
    {
        /*test
        java.io.File file= new java.io.File("test.txt");
	System.out.println("dose exist? "+file.exists());
        */
        init(args);
        print();
        //reed args file
        //copy into memmory
        //editor_loop
        //exit 
    }
}
