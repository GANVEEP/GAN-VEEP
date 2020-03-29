package makevehicletrace;

import java.util.List;
import java.util.TreeMap;

public class vehiclelane {

	double length;//道路的长度
	float[] vehicleposition=new float[8];//车道的四个点坐标
	vehiclelane[] directionroad=new vehiclelane[4];//0代表掉头，1代表左转，2代表直行，3代表右转，999代表不存在
	int[] count=new int[4];//记录从当前车道转向，这几条车道的车辆的总数。
	int lanenumber;//车道ID
	TreeMap<Integer, vehicle> vehiclelist=new TreeMap<Integer, vehicle>();//车辆ID和车辆信息
	point oriPoint=new point();
	point endPoint=new point();
	public vehiclelane(int id){
		this.lanenumber=id;
		for(int i=0;i<directionroad.length;i++){
			directionroad[i]=new vehiclelane();
			directionroad[i].lanenumber=999;
		}
	}
	public vehiclelane(){

	}
	
}
