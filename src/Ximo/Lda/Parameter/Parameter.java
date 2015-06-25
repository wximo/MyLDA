package Ximo.Lda.Parameter;

public class Parameter {
	double alpha;
	double beta=0.01;
	int topicNum;
	int Steps=1000;
	int saveSteps=10;
	int saveBeginAt=998;
	//String pathName="data\\original data";
	//String pathName="data/original data";
	
	String pathName="Data61";
	
	//String savePath="data\\result";
	String savePath="result6";
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Parameter(int i){topicNum=i;alpha=50/topicNum;}
	
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getBeta() {
		return beta;
	}
	public void setBeta(double beta) {
		this.beta = beta;
	}
	public int getTopicNum() {
		return topicNum;
	}
	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}
	public int getSteps() {
		return Steps;
	}
	public void setSteps(int steps) {
		Steps = steps;
	}
	public int getSaveSteps() {
		return saveSteps;
	}
	public void setSaveSteps(int saveSteps) {
		this.saveSteps = saveSteps;
	}
	public int getSaveBeginAt() {
		return saveBeginAt;
	}
	public void setSaveBeginAt(int saveBeginAt) {
		this.saveBeginAt = saveBeginAt;
	}
	
}
