package com.town.board.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class BoardFileUploadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardFileUploadAction_execute 호출 ");

		// 1) 파일업로드
		// - 가상의 업로드 폴더 설정 upload폴더 생성
		ServletContext ctx = request.getServletContext();
		String realPath = ctx.getRealPath("/upload");
		// System.out.println(" M : "+realPath);

		// - 업로드 파일의 크기 설정(제한)
		int maxSize = 5 * 1024 * 1024; // 5MB

		// - MultipartRequest 객체 생성(업로드)
		MultipartRequest multi 
				= new MultipartRequest(
							request,
							realPath,
							maxSize,
							"UTF-8",
							new DefaultFileRenamePolicy()
						);

		System.out.println(" M : 파일 업로드 완료! ");

		// 2) DB저장
		// - 전달정보 저장 (DTO)
		BoardDTO dto = new BoardDTO();
		// nickname, subject, content, file, ip, +addr
		dto.setNickname(multi.getParameter("nickname"));
		dto.setSubject(multi.getParameter("subject"));
		dto.setContent(multi.getParameter("content"));

		// 파일
		int count = Integer.parseInt(multi.getParameter("count"));

		StringBuffer file = new StringBuffer();

		for (int i = 0; i < count; i++) {
			file.append(multi.getFilesystemName("file" + i));
			if (count - i != 1) {
				file.append(",");
			}
		}

		dto.setFile(file.toString());
		
		// 썸네일(여러이미지중 첫번째이미지를 썸네일로)
		ParameterBlock pb = new ParameterBlock();
		pb.add(realPath+"/"+multi.getFilesystemName("file0"));
		RenderedOp rOp = JAI.create("fileload", pb);
		
		BufferedImage bi = rOp.getAsBufferedImage();
		BufferedImage thumb = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = thumb.createGraphics();
		g.drawImage(bi, 0, 0, 100, 100, null);
		File sFile = new File(realPath+"/sm_"+multi.getFilesystemName("file0"));
		ImageIO.write(thumb, "jpg", sFile);
		
		// ip
		dto.setIp(request.getRemoteAddr());
		
		// addr
		String id = multi.getParameter("id");

		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = new MemberDTO();
		mdto = mdao.getMember(id);

		String addr = null;

		if (mdto.getAddr() != null) {
			String[] addrArr = mdto.getAddr().split(" ");
			addr = addrArr[0] + " " + addrArr[1] + " " + addrArr[2];
		}
		dto.setAddr(addr);


		// DAO 객체 생성 - 업로드
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(dto);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);

		return forward;
	}

}
