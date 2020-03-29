package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;

public class everytrajectory {

	public static void twotrajectory(String realpath,String prepath,String sp)throws Exception{
		double aa=0;
		String preline,realline;
		double prex,prey,prev,realx,realy,realv;
		double dloss,vloss;
		String[] press=new String[20];
		String[] realss=new String[20];
		double totald=0,totalv=0;
		int num=0;
		FileReader reader = new FileReader(prepath);
		BufferedReader prebr = new BufferedReader(reader);//车道四个点的文件
		FileReader reader1 = new FileReader(realpath);
		BufferedReader realbr = new BufferedReader(reader1);//车道四个点的文件
		while ((preline = prebr.readLine()) != null) {
			realline=realbr.readLine();
			if(realline!=null&&preline!=null){
			press=preline.split(" ");
			prex=Double.parseDouble(press[0]);
			prey=Double.parseDouble(press[1]);
			prev=Double.parseDouble(press[2].split("\"")[0]);
			realss=realline.split(" ");
			realx=Double.parseDouble(realss[0]);
			realy=Double.parseDouble(realss[1]);
			realv=Double.parseDouble(realss[2].split("\"")[0]);
			dloss=everytrajectory.getdistance(prex, prey, realx, realy);
			vloss=Math.abs(prev-realv);
			totald=totald+dloss;
			totalv=totalv+vloss;
			num++;
			if(num==30){
				break;
			}
			}
			}
		totald=totald/num;
		totalv=totalv/num;
		printdata.method("D:\\vehiclelane1\\evaluation\\microevaluation\\distance.txt", sp+" "+totald+"");
		printdata.method("D:\\vehiclelane1\\evaluation\\microevaluation\\v.txt", sp+" "+totalv+"");
		
		realbr.close();
		prebr.close();
//		return(aa);
	}
	public static double getdistance(double x1,double y1,double x2,double y2){
		double distance=0;
		distance=Math.sqrt((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
		return(distance);
	}
	public static void tongji(String path)throws Exception{
		FileReader reader = new FileReader(path);
		BufferedReader prebr = new BufferedReader(reader);//车道四个点的文件
		String line;
		double d;
		int[] num=new int[6];//20  50   》50
		while ((line = prebr.readLine()) != null) {
			d=Double.parseDouble(line.split(" ")[1]);
			if(d<=20){
				num[0]++;
			}
			else if(d>20&&d<=50){
				num[1]++;
			}
			else if(d>50&&d<=80){
				num[2]++;
			}
			else if(d>80&&d<=110){
				num[3]++;
			}
			else if(d>110&&d<=140){
				num[4]++;
			}
			else{
//				System.out.println(line);
				num[5]++;
			}
		}
		prebr.close();
		for(int i=0;i<6;i++){
			System.out.println(num[i]);
		}
	}
	public static void xy(String prepath,String realpath,String sp)throws Exception{
		double aa=0;
		String preline,realline;
		double prex,prey,prev,realx,realy,realv;
		double dloss,vloss;
		String[] press=new String[20];
		String[] realss=new String[20];
		double totald=0,totalv=0;
		int num=0;
		FileReader reader = new FileReader(prepath);
		BufferedReader prebr = new BufferedReader(reader);//车道四个点的文件
		FileReader reader1 = new FileReader(realpath);
		BufferedReader realbr = new BufferedReader(reader1);//车道四个点的文件
		while ((preline = prebr.readLine()) != null) {
			realline=realbr.readLine();
			if(realline!=null&&preline!=null){
			press=preline.split(" ");
			prex=Double.parseDouble(press[0]);
			prey=Double.parseDouble(press[1]);
			prev=Double.parseDouble(press[2].split("\"")[0]);
			realss=realline.split(" ");
			realx=Double.parseDouble(realss[0]);
			realy=Double.parseDouble(realss[1]);
			realv=Double.parseDouble(realss[2].split("\"")[0]);
			printdata.method("D:\\vehiclelane1\\evaluation\\xy\\realx.txt", sp+" "+realx);
			printdata.method("D:\\vehiclelane1\\evaluation\\xy\\realy.txt", sp+" "+realy);
			printdata.method("D:\\vehiclelane1\\evaluation\\xy\\prex.txt", sp+" "+prex);
			printdata.method("D:\\vehiclelane1\\evaluation\\xy\\prey.txt", sp+" "+prey);
			}
			}
		realbr.close();
		prebr.close();
	}
	public static void tongjiv(String path)throws Exception{
		FileReader reader = new FileReader(path);
		BufferedReader prebr = new BufferedReader(reader);//车道四个点的文件
		String line;
		double d;
		int[] num=new int[6];//20  50   》50
		while ((line = prebr.readLine()) != null) {
			d=Double.parseDouble(line.split(" ")[1]);
			if(d<=3){
				num[0]++;
			}
			else if(d>3&&d<=6){
				num[1]++;
			}
			else if(d>6&&d<=9){
				num[2]++;
			}
			else if(d>9&&d<=12){
				num[3]++;
			}
			else if(d>12&&d<=15){
				num[4]++;
			}
			else{
//				System.out.println(line);
				num[5]++;
			}
		}
		prebr.close();
		for(int i=0;i<6;i++){
			System.out.println(num[i]);
		}
	}
	public static void main(String[] args)throws Exception {
		double a=everytrajectory.getdistance(0, 0, 1, 1);
		System.out.println(a);
		everytrajectory.tongji("D:\\vehiclelane1\\evaluation\\microevaluation\\distance.txt");
//		everytrajectory.tongjiv("D:\\vehiclelane1\\evaluation\\microevaluation\\v.txt");
	}

}
