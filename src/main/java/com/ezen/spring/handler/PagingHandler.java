package com.ezen.spring.handler;

import com.ezen.spring.domain.PagingVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingHandler {
    private int startPage;
    private int endPage;
    private int realEndPage;
    private boolean prev, next;
    private int qty = 5;

    private int totalCount;
    private PagingVO pgvo;

    public PagingHandler(PagingVO pgvo, int totalCount){
        this.pgvo = pgvo;
        this.totalCount = totalCount;

        this.endPage = (int)Math.ceil((double)pgvo.getPageNo()/qty)*qty;
        this.startPage = endPage - (qty-1);

        this.realEndPage = (int)Math.ceil((double)totalCount/pgvo.getQty());

        if(realEndPage < endPage){
          this.endPage = realEndPage;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEndPage;
    }

}
