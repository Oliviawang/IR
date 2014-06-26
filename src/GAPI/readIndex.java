package GAPI;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.util.Version;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class readIndex {
	
	public static void main(String[] args) {
		try{
			
			//if( args==null || args.length==0 ) {
				//System.out.println("args[0]: path of lucene index directory.");
				//System.out.println("args[1]: a word; postings of the word will be printed.");
		//	}
			
		//	String path_dir = args[0];
			String word = "island";
			
			// first step, build a lucene index reader
			Directory ixdir = FSDirectory.open(new File("D:\\workspace\\eclipse\\IR\\WebContent\\build.index"));
			IndexReader ixreader = IndexReader.open(ixdir);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
			
			// get a stem of the word
			String stem = getStem(word, analyzer);
			// now create a lucene Term object.
			// you should also specify in which document field you would like to search for the term ("CONTENT");
			Term term = new Term("TEXT", stem);
			
			// now retrieve posting list for the term
			// TermDocs is an object for reading term doc-freq posting
			// if you need a term doc-position posting, use ixreader.termPositions(term) to get a TermPositions posting object
			TermDocs posting = ixreader.termDocs(term);
			
			// retrieve basic information of the term
			System.out.print("term stem \""+stem+"\"");
			// get the document frequency DF for the term
			int DF = ixreader.docFreq(term);
			System.out.print(", DF = "+DF);
			// get the total number of document in the index
			int N = ixreader.numDocs();
			System.out.print(", N = "+N);
			if(DF>0){
				// now you can calculate IDF for the term.
				double IDF = Math.log((N)/(DF));
				System.out.printf(", IDF = %6.4f\n", IDF);
			}
			
			// now iteratively reading postings info
			System.out.printf("%-10s  %-30s  %-6s  %-6s\n", "internalid", "docno", "TF", "doclen");
			// each time posting.next() tries to move posting list to the next document, if false, it indicates the end of the posting list
			while(posting.next()){
				
				// you can read the current document and TF by doc() and freq()
				int docid = posting.doc();
				int tf = posting.freq();
				
				// now you can get the DOCNO we previously stored in the index for each document.
				String docno = ixreader.document(docid).get("DOCNO");
				
				// Lucene uses VSM and does not store document length in index, so you need to calculate it by yourself.
				// ixreader.getTermFreqVector(docid, "CONTENT") will give you a document vector object, you can 
				// sum up frequency of each word to calculate the length of the document
				int doclen = 0;
				for( int freq : ixreader.getTermFreqVector(docid, "TEXT").getTermFrequencies() ){
					doclen = doclen + freq;
				}
				System.out.printf("%-10s  %-30s  %-6d  %-6d\n", docid, docno, tf, doclen);
			}
			
			// Lucene does not store total frequency of a term in the whole index.
			// so, if you want to implement language model and smoothing, you probably need to calculate it by your self.
			
			// remeber to close it.
			ixreader.close();
			ixdir.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	static String getStem(String word, Analyzer analyzer) throws IOException {
		String stem = null;
		TokenStream ts = analyzer.tokenStream("TEXT", new StringReader(word));
		CharTermAttribute attr = ts.addAttribute(CharTermAttribute.class);
		if(ts.incrementToken()){
			stem = attr.toString();
		}
		ts.close();
		return stem;
	}
	
}
