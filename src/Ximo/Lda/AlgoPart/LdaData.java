package Ximo.Lda.AlgoPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Ximo.Lda.Parameter.Parameter;

public class LdaData {
	ArrayList<String> fileName=null;
	
	int M;//number of articles
	int K;//number of topics
	int V;//number of words
	
	ArrayList<String> wordsAll=null;//all words
	HashMap<String,Integer> word2int=null;//all words and their index
	
	int[][] text;//text[i][j] i'th text,j'th word the index
	int[][] topic;//topic[i][j] i'th text,j'th word the topic
	double alpha;//super parameter of dirichlet
	double beta;//super parameter of dirichlet
	int[][] textsTopic;//textsTopic[i][j] i'th text,j'th topic number
	int[][] topicWord;//topicWord[i][j] i'th topic,j'th words number
	int[] topicsNums;// topicsNums[i] i'th topic the number of words
	int[] textsNumofWords; // textsNumofWords[i] i'th texts the number of words
	double[][] phi; //topic-word distribution parameter
	double[][] theta;//doc-topic distribution parameter
	
	double[][] wordsTopic;

	public LdaData(Parameter p){
		wordsAll=new ArrayList<String>();
		word2int=new HashMap<String,Integer>();
		alpha=p.getAlpha();
		beta=p.getBeta();
		K=p.getTopicNum();
		fileName=new ArrayList<String>();
	}
	
	//save the data of all lda part.
	
	public ArrayList<String> getFileName() {
		return fileName;
	}

	public void setK(int k) {
		K = k;
	}

	public int getM() {
		return M;
	}

	public int getK() {
		return K;
	}

	public int getV() {
		return V;
	}

	public ArrayList<String> getWordsAll() {
		return wordsAll;
	}

	public HashMap<String, Integer> getWord2int() {
		return word2int;
	}

	public int[][] getText() {
		return text;
	}

	public int[][] getTopic() {
		return topic;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public int[][] getTextsTopic() {
		return textsTopic;
	}

	public int[][] getTopicWord() {
		return topicWord;
	}

	public int[] getTopicsNums() {
		return topicsNums;
	}

	public int[] getTextsNumofWords() {
		return textsNumofWords;
	}

	public double[][] getPhi() {
		return phi;
	}

	public double[][] getTheta() {
		return theta;
	}

	public void wordsAll(ArrayList<ArrayList<String> > textsIndeped){
		//all words count and delete the repeat
		//...
		M=textsIndeped.size();
		wordsAll=new ArrayList<String>();
		for(int i=0;i<textsIndeped.size();i++){
			wordsAll.addAll(textsIndeped.get(i));
		}
		Set<String> ss=new HashSet<String>();
		ss.addAll(wordsAll);
		wordsAll.clear();
		wordsAll.addAll(ss);
		
		V=wordsAll.size();
		wordsTopic=new double[V][K];
		word2int();
		
		text=new int[M][];
		topic=new int[M][];
		for(int i=0;i<M;i++){
			text[i]=new int[textsIndeped.get(i).size()];
			topic[i]=new int[textsIndeped.get(i).size()];
			for(int j=0;j<textsIndeped.get(i).size();j++){
				text[i][j]=word2int.get(textsIndeped.get(i).get(j));
			}
		}
	}
	
	public void word2int(){
		for(int i=0;i<V;i++){
			word2int.put(wordsAll.get(i), i);
		}
	}
	
}


