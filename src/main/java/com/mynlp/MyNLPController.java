package com.mynlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mynlp.service.MyPOSTagger;
import com.mynlp.service.MyPersonNameFinder;
import com.mynlp.service.MySentenceDetector;
import com.mynlp.service.MyTokenizer;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

@RestController
public class MyNLPController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public BaseOutput greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new BaseOutput(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("/SentenceDetect")
	public static String[] detectSentence(@RequestParam(value="str", defaultValue="badvalue") String str) throws IOException {
    	String paragraph = str;
    	MySentenceDetector sd = new MySentenceDetector();
		return sd.detectSentense(paragraph);
	}
    
    @RequestMapping("/Tokenize")
	public static String[] tokenize(@RequestParam(value="str", defaultValue="badvalue") String str) throws IOException {
		MyTokenizer myTokenizer = new MyTokenizer();
		return myTokenizer.tokenizeInput(str);
	}
	
    @RequestMapping("/findName")
	public static String[] findName(@RequestParam(value="str", defaultValue="badvalue") String str) throws IOException {
		MyPersonNameFinder myPersonNameFinder = new MyPersonNameFinder();
		return myPersonNameFinder.findNames(str);
	}
	
   @RequestMapping("/POSTag")
	public static String[] POSTag(@RequestParam(value="str", defaultValue="badvalue") String str) throws IOException {	
	   MyPOSTagger myPOSTagger = new MyPOSTagger();
	   return myPOSTagger.tagPOS(str);
	}
	
    @RequestMapping("/chunk")
	public static void chunk() throws IOException {
		POSModel model = new POSModelLoader()
				.load(new File("C:\\Users\\valuri1\\Desktop\\hack\\en-pos-maxent.bin"));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);
	 
		String input = "Hi. How are you? This is Mike.";
		Charset charset = Charset.forName("UTF-8");
		InputStreamFactory isf = new MarkableFileInputStreamFactory(new File("myText.txt"));
		ObjectStream<String> lineStream = new PlainTextByLineStream(isf, charset);
		/*ObjectStream<String> lineStream = new PlainTextByLineStream(
				new StringReader(input));*/
	 
		perfMon.start();
		String line;
		String whitespaceTokenizerLine[] = null;
	 
		String[] tags = null;
		while ((line = lineStream.read()) != null) {
			whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			tags = tagger.tag(whitespaceTokenizerLine);
	 
			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			System.out.println(sample.toString());
				perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
	 
		// chunker
		InputStream is = new FileInputStream("C:\\Users\\valuri1\\Desktop\\hack\\en-chunker.bin");
		ChunkerModel cModel = new ChunkerModel(is);
	 
		ChunkerME chunkerME = new ChunkerME(cModel);
		String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
	 
		for (String s : result)
			System.out.println(s);
	 
		Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
		for (Span s : span)
			System.out.println(s.toString());
	}
	
    @RequestMapping("/Parse")
	public static String[] Parse(@RequestParam(value="str", defaultValue="badvalue") String str) throws InvalidFormatException, IOException {
		// http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Parser#Training_Tool
		InputStream is = new FileInputStream("C:\\Users\\valuri1\\Desktop\\hack\\en-parser-chunking.bin");
	 
		ParserModel model = new ParserModel(is);
	 
		Parser parser = ParserFactory.create(model);
	 
		String sentence = str;//"Programcreek is a very huge and useful website.";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		
		String [] retval = new String[topParses.length];
		int i = 0;
	 
		for (Parse p : topParses) {
			p.show();
			//p.get
			retval[i] = p.getLabel();
			i++;
		}
		is.close();
		
		return retval;
		
	 
		/*
		 * (TOP (S (NP (NN Programcreek) ) (VP (VBZ is) (NP (DT a) (ADJP (RB
		 * very) (JJ huge) (CC and) (JJ useful) ) ) ) (. website.) ) )
		 */
	}
    
    
    
}