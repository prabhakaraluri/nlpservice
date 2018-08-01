/**
 * 
 */
package com.mynlp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mynlp.util.NLPConstants;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * @author valuri1
 *
 */
public class MyPersonNameFinder {

	public String[] findNames(String input) throws IOException {
		InputStream is = new FileInputStream(NLPConstants.personNameModelFilePath);
		TokenNameFinderModel model = new TokenNameFinderModel(is);
		is.close();
		NameFinderME nameFinder = new NameFinderME(model);
		String[] sentence = input.split(" ");
		Span nameSpans[] = nameFinder.find(sentence);
		String[] names = new String[nameSpans.length];
		int i = 0;
		for (Span s : nameSpans) {
			// System.out.println(s.toString());
			String name = "";
			for (int index = s.getStart(); index < s.getEnd(); index++) {
				// System.out.println(sentence[index] + " ");
				name += sentence[index] + " ";
			}
			names[i] = name;
			i++;
		}
		return names;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyPersonNameFinder myNameFinder = new MyPersonNameFinder();
			String[] result = myNameFinder.findNames(NLPConstants.personNameInputString);

			for (int index = 0; index < result.length; index++)
				System.out.println("Names : " + result[index]);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
