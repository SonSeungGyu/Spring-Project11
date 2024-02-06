package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

@Service//서비스 클래스로 지정
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository repository;
	
	//게시물 등록
	@Override
	public int register(BoardDTO dto) {
		Board entity = dtoToEntity(dto);

		repository.save(entity);

		int newNo = entity.getNo();

		System.out.println(entity);

		return newNo;
	}

	//게시물 목록조회
	@Override
	public List<BoardDTO> getList() {
		//데이터베이스에서 목록을 가져옴
		List<Board> result = repository.findAll();
		//list 생성
		List<BoardDTO> list = new ArrayList<>();
		
		list = result.stream()//리스트에 스트림 생성
				.map(entity -> entityToDto(entity))//중간연산으로 엔티티를 dto로 변환
				.collect(Collectors.toList());//최종연산으로 결과를 리스트로 변환
		return list;//dto리스트 반환
	}
	
	//게시물 상세조회
	@Override
	public BoardDTO read(int no) {
		Optional<Board> result = repository.findById(no);
		if(result.isPresent()) {
			Board board = result.get();
			BoardDTO boardDTO = entityToDto(board);
			return boardDTO;
		}
		return null;
	}
	
	//게시물 수정
	@Override
	public void modify(BoardDTO dto) {
		//파라미터로 받은 dto는 DTO 타입이므로 프라이머리키인 no로 접근후
		//이 no는 레파지토리의 엔티티 no와 같음을 이용하여 엔티티 타입의 변수에 할당.
		Optional<Board> result = repository.findById(dto.getNo());
		
		if(result.isPresent()) {
			Board entity = result.get();
			
			//기존 엔티티에서 제목과 내용만 변경
			entity.setTitle(dto.getTitle());
			entity.setContent(dto.getContent());
			
			//다시 저장
			repository.save(entity);
		}
	}
	
	//게시물 삭제
	@Override
	public int remove(int no) {
		
		Optional<Board> result = repository.findById(no);
		if(result.isPresent()) {
			repository.deleteById(no);
			return 1;//성공
		}else {
			return 0;//실패
		}
	}

}
