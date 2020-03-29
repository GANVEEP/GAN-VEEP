package makevehicletrace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class getinfobylane {
	
    public static void main(String[] args){
    	getinfobylane.method1("D:\\vehiclelane\\1.txt", "1111");
    	getinfobylane.method1("D:\\vehiclelane\\1.txt", "2222");
    } 
    public static void pritinfobyvehicle(String path,vehicle v){
    	String printinfo=v.id+" "+v.x+" "+v.y+" "+v.speed+" "+v.time+" "+v.lannumber+" "+v.tolukou+" "+v.tolastcar+" "+v.havelastcar+" "+v.frontcarspeed;
		if(getinfobylane.judeFileExists(new File(path))){//返回真则已有文件存在
			
		}
		else{//返回假说明没有文件中存在，输出第一行备注信息
			String remarks="ID X坐标 Y坐标 速度 时间 车道号 路口距离 前车距 有前车 前车速度";
//			for(int i=0;i<v.v.length;i++){
//			remarks=remarks+" "+v.v[i];
//			}
			getinfobylane.method1(path, remarks);
		}
		getinfobylane.method1(path, printinfo);
    }
    
	public static void pritinfobylane(String path,vehicle v){
		String printinfo=v.id+" "+v.x+" "+v.y+" "+v.speed+" "+v.time;
		if(getinfobylane.judeFileExists(new File(path))){//返回真则已有文件存在
			
		}
		else{//返回假说明没有文件中存在，输出第一行备注信息
			String remarks="ID X坐标 Y坐标 速度 时间";
			for(int i=0;i<v.v.length;i++){
			remarks=remarks+" "+v.v[i];
			}
			getinfobylane.method1(path, remarks);
		}
		getinfobylane.method1(path, printinfo);
	}
	public static void pritinfobytime(String path,vehicle v)throws Exception{
		String printinfo=v.id+" "+v.x+" "+v.y+" "+v.speed+" "+v.time+" "+v.lannumber+" "+v.tolukou+" "+v.tolastcar+" "+v.havelastcar+" "+v.frontcarspeed;
		trafficlight.settrafficlight("D:\\12point.txt");
		int lannum=(int)v.lannumber;
		int t=(int)v.time;
		double traffic=trafficlight.tafficlight[lannum][t];
		printinfo=printinfo+" "+traffic;
		if(getinfobylane.judeFileExists(new File(path))){//返回真则已有文件存在
			
		}
		else{//返回假说明没有文件中存在，输出第一行备注信息
			//String remarks="ID X坐标 Y坐标 速度 车道号 时间";
			String remarks="ID X坐标 Y坐标 速度 时间 车道号 路口距离 前车距 有前车 前车速 交通灯";
//			for(int i=0;i<v.v.length;i++){
//			remarks=remarks+" "+v.v[i];
//			}
			getinfobylane.method1(path, remarks);
		}
		getinfobylane.method1(path, printinfo);
	}
	public static void method1(String path,String printinfo) {
		FileWriter fw = null;
		try {
		//如果文件存在，则追加内容；如果文件不存在，则创建文件
		File f=new File(path);
		fw = new FileWriter(f, true);
		} catch (IOException e) {
		e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(printinfo);
		pw.flush();
		try {
		fw.flush();
		pw.close();
		fw.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
	
	 public static boolean judeFileExists(File file) {

	        if (file.exists()) {
	            return(true);
	        } else {
	            //System.out.println("file not exists, create it ...");
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            return(false);
	        }

	    }
}
