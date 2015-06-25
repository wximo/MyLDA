package Ximo.Lda.AlgoPart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import Ximo.Lda.Parameter.Parameter;

public class LdaGibbsSampling {
	Map<String,ArrayList<Double>> w=new HashMap<String,ArrayList<Double>>();
	Map<String,ArrayList<Double>> ww=new HashMap<String,ArrayList<Double>>();
	LdaData ld=null;
	Parameter p=null;
	public LdaGibbsSampling(int i){
	ld=new LdaData(new Parameter(i));p=LdaModel.p;ld=LdaModel.ld;
	ld.textsTopic=new int[ld.M][ld.K];
	ld.topicWord=new int[ld.K][ld.V+1];
	ld.topicsNums=new int[ld.K];
	ld.textsNumofWords=new int[ld.M];
	ld.wordsTopic=new double[ld.V][ld.K];
	}
	
	public void gibbsSampling(int m) throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("gibbs sampling");
		initTopics1();
		for(int stp=0;stp<p.getSteps();stp++){
			if(stp>=p.getSaveBeginAt()){
				updateParam();
				stepSave(stp);
			}else{
				updateParam();
			}
			
			//if(stp%10==0){
		//		System.out.print(result()+",");
		//	}
			
			for(int i=0;i<ld.M;i++){
				int N=ld.text[i].length;
				for(int j=0;j<N;j++){
					int newTopic=SampleNewTopic(i,j);
					ld.topic[i][j]=newTopic;
				}
			}	
		}
		System.out.println("\nfinished\n");
		
		LdaModel.backupsLdaData(ld);
		
	}
	
	public void initTopics(){
		for(int i=0;i<ld.M;i++){
			int n=ld.text[i].length;
			for(int j=0;j<n;j++){
				int initTopic = (int)(Math.random() *ld.K);
				ld.topic[i][j]=initTopic;
				ld.textsTopic[i][initTopic]++;
				ld.topicWord[initTopic][ld.text[i][j]]++;
				ld.topicsNums[initTopic]++;
			}
			ld.textsNumofWords[i]=n;
		}
		//TODO update
		
		ld.phi=new double[ld.K][ld.V];
		ld.theta=new double[ld.M][ld.K];
	}
	
	@SuppressWarnings("resource")
	public void initTopics1() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("read xianyan distrbutions");
		Scanner reader=null;
		String path="result5/999-wordsTopic.txt";
		reader=new Scanner(new InputStreamReader(new FileInputStream(path),"UTF-8"));
		
		while(reader.hasNext()){
			ArrayList<Double> d=new ArrayList<Double>();		ArrayList<Double> d2=new ArrayList<Double>();
			String s="";
			s=reader.next();
			Double t=0.0;
			for(int i=0;i<6;i++){
				t=reader.nextDouble();
				d.add(t);
				d2.add(t);
			}
			ww.put(s, d2);
			for(int i=1;i<6;i++){
				d.set(i, d.get(i)+d.get(i-1));
			}
			w.put(s, d);
		}
		
		
		for(int i=0;i<ld.M;i++){
			int n=ld.text[i].length;
			for(int j=0;j<n;j++){
				int initTopic=0;
				if(w.containsKey(ld.wordsAll.get(ld.text[i][j]))){
					//System.out.print(1);
				double u = Math.random() *w.get(ld.wordsAll.get(ld.text[i][j])).get(5) ; //p[] is unnormalized
				int newTopic=0;
				for(newTopic = 0; newTopic < ld.K; newTopic++){
					if(u < w.get(ld.wordsAll.get(ld.text[i][j])).get(newTopic)){
						//System.out.println(w.get(ld.wordsAll.get(i))+" "+newTopic);
						initTopic=newTopic;
						break;
					}
				}
				}else{//System.out.print(0);
				 initTopic = (int)(Math.random() *ld.K);}
				ld.topic[i][j]=initTopic;
				ld.textsTopic[i][initTopic]++;
				ld.topicWord[initTopic][ld.text[i][j]]++;
				ld.topicsNums[initTopic]++;
				
			}
			ld.textsNumofWords[i]=n;
		}
		//TODO update
		
		ld.phi=new double[ld.K][ld.V];
		ld.theta=new double[ld.M][ld.K];
		
		
		
		System.out.println("finished");
	}
	
	
	public void updateParam(){
		for(int i=0;i<ld.K;i++){
			for(int j=0;j<ld.V;j++){
				ld.phi[i][j]=(ld.topicWord[i][j]+ld.beta)/(ld.topicsNums[i]+ld.V*ld.beta);
				ld.wordsTopic[j][i]=ld.phi[i][j];
			}
		}
		for(int i=0;i<ld.M;i++){
			for(int j=0;j<ld.K;j++){
				ld.theta[i][j]=(ld.textsTopic[i][j]+ld.alpha)/(ld.textsNumofWords[i]+ld.K*ld.alpha);
			}
		}
	}
	
	public int SampleNewTopic(int m,int n){
		//to do
		int oldTopic=ld.topic[m][n];
		ld.textsNumofWords[m]--;
		ld.textsTopic[m][oldTopic]--;
		ld.topicsNums[oldTopic]--;
		ld.topicWord[oldTopic][ld.text[m][n]]--;
		
		double p[]=new double[ld.K];
		for(int i=0;i<ld.K;i++){
			p[i]=(ld.topicWord[i][ld.text[m][n]]+ld.beta)/(ld.topicsNums[i]+ld.V*ld.beta)*(ld.textsTopic[m][i]+ld.alpha)/(ld.textsNumofWords[m]+ld.K*ld.alpha);
		}
		
		//cumulated probability for p 
		for(int i=1;i<ld.K;i++){
			p[i]+=p[i-1];
		}
		
		double u = Math.random() * p[ld.K-1]; //p[] is unnormalized
		
		int newTopic=0;
		for(newTopic = 0; newTopic < ld.K; newTopic++){
			if(u < p[newTopic]){
				//System.out.println(oldTopic+" "+newTopic);
				break;
			}
		}
		
		ld.textsNumofWords[m]++;
		ld.textsTopic[m][newTopic]++;
		ld.topicsNums[newTopic]++;
		ld.topicWord[newTopic][ld.text[m][n]]++;
		
		return newTopic;
	}
	
	public void stepSave(int th){
		String path=p.getSavePath();
		path=path+"/"+String.valueOf(th);
		
		String p1=path+"-phi-topic-word.txt";
		String p2=path+"-theta-doc-topic.txt";
		String p3=path+"-wordsTopic.txt";
		
		int num=p.getSaveSteps();
		//num=p.getTopicNum()*5;
		//phi K*V	
		try {
			File f=new File(p1);
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);
			TreeMap<Double,ArrayList<Integer> >tmphi=new TreeMap<Double,ArrayList<Integer> >(new descendComparator());
			for(int i=0;i<ld.K;i++){
				for(int j=0;j<ld.V;j++){
					if(tmphi.containsKey(ld.phi[i][j])){
						ArrayList<Integer> a=new ArrayList<Integer>();
						a=tmphi.get(ld.phi[i][j]);
						a.add(j);
						tmphi.put(ld.phi[i][j], a);
					}else{
						ArrayList<Integer> a=new ArrayList<Integer>();
						a.add(j);
						tmphi.put(ld.phi[i][j], a);
					}
				}
				Set<Double> keys = tmphi.keySet();
				Iterator<Double> it = keys.iterator();
				int k=0;
				writer.write("topic "+String.valueOf(i)+" : "+"\r\n");
				while(it.hasNext() && k<num){
					double a = (double) it.next();
					for(int j=0;j<tmphi.get(a).size() && k<num;j++){
						writer.write(" "+a+" "+ld.wordsAll.get(tmphi.get(a).get(j))+"\r\n");k++;
					}
				}
				tmphi.clear();
				writer.write("\r\n");
			}
			writer.close();} catch (Exception e) {
							e.printStackTrace();
					}
		
		//theta M*K
		try {
			File f=new File(p2);
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);
			TreeMap<Double,ArrayList<Integer> >tmtheta=new TreeMap<Double,ArrayList<Integer> >(new descendComparator());
			for(int i=0;i<ld.M;i++){
				for(int j=0;j<ld.K;j++){
					if(tmtheta.containsKey(ld.theta[i][j])){
						ArrayList<Integer> a=new ArrayList<Integer>();
						a=tmtheta.get(ld.theta[i][j]);
						a.add(j);
						tmtheta.put(ld.theta[i][j], a);
					}else{
						ArrayList<Integer> a=new ArrayList<Integer>();
						a.add(j);
						tmtheta.put(ld.theta[i][j], a);
				}}
				Set<Double> keys = tmtheta.keySet();
				Iterator<Double> it = keys.iterator();
				int k=0;
				writer.write("doc "+ld.fileName.get(i)+" : "+"\r\n");
				while(it.hasNext() && k<num){
					 double a = (double) it.next();//k++;
					 
					 for(int j=0;j<tmtheta.get(a).size() && k<num;j++){
							writer.write(" "+a+" "+tmtheta.get(a).get(j)+"\r\n");k++;
						}			      
				}
				tmtheta.clear();
				writer.write("\r\n");
			}
			writer.close();} catch (Exception e) {
							e.printStackTrace();
					}
		
		//wordsTopic
		try {
			File f=new File(p3);
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);
			for(int i=0;i<ld.V;i++){
				//write.write(ld.wordsAll.get(i)+" ");
				double  t11=0.0;
				for(int j=0;j<ld.K;j++){
					t11+=ld.phi[j][i];
				}
				ArrayList<Double> da=new ArrayList<Double>();
				
				for(int j=0;j<ld.K;j++){
					da.add(ld.phi[j][i]/t11);
					//write.write(ld.phi[j][i]/t1+" ");
				}

				ww.put(ld.wordsAll.get(i), da);//
				//System.out.println(ld.wordsAll.get(i)+" "+da);
				
				//write.write("\r\n");
			}

			for(Map.Entry<String,ArrayList<Double>>  entry:ww.entrySet()){
				//System.out.println(entry.getKey()+"--->"+entry.getValue());    
				writer.write(entry.getKey()+" ");//System.out.print(entry.getKey()+" ");
				for(int i=0;i<entry.getValue().size();i++){
					writer.write(entry.getValue().get(i)+" ");//System.out.print(entry.getValue().get(i)+" ");
				}//System.out.println("");
				writer.write("\r\n");
			}
			writer.close();
		} catch (Exception e) {
							e.printStackTrace();
					}
	}

	/*
	public Double result() throws UnsupportedEncodingException, FileNotFoundException{
		HashMap<String,Integer> aa=new HashMap<String,Integer>();
		String path=p.getSavePath();
		int k=p.getTopicNum();//System.out.println(k);
		path=path+"\\"+String.valueOf(999);
		String p1=path+"-phi-topic-word.txt";
		File file=new File(p1);
		@SuppressWarnings("resource")
		Scanner br=new Scanner(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		ArrayList<po> p=new ArrayList<po>();
		Double[][] t;
		int i=0;
		while(k--!=0){
			po a=new po();
			String s=br.nextLine();
			//System.out.println(s);
			int tt=100;
			tt=this.p.getTopicNum()*5;
			while(tt--!=0){
				Double ss=br.nextDouble();//System.out.println(ss);
				//br.nextByte();
				s=br.nextLine();//System.out.println(s);
				a.c.add(s);
				if(!aa.containsKey(s)){
					aa.put(s, i++);
				}
				a.cc.add(ss);
				a.ccc.add(aa.get(s));//System.out.println(aa.get(s));
				//System.out.println(aa);
			}
			//System.out.println(a.ccc);
			p.add(a);s=br.nextLine();
		}
		
		for(i=0;i<p.size();i++)
			p.get(i).sortt(aa.size());
		//System.out.println(aa.size());
		k=this.p.getTopicNum();
		t=new Double[k][k];
		Double min=1.0;//int h=0,sl=0;
		//Double max=0.0;
		for(i=0;i<k;i++){
			for(int j=i;j<k;j++){
				Double d1=0.0,d2=0.0,d3=0.0;
				Double d=0.0;
				for(int kk=0;kk<aa.size();kk++){
					d1+=(p.get(i).d.get(kk)*p.get(j).d.get(kk));
					d2+=(Math.pow(p.get(i).d.get(kk),2));
					d3+=(Math.pow(p.get(j).d.get(kk),2));
				}
				d2=Math.pow(d2,0.5);
				d3=Math.pow(d3,0.5);
				d=d1/(d2*d3);
				if(min>d && d!=0.0){min=d;}
				//if(max<d){max=d;}
				t[i][j]=d;//System.out.print(d+" ");
			}//System.out.println("");
		}
		//System.out.println(min+" "+h+" "+sl);
		
		//Double d=0.0;
		return min;
		//return max;
	}

	*/
	
	public Double result(){
		
		Double nd=0.0;
		Double wd=0.0;
		for(int i=0;i<ld.M;i++){
			int p=ld.text[i].length;
			nd+=p;
			for(int j=0;j<p;j++){
				Double pwd=0.0;
				for(int k=0;k<ld.K;k++){
					pwd+=ld.phi[k][ld.text[i][j]]*ld.theta[i][k];
				}
				wd+=Math.log(pwd);
			}
		}	
		Double p=Math.exp(-wd/nd);
		return p;
	}
	
}


class descendComparator implements Comparator<Object>
{
    public int compare(Object o1,Object o2)
    {
        Double i1=(Double)o1;
        Double i2=(Double)o2;
        return -i1.compareTo(i2);
    }
}
