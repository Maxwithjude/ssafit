package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Board;
import com.ssafy.pjt.model.dto.SearchCondition;
@Service
public interface BoardService {
	// 게시글 전체 조회 및 삭제
	public List<Board> searchBoard(SearchCondition searchCondition);

	// 게시글 상세조회
	public Board readBoard(int id);

	// 게시글 작성
	public boolean writeBoard(Board board);

	/// 게시글 삭제
	public boolean removeBoard(int id);

	// 게시글 수정
	public boolean modifyBoard(Board board);
}
