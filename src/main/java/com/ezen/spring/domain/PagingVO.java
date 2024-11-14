package com.ezen.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Setter
@Getter
public class PagingVO {
    private int pageNo;
    private int qty;

    // 검색을 위한 멤버변수
    private String type;
    private String keyword;

    // 기본생성자는 커스텀이 필요하므로
    // @NoArgsConstructor는 붙이지 않았음
    public PagingVO(){
        this.pageNo = 1;
        this.qty = 10;
    }

    public PagingVO(int pageNo, int qty){
        this.pageNo = pageNo;
        this.qty = qty;
    }

    // limit 번지,개수에서 번지를 구해줄 메서드
    public int getStartIndex(){
        return (this.pageNo-1)*qty;
    }

    public String[] getTypeToArray(){
        return this.type == null? new String[]{} : this.type.split("");
    }
}
