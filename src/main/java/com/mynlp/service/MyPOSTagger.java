/**
 * 
 */
package com.mynlp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mynlp.util.NLPConstants;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * @author valuri1
 *
 */
public class MyPOSTagger {

	public String[] tagPOS(String input) throws IOException {
		InputStream tokenModelIn = null;
		InputStream posModelIn = null;
		String[] myStringArray = null;

		try {
			// tokenize the sentence
			tokenModelIn = new FileInputStream(NLPConstants.tokenModelFilePath);
			TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
			Tokenizer tokenizer = new TokenizerME(tokenModel);
			String tokens[] = tokenizer.tokenize(input);

			// Parts-Of-Speech Tagging
			// reading parts-of-speech model to a stream
			posModelIn = new FileInputStream(NLPConstants.posModelFilePath);
			// loading the parts-of-speech model from stream
			POSModel posModel = new POSModel(posModelIn);
			// initializing the parts-of-speech tagger with model
			POSTaggerME posTagger = new POSTaggerME(posModel);
			// Tagger tagging the tokens
			String tags[] = posTagger.tag(tokens);
			// Getting the probabilities of the tags given to the tokens
			double probs[] = posTagger.probs();

			System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");

			myStringArray = new String[tokens.length];

			for (int i = 0; i < tokens.length; i++) {
				//System.out.println(tokens[i] + "\t:\t" + tags[i] + "\t:\t" + probs[i]);
				myStringArray[i] = tokens[i] + " : " + tags[i] + " :  " + probs[i];
			}

		} catch (IOException e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		} finally {
			if (tokenModelIn != null) {
				try {
					tokenModelIn.close();
				} catch (IOException e) {
				}
			}
			if (posModelIn != null) {
				try {
					posModelIn.close();
				} catch (IOException e) {
				}
			}
		}
		return myStringArray;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MyPOSTagger myPOSTagger = new MyPOSTagger();
			String[] result = myPOSTagger.tagPOS(NLPConstants.posTagInputString);
			for(int index=0;index<result.length;index++)
				System.out.println(result[index]);
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
