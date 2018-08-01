/**
 * 
 */
package com.mynlp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mynlp.util.NLPConstants;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * @author valuri1
 *
 */
public class MySentenceDetector {

	/**
	 * @param args
	 */

	public String[] detectSentense(String input) throws IOException {

		String paragraph = input;
		
		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream(NLPConstants.sentenseModelFilePath);
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		String sentences[] = sdetector.sentDetect(paragraph);
		is.close();
		return sentences;
	}

	public static void main(String[] args) {

		try {
			MySentenceDetector sd = new MySentenceDetector();
			String input = NLPConstants.sentenceInputString;
			String[] res = sd.detectSentense(input);
			for (int index = 0; index < res.length; index++) {
				System.out.println("Sentense : " + res[index]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
