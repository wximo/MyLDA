package Ximo.Lda.AlgoPart;

import java.io.IOException;
import java.util.ArrayList;

import Ximo.Lda.DataProcess.DataPreprocess;
import Ximo.Lda.Parameter.Parameter;
import Ximo.Lda.fileSys.FileSys;

public class LdaModel {
	
	static Parameter p=null;
	static LdaData ld=null;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//step 1 data part
		dataPart(10);
		for (int j=6;j<=6;j++){
			//step 2 algorithm part
				ld.setK(j);
				p.setTopicNum(j);
				LdaGibbsSampling lgs=new LdaGibbsSampling(j);
				lgs.gibbsSampling(0);
				System.out.println(j+" "+lgs.result());
		}
		}

	static public void dataPart(int j) throws IOException, InterruptedException{
		ArrayList<String> textsAll=null;
		ArrayList<ArrayList<String> > textsIndeped=null;

		p=new Parameter(j);
		//1.cut the texts and resaved
		//	DataPreprocess.textCut(p.getPathName()); finished before
		
		//2.read the new cuted texts
		textsAll=new ArrayList<String>();
		textsIndeped=new ArrayList<ArrayList<String> >();
		ArrayList<String> fileName=new ArrayList<String>();
		FileSys.readFiles(p.getPathName(), textsAll,fileName);
		//	FileSys.deleteLines(p.getPathName()); no need
		
		//3.data process
		DataPreprocess.textDeleteNoise(textsAll,textsIndeped);
	//	DataPreprocess.deleteLFwords(textsIndeped);
	
		//4.make the lda data
		ld=new LdaData(p);
		ld.wordsAll(textsIndeped);
		ld.fileName=fileName;
		//ld.AlgoData();//all parameters
	}
	public static void backupsLdaData(LdaData l){
		ld=l;
		System.out.println(ld.K);
		System.out.println(ld.M);
		System.out.println(ld.V);
		//FileSys.saveLda(p.getSavePath(), l);
	}
}
