package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;

public class directionindex {

	public static boolean twopoint(point p1,point p2){//判断两个点是否相同
		if(p1.x==p2.x&&p1.y==p2.y){
			return(true);//相同为真，不同为假
		}
		else{
			return(false);
		}
	}
	public static vehiclelane[] directionindex(String path)throws Exception{//把12point里面的那24条道路的坐标点提取并建立方向索引
		
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] allinfo=new String[20];
		String[] allinfo1=new String[20];
		String[] allinfo2=new String[20];
		int j=0;
		int lanenum=24;
		vehiclelane[] alllane=new vehiclelane[lanenum];
		for(int i=0;i<lanenum;i++){//把12条道路对象初始化
			alllane[i]=new vehiclelane(i);
		}
		while ((line = br.readLine()) != null) {
			allinfo=line.split(" ");
			//System.out.println(allinfo[0]);
			alllane[j].oriPoint.x=Float.parseFloat(allinfo[0]);
			alllane[j].oriPoint.y=Float.parseFloat(allinfo[1]);
			alllane[j].endPoint.x=Float.parseFloat(allinfo[2]);
			alllane[j].endPoint.y=Float.parseFloat(allinfo[3]);
			alllane[j].length=directionindex.getlength(alllane[j].oriPoint, alllane[j].endPoint);
			j++;
		}
		for(int i=0;i<lanenum;i++){
			for(int m=0;m<lanenum;m++){
				if(directionindex.twopoint(alllane[i].oriPoint, alllane[m].endPoint)&&
						directionindex.twopoint(alllane[i].endPoint, alllane[m].oriPoint)){//这个判断掉头,起点与终点相同，终点与起点相同
					alllane[i].directionroad[0].lanenumber=alllane[m].lanenumber;
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//终点与起点相同，起点与终点不同且两直线×乘为0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						!directionindex.twopoint(alllane[i].oriPoint, alllane[m].endPoint)&&
						0==directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					alllane[i].directionroad[2].lanenumber=alllane[m].lanenumber;//这个判断直行
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//终点与起点相同，起点与终点不同且两直线×乘大于0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						0<directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					    //叉乘大于0，说明是逆时针方向，即左转
					alllane[i].directionroad[1].lanenumber=alllane[m].lanenumber;
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//终点与起点相同，起点与终点不同且两直线×乘小于0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						0>directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					    //叉乘小于0，说明是顺时针方向，即右转
					alllane[i].directionroad[3].lanenumber=alllane[m].lanenumber;
				}
				
			}
		}
		FileReader reader1 = new FileReader("D:\\vehiclelane1\\finish\\okvehicle.txt");//此处的地址是训练数据的地址
		BufferedReader br1 = new BufferedReader(reader1);

		double previousid,nextid;
		int previousidint,nextidint;
		while ((line = br1.readLine()) != null) {
//			System.out.println(line);
			allinfo=line.split("-");
			if(allinfo.length>1){
			allinfo1=allinfo[0].split(" ");
			allinfo2=allinfo[1].split(" ");
			previousid=Double.parseDouble(allinfo1[7]);
			nextid=Double.parseDouble(allinfo2[7]);
			previousidint=(int)previousid;
			nextidint=(int)nextid;//将前车道id和后车道ID转化为int型
			if(!allinfo1[7].equals(allinfo2[7])){
				
				for(int m=0;m<4;m++){
//					System.out.println(previousidint);
//					System.out.println(m);
					if(alllane[previousidint].directionroad[m].lanenumber==nextid){
						
						alllane[previousidint].count[m]++;
						//System.out.println("前车道号："+previousidint+"  后车道号："+nextidint);
						//System.out.println("后车道号："+alllane[previousidint].directionroad[m].lanenumber+"  后车道号："+nextidint);
					}
				}
				
			}
		}
	}
		
//		for(int i=0;i<lanenum;i++){
//
//				System.out.println("车道号："+alllane[i].lanenumber+"  掉头："+alllane[i].directionroad[0].lanenumber+"  左转："+alllane[i].directionroad[1].lanenumber+"  直行："+alllane[i].directionroad[2].lanenumber+"  右转："+alllane[i].directionroad[3].lanenumber);
//
//			    System.out.println("车道转向统计："+alllane[i].lanenumber+"  掉头："+alllane[i].count[0]+"  左转："+alllane[i].count[1]+"  直行："+alllane[i].count[2]+"  右转："+alllane[i].count[3]);
//		}
		return(alllane);
	}
	public static double getlength(point p1,point p2){//计算两个点的距离
		double length;
		length=Math.sqrt((p2.y-p1.y)*(p2.y-p1.y)+(p2.x-p1.x)*(p2.x-p1.x));
		return(length);
	}
	public static float getdianc(point p1,point p2,point p3,point p4){//得到两个向量的夹角
		//向量的点积：x1*x2+y1*y2。向量的×积（向量积）：|c|=|a|*|b|sin(a,b),或x1*y2-x2*y1
		float x1=(float)(p2.x-p1.x);
		float y1=(float)(p2.y-p1.y);
		float x2=(float)(p4.x-p3.x);
		float y2=(float)(p4.y-p3.y);
		float cosab,ab,a,b;
		ab=x1*x2+y1*y2;
		a=(float) Math.sqrt(x1*x1+y1*y1);
		b=(float) Math.sqrt(x2*x2+y2*y2);
		cosab=ab/(a*b);
		return(cosab);
	}
	public static float getchac(point p1,point p2,point p3,point p4){//得到两个向量的夹角
		//向量的点积：x1*x2+y1*y2。向量的×积（向量积）：|c|=|a|*|b|sin(a,b),或x1*y2-x2*y1
		float x1=(float)(p2.x-p1.x);
		float y1=(float)(p2.y-p1.y);
		float x2=(float)(p4.x-p3.x);
		float y2=(float)(p4.y-p3.y);
		float cosab,ab,a,b;
		cosab=x1*y2-x2*y1;
		return(cosab);//返回×乘
	}
	
	public static void main(String[] args) throws Exception{
		point p1=new point(0,1000);
		point p2=new point(0,500);
		point p3=new point(0,500);
		point p4=new point(0,0);
        System.out.println(directionindex.getchac(p1, p2, p3, p4));
//		directionindex.directionindex("D:\\12point.txt");
//		point p1=new point(1,1);
//		point p2=new point(2,2);
//		point p3=new point();
//		point p4=new point();
//		System.out.println(directionindex.getchac(p1, p2, p3, p4));
//		System.out.println(directionindex.getdianc(p1, p2, p3, p4));

	}

}
