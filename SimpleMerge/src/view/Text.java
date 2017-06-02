package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

import model.compare.ComparedLine;
import model.compare.Line;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class Text extends JTextPane
{
	private ArrayList<Line> arraylist;
	private ArrayList<LineColorOffset> linecolorlist;
	private Highlighter hilite;
	private Document doc;//getDocument()통해서 초기화
	private DefaultHighlightPainter yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
	private DefaultHighlightPainter grayPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
	private DefaultHighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
	
	//File path needed
	public Text()
	{
		hilite = new MyHighlighter();
		setHighlighter(hilite); 
		//Test
		setText("Line1\n\n\n\nLine2\nLine3\nLine4\nLine5\n");
		//
	}
	
	public Text AcceptLineColor(ArrayList<Line> arraylist)
	{
		try 
		{
			String[] text = doc.getText(0, doc.getLength()).split("\n");
			int start = 0;
			int end = 0;
			int gray_color = 0;
			int yellow_color = 0;//같은 색상이 연속될 경우
			ComparedLine comparedline;
			LineColorOffset linecoloroffset = null;
			for(int i = 0; i < text.length; i ++)
			{
				comparedline = (ComparedLine)arraylist.get(i);
				if(comparedline.tag.equals(ComparedLine.Tag.space))
				{
					hilite.addHighlight(start, start + text[i].length() + 1, grayPainter);
					if(gray_color == 0 && yellow_color == 0)
						linecoloroffset = new LineColorOffset(start);
					if(yellow_color == 1){
						linecoloroffset.setEnd(start - 1);
						linecolorlist.add(linecoloroffset);
						linecoloroffset = new LineColorOffset(start);
						yellow_color = 0;
					}
					gray_color = 1;
				}
				else if(comparedline.tag.equals(ComparedLine.Tag.notequal))
				{
					hilite.addHighlight(start, start + text[i].length() + 1, yellowPainter);
					if(gray_color == 0 && yellow_color == 0)
						linecoloroffset = new LineColorOffset(start);
					if(gray_color == 1){
						linecoloroffset.setEnd(start - 1);
						linecolorlist.add(linecoloroffset);
						linecoloroffset = new LineColorOffset(start);
						gray_color = 0;
					}
					yellow_color = 1;
				}
				start += text[i].length() + 1;
			}
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
		return this;
	}
	//툴바 버튼을 통해서 선택된 라인의 집합을 Red로 색상 전환
	public void SelectLineSet(int index) throws BadLocationException
	{
		hilite.addHighlight(linecolorlist.get(index).getStart(), linecolorlist.get(index).getEnd(), redPainter);
	}
	public ArrayList<LineColorOffset> getLineColorList(){
		return linecolorlist;
	}
}
//색칠된 구간의 위치를 저장
class LineColorOffset
{
	int start;
	int end;
	public LineColorOffset(int start)
	{
		this.start = start;
	}
	public void setEnd(int end)
	{
		this.end = end;
	}
	public int getStart()
	{
		return start;
	}
	public int getEnd() 
	{
		return end;
	}
}
