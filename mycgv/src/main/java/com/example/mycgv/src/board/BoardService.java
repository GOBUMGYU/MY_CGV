package com.example.mycgv.src.board;

import com.example.mycgv.src.board.model.PostBoardReq;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {

    int createBoard(PostBoardReq postBoardReq);
    int getTotalCount();
    List<PostBoardReq> boardList(int startCount, int endCount);
    PostBoardReq Content(Long bid);
    void getUpdateHits(Long bid);
    int update(PostBoardReq postBoardReq);
    int delete(Long bid);
}
