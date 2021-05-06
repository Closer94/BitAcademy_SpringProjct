package com.saltlux.mydictionary.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saltlux.mydictionary.dto.JsonResult;
import com.saltlux.mydictionary.service.BoardService;
import com.saltlux.mydictionary.vo.ReplyVo;

@RestController

@RequestMapping(value = "/restBoard")

public class RestBoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject

	private BoardService boardService;

	@RequestMapping(value = "/getReplyList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getReplyList(@RequestParam("bid") int bid) throws Exception {
		List<ReplyVo> list = boardService.getReplyList(bid);
		return JsonResult.success(list);
	}

}