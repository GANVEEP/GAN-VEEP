package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.TreeMap;

import com.sun.media.sound.RIFFInvalidDataException;

public class practice {

	public static void deleteFile(File file) {
		if(file.exists()) {//判断路径是否存在
			if(file.isFile()){//boolean isFile():测试此抽象路径名表示的文件是否是一个标准文件。 
				file.delete();
			}else{//不是文件，对于文件夹的操作
				//保存 路径D:/1/新建文件夹2  下的所有的文件和文件夹到listFiles数组中
				File[] listFiles = file.listFiles();//listFiles方法：返回file路径下所有文件和文件夹的绝对路径
				for (File file2 : listFiles) {
					/*
					 * 递归作用：由外到内先一层一层删除里面的文件 再从最内层 反过来删除文件夹
					 *    注意：此时的文件夹在上一步的操作之后，里面的文件内容已全部删除
					 *         所以每一层的文件夹都是空的  ==》最后就可以直接删除了
					 */
					deleteFile(file2);
				}
			}
			file.delete();
		}else {
			System.out.println("该file路径不存在！！");
		}
	}

	public static void main(String[] args)throws Exception{
//		rectangle[] r1=generattraindata.laneinfo();//道路四个点的坐标，和道路方向点
//		vehiclelane[] alllane=directionindex.directionindex("D:\\12point.txt");//道路连接索引
//		//practice.rechange(r1, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl");
//		//TreeMap<Integer, timestatistic> treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl","D:\\12point.txt");
//		practice.crossroad(alllane, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\predata1.txt");//python预测出来的数据，转弯判定后的数据
//		File file3 = new File("D:\\vehiclelane1\\formaldata\\111.txt");
//		deleteFile(file3);
//        changenext.readroadindex("C:\\SUMO\\bin\\test\\tian\\tian.edg.xml");
//        changenext.readvehicleroute("C:\\SUMO\\bin\\test\\newh\\lovekq.rou.xml");
		rectangle[] r1=generattraindata.laneinfo();
		point p=new point(489.45,500);
		point p1=changeXY.judge(r1[22], p);
		System.out.println(p1.x+" "+p1.y);
	}
	//处理python预测出来的数据，判断每一辆车是否行驶到下一条道路，是的话经过方向判断模块
	public static void crossroad(vehiclelane[] alllane,String path,String newpath)throws Exception{
		Random rand = new Random();
		vehiclelane[] v=directionindex.directionindex("D:\\12point.txt");//每个车道的下一车道索引，
		DecimalFormat df=new DecimalFormat("#0");
		FileReader reader = new FileReader(path);//读取数据
		BufferedReader br = new BufferedReader(reader);
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,time;//x坐标 速度 前车距 路口距 车ID 道ID 时间
        double roid;
		int roadid,nextroadid,nextdirection;
		String printinfo;
    
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		printdata.method(newpath, "车辆ID x坐标 y坐标 速度 时间 道路id 前车距 路口距");
		line = br.readLine();//忽略第一行题头
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			xzuobiao=Double.parseDouble(d[1]);
			roid=Double.parseDouble(d[5]);
			roadid=(int)roid;
			if(xzuobiao>(alllane[roadid].length-7.5)){//此时可以判断车辆已经驶出了。当前道路，所以要判断该车辆下一阶段的转弯方向
				while(true){//选取一个随机的道路，让车辆行驶
					nextdirection=rand.nextInt(4);
					if(alllane[roadid].directionroad[nextdirection].lanenumber!=999){
						//nextroadid=alllane[roadid].directionroad[nextroadid].lanenumber;
						break;
					}
				}
				xzuobiao=changenext.rechangenext(xzuobiao, 0, nextdirection);//得到了
				nextroadid=alllane[roadid].directionroad[nextdirection].lanenumber;
				printinfo=d[0]+" "+xzuobiao+" "+d[2]+" "+d[3]+" "+d[4]+" "+nextroadid+" "+999+" "+999;
				printdata.method(newpath, printinfo);
			}
			else{//此时车辆还没有驶出当前坐标范围，所以直接把信息打印出去就行
				printdata.method(newpath, line);
			}
		}
	}
	public static void rechange(rectangle[] r1,String path,String newpath)throws Exception{
		DecimalFormat df=new DecimalFormat("#0");
		FileReader reader = new FileReader(path);//读取数据
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,time;//x坐标 速度 前车距 路口距 车ID 道ID 时间
        double roid;
		int roadid;
		String s;
        point p=new point();
		line=br.readLine();//打印第一行题头
		//printdata.method(newpath, "车辆ID x坐标 y坐标 速度 时间 道路id 前车距 路口距");
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			vid=Double.parseDouble(d[0]);
			xzuobiao=Double.parseDouble(d[1]);
			yzuobiao=Double.parseDouble(d[2]);
			roid=Double.parseDouble(d[5]);
			speed=Double.parseDouble(d[3]);
			time=Double.parseDouble(d[4]);
			roadid=(int)roid;
			p.x=xzuobiao;
			p.y=yzuobiao;
			p=changeXY.njudge(r1[roadid], p);
			System.out.println("矩阵坐标："+roadid);
			System.out.println("转换前：xy坐标"+xzuobiao+" "+yzuobiao);
			System.out.println("转换后：xy坐标"+p.x+" "+p.y);
			s="$ns_ at "+time+" \"$node_("+df.format(vid)+") setdest "+xzuobiao+" "+yzuobiao+" "+speed+"\"";
//			System.out.println(path);
//			System.out.println(newpath);
			printdata.method( newpath,s);
		}
	}
	public static void mm(TreeMap<Integer, timestatistic> treeMapArray){
		timestatistic timestatistic=treeMapArray.get(1);
		vehicle v=timestatistic.vehiclelanelist.get(1).vehiclelist.get(11);
		v.x=999;
	}
}
