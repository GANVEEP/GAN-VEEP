package makevehicletrace;

public class isinside {

	//目前只能判断与坐标轴垂直的矩形、与坐标轴不垂直的矩形以后再加
	public static boolean isInside(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4,double x,double y){

		double[][] d=isinside.sort1(x1, y1, x2, y2, x3, y3, x4, y4);
		x1=d[0][0];
		y1=d[0][1];
		x2=d[1][0];
		y2=d[1][1];
		x3=d[2][0];
		y3=d[2][1];
		x4=d[3][0];
		y4=d[3][1];
	    if (y1 == y2 || y1==y3){//x1y1代表左下的点，x4y4代表的是右上的带点
	    	//System.out.print("******");
	    	
	        return isinside.isInside(x1, y1, x4, y4, x, y);
	    }
	    else{
	    	
	    }
	    double a = Math.abs(y4 - y3);
	    double b = Math.abs(x4 - x3);
	    double c = Math.sqrt(a*a + b*b);
	    double sin = a / c;
	    double cos = b / c;
	    double x1R = x1*cos + y1*sin;
	    double  y1R = y1*cos - x1*sin;
	    double  x4R = x4*cos + y4*sin;
	    double y4R = y4*cos - x4*sin;
	    double xR = x*cos + y*sin;
	    double yR = y*cos - x*sin;
	    return isInside(x1R, y1R, x4R, y4R, xR, yR);
	}
    public static boolean isInside(double x1,double y1,double x4,double y4,double x,double y){
        if (x < x1 || x > x4 || y < y1 || y > y4){
            return (false);
        }
        return (true);
    }
    public static double[][] sort1(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4){
    	double[][] d=new double[4][2];
		d[0][0]=x1;
		d[0][1]=y1;
		d[1][0]=x2;
		d[1][1]=y2;
		d[2][0]=x3;
		d[2][1]=y3;
		d[3][0]=x4;
		d[3][1]=y4;
    	double x,y;
    	double minx,miny;
    	double maxx,maxy;
    	minx=d[0][0];//最大和最小的x，和y值
    	miny=d[0][1];
    	maxx=d[0][0];
    	maxy=d[0][1];
    	for(int i=0;i<d.length;i++){
    		if(minx>d[i][0]){
    			minx=d[i][0];
    		}
    		if(miny>d[i][1]){
    			miny=d[i][1];
    		}
    		if(maxx<d[i][0]){
    			maxx=d[i][0];
    		}
    		if(maxy<d[i][1]){
    			maxy=d[i][1];   			
    		}
    	}
		for(int i=0;i<d.length;i++){
			if(d[i][0]==minx && d[i][1]==miny){
				d[i][0]=d[0][0];
				d[i][1]=d[0][1];
				d[0][0]=minx;
				d[0][1]=miny;
			}
		}
		for(int i=0;i<d.length;i++){
			if(d[i][0]==maxx && d[i][1]==maxy){
				d[i][0]=d[3][0];
				d[i][1]=d[3][1];
				d[3][0]=maxx;
				d[3][1]=maxy;
			}
		}
		return(d);
    }
    static public void main(String[] args){
    	boolean b=isinside.isInside(1000.0, 0.0, 1000.0, 500.0, 1006.5, 0, 1006.5, 500 ,1000,497.93);
    	System.out.println(b);
//    	boolean b1=isinside.isInside(500.0,1000.0,1000.0,1000.0,1000.0,993.5,500.0,993.5,525.33,995.05);
//    	System.out.println(b1);
//    	double[][] d= isinside.sort1(500.0,1000.0,1000.0,1000.0,1000.0,993.5,500.0,993.5);
//    	//System.out.println(d);
//    	for(int i=0;i<d.length;i++){
//    		System.out.println(d[i][0]+"  "+d[i][1]);
//    	}
    } 
}
