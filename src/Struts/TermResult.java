package Struts;

public class TermResult {
public Integer docid;
public String docno;
public Integer doclen;
public double score;
public String word;
	public void setDocID(int docid) {
		// TODO Auto-generated method stub
		this.docid=docid;
	}

	public void setDocNum(String docno) {
		// TODO Auto-generated method stub
		this.docno=docno;
	}

	public void setLength(int doclen) {
		// TODO Auto-generated method stub
	this.doclen=doclen;	
	}

	public void setTermFreq(double score) {
		// TODO Auto-generated method stub
		this.score=score;
	}

	public void setWord(String word) {
		// TODO Auto-generated method stub
		this.word=word;
	}

	
	
}
