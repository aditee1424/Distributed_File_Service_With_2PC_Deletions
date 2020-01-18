//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
//Site Referred for this class:- 
//https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
public class CustomOutputStream extends OutputStream {
	  private JTextArea textArea;

	    public CustomOutputStream(JTextArea textArea) {
	        this.textArea= textArea;
	    }
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
        // keeps the textArea up to date
        textArea.update(textArea.getGraphics());

	}

}
