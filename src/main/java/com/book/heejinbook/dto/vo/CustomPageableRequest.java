package com.book.heejinbook.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomPageableRequest {

    @ApiModelProperty(value = "현재 페이지 (0부터 시작)", required = true, example = "0")
    private Integer page;
    @ApiModelProperty(value = "조회할 개수", required = true, example = "10")
    private Integer size;
    @ApiModelProperty(value = "책일때 가나다: title,asc 리뷰개수순: r.id,desc 리뷰일 때 id,desc", example = "lexically")
    private String sort;
}
