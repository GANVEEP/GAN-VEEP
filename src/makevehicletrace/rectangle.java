package makevehicletrace;

public class rectangle {

	int id;
	double roadlength;
	point p1=new point();//���½ǵĵ�����
	point p2=new point();//���Ͻǵĵ�
	point p3=new point();//���½ǵĵ�
	point p4=new point();//���Ͻǵĵ�����
	
	point pori=new point();//����������������
	point pend=new point();//�յ�
	public rectangle(){
		
	}
	public void sort(){
		double direction=(pori.y-pend.y)/(pori.x-pend.x);
		point[] pp=new point[4];
		point ppp=new point();
		for(int i=0;i<4;i++){
			pp[i]=new point();
		}
		pp[0]=p1;
		pp[1]=p2;
		pp[2]=p3;
		pp[3]=p4;
		for(int j=0;j<4;j++){
		for(int i=0;i<3;i++){
			if(pp[i].add()>pp[i+1].add()){
				ppp=pp[i];
				pp[i]=pp[i+1];
				pp[i+1]=ppp;
			}
		}
		}
		p1=pp[0];
		p4=pp[3];
		if(direction>1){
			p2=pp[2];
			p3=pp[1];
		}
		else{
			p3=pp[2];
			p2=pp[1];
		}
		
	}
	public rectangle(point p1,point pori,point pend){
		this.p1=p1;
		this.pori=pori;
		this.pend=pend;
	}
}
