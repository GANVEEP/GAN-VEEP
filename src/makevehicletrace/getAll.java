package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//import MAKERULE.SpeedAndDistance;
//import anticipation.AbandonError;
//import anticipation.AbandonRepeat;

public class getAll {//遍历数据集\1101下的所有 浮动车数据文档

	static String basePath="D:\\vehiclelane1\\evaluation\\preeveryvehicle";
	static void findFile(File dir) throws Exception{
	File[] dirFiles = dir.listFiles();
	String[] ss=new String[100];
	String sss;
	for(File temp : dirFiles){
	//if(!temp.isFile()){
	//findFile(temp);
	//}
	//查找指定的文件
		if(temp.isFile() && temp.getAbsolutePath().endsWith(".txt") ){
	      System.out.println(temp.isFile() + "  " + temp.getAbsolutePath());
          String realpath,prepath;
	      sss="D:\\vehiclelane1\\evaluation\\realeveryvehicle\\"+temp.getName();
	      System.out.println(temp.isFile() + "  " + sss);
	      realpath=sss;
	      prepath=temp.getAbsolutePath();
//	      everytrajectory.twotrajectory(realpath, prepath,temp.getName().split("\\.")[0]);//生成位置和速度误差分布结果
	      everytrajectory.xy(realpath, prepath,temp.getName().split("\\.")[0]);//生成xy轨迹对比
		}
	}
}
	  
	  /**
	  * @param file 要读取的文件对象
	  * @return 返回文件的内容
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
	  * @param file 要写入的文件对象
	  * @param content 要写入的文件内容
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
