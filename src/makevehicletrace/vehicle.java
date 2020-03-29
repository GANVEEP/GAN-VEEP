package makevehicletrace;

public class vehicle {
    double lannumber;//车道号
    int laneseq;//车道序列，每一条车道都划分为两个子车道，用车道序列表示,离起点终点连线越近编号越小
	float[] v;
	double x;
	double y;
	double speed;
	
	double tolukou;//车辆里路口的距离
	double havelastcar;//是否前面有车0代表没有车，1代表有车
	double tolastcar;//前面车辆的距离
	double frontcarspeed;//前面车辆的速度
	double time;//车辆的时间
	int id;//车辆ID
	public vehicle(double x,double y,double speed, int id){
		this.id=id;
		this.speed=speed;
		this.x=x;
		this.y=y;
	}
}
