package makevehicletrace;

import java.text.DecimalFormat;

public class changeXY {

	
	public static point judge(rectangle r1,point px){//按照矩阵的方向和位置，将坐标转化为标准道路坐标
		DecimalFormat df=new DecimalFormat("#0.00");
		double direction;
		point p=new point();
		direction=r1.pend.x+r1.pend.y-r1.pori.x-r1.pori.y;//代表方向，大于零代表正向，小于零代表反向，需要转方向
		if(direction>0){
			//System.out.println("@@");
			p=changeXY.exangexy(r1, px);
			
		}
		else{
			p=changeXY.nexangexy(r1, px);
//			System.out.println("@@");
		}
		p.y=Double.parseDouble(df.format(p.y));
		p.x=Double.parseDouble(df.format(p.x));
		return(p);
	}
	public static point nexangexy(rectangle r1,point x){//反向的转换函数
		
		point p1=new point();
		point p2=new point();
		point p3=new point();
		point p4=new point();
		p1=r1.p1;
		p2=r1.p2;
		p3=r1.p3;
		p4=r1.p4;
		point p=new point();//返回这个点
		point p11=new point();
		point p14=new point();
		p11.x=0;//平移后的对角点
		p11.y=0;

		if(((p1.y-p4.y)/(p1.x-p4.x))>1){//代表车道是与y轴平行的车道
			x.x=x.x-p2.x;
			x.y=x.y-p2.y;
			if(x.y==0){
				x.y=x.x;
				x.x=0;
				return(x);
			}
			else if(x.x==0){
				x.x=Math.abs(x.y);
				x.y=0;
				return(x);
			}
			else{
			double k1=x.y/x.x;//用斜率
			double k=-1/k1;
			double length=returndistance(p11, x);
			p.x=Math.sqrt((length*length)/(1+k*k));
			p.y=k*p.x;
//			System.out.pri ntln("P点："+p.x+"  "+p.y+" k:"+k+" k1:"+k1); 
			return(p);
			}
		}
		else{//代表车道与x轴平行
			x.x=x.x-p3.x;
			x.y=x.y-p3.y;
			p.x=-x.x;
			p.y=x.y;
			return(p);
		}
	}
	
	public static point exangexy(rectangle r1,point x){//正向的转换函数
		//坐标平移
		point p1=new point();
		point p4=new point();
		p1=r1.p1;
		p4=r1.p4;
		point p=new point();
		point p11=new point();
		point p12=new point();
		p11.x=0;//平移后的对角点
		p11.y=0;
		p12.x=p4.x-p1.x;
		p12.y=p4.y-p1.y;
		x.x=x.x-p1.x;
		x.y=x.y-p1.y;
		
			double k1=x.y/x.x;
			double k=-1/k1;
			double length=returndistance(p11, x);
			p.x=Math.sqrt((length*length)/(1+k*k));
			p.y=k*p.x;
			//System.out.println(p.x+"  "+p.y+"  "+k);
			//System.out.println(p.x+"  "+p.y);
			if(((p1.y-p4.y)/(p1.x-p4.x))>1){
				if(x.x==0){
					x.y=0;
					x.x=Math.abs(x.y);
					return(x);
				}
				else if(x.y==0){
					x.x=0;
					x.y=(-1)*Math.abs(x.x);
					x.y=x.y+6.5;
					return(x);
				}else{
					p.y=p.y+6.5;//新加句
					return(p);
				}

			}
			else{
				return(x);
			}
		

	}
	
	public static point njudge(rectangle r1,point px){//坐标的逆转换
		DecimalFormat df=new DecimalFormat("#0.00");
		double direction;
		point p=new point();
		direction=r1.pend.x+r1.pend.y-r1.pori.x-r1.pori.y;//代表方向，大于零代表正向，小于零代表反向，需要转方向
		if(direction>0){
			//System.out.println("@@");
			p=changeXY.inversechange(r1, px);
			
		}
		else{
			//System.out.println("@@");
			p=changeXY.ninversechange(r1, px);
		}
		p.y=Double.parseDouble(df.format(p.y));
		p.x=Double.parseDouble(df.format(p.x));
		return(p);
	}
	public static point ninversechange(rectangle r1,point x){//反向的逆转换函数  
		point p1=r1.p1;
		point p4=r1.p4;
		point p3=r1.p3;
		point p2=r1.p2;
		double length=returndistance(new point(0, 0),x);

		double k1=(x.y)/(x.x);
		double k=-1/k1;
		point p=new point();
		
		if(((p1.y-p4.y)/(p1.x-p4.x))>1){//判断是否平行于x轴
			if(x.x==0){
				x.x=Math.abs(x.y);
				x.y=0;
				x.x=x.x+p2.x;
				x.y=x.y+p2.y;
				return(x);
			}
			else if(x.y==0){
				x.y=(-1)*Math.abs(x.x);
				x.x=0;
				x.x=x.x+p2.x;
				x.y=x.y+p2.y;
				return(x);
			}
			else{
				p.x=Math.sqrt((length*length)/(1+k*k));
				//System.out.println(p.x);
				p.y=k*p.x;
				p.x=p.x+p2.x;
				p.y=p.y+p2.y;	
				return(p);
			}
		}
		else{
			x.x=-x.x;
			x.x=x.x+p3.x;
			x.y=x.y+p3.y;
			return(x);
		}
	}
	public static point inversechange(rectangle r1,point x){//正向的逆转换函数
		point p1=r1.p1;
		point p4=r1.p4;
		point p=new point();
		double length=returndistance(new point(0, 0),x);

		if(((p1.y- p4.y)/(p1.x-p4.x))>1){
			x.y=x.y-6.5;//新加句
			if(x.x==0){
				x.x=Math.abs(x.y);
				x.y=0;
				x.x=x.x+p1.x;
				x.y=x.y+p1.y;
				return(x);
			}
			else if(x.y==0){
				x.y=Math.abs(x.x);
				x.x=0;
				x.x=x.x+p1.x;
				x.y=x.y+p1.y;
				return(x);
			}
			else{
				
			
			double k=(x.y)/(x.x);
			k=-1/k;
			p.x=Math.sqrt((length*length)/(1+k*k));
			p.y=k*p.x;
			p.x=p.x+p1.x;
			p.y=p.y+p1.y;
			//System.out.println(p.y); 
			return(p);
			}
		}
		else{
			x.x=x.x+p1.x;
			x.y=x.y+p1.y;
			return(x);
		}
	}
	public static double abs(double a){
		if(a<0){
			return(-a);
		}
		else{
			return(a);
		}
	}
	public static double returndistance(point p1,point p2){
		DecimalFormat df=new DecimalFormat("#0.00");
		double distance=Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));//加一个格式控制
		distance=Double.parseDouble(df.format(distance));
		return(distance);
		
	}
	public static void main(String[] args) throws Exception{
		
//		rectangle r1=new rectangle();
//		r1.p1=new point(10,20);//第一组测试数据，竖着的
//		r1.p2=new point(10,60);
//		r1.p3=new point(20,20);
//		r1.p4=new point(20,60);
//		
//		r1.pori=new point(15,20);
//		r1.pend=new point(15,60);
//		
//		point px=new point(15,25);
//		point px1=new point(5,-5);
		rectangle[] r1=generattraindata.laneinfo();//道路方向，和道路范围信息存储数组
//		r1.p1=new point(10,10);//第二组测试数据，横着的
//		r1.p2=new point(10,20);
//		r1.p3=new point(60,10);
//		r1.p4=new point(60,20);
//		r1.pori=new point(15,15);
//		r1.pend=new point(50,15);		
//		point px=new point(15,15);
//		point px1=new point(5,5);
		
		point p1=new point();
		point p2=new point();
//		p1=changeXY.judge(r1, px);
		point px1=new point(123.88,1.55);
		p2=changeXY.njudge(r1[0], px1);
//		p2=changeXY.inversechange(new point(1,1), new point(6,2), new point(3,1));
//		System.out.println(p1.x+"  "+p1.y);
		System.out.println(p2.x+"  "+p2.y);
	}

}
