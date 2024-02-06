package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@RestController //controller, responsebody 어노테이션을 포함하고 있음
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@PostMapping
	public ResponseEntity<Integer> register(@RequestBody BoardDTO dto){
		int no = service.register(dto);
		
		return new ResponseEntity<>(no, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<BoardDTO>> getList(){
		List<BoardDTO> list = service.getList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{no}")//파라미터 위치를 지정해주는 것
	public ResponseEntity<BoardDTO> read(@PathVariable("no")int no){
		BoardDTO dto = service.read(no);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity modify(@RequestBody BoardDTO dto) {
		service.modify(dto);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{no}")
	public ResponseEntity remove(@PathVariable("no")int no) {
		service.remove(no);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
			

}
