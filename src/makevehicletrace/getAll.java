package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//import MAKERULE.SpeedAndDistance;
//import anticipation.AbandonError;
//import anticipation.AbandonRepeat;

public class getAll {//�������ݼ�\1101�µ����� �����������ĵ�

	static String basePath="D:\\vehiclelane1\\evaluation\\preeveryvehicle";
	static void findFile(File dir) throws Exception{
	File[] dirFiles = dir.listFiles();
	String[] ss=new String[100];
	String sss;
	for(File temp : dirFiles){
	//if(!temp.isFile()){
	//findFile(temp);
	//}
	//����ָ�����ļ�
		if(temp.isFile() && temp.getAbsolutePath().endsWith(".txt") ){
	      System.out.println(temp.isFile() + "  " + temp.getAbsolutePath());
          String realpath,prepath;
	      sss="D:\\vehiclelane1\\evaluation\\realeveryvehicle\\"+temp.getName();
	      System.out.println(temp.isFile() + "  " + sss);
	      realpath=sss;
	      prepath=temp.getAbsolutePath();
//	      everytrajectory.twotrajectory(realpath, prepath,temp.getName().split("\\.")[0]);//����λ�ú��ٶ����ֲ����
	      everytrajectory.xy(realpath, prepath,temp.getName().split("\\.")[0]);//����xy�켣�Ա�
		}
	}
}
	  
	  /**
	  * @param file Ҫ��ȡ���ļ�����
	  * @return �����ļ�������
	  * */
	  public static String  readFileContent(File file) throws Exception{
	  FileReader fr = new FileReader(file);
	  BufferedReader br = new BufferedReader(fr);
	  StringBuffer sb = new StringBuffer();
	  while(br.ready()){
	  sb.append(br.readLine());
	  }
	  System.out.println(sb.toString());
	  return sb.toString();
	  }
	  
	  /**
	  * @param file Ҫд����ļ�����
	  * @param content Ҫд����ļ�����
	  * */
	  public static void  writeFileContent(File file,String content) throws Exception{
	  FileWriter fw = new FileWriter(file);
	  fw.write(content);
	  fw.flush();
	  fw.close();
	  }
	  
	  public static void main(String[] args) throws Exception{
 
		findFile(new File(basePath));
		
 
	  }
}
