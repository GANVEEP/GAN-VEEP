package makevehicletrace;

public class vehicle {
    double lannumber;//������
    int laneseq;//�������У�ÿһ������������Ϊ�����ӳ������ó������б�ʾ,������յ�����Խ�����ԽС
	float[] v;
	double x;
	double y;
	double speed;
	
	double tolukou;//������·�ڵľ���
	double havelastcar;//�Ƿ�ǰ���г�0����û�г���1�����г�
	double tolastcar;//ǰ�泵���ľ���
	double frontcarspeed;//ǰ�泵�����ٶ�
	double time;//������ʱ��
	int id;//����ID
	public vehicle(double x,double y,double speed, int id){
		this.id=id;
		this.speed=speed;
		this.x=x;
		this.y=y;
	}
}
