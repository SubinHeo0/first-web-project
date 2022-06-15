package com.town.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
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
					
					System.out.println("DAO : 디비 연결 자원해제 ");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		// 디비 자원해제
		
		// insertBoard(dto);
		public void insertBoard(BoardDTO dto){
			int num = 0; // 글번호 저장변수
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3 sql 작성(글번호계산) & pstmt 객체
				sql = "select max(num) from town_board";
				pstmt = con.prepareStatement(sql);
				
				// 4 sql 실행
				rs = pstmt.executeQuery();
				
				// 5 데이터 처리
				if(rs.next()){ // max(num) 컬럼의 결과는 항상 존재함(커서 있음)
					num = rs.getInt(1)+1;//인덱스
//					num = rs.getInt("max(num)")+1;//컬럼명
				}
				
				System.out.println(" DAO : 글번호 "+num);
				
				/////////////////////////////////////////////////////////////
				// 글쓰기 
				
				// 이미 연결됨 (1.2. 단계 생략)
				// 3. sql(insert) 작성 & pstmt 객체
				sql = "insert into town_board(num,nickname,addr,subject,content,"
						+ "readcount,re_ref,re_lev,re_seq,date,ip,file,c_count) "
						+ "values(?,?,?,?,?,?,?,?,?,now(),?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, num); // 직접 계산한 글번호
				pstmt.setString(2, dto.getNickname());
				pstmt.setString(3, dto.getAddr());
				pstmt.setString(4, dto.getSubject());
				pstmt.setString(5, dto.getContent());
				
				pstmt.setInt(6, 0); // readcount  모든글의 조회수 0
				pstmt.setInt(7, num); // re_ref   답글 -> 그룹번호   일반글은 글번호와 동일
				pstmt.setInt(8, 0); // re_lev  답글 -> 들여쓰기  일반글은 0
				pstmt.setInt(9, 0); // re_seq  답글 -> 순서      일반글은 0
				
				pstmt.setString(10, dto.getIp());
				pstmt.setString(11, dto.getFile());
				pstmt.setInt(12, 0); // 댓글은 0
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 글쓰기 완료! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// insertBoard(dto);
		
		// getBoardCount();
		public int getBoardCount(){
			int result = 0;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성(select) & pstmt 객체
				sql = "select count(num) from town_board";
				pstmt = con.prepareStatement(sql);
				// 4. sql 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리
				if(rs.next()){
					//result = rs.getInt("count(num)");
					result = rs.getInt(1);
				}
				
				System.out.println(" DAO : 게시판 글개수 "+result+"개");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return result;
		}
		// getBoardCount();
		
		// getBoardList(startRow, pageSize);
		public List<BoardDTO> getBoardList(int startRow,int pageSize){
			List<BoardDTO> boardList = new ArrayList<BoardDTO>();
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체 (num 내림차순 정렬, 페이징 처리)
				//        =>  re_ref 내림차순, re_seq 오름차순   /  limit 시작행-1,개수
				//sql = "select * from itwill_board order by num desc limit ?,?";
				sql = "select * from town_board order by re_ref desc, re_seq asc limit ?,?";
				pstmt = con.prepareStatement(sql);
				
				//???
				pstmt.setInt(1, startRow-1); // 시작행 - 1
				pstmt.setInt(2, pageSize); // 개수
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리	
				while(rs.next()){ // 데이터 있을때 DB정보를 모두 저장
					// 글 1개의 정보 => BoardBean 객체
					BoardDTO dto = new BoardDTO();
					
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setFile(rs.getString("file"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setAddr(rs.getString("addr"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setSubject(rs.getString("subject"));
					dto.setC_count(rs.getInt("c_count"));
					
					// BoardDTO 객체의 정보 => List 한칸에 저장
					
					boardList.add(dto);
				}// while
				
				System.out.println(" DAO : 게시판 글 전체 목록 저장완료! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return boardList;
		}
		// getBoardList(startRow, pageSize);
		
		// getBoardCount(search); - 오버로딩
		public int getBoardCount(String search){
			int searchCnt = 0;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql 작성 & pstmt 객체
				sql = "select count(*) from town_board where subject like ? or content like ? or addr like ?";
				pstmt = con.prepareStatement(sql);
				
				// ??? 처리
				pstmt.setString(1, "%"+search+"%");	// ? => '%검색어%'
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					searchCnt = rs.getInt(1);	// 인덱스
					//searchCnt = rs.getInt("count(*)");	// 컬럼명
					
				}
				System.out.println(" DAO : 검색된 글 개수 "+searchCnt);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return searchCnt;
		}
		// getBoardCount(search); - 오버로딩
		
		// getBoardList(startRow, pageSize, search); - 오버로딩
		public List<BoardDTO> getBoardList(int startRow, int pageSize, String search){
			List<BoardDTO> searchBoardList = new ArrayList<BoardDTO>();
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql 작성 & pstmt 객체
				// 정렬 - re_ref(내림차순), re_seq(오름차순)
				// limit 원하는 만큼만 가져오기
				// 검색어
				sql = "select * from town_board "
						+ "where subject like ? or content like ? or addr like ? "
						+ "order by re_ref desc, re_seq asc limit ?,?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, "%"+search+"%");
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				pstmt.setInt(4, startRow -1);
				pstmt.setInt(5, pageSize);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터 처리
				while(rs.next()){
					// 글 1개의 정보
					BoardDTO dto = new BoardDTO();
					
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setFile(rs.getString("file"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setAddr(rs.getString("addr"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setSubject(rs.getString("subject"));
					dto.setC_count(rs.getInt("c_count"));
					
					// DTO -> arrayList 한칸에 저장
					searchBoardList.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return searchBoardList; 
		}
		// getBoardList(startRow, pageSize, search); - 오버로딩
		
		// updateReadCount(num);
		public void updateReadCount(int num){
			
			try {
				// 1.2 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 생성
				// 기존 조회수 + 1  구문
				sql = "update town_board set readcount = readcount + 1 where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : "+num+"번글 조회수 1증가! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// updateReadCount(num);
		
		// getBoard(num);
		public BoardDTO getBoard(int num){
			BoardDTO dto = null;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "select * from town_board where num = ?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터 처리
				if(rs.next()){
					dto = new BoardDTO();
					
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setFile(rs.getString("file"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setAddr(rs.getString("addr"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setSubject(rs.getString("subject"));
					dto.setC_count(rs.getInt("c_count"));
				}
				System.out.println(" DAO : 게시판 글 1개 저장완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			   closeDB();	
			}			
			
			return dto;
		}
		// getBoard(num);
		
		// updateBoard(dto);
		public void updateBoard(BoardDTO dto){
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "update town_board set subject=?,content=? where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setInt(3, dto.getNum());
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 정보 수정완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// updateBoard(dto);
		
		// deleteBoard(num);
		public void deleteBoard(int num){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "delete from town_board where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 게시글 삭제 완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// deleteBoard(num);
		
		// reInsertBoard(dto);
		public void reInsertBoard(BoardDTO dto){
			int num = 0;
			
			try {
				// 답글 번호 계산-----------------------------------------------------------
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql작성 & pstmt 생성
				sql ="select max(num) from town_board";
				pstmt = con.prepareStatement(sql);
				
				// 4. sql실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					num = rs.getInt(1) + 1;
					
				}
				
				System.out.println(" DAO : 답글의 번호 "+num);
				
				// 답글 순서 재배치 (기존의 답글)----------------------------------------------
				// 3. sql구문 & pstmt 객체
				// 답글중에서 seq값이 동일한 값이 있을 때 1증가
				// re_ref(같은그룹) , re_seq(순서) 기존(부모글)의 값보다 클 때
				sql="update town_board set re_seq = re_seq + 1 where re_ref=? and re_seq>?";
				pstmt = con.prepareStatement(sql);
				
				// ?????
				pstmt.setInt(1, dto.getRe_ref());
				pstmt.setInt(2, dto.getRe_seq());
				
				// 4. sql 실행
				int check = pstmt.executeUpdate();
				
				if(check > 0)
					System.out.println(" DAO : 답글 순서 재배치 완료! ");
				
				// 답글 저장 (ref-부모글의 값, lev-부모+1, seq-부모+1)-------------------------
				sql ="insert into town_board(num,nickname,addr,subject,content,"
						+ "readcount,re_ref,re_lev,re_seq,date,ip,file,c_count) "
						+ "values(?,?,?,?,?,?,?,?,?,now(),?,?,?)";
				pstmt = con.prepareStatement(sql);
				
				// ????
				pstmt.setInt(1, num);
				pstmt.setString(2, dto.getNickname());
				pstmt.setString(3, dto.getAddr());
				pstmt.setString(4, dto.getSubject());
				pstmt.setString(5, dto.getContent());
				pstmt.setInt(6, 0);	// 조회수 0
				pstmt.setInt(7, dto.getRe_ref());	// ref - 부모글의 번호
				pstmt.setInt(8, dto.getRe_lev()+1);	// lev - 부모글 + 1
				pstmt.setInt(9, dto.getRe_seq()+1);	// seq - 부모글 + 1
				pstmt.setString(10, dto.getIp());
				pstmt.setString(11, dto.getFile());
				pstmt.setInt(12, dto.getC_count());
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// reInsertBoard(dto);
		
		// getUserBoardCount(nickname);
		public int getUserBoardCount(String nickname){
			int result = 0;
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql 작성 & pstmt 객체
				sql = "select count(*) from town_board where nickname=?";
				pstmt = con.prepareStatement(sql);
				
				// ??? 처리
				pstmt.setString(1, nickname);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터처리
				if(rs.next()){
					result = rs.getInt(1);	// 인덱스
				}
				
				System.out.println(" DAO : 검색된 글 개수 "+result);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return result;
		}
		// getUserBoardCount(nickname);
		
		// getUserBoardList(startRow, pageSize, nickname);
		public List<BoardDTO> getUserBoardList(int startRow, int pageSize, String nickname){
			List<BoardDTO> userBoardList = new ArrayList<BoardDTO>();
			
			try {
				// 1.2. 디비연결
				con = getCon();
				
				// 3. sql 작성 & pstmt 객체
				// 정렬 - re_ref(내림차순), re_seq(오름차순)
				// limit 원하는 만큼만 가져오기
				// 검색어
				sql = "select * from town_board "
						+ "where nickname=? "
						+ "order by re_ref desc, re_seq asc limit ?,?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, nickname);
				pstmt.setInt(2, startRow -1);
				pstmt.setInt(3, pageSize);
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터 처리
				while(rs.next()){
					// 글 1개의 정보
					BoardDTO dto = new BoardDTO();
					
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setFile(rs.getString("file"));
					dto.setIp(rs.getString("ip"));
					dto.setNickname(rs.getString("nickname"));
					dto.setNum(rs.getInt("num"));
					dto.setAddr(rs.getString("addr"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setSubject(rs.getString("subject"));
					dto.setC_count(rs.getInt("c_count"));
					
					// DTO -> arrayList 한칸에 저장
					userBoardList.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return userBoardList; 
		}
		// getUserBoardList(startRow, pageSize, nickname);
		
		// updateCommentCount(Integer.parseInt(request.getParameter("b_num")));
		public void updateCommentCount(int num){
				
			try {
				// 1.2 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 생성
				// 기존 조회수 + 1  구문
				sql = "update town_board set c_count = c_count + 1 where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : "+num+"번글 댓글수 1증가! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// updateCommentCount(Integer.parseInt(request.getParameter("b_num")));
		
		// decreaseCommentCount(b_num);
		public void decreaseCommentCount(int num){
			
			try {
				// 1.2 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 생성
				// 기존 조회수 + 1  구문
				sql = "update town_board set c_count = c_count - 1 where num=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setInt(1, num);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : "+num+"번글 댓글수 1감소! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
		}
		// decreaseCommentCount(b_num);
		
		// deleteBoard(nickname); - 오버로딩
		public void deleteBoard(String nickname){
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체
				sql = "delete from town_board where nickname=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, nickname);
				
				// 4. sql 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 게시글 삭제 완료 ");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		// deleteBoard(nickname); - 오버로딩
		
		
		
		
}
