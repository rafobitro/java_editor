# Java Text Editor (Learning Project)

This project was my way of getting familiar with the Java language  
by trying to create a simple text editor with some basic editing functionality.  

I intentionally avoided looking at existing editor implementations  
so I could explore my own approach.  

After later researching how real editors work,  
I realized that my strategy — focusing on separating words  
instead of indexing characters — makes the editing logic  
unnecessarily complex and difficult to maintain.  

This project is more of a learning experiment than a practical editor.  
And while it does not use the most efficient editor technologies,  
it demonstrates reasonable performance —  
using almost no CPU or memory under typical usage.  

It already includes (or will soon include) features such as:  
- Syntax highlighting  
- Copy and paste  
- Undo and redo (tree-based)  
- Cursor movement by words  
- Proper cursor behavior when changing lines  

---

## Architecture  

The main architectural idea is based on **word nodes**:  

- Each `WordNode` represents a dynamic string (a word).  
- Each `WordNode` also stores an integer representing the number of spaces that follow it.  
- This means the document is essentially stored as a chain of words + space counts, instead of a flat character array.  

### Performance Considerations  

- Example: if you have 100,000 lines of code and delete the first line,  
  the editor rewrites 99,999 pointers.  
  This is not the most efficient approach, but it is still a reasonable workload for modern computers.  

- Because of this design, even simple editing operations require complex handling:  
  splitting words, merging words, deleting characters, and constantly working  
  with four values — **XY coordinates, word index, and character index**.  

- This makes the key-handling logic more complicated and sometimes hard to read,  
  even after several rewrites for clarity.  

- I could simplify things by hiding `wordIndex` and `charIndex`  
  and only toggling XY positions.  
  I even wrote a function that recalculates word and character indexes  
  from XY coordinates.  
  However, this approach would hurt performance (especially for extremely long single lines).

  
