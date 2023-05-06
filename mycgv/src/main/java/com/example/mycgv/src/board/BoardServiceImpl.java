package com.example.mycgv.src.board;

import com.example.mycgv.src.board.model.PostBoardReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void getUpdateHits(Long bid) {
        boardRepository.updateHits(bid);
    }

    @Override
    public int update(PostBoardReq postBoardReq) {
        return boardRepository.update(postBoardReq);
    }

    @Override
    public int delete(Long bid) {
        return boardRepository.delete(bid);
    }


    @Override
    public PostBoardReq Content(Long bid) {
        return boardRepository.content(bid);
    }

    @Override
    public int createBoard(PostBoardReq postBoardReq) {
        return boardRepository.createBoard(postBoardReq);
    }

    @Override
    public int getTotalCount() {
        return boardRepository.totalCount();
    }


    @Override
    public List<PostBoardReq> boardList(int startCount, int endCount) {
        List<PostBoardReq> list = boardRepository.boardList(startCount, endCount);

        return list;
    }
}
