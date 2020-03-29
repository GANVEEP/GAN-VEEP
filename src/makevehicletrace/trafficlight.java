package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;

public class trafficlight {

	static point[] points=new point[24];//存储点的数组
	static int[][] tafficlight=new int[24][2000];//这里记录垂直于X轴的车道的初始信号灯信息
    static int[] orign=new int[24];//1代表绿灯   0代表红灯
	public static void settrafficlight(String path)throws Exception{
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[100];
		int signal=0;
		byte[] by;
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
            if(d[0].equals(d[2])){//垂直于x轴
            	orign[signal]=1;
            }
            else{//垂直于y轴
            	orign[signal]=0;
            }
            signal++;
		}
		br.close();
		boolean b=false;
		for(int i=0;i<2000;i++){
			if(i%39==0){
				b=trafficlight.booleanx(b);
			}
			if(b==true){
				for(int j=0;j<24;j++){
					tafficlight[j][i]=orign[j];
					if(j==1||j==5||j==2||j==11||j==12||j==15||j==16||j==19){
						tafficlight[j][i]=1;
					}
				}
				
			}
			else {
				for(int j=0;j<24;j++){
					tafficlight[j][i]=trafficlight.returnx(orign[j]);
					if(j==1||j==5||j==2||j==11||j==12||j==15||j==16||j==19){
						tafficlight[j][i]=1;
					}
				}
			}
			
		}
	}
	public static boolean booleanx(boolean b){
		if(b==true){
			return(false);
		}
		else{
			return(true);
		}
	}
	public static int returnx(int n){
		if(n==0){
			return(1);
		}
		else{
			return(0);
		}
	}
	public static void readtrafficlight(String path)throws Exception{
		points[1]=new point(0,0);
		points[2]=new point(500,0);
		points[3]=new point(1000,0);
		points[4]=new point(1000,500);
		points[5]=new point(500,500);
		points[6]=new point(0,500);
		points[7]=new point(0,1000);
		points[8]=new point(500,1000);
		points[9]=new point(1000,1000);
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);//车道四个点的文件
		String line;
		String[] d=new String[100];
		int signal=1;
		byte[] by;
		while ((line = br.readLine()) != null) {
			d=line.split(" ");

			if(d.length>10){
				d[10].split("\"");
				by=d[10].split("\"")[1].getBytes();
				System.out.println(by[0]);
			}
//			System.out.println(d[8]);
			if(d[d.length-1].equals("offset=\"0\">")){
				signal++;
			}
		}
	}
	public static void main(String[] args) throws Exception{
		trafficlight.settrafficlight("D:\\12point.txt");

		for(int i=0;i<100;i++){
			System.out.println("时间："+i+"  交通灯"+trafficlight.tafficlight[0][i]);
		}
	}

}
