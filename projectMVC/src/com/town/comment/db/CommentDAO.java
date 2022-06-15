package com.town.comment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommentDAO {
	
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
		
		// insertComment(dto);
		public void insertComment(CommentDTO dto){
			int num = 0;	// 댓글번호 저장변수
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql작성 & pstmt객체
				sql = "select max(num) from town_comment";
				pstmt = con.prepareStatement(sql);
				
				// 4. sql실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){ // max(num) 컬럼의 결과는 항상 존재함(커서 있음)
					num = rs.getInt(1)+1;//인덱스
				}
				
				System.out.println(" DAO : 댓글번호 "+num);
				
				/////////////////////////////////////////////////////////////
				// 글쓰기 
				
				// 이미 연결됨 (1.2. 단계 생략)
				// 3. sql(insert) 작성 & pstmt 객체
				sql = "insert into town_comment(num,b_num,nickname,addr,content,"
						+ "re_ref,re_lev,re_seq,date,ip) "
						+ "values(?,?,?,?,?,?,?,?,now(),?)";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				pstmt.setInt(2, dto.getB_num());
				pstmt.setString(3, dto.getNickname());
				pstmt.setString(4, dto.getAddr());
				pstmt.setString(5, dto.getContent());
				
				pstmt.setInt(6, num); // re_ref   답글 -> 그룹번호   일반글은 글번호와 동일
				pstmt.setInt(7, 0); // re_lev  답글 -> 들여쓰기  일반글은 0
				pstmt.setInt(8, 0); // re_seq  답글 -> 순서      일반글은 0
				
				pstmt.setString(9, dto.getIp());
				
				// 4. sql실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 댓글쓰기 완료! ");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// insertComment(dto);
		
		// getCommentCount(b_num);
		public int getCommentCount(int b_num){
			int result = 0;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql 작성 & pstmt 객체
				sql = "select count(*) from town_comment where b_num=?";
				pstmt = con.prepareStatement(sql);
				
				// ??? 처리
				pstmt.setInt(1, b_num);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					result = rs.getInt(1);	// 인덱스
				}
				
				System.out.println(" DAO : 검색된 댓글 개수 "+result);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return result;
		}
		// getCommentCount(b_num);
		
		// getCommentList(Integer.parseInt(request.getParameter("b_num")));
		public List<CommentDTO> getCommentList(int b_num){
			// 글번호에 해당하는 댓글다가져오기
			List<CommentDTO> commentList = new ArrayList<CommentDTO>();
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 (페이징처리x) =>  re_ref 오름차순, re_seq 오름차순
				sql = "select * from town_comment where b_num=? "
						+ "order by re_ref asc, re_seq asc";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, b_num);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				while(rs.next()){ // 데이터 있을 때 DB정보를 모두 저장
					// 댓글 1개 정보 => dto
					CommentDTO dto = new CommentDTO();
					
					dto.setAddr(rs.getString("addr"));
					dto.setB_num(rs.getInt("b_num"));
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					
					// CommentDTO 객체의 정보 => List 한칸에 저장
					
					commentList.add(dto);
					
				}// while
				
				System.out.println(" DAO : 해당 글("+b_num+")에 해당하는 댓글 전체 목록 저장완료! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return commentList;
		}
		// getCommentList(Integer.parseInt(request.getParameter("b_num")));
		
		// deleteComment(b_num); - 게시글번호에 해당하는 댓글 삭제
		public void deleteComment(int b_num){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "delete from town_comment where b_num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, b_num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 글번호("+b_num+") 댓글 삭제 완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// deleteComment(b_num);
		
		// deleteOneComment(num); - 댓글번호에 해당하는 댓글 삭제
		public void deleteOneComment(int num){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "delete from town_comment where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 댓글("+num+") 삭제 완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// deleteOneComment(num);
		
		// updateComment(dto); - 댓글의 내용만 업데이트
		public void updateComment(CommentDTO dto){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "update town_comment set content=? where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, dto.getContent());
				pstmt.setInt(2, dto.getNum());
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 댓글 수정완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// updateComment(dto);
		
		// getComment(num);
		public CommentDTO getComment(int num){
			CommentDTO dto = null;
			
			try {
				// 1.2 디비연결
				con = getCon();
				// 3. sql작성 & pstmt객체
				sql = "select * from town_comment where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					dto = new CommentDTO();
					
					dto.setAddr(rs.getString("addr"));
					dto.setB_num(rs.getInt("b_num"));
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
				}
				System.out.println(" DAO : 댓글 1개 저장완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return dto;
		}
		// getComment(num);
		
		// reInsertComment(dto);
		public void reInsertComment(CommentDTO dto){
			int num = 0;	// 대댓글 번호
			
			try {
				// 대댓글 번호 계산-----------------------------------------------------------
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql작성 & pstmt 생성
				sql ="select max(num) from town_comment";
				pstmt = con.prepareStatement(sql);
				
				// 4. sql실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					num = rs.getInt(1) + 1;
					
				}
				
				System.out.println(" DAO : 대댓글의 번호 "+num);
				
				// 대댓글 순서 재배치 (기존의 답글)----------------------------------------------
				// 3. sql구문 & pstmt 객체
				// 답글중에서 seq값이 동일한 값이 있을 때 1증가
				// re_ref(같은그룹) , re_seq(순서) 기존(부모글)의 값보다 클 때
				sql="update town_comment set re_seq = re_seq + 1 where re_ref=? and re_seq>?";
				pstmt = con.prepareStatement(sql);
				
				// ?????
				pstmt.setInt(1, dto.getRe_ref());
				pstmt.setInt(2, dto.getRe_seq());
				
				// 4. sql 실행
				int check = pstmt.executeUpdate();
				
				if(check > 0)
					System.out.println(" DAO : 대댓글 순서 재배치 완료! ");
				
				// 답글 저장 (ref-부모글의 값, lev-부모+1, seq-부모+1)-------------------------
				sql ="insert into town_comment(num,b_num,nickname,addr,content,"
						+ "re_ref,re_lev,re_seq,date,ip) "
						+ "values(?,?,?,?,?,?,?,?,now(),?)";
				pstmt = con.prepareStatement(sql);
				
				// ????
				pstmt.setInt(1, num);
				pstmt.setInt(2, dto.getB_num());
				pstmt.setString(3, dto.getNickname());
				pstmt.setString(4, dto.getAddr());
				pstmt.setString(5, dto.getContent());
				pstmt.setInt(6, dto.getRe_ref());	// ref - 부모댓글의 번호
				pstmt.setInt(7, dto.getRe_lev()+1);	// lev - 부모글 + 1
				pstmt.setInt(8, dto.getRe_seq()+1);	// seq - 부모글 + 1
				pstmt.setString(9, dto.getIp());
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// reInsertComment(dto);
		
		// deleteComment(nickname); - 오버로딩
		public void deleteComment(String nickname){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "delete from town_comment where nickname=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, nickname);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : "+nickname+"님의 댓글 삭제 완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// deleteComment(nickname); - 오버로딩
		
		
		
		
		
}
