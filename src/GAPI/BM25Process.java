package GAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Struts.TermResult;

public class BM25Process {
	  public static ArrayList<TermResult> FinalResult = new ArrayList<TermResult>();
	
	public BM25Process(){
		
		super();
		
	}
	
	public static ArrayList<TermResult> getBM25Result(ArrayList<TermResult> termResults){
		
		for (TermResult ResultItem : termResults) {
            boolean endflag = true;
            for (TermResult FinalResultItem : FinalResult) {
                if (FinalResultItem.docno.equals(ResultItem.docno)&&!FinalResultItem.docid.equals(ResultItem.docid)) {
                    FinalResultItem.score += ResultItem.score;
                    FinalResultItem.word += " " + ResultItem.word;
                    endflag = false;
                    break;
                }
            }
            if (endflag) {
                TermResult trResult = new TermResult();
                 trResult=ResultItem;
                 
                FinalResult.add(ResultItem);
            }
        }
		
		   Collections.sort(FinalResult, new Comparator<TermResult>() {

	            @Override
	            public int compare(TermResult o1, TermResult o2) {
	                if (o1.score > o2.score) {
	                    return -1;
	                } else if (o1.score < o2.score) {
	                    return 1;
	                } else {
	                    return 0;
	                }
	            }
	        

				
				});
		return FinalResult;		
	}
}
