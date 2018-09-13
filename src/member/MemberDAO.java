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
		//connection객체를 생성해서 디비에 연결해줍
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url, username, password);
			System.out.println("드라이버 연결 성공");
				
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("드라이버 연결 실패");
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
			         // select한 결과는 ResultSet에 담겨 리턴된다.
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
	
/*	 // 조건에 맞는 행을 DB에서 삭제하는 메서드
	   public Student selectOne(String id) {
	      String sql = "select * from student where id=?;";
	      PreparedStatement pstmt = null;
	      Student re = new Student();
	      try {
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, id);
	         ResultSet rs = pstmt.executeQuery();
	         // select한 결과는 ResultSet에 담겨 리턴된다.
	         if (rs.next()) { // 가져올 행이 있으면 true, 없으면 false
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
