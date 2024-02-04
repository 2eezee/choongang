package com.oracle.oBootBoard.dao;

import java.util.ArrayList;

import com.oracle.oBootBoard.dto.BDto;

public interface BDao {
	public ArrayList<BDto> boardList();

	public void write(String bName, String bTitle, String bContent);

	public BDto contentView(String bId);

	public void modify(String bId, String bName, String bTitle, String bContent);

	public BDto reply_view(String bId);

	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent);

	public void delete(String bId);

}