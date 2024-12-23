package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pjt.model.dao.BoardDao;
import com.ssafy.pjt.model.dto.Board;
import com.ssafy.pjt.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

//	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	// 게시글 전체 조회 (검색)
	@Override
	public List<Board> searchBoard(SearchCondition searchCondition) {
		return boardDao.search(searchCondition);
	}

	// 게시글 상세 조회
	@Override
	public Board readBoard(int id) {
		boardDao.updateViewCnt(id);
		return boardDao.selectOne(id);
	}

	// 게시글 작성
	@Transactional
	@Override
	public boolean writeBoard(Board board) {
		return boardDao.insertBoard(board) == 1;
	}

	// 게시글 삭제
	@Transactional
	@Override
	public boolean removeBoard(int id) {
		return boardDao.deleteBoard(id) == 1;
	}

	// 게시글 수정
	@Transactional
	@Override
	public boolean modifyBoard(Board board) {
		return boardDao.updateBoard(board) == 1;
	}
}
