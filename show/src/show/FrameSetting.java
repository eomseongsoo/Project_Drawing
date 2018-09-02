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

	// 메뉴바와 그리기 메뉴, 그리그 메뉴 하위 메뉴 변수설정
	private MenuBar mb = new MenuBar();
	
	private Menu File = new Menu("File");
	private MenuItem Save=new MenuItem("Save");
	private MenuItem Open=new MenuItem("Open");
	private MenuItem Exit=new MenuItem("Exit");
	private MenuItem New=new MenuItem("New");
	
	
	// 메뉴바로 구현할지말지 미정이다... 실제 그림판 처럼 기호로 메뉴바가 아닌 바로 버튼을 클릭해서 구현하는 것이 좋을 것 같다..
	// 이벤트 처리가 필요하다 조원끼리 협의해서 파트 나누어서 진행해도 좋을듯?
	private Menu draw = new Menu("Draw");
	private MenuItem pen = new MenuItem("Pen"); // 펜 그리기
	private MenuItem line = new MenuItem("Line"); // 선 그리기
	private MenuItem Circle = new MenuItem("Circle"); // 원 그리기
	private MenuItem recg = new MenuItem("Recgtangle"); // 사각형 그리기

	private int x,y,x1,y1;//마우스 눌렷을때와 땠을 때의 각 좌표값
	private Vector vc=new Vector();
	
	// 프레임 설정
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
		// 마우스를 눌렀다 떼었을때 

		  if(pen.isEnabled()) {  //라인을 체크하면
			  g.drawLine(x, y, x1, y1);  //x,y좌표에서 x1,y1좌표에 라인을 그려라
		  }
		  else if(Circle.isEnabled()){  //oval을 체크하면
			  g.drawOval(x, y, x1 - x, y1 - y); //oval을 그려라
		  }
		  else if(recg.isEnabled()) {    //rect를 체크하면
			  g.drawRect(x, y, x1 - x, y1 - y);  //rect를 그려라
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
		  if(pen.isEnabled() != true) {  //pen이 true가 아닐때에만 아래를 실행하라
			   int dist = 0;
			   if(line.isEnabled() == true) dist =1; // line가 체크되면 1값을 대입
			   else if(Circle.isEnabled() == true) dist = 2; // ovaldl 체크되면 2값을 대입
			   else if(recg.isEnabled()== true) dist = 3; // rect가 체크되면 3값을 대입
			   Draw d = new Draw();  //d 객체생성
			   d.setDist(dist);  //dist 값 대입

			   //각각의 값 대입
			   d.setX(x);  
			   d.setY(y);
			   d.setX1(x1);
			   d.setY1(y1);
			   vc.add(d); //vc에 값을 저장하라
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

