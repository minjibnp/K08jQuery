package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ZipcodeDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;

	public ZipcodeDAO() {

		try {
			Context ctx = new InitialContext();
			DataSource source = (DataSource) ctx.lookup("java:comp/env/jdbc/myoracle");
			con = source.getConnection();

			System.out.println("DBCP연결성공(인자생성자)");
		} catch (Exception e) {
			System.out.println("DBCP연결실패(인자생성자)");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			// 연결을 해제하는것이 아니고 풀에 다시 반납한다.
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			System.out.println("자원반납시 예외발생");
		}
	}

	// 그룹함수 count()를 통해 회원의 존재유무만 판단한다
	public ArrayList<String> getSido() {

		ArrayList<String> sidoAddr = new ArrayList<String>();

		String sql = " SELECT distinct sido FROM zipcode " + " WHERE 1=1 " + " ORDER BY sido ASC ";
		/*
		 * String sql = " SELECT sido FROM zipcode " + " WHERE 1=1 " + " GROUP BY sido "
		 * + " ORDER BY sido ASC ";
		 */
		try {

			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			// rs.next();
			// select절의 첫번째 결과값을 얻어오기 위해 getInt()사용함.

			while (rs.next())// 회원이 아닌경우
				sidoAddr.add(rs.getString(1));

		} catch (Exception e) {
			// 예외가 발생한다면 확인이 불가능하므로 무조건 false를 반환한다.
			e.printStackTrace();
		}
		return sidoAddr;
	}

	public ArrayList<String> getGugun(String sido) {
		ArrayList<String> gugunAddr = new ArrayList<String>();
		// 쿼리문 작성
		String sql = "SELECT distinct gugun FROM zipcode " 
					+ " WHERE sido=? " 
					+ " ORDER BY gugun DESC ";

		try {
			// prepare객체를 통해 쿼리문을 전송한다.
			// 생성자에서 연결정보를 저장한 커넥션 객체를 사용함.
			psmt = con.prepareStatement(sql);
			psmt.setString(1, sido);
			rs = psmt.executeQuery();
			// 실행결과를 가져오기 위해 next()를 호출한다.
			while (rs.next()) {
				gugunAddr.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gugunAddr;
	}
}
