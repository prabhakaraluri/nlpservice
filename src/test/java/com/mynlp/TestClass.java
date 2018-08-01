package com.mynlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.Span;

public class TestClass {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		String str = " Jane Smith has a problem wiht her right ligament. She consulted Mike Madril";
	 
		InputStream is = new FileInputStream("C:\\Users\\valuri1\\Desktop\\hack\\en-parser-chunking.bin");
		 
		ParserModel model = new ParserModel(is);
	 
		Parser parser = ParserFactory.create(model);
	 
		String sentence = str;//"Programcreek is a very huge and useful website.";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		
		String [] retval = new String[topParses.length];
		int i = 0;
	 
		for (Parse p : topParses) {
			p.show();
			retval[i] = p.getLabel();
			i++;
		}
		is.close();

	}

}
