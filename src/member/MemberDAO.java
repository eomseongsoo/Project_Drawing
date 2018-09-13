package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MemberDAO {

	private Connection con;
	private static final String username="root";
	private static final String password="1111";
	private static final String url="jdbc:mysql://localhost/summer";
	
	public MemberDAO() {
		//connection��ü�� �����ؼ� ��� ��������
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url, username, password);
			System.out.println("����̹� ���� ����");
				
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("����̹� ���� ����");
		}
		
		
	}
	
	public boolean login(String id,String password) {
		String sql="select password from member where id=?;";
		PreparedStatement pstmt=null;
		Member member=new Member();
		String pass=null;
		      try {
			         pstmt = con.prepareStatement(sql);
			         pstmt.setString(1, id);
			         ResultSet rs = pstmt.executeQuery();
			         // select�� ����� ResultSet�� ��� ���ϵȴ�.
			         if(rs.next()) {
			         pass=rs.getString("password");
			         }
			      } catch (SQLException e) {
			         e.printStackTrace();
			      } finally {
			         try {
			            if (pstmt != null && !pstmt.isClosed())
			               pstmt.close();
			         } catch (SQLException e) {
			            e.printStackTrace();
			         }
			      }
		      if(pass.equals(password)) {
		    	  return true;
		      }else {
		    	  return false;
		      }
	}
	
/*	 // ���ǿ� �´� ���� DB���� �����ϴ� �޼���
	   public Student selectOne(String id) {
	      String sql = "select * from student where id=?;";
	      PreparedStatement pstmt = null;
	      Student re = new Student();
	      try {
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, id);
	         ResultSet rs = pstmt.executeQuery();
	         // select�� ����� ResultSet�� ��� ���ϵȴ�.
	         if (rs.next()) { // ������ ���� ������ true, ������ false
	            re.setId(rs.getString("id"));
	            re.setName(rs.getString("name"));
	            re.setGrade(rs.getString("grade"));
	         }
	         re.toString();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (pstmt != null && !pstmt.isClosed())
	               pstmt.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return re;
	   }*/
}
