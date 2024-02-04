package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExcuteCommand {
	private final BDao jdbcDao;
	public BExcuteCommand(BDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public void bListCmd(Model model) {
		// Dao 연결
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BListCommand boardList.size() --> " + boardDtoList.size());
		model.addAttribute("boardList", boardDtoList);
	}

	public void bWriteCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bName  ,bTitle  , bContent  추출
//		  3) dao  instance 선언
//		  4) write method 이용하여 저장(bName, bTitle, bContent)
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
		
	}

	// HW2
	public void bContentCmd(Model model) {
		// 1.  model Map으로 전환 
		Map<String, Object> map = model.asMap();

		// 2.  request -> bId Get
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");

		System.out.println("bContentCmd bId->"+bId);
		
		// 3.  HW3
		BDto board = jdbcDao.contentView(bId);
		System.out.println("bContentCmd board.getbName->"+board.getbName());
		model.addAttribute("mvc_board", board);

	}
	
	public void bModifyCmd(Model model) {
		// 1. model Map 선언
		Map<String, Object> map = model.asMap();
		
		// 2. parameter -> bId, bName, bTitle, bContent
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.modify(bId, bName, bTitle, bContent);
	}

	public void bReply_viewCmd(Model model) {
		// 1. model 이용, map 선언
		// 2. request 이용 -> bId 추출
		// 3. 
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		
		BDto bDto = jdbcDao.reply_view(bId);
		
		System.out.println("bReply_viewCmd board.getbName->"+bDto.getbName());
		model.addAttribute("reply_view", bDto);	
		
		
	}
	
	public void bReplyCmd(Model model) {
		// 1. model Map 선언
		Map<String, Object> map = model.asMap();
		
		// 2. request -> bId, bName, bTitle, bContent, bGroup, bStep, bIndent 추출 
		// 3. reply method 이용하여 댓글저장 
		// - dao.reply( bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		// [1] bId SEQUENCE = bGroup
		// [2] bName, bTitle, bContent -> request value
		// [3] 홍해 기적
		// [4] bStep / bIndent +1 
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		
		int bIntGroup = Integer.parseInt(request.getParameter("bGroup"));
		System.out.println("BReplyCommand bIntGroup -> " + bIntGroup);
		
		jdbcDao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		
	}

	public void bDeleteCmd(Model model) {
		// 1. Model 이용, map 선언
		// 2. request 이용 -> bId 추출
		// 3. delete method 이용하여 삭제
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");		
		String bId = request.getParameter("bId");	
		
		jdbcDao.delete(bId);
		
	}
}
