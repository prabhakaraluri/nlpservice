/**
 * 
 */
package com.mynlp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mynlp.util.NLPConstants;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * @author valuri1
 *
 */
public class MyTokenizer {
	
	public String[] tokenizeInput(String input) throws IOException{
		InputStream is = new FileInputStream(NLPConstants.tokenModelFilePath);
		TokenizerModel model = new TokenizerModel(is);
		Tokenizer tokenizer = new TokenizerME(model);
		String tokens[] = tokenizer.tokenize(input);
		is.close();
		return tokens;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MyTokenizer tokenizer = new MyTokenizer();
			String[] tokens = tokenizer.tokenizeInput(NLPConstants.tokenizeInputString);
			for (int index=0;index<tokens.length;index++)
					System.out.println("Token : "+tokens[index]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
