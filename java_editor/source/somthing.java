asda asd asd 
 try (BufferedReader reader = new BufferedReader(new FileReader(file_name[0]))) {
        String line;
        while ((line = reader.readLine()) != null) {
	    int n=line.length();

	    ArrayList<WordNode> l = new ArrayList<>();
	    String word="";
	    int spaces=0;

	    for(int i=0;i<n;i++){
               
		if(line.get(i)==' '){
		    if(word.length!=0){
			l.add(new WordNode(word,spaces));
			spaces=0;
			word="";
		    }	    
		    else spaces++;
		}
		else{
                    word+=line.get(i);
		}
		

	    
	    if(word.length!=0){
                l.add(new WordNode(word, spaces));
	        l.add(new WordNode ("",1));
            }
	    else{
                l.add(new WordNode("",spaces+1));
	    }
	        text.add(l);
	    }
	}
    } catch (IOException e) {
        e.printStackTrace();
    }
