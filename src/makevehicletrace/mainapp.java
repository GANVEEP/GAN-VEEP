package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.TreeMap;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

public class mainapp {

	static DecimalFormat df=new DecimalFormat("#0.00");
	public static void main(String[] args)throws Exception {//这是一个预测的应用（利用已经生成好的模型）
		DecimalFormat df=new DecimalFormat("#0.00");
		//先提取道路模型
		vehiclelane[] alllane=directionindex.directionindex("D:\\12point.txt");//道路连接索引
		rectangle[] r1=generattraindata.laneinfo();//道路方向，和道路范围信息存储数组
        changenext.readroadindex("C:\\SUMO\\bin\\test\\tian\\tian.edg.xml");
        changenext.readvehicleroute("C:\\SUMO\\bin\\test\\newh\\lovekq.rou.xml");
		String oldp="D:\\0104mobility.tcl";
		String oldp1="D:\\vehiclelane1\\formaldata\\mobility.tcl";
		//提取ns2mobility.tcl的数据，得到先按照时间分类，后按照车道分类的车辆数据集。
//		TreeMap<Integer, timestatistic> treeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp1,"D:\\12point.txt");
        int num=45;
        boolean b=false;
        TreeMap<Integer, timestatistic> treeMap1;
        double[] zs=mainapp.zs("D:\\zaoyin\\g0.4.txt");  
        boolean zaosheng=true;
		while(num<76){
			if(b==false){   
				treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp,"D:\\12point.txt");
				b=true;
			}
			else {
				treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp1,"D:\\12point.txt");
			}
		timestatistic ti=treeMap1.get(num);
		
		num++;

		getlaneinformation.vehiclelanenumbertrue=true;
		System.out.println("2020-01-15 时间："+num);
		for(int i=0;i<ti.vehiclelanelist.size();i++){
			for (Integer m : ti.vehiclelanelist.get(i).vehiclelist.keySet()) {
				vehicle v=ti.vehiclelanelist.get(i).vehiclelist.get(m);
//				System.out.println(i+"********************");
				getinfobylane.pritinfobytime("D:\\vehiclelane1\\formaldata\\data.txt", v);
			}
		}
		if(zaosheng==true){
			zaosheng=false;
			//把原始车辆信息转化为标准道路车辆信息，oldpath，newpath
			generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt",zs);
		}
		else{
			generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt");
		}
		//把原始车辆信息转化为标准道路车辆信息，oldpath，newpath
//		generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt",zs);
		
		
		//预测车辆轨迹15.121657 2.9491897(x坐标，速度，车道号形式)	
		//根据坐标转换后的数据，利用python模型预测车辆下一时刻位置，然后把所有预测出来的数据存储到predata.txt文件里
		mainapp.traindata("D:\\vehiclelane1\\formaldata\\newdata.txt", "D:\\vehiclelane1\\formaldata\\predata.txt");
		
		//判断预测出来的数据是否跨道路，如果跨道路将其转化为下一道路的坐标
		changenext.crossroad(alllane, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\predata1.txt");//python预测出来的数据，转弯判定后的数据
		
		//把python预测出来的车辆位置转化为真实道路的位置，存储到文件里
		mainapp.rechange(r1, "D:\\vehiclelane1\\formaldata\\predata1.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl");
//		if(num==47)
//			break;
		deleteFile("D:\\vehiclelane1\\formaldata\\data.txt"); 
		deleteFile("D:\\vehiclelane1\\formaldata\\newdata.txt");
		deleteFile("D:\\vehiclelane1\\formaldata\\predata.txt");
		deleteFile("D:\\vehiclelane1\\formaldata\\predata1.txt");

        }
		//TreeMap<Integer, timestatistic> treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl","D:\\12point.txt");
	}
	public static double[] zs(String path)throws Exception{
	      double[] zhaos=new double[300];
			DecimalFormat df=new DecimalFormat("#0");
			FileReader reader = new FileReader(path);//读取数据
			BufferedReader br = new BufferedReader(reader);
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				if(!line.equals("")){
					zhaos[i]=Double.parseDouble(line);
					System.out.println(zhaos[i]);
					i++;
				}

			}
	      return zhaos;
	}
	public static void deleteFile(String path){	
		File file3 = new File(path);
		deleteFile(file3);
	}
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
	//最终转换层，把预测出来的数据，通过逆坐标转换层，转换为所处真实道路的位置数据
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
			getlaneinformation.vehiclelanenumber[(int)vid]=roadid;
			p=changeXY.njudge(r1[roadid], p);
//			System.out.println("矩阵坐标："+roadid);
//			System.out.println("转换前：xy坐标"+xzuobiao+" "+yzuobiao);
//			System.out.println("转换后：xy坐标"+p.x+" "+p.y);
			s="$ns_ at "+time+" \"$node_("+df.format(vid)+") setdest "+p.x+" "+p.y+" "+speed+"\" "+roadid;
//			System.out.println(path);
//			System.out.println(newpath);
			printdata.method( newpath,s);
		}
		br.close();
	}
	//预测车辆的位置函数，预测后再把预测值统一输出到predata文件中
	public static void traindata(String path,String newpath  )throws Exception{//把
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		int a=0;
		String data="";
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,roadid,time;//x坐标 速度 前车距 路口距 车ID 道ID 时间
		double havefront,frontcarv,trafficlight;
		String[] ss=new String[400];  
		int numberof=0;
		line=br.readLine();//打印第一行题头
		printdata.method(newpath, "车辆ID x坐标 y坐标 速度 时间 道路id 前车距 路口距");
		while ((line = br.readLine()) != null) {
			
			ss[numberof]=line;
			numberof++;
			d=line.split(" ");
//			System.out.println(line);
			xzuobiao=Double.parseDouble(d[0]);
			yzuobiao=Double.parseDouble(d[1]);
			speed=Double.parseDouble(d[2]);
			disfromlukou=Double.parseDouble(d[3]);
			havefront=Double.parseDouble(d[4]);
			disfromcar=Double.parseDouble(d[5]);
			frontcarv=Double.parseDouble(d[6]);
			vid=Double.parseDouble(d[7]);
			roadid=Double.parseDouble(d[8]);
			time=Double.parseDouble(d[9]);
			trafficlight=Double.parseDouble(d[10]);
			// x坐标 速度 路口距 有前车 前车距 前车速 交通灯
			data=data+d[0]+" "+d[2]+" "+d[3]+" "+d[4]+" "+d[5]+" "+d[6]+" "+d[10]+"-";//X坐标，速度，前车距，路口距，车道号
			
		}
		br.close();
//		System.out.println(data+"输入");
		String s=usepython.print(data);
		String[] news=s.split("-");
		for(int i=0;i<news.length-1;i++){
//			d1=s.split("-");//得到了预测车辆的X坐标，速度，
			d1=news[i].split(" ");
			xzuobiao=Double.parseDouble(d1[0]);
			speed=Double.parseDouble(d1[1]);
			d=ss[i].split(" ");
			double x=Double.parseDouble(d[0]);//前一时刻的x坐标
			yzuobiao=Double.parseDouble(d[1]);
			disfromlukou=Double.parseDouble(d[3]);
			havefront=Double.parseDouble(d[4]);
			disfromcar=Double.parseDouble(d[5]);
			frontcarv=Double.parseDouble(d[6]);
			vid=Double.parseDouble(d[7]);
			roadid=Double.parseDouble(d[8]);
			time=Double.parseDouble(d[9]);
			trafficlight=Double.parseDouble(d[10]);
			time=time+1;
			if(havefront==1){//有前车那么前车就是他的约束
				double max=disfromcar+frontcarv-7;
				if(max<(xzuobiao-x)){
					 xzuobiao=x+max;
					 speed=max;
					 if(speed<0){
						 speed=0;
					 }
				}
			}
			else{//没有前车，那么路口就是他的约束
				if(trafficlight==0){
				if(xzuobiao>491.9){
					xzuobiao=491.9;
					speed=0; 
				}
			}
			}
			data=vid+" "+df.format(xzuobiao)+" "+d[1]+" "+df.format(speed)+" "+time+" "+roadid+" "+999+" "+999;
			printdata.method(newpath, data);
		}		
	}
}
