 for()    charIndex=0;               
   for    for for() for() for for int asdasdasd for       
            text.aasdindd int for(X+1,new ArrayList<WordNode>());               
     int  int int (X+1,new ArrayList<String>());               
     for            
int(int wordI=wordIndex;wordI<text.get(X).size();wordI++){               
  text.get(X+1).add (new WordNode(text.get(X).get(wordI)));               
  colors.get(X+1).add ( new String(colors.get(X).get(wordI)));               
     }               
     for(int wordI=text.get(X).size()-2;wordI>=wordIndex;wordI--){               
  text.get(X).remove(wordI);               
  colors.get(X).remove(wordI);               
     }               
     text.get(X).get(wordIndex).spaces=charIndex;               
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
                
