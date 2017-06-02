package controller.compare;

import java.util.ArrayList;

import javax.swing.JTextArea;

public interface CmpParagraph {

	//transform JTextArea contents to structured ArrayList data
	//return ArrayList<Paragraph>
	public ArrayList<Paragraph> constructPG(JTextArea contents);
	
	/*both side text paragraphs are moved to match
	the position of similar or even equal paragraphs*/
	//please check the Editable option value
	public void matchSimilarPG(ArrayList<Paragraph> Left_contents, ArrayList<Paragraph> Right_contents);
	
}