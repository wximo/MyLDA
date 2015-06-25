package Ximo.Lda.AlgoPart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import Ximo.Lda.Parameter.Parameter;

public class resultValue {
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		//wen dang bing mei you an zhao shun xu lai .xu yao shai xuan
		String s[]={"Auto","Medicine","Culture","Economy","Military","Sports"};
		Parameter p=new Parameter(6);
		String file=p.getSavePath()+"/999-theta-doc-topic.txt";
		Scanner reader=null;
		int bj[]=new int[6];
		ArrayList<ArrayList<Double> >a=new ArrayList<ArrayList<Double> >(3000);
		ArrayList<Double> ab=new ArrayList<Double>();
		for(int i=0;i<3000;i++)
			a.add(ab);
		try {
					reader=new Scanner(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					int b=0;
					Double d=0.0;
					//reader.nextLine();
					for(int i=0;i<3000;i++){
						String ss=reader.nextLine();
						ArrayList<Double> te=new ArrayList<Double>(); 
						for(int ki=0;ki<6;ki++){
							te.add(0.0);
						}
						for(int j=0;j<6;j++){
							d=reader.nextDouble();
							b=reader.nextInt();
							te.set(b, d);
							//te.add(b);
						}
						if(i!=2999){
					reader.nextLine();
					reader.nextLine();}
						for(int ii=0;ii<6;ii++){
							if(ss.contains(s[ii])){
								a.set(ii*500+bj[ii], te);
								bj[ii]++;
								break;
								}
							}
					}
					} catch (FileNotFoundException e) {
							e.printStackTrace();
					} catch (IOException e) {
							e.printStackTrace();
					} finally {
							if (reader != null) {
									reader.close();
												}
								}
		
		//System.out.println(a);
		
		for(int i=0;i<6;i++)
			bj[i]=0;
		String path="result6/999-theta-doc-topic.txt";
		ArrayList<ArrayList<Double> >ay=new ArrayList<ArrayList<Double> >(3000);
		for(int i=0;i<3000;i++)
			ay.add(ab);
		try {
					reader=new Scanner(new InputStreamReader(new FileInputStream(path),"UTF-8"));
					int b=0;
					Double d=0.0;
					//reader.nextLine();
					for(int i=0;i<3000;i++){
						String ss=reader.nextLine();
						ArrayList<Double> te=new ArrayList<Double>(); 
						for(int ki=0;ki<6;ki++){
							te.add(0.0);
						}
						for(int j=0;j<6;j++){
							d=reader.nextDouble();
							b=reader.nextInt();
							te.set(b, d);
						}
						if(i!=2999){
					reader.nextLine();
					reader.nextLine();}
						for(int ii=0;ii<6;ii++){
							if(ss.contains(s[ii])){
								ay.set(ii*500+bj[ii], te);
								bj[ii]++;
								break;
								}
							}
					}
					} catch (FileNotFoundException e) {
							e.printStackTrace();
					} catch (IOException e) {
							e.printStackTrace();
					} finally {
							if (reader != null) {
									reader.close();
												}
								}
		
		
		int top[]={4,3,2,1,0,5};
		int k=0;
			for(int i=0;i<6;i++){
				int aa[]=new int[6];
				for(int j=0;j<500;j++){
					int abc[]=new int[6];
						Map<Integer,Double> mid=new HashMap<Integer,Double>();
						for(int ki=0;ki<3000;ki++){
							Double dl=0.0;
							for(int ii=0;ii<6;ii++){
								dl+=(Math.pow(ay.get(ki).get(ii)-a.get(i*500+j).get(ii),2));
							}
							dl=Math.sqrt(dl);
							mid.put(ki, dl);
						}
				
						List<Map.Entry<Integer, Double>> mid2=
							    new ArrayList<Map.Entry< Integer,Double>>(mid.entrySet());
						Collections.sort(mid2, new Comparator<Map.Entry< Integer,Double>>() {   
						    public int compare(Map.Entry< Integer,Double> o1, Map.Entry< Integer,Double> o2) {      
						        //return (o2.getValue() - o1.getValue()); 
						        return (o1.getValue()).compareTo(o2.getValue());
						    }
						}); 
					
					for(int ki=0;ki<20;ki++){
						abc[mid2.get(ki).getKey()/500]++;
					}mid.clear();
				//	for(int ki=0;ki<6;ki++)
				//		System.out.print(abc[ki]+" ");
					
					int ti=0;int ma=0;
					for(int ki=0;ki<6;ki++){
						if(abc[ki]>ma)
						{	ma=abc[ki];
							ti=ki;
						}
					}
					aa[top[ti]]++;
				}
				for(int j=0;j<6;j++){
					//System.out.print(j+":"+aa[j]+"\t\t");
					System.out.printf("%d:%-4d\t",j,aa[j]);
				}System.out.println("");
			}

	}}
class descendComparator2 implements Comparator<Object>
{
    public int compare(Object o1,Object o2)
    {
        Double i1=(Double)o1;
        Double i2=(Double)o2;
        return -i2.compareTo(i1);
    }
}
