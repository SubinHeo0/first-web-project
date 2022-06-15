package com.town.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.town.board.db.BoardDTO;


public class MemberDAO {
	
	// 공통변수 선언
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// 디비연결 메서드
	public Connection getCon() throws Exception{
		// 1. 2. 디비연결
		
		// 1) 프로젝트 정보를 초기화
		Context initCTX = new InitialContext();
		// 2) 프로젝트에 저장된 리소스 정보를 불러오기
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/town");
		
		// 3) 디비연결
		con = ds.getConnection();
		
		System.out.println(" DAO : 디비연결 성공(커넥션풀) ");
		System.out.println(" DAO : " + con);
		
		return con;
	}
	// 디비연결 메서드
	
	// 디비 자원해제
	public void closeDB(){
			try {
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
				
				System.out.println(" DAO : 디비 연결 자원해제 ");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	// 디비 자원해제
	
	// 아이디 중복체크
	public int idCheck(String id){
		int result = 0;
		
		try {
			// 1.2 디비연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			sql = "select * from town_member where id = ?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, id);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()){	// 데이터가 있음 -> 아이디 중복
				result = 0;
			} else { // 데이터가 없으면
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 아이디 중복체크
	
	// 닉네임 중복체크 nicknameCheck(nickname);
	public int nicknameCheck(String nickname){
		int result = 0;
		
		try {
			// 1.2 디비연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			sql = "select * from town_member where nickname = ?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, nickname);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()){	// 데이터가 있음 -> 전화번호 중복
				result = 0;
			} else { // 데이터가 없으면
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 닉네임 중복체크 nicknameCheck(nickname);
	
	// 전화번호 중복체크telCheck(in_tel)
	public int telCheck(String tel){
		int result = 0;
		
		try {
			// 1.2 디비연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			sql = "select * from town_member where tel = ?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, tel);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()){	// 데이터가 있음 -> 전화번호 중복
				result = 0;
			} else { // 데이터가 없으면
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 전화번호 중복체크
	
	// 이메일 중복체크 emailCheck(in_email);
	public int emailCheck(String email){
		int result = 0;
		
		try {
			// 1.2 디비연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			sql = "select * from town_member where email = ?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, email);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()){	// 데이터가 있음 -> 이메일 중복
				result = 0;
			} else { // 데이터가 없으면
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 이메일 중복체크
	
	// insertMember();
	public void insertMember(MemberDTO dto){
		System.out.println(" DAO : 회원가입 메서드 실행! ");
		try {
			// 1.2. 디비연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			sql = "insert into town_member(id,pw,name,nickname,birth,gender,tel,email,addr_num,addr_load,addr,addr_detail,addr_dong,reg_date) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);

			
			// ???
//			String email = dto.getEmail().length()>0 ? dto.getEmail() : null;
//			String addr = dto.getAddr().length()>0 ? dto.getAddr() : null;
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getNickname());
			pstmt.setString(5, dto.getBirth());
			pstmt.setString(6, dto.getGender());
			pstmt.setString(7, dto.getTel());
			pstmt.setString(8, dto.getEmail());
			pstmt.setString(9, dto.getAddr_num());
			pstmt.setString(10, dto.getAddr_load());
			pstmt.setString(11, dto.getAddr());
			pstmt.setString(12, dto.getAddr_detail());
			pstmt.setString(13, dto.getAddr_dong());
			pstmt.setTimestamp(14, dto.getReg_date());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 회원가입 완료! ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	// insertMember();
	
	// loginCheck(id,pw);
	public int loginCheck(String id, String pw){
		int result = -1;
		
		try {
			// 1.2. 디비연결
			con = getCon();
			
			// 3. sql작성 & pstmt객체
			sql = "select pw from town_member where id=?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, id);
			
			// 4. sql실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터처리
			if(rs.next()){
				// 아이디에 해당하는 비밀번호 존재 => 비밀번호 일치 확인
				if(pw.equals(rs.getString("pw"))){
					result = 1;
				} else {
					// 비밀번호 틀림
					result = 0;
				}
			} else {
				// 아이디에 해당하는 비밀번호가 없음 => 비회원
				result = -1;
			}
			
			System.out.println(" DAO : 로그인 체크완료! ("+result+")");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// loginCheck(id,pw);
	
	// getMember(id);
	public MemberDTO getMember(String id){
		MemberDTO dto = null;
		
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql작성 & pstmt객체
			sql = "select * from town_member where id=?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, id);
			
			// 4. sql실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터처리
			if(rs.next()){
				dto = new MemberDTO();
				
				dto.setAddr(rs.getString("addr"));
				dto.setAddr_detail(rs.getString("addr_detail"));
				dto.setAddr_dong(rs.getString("addr_dong"));
				dto.setAddr_load(rs.getString("addr_load"));
				dto.setAddr_num(rs.getString("addr_num"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPw(rs.getString("pw"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setTel(rs.getString("tel"));
				dto.setNickname(rs.getString("nickname"));
				
			}
			
			System.out.println(" DAO : 회원 ("+id+") 정보 불러오기 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return dto;
	}
	// getMember(id);
	
	// updateMember(dto);
	public void updateMember(MemberDTO dto){
		try {
			// 1.2. 디비연결
			con = getCon();
			
			// 3. sql작성 & pstmt객체
			sql = "update town_member set pw=?,name=?,nickname=?,birth=?,gender=?,tel=?,email=?,addr_num=?,addr_load=?,addr=?,addr_detail=?,addr_dong=? "
					+ "where id=?";
			pstmt = con.prepareStatement(sql);
			
			// ???
//			String email = dto.getEmail().length()>0 ? dto.getEmail() : null;
//			String addr = dto.getAddr().length()>0 ? dto.getAddr() : null;
			
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getNickname());
			pstmt.setString(4, dto.getBirth());
			pstmt.setString(5, dto.getGender());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getEmail());
			pstmt.setString(8, dto.getAddr_num());
			pstmt.setString(9, dto.getAddr_load());
			pstmt.setString(10, dto.getAddr());
			pstmt.setString(11, dto.getAddr_detail());
			pstmt.setString(12, dto.getAddr_dong());
			pstmt.setString(13, dto.getId());
			
			// 4. sql실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 회원정보 수정 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// updateMember(dto);
	
	// deleteMember(id);
	public void deleteMember(String id){
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql작성 & pstmt객체
			sql = "delete from town_member where id=?";
			pstmt = con.prepareStatement(sql);
			
			// ??? 
			pstmt.setString(1, id);
			
			// 4. sql실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 회원정보 삭제 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// deleteMember(id);
	
	// getAddr(nickname);
	public String getAddr(String nickname){
		String addr = null;
		
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql작성 & pstmt객체
			sql = "select addr from town_member where nickname=?";
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setString(1, nickname);
			
			// 4. sql실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터처리
			if(rs.next()){
				addr = rs.getString("addr");
			}
			
			System.out.println(" DAO : 회원 닉네임 ("+nickname+") 에 해당하는 주소 불러오기 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return addr;
	}
	// getAddr(nickname);
	
	// getMemberCount
	public int getMemberCount(){
		int result = 0;
		
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql 작성(select) & pstmt 객체
			sql = "select count(id) from town_member";
			pstmt = con.prepareStatement(sql);
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()){
				//result = rs.getInt("count(num)");
				result = rs.getInt(1);
			}
			
			System.out.println(" DAO : 회원수 "+result+"명");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// getMemberCount
	
	// getMemberList(startRow, pageSize);
	public List<MemberDTO> getMemberList(int startRow,int pageSize){
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql 작성 & pstmt 객체 (페이징 처리)
			sql = "select * from town_member where id != ? limit ?,?";
			pstmt = con.prepareStatement(sql);
			
			//???
			pstmt.setString(1, "admin");
			pstmt.setInt(2, startRow-1); // 시작행 - 1
			pstmt.setInt(3, pageSize); // 개수
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리	
			while(rs.next()){ // 데이터 있을때 DB정보를 모두 저장
				// 글 1개의 정보 => BoardBean 객체
				MemberDTO dto = new MemberDTO();
				
				dto.setAddr(rs.getString("addr"));
				dto.setAddr_detail(rs.getString("addr_detail"));
				dto.setAddr_dong(rs.getString("addr_dong"));
				dto.setAddr_load(rs.getString("addr_load"));
				dto.setAddr_num(rs.getString("addr_num"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setNickname(rs.getString("nickname"));
				dto.setPw(rs.getString("pw"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setTel(rs.getString("tel"));
				
				// MemberDTO 객체의 정보 => List 한칸에 저장
				
				memberList.add(dto);
			}// while
			
			System.out.println(" DAO : 회원 전체 정보 저장완료! ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return memberList;
	}
	// getMemberList(startRow, pageSize);
	

}
