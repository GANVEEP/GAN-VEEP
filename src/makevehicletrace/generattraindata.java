package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class generattraindata {

	public static void  ppp(rectangle r1){
		point p1=r1.p1;
		
	}
	public static rectangle[] laneinfo()throws Exception{
		String point24="D:\\12point.txt";//起点终点坐标
		String vehiclerange="D:\\vehiclerange.txt";//车道范围坐标
//		String vehicledata="D:\\vehiclelane1\\everyvehicle\\";
//		String traindata="D:\\vehiclelane1\\traindata\\";
		int lanenum=24;
		rectangle[] everylane=new rectangle[lanenum];//存储道路数据的变量，很重要
		for(int i=0;i<lanenum;i++){
			everylane[i]=new rectangle();
		}
		FileReader reader1 = new FileReader(point24);
		BufferedReader br1 = new BufferedReader(reader1);
		String line;
		lanenum=0;
		String[] zuobiao=new String[20];
		while ((line = br1.readLine()) != null) {
			zuobiao=line.split(" ");
			everylane[lanenum].pori.x=Double.parseDouble(zuobiao[0]);
			everylane[lanenum].pori.y=Double.parseDouble(zuobiao[1]);
			everylane[lanenum].pend.x=Double.parseDouble(zuobiao[2]);
			everylane[lanenum].pend.y=Double.parseDouble(zuobiao[3]);
			everylane[lanenum].roadlength=generattraindata.getlength(everylane[lanenum].pori, everylane[lanenum].pend);
			lanenum++;
		}
		FileReader reader2 = new FileReader(vehiclerange);
		BufferedReader br2 = new BufferedReader(reader2);
		lanenum=0;
		while ((line = br2.readLine()) != null) {
			System.out.println(lanenum);
			zuobiao=line.split(" ");
			everylane[lanenum].id=lanenum;
			everylane[lanenum].p1.x=Double.parseDouble(zuobiao[0]);
			everylane[lanenum].p1.y=Double.parseDouble(zuobiao[1]);
			everylane[lanenum].p2.x=Double.parseDouble(zuobiao[2]);
			everylane[lanenum].p2.y=Double.parseDouble(zuobiao[3]);
			everylane[lanenum].p3.x=Double.parseDouble(zuobiao[4]);
			everylane[lanenum].p3.y=Double.parseDouble(zuobiao[5]);
			everylane[lanenum].p4.x=Double.parseDouble(zuobiao[6]);
			everylane[lanenum].p4.y=Double.parseDouble(zuobiao[7]);
			everylane[lanenum].sort();
			lanenum=lanenum+1;
		}
		br1.close();
		br2.close();
//		for(int i=0;i<24;i++){
//			System.out.print(everylane[i].id+" ");
//			System.out.print(everylane[i].p1.x+" ");
//			System.out.println(everylane[i].p1.y);
//		}
		return(everylane);
		
	} 
	public static double getlength(point p1,point p2){
		double length;
		length=Math.sqrt((p2.y-p1.y)*(p2.y-p1.y)+(p2.x-p1.x)*(p2.x-p1.x));
		return(length);
	}
	public static void gettraindata(rectangle[] r1,String oldpath,String newpath)throws Exception{
		String vehicledata="D:\\vehiclelane1\\everyvehicle\\";
		String traindata="D:\\vehiclelane1\\traindata\\";
		FileReader reader1 = new FileReader(vehicledata+oldpath);
		BufferedReader br1 = new BufferedReader(reader1);
		String line;
		point p=new point();
		point p1=new point();//转化后的坐标
		String vid,sx,sy,speed,time,laneid,lukou,qianche;
		double x,y;
		int laid;
		String lastspeed,havelast;
		String printinfo;
		String[] zuobiao=new String[20];
		traindata=traindata+newpath;
		printinfo="x坐标 速度 路口距 有前车 前车距 前车速 车ID 道ID 时间";
		generattraindata.method(traindata, printinfo);
//		generattraindata.method("D:\\vehiclelane1\\finish\\okvehicle.txt",printinfo);
		String lastprintinfo=null;
		br1.readLine();//把第一行标志去掉
		boolean isfirst=true;
		while ((line = br1.readLine()) != null) {
			zuobiao=line.split(" ");
			vid=zuobiao[0];
			sx=zuobiao[1];
			sy=zuobiao[2];
			speed=zuobiao[3];
			time=zuobiao[4];
			laneid=zuobiao[5];
			lukou=zuobiao[6];
			qianche=zuobiao[7];
			havelast=zuobiao[8];
			lastspeed=zuobiao[9];
			x=Double.parseDouble(sx);//把xy左边转化为数值
			y=Double.parseDouble(sy);
			laid=(int)Double.parseDouble(laneid);//把车道号转化为数值
			p.x=x;
			p.y=y;
			p1=changeXY.judge(r1[laid], p);
			p1.x=Math.abs(p1.x);
			//x坐标，速度，路口距，是否有前车，前车距，前车速，车ID，道ID，时间
			printinfo=p1.x+" "+speed+" "+lukou+" "+havelast+" "+qianche+" "+lastspeed+" "+vid+" "+laneid+" "+time;
			
			if(isfirst==false){
				generattraindata.method(traindata,lastprintinfo+"-"+printinfo);
				generattraindata.method("D:\\vehiclelane1\\finish\\okvehicle.txt",lastprintinfo+"-"+printinfo);
			}
			isfirst=false;
			lastprintinfo=printinfo;
		}
		br1.close();
	}
	
	public static void gettraindata1(rectangle[] r1,String oldpath,String newpath)throws Exception{
		String vehicledata="D:\\vehiclelane1\\everyvehicle\\";
		String traindata="D:\\vehiclelane1\\traindata\\";
//		FileReader reader1 = new FileReader(vehicledata+oldpath);
		FileReader reader1 = new FileReader(oldpath);
		BufferedReader br1 = new BufferedReader(reader1);
		String line;
		point p=new point();
		point p1=new point();//转化后的坐标
		String vid,sx,sy,speed,time,laneid,lukou,qianche,frontcar,frontv,trafficlight;
		double x,y;
		int laid;
		String printinfo;
		String[] zuobiao=new String[20];
		traindata=newpath;
		printinfo="x坐标 y坐标 速度 路口距 有前车 前车距 前车速 车ID 道ID 时间 交通灯状态";
		generattraindata.method(traindata, printinfo);
		String lastprintinfo=null;
		br1.readLine();//把第一行标志去掉
		boolean isfirst=true;
		while ((line = br1.readLine()) != null) {
			zuobiao=line.split(" ");
			vid=zuobiao[0];
			sx=zuobiao[1];
			sy=zuobiao[2];
			speed=zuobiao[3];
			time=zuobiao[4];
			laneid=zuobiao[5];
			lukou=zuobiao[6];
			qianche=zuobiao[7];
			frontcar=zuobiao[8];
			frontv=zuobiao[9];
			trafficlight=zuobiao[10];
			x=Double.parseDouble(sx);//把xy左边转化为数值
			y=Double.parseDouble(sy);
			laid=(int)Double.parseDouble(laneid);//把车道号转化为数值
			p.x=x;
			p.y=y;
			p1=changeXY.judge(r1[laid], p);
//			x坐标 y坐标 速度 路口距 有前车 前车距 前车速 车ID 道ID 时间 交通灯状态
			printinfo=p1.x+" "+p1.y+" "+speed+" "+lukou+" "+frontcar+" "+qianche+" "+frontv+" "+vid+" "+laneid+" "+time+" "+trafficlight;
//			printinfo=p1.x+" "+p1.y+" "+speed+" "+qianche+" "+lukou+" "+vid+" "+laneid+" "+time;
//			printinfo=printinfo+" "+frontcar+" "+frontv+" "+trafficlight;
			generattraindata.method(traindata,printinfo);

			lastprintinfo=printinfo; 
		}
		br1.close();
	}
	public static void gettraindata1(rectangle[] r1,String oldpath,String newpath,double[] zs)throws Exception{
		String vehicledata="D:\\vehiclelane1\\everyvehicle\\";
		String traindata="D:\\vehiclelane1\\traindata\\";
//		FileReader reader1 = new FileReader(vehicledata+oldpath);
		FileReader reader1 = new FileReader(oldpath);
		BufferedReader br1 = new BufferedReader(reader1);
		String line;
		point p=new point();
		point p1=new point();//转化后的坐标
		String vid,sx,sy,speed,time,laneid,lukou,qianche,frontcar,frontv,trafficlight;
		double x,y;
		int laid;
		String printinfo;
		String[] zuobiao=new String[20];
		traindata=newpath;
		printinfo="x坐标 y坐标 速度 路口距 有前车 前车距 前车速 车ID 道ID 时间 交通灯状态";
		generattraindata.method(traindata, printinfo);
		String lastprintinfo=null;
		br1.readLine();//把第一行标志去掉
		boolean isfirst=true;
		int z=0;
		while ((line = br1.readLine()) != null) {
			zuobiao=line.split(" ");
			vid=zuobiao[0];
			sx=zuobiao[1];
			sy=zuobiao[2];
			speed=zuobiao[3];
			time=zuobiao[4];
			laneid=zuobiao[5];
			lukou=zuobiao[6];
			qianche=zuobiao[7];
			frontcar=zuobiao[8];
			frontv=zuobiao[9];
			trafficlight=zuobiao[10];
			x=Double.parseDouble(sx);//把xy左边转化为数值
			y=Double.parseDouble(sy);
			laid=(int)Double.parseDouble(laneid);//把车道号转化为数值
			p.x=x+zs[z];
			z++;
			p.y=y;
			p1=changeXY.judge(r1[laid], p);
//			x坐标 y坐标 速度 路口距 有前车 前车距 前车速 车ID 道ID 时间 交通灯状态
			printinfo=p1.x+" "+p1.y+" "+speed+" "+lukou+" "+frontcar+" "+qianche+" "+frontv+" "+vid+" "+laneid+" "+time+" "+trafficlight;
//			printinfo=p1.x+" "+p1.y+" "+speed+" "+qianche+" "+lukou+" "+vid+" "+laneid+" "+time;
//			printinfo=printinfo+" "+frontcar+" "+frontv+" "+trafficlight;
			generattraindata.method(traindata,printinfo);

			lastprintinfo=printinfo; 
		}
		br1.close();
	}
	public static void method(String path,String printinfo) {
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
	public static void main(String[] args) throws Exception{
		rectangle[] r1=generattraindata.laneinfo();
		for(int i=0;i<299;i++){
			generattraindata.gettraindata(r1,i+"vehiclelane.txt",i+"vehicle.txt");
		}
		
	}

}
