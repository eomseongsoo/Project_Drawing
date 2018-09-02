package show;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Vector;

class Draw implements Serializable{
 	 private int x, y, x1, y1;
	 private int dist;
	 public int getDist() {return dist;}
	 public void setDist(int dist) {this.dist = dist;}
	 public int getX() {return x;}
	 public void setX(int x) {this.x = x;}
	 public int getY() {return y;}
	 public void setY(int y) {this.y = y;}
	 public int getX1() {return x1;}
	 public void setX1(int x1) {this.x1 = x1;}
	 public int getY1() {return y1;}
	 public void setY1(int y1) {this.y1 = y1;}
	
}
public class FrameSetting extends Frame  implements MouseListener, MouseMotionListener {

	// �޴��ٿ� �׸��� �޴�, �׸��� �޴� ���� �޴� ��������
	private MenuBar mb = new MenuBar();
	
	private Menu File = new Menu("File");
	private MenuItem Save=new MenuItem("Save");
	private MenuItem Open=new MenuItem("Open");
	private MenuItem Exit=new MenuItem("Exit");
	private MenuItem New=new MenuItem("New");
	
	
	// �޴��ٷ� ������������ �����̴�... ���� �׸��� ó�� ��ȣ�� �޴��ٰ� �ƴ� �ٷ� ��ư�� Ŭ���ؼ� �����ϴ� ���� ���� �� ����..
	// �̺�Ʈ ó���� �ʿ��ϴ� �������� �����ؼ� ��Ʈ ����� �����ص� ������?
	private Menu draw = new Menu("Draw");
	private MenuItem pen = new MenuItem("Pen"); // �� �׸���
	private MenuItem line = new MenuItem("Line"); // �� �׸���
	private MenuItem Circle = new MenuItem("Circle"); // �� �׸���
	private MenuItem recg = new MenuItem("Recgtangle"); // �簢�� �׸���

	private int x,y,x1,y1;//���콺 ���������� ���� ���� �� ��ǥ��
	private Vector vc=new Vector();
	
	// ������ ����
	public FrameSetting(String title) {
		super(title);

		this.setMenuBar(mb);
		mb.add(File);
		File.add(New);
		File.add(Open);
		File.add(Save);
		File.add(Exit);
		
		mb.add(draw);
		draw.add(pen);
		draw.add(line);
		draw.add(Circle);
		draw.add(recg);
		super.setSize(300, 200);
		// Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Dimension frame = super.getSize();
		setVisible(true);
	


	}


	public void paint(Graphics g) {
		for(int i=0;i<vc.size();i++) {
			Draw d=(Draw)vc.elementAt(i);
			if(d.getDist()==1) {
				g.drawLine(d.getX(), d.getY(), d.getX1(),d.getY1());
			}
			else if(d.getDist()==2){
				g.drawOval(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
			}
			else if(d.getDist()==3) {
				g.drawRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
			}
		}
		// ���콺�� ������ �������� 

		  if(pen.isEnabled()) {  //������ üũ�ϸ�
			  g.drawLine(x, y, x1, y1);  //x,y��ǥ���� x1,y1��ǥ�� ������ �׷���
		  }
		  else if(Circle.isEnabled()){  //oval�� üũ�ϸ�
			  g.drawOval(x, y, x1 - x, y1 - y); //oval�� �׷���
		  }
		  else if(recg.isEnabled()) {    //rect�� üũ�ϸ�
			  g.drawRect(x, y, x1 - x, y1 - y);  //rect�� �׷���
		  }


	

	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x1=e.getX();
		y1=e.getY();
		this.repaint();
		if(pen.isEnabled()) {  
			   Draw d = new Draw();  
			   d.setDist(1);
			   d.setX(x);
			   d.setY(y);
			   d.setX1(x1);
			   d.setY1(y1);
			   vc.add(d);
			   x = x1;
			   y = y1;
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x=e.getX();
		y=e.getY();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x1=e.getX();
		y1=e.getY();
		this.repaint();
		  if(pen.isEnabled() != true) {  //pen�� true�� �ƴҶ����� �Ʒ��� �����϶�
			   int dist = 0;
			   if(line.isEnabled() == true) dist =1; // line�� üũ�Ǹ� 1���� ����
			   else if(Circle.isEnabled() == true) dist = 2; // ovaldl üũ�Ǹ� 2���� ����
			   else if(recg.isEnabled()== true) dist = 3; // rect�� üũ�Ǹ� 3���� ����
			   Draw d = new Draw();  //d ��ü����
			   d.setDist(dist);  //dist �� ����

			   //������ �� ����
			   d.setX(x);  
			   d.setY(y);
			   d.setX1(x1);
			   d.setY1(y1);
			   vc.add(d); //vc�� ���� �����϶�
			  }



	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	




}

