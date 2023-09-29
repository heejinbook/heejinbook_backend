package com.book.heejinbook.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "책 리스트 조회 DTO")
public class BookListRequest {

    @ApiModelProperty(value = "검색어", example = "마블")
    private String searchKeyword;
    @ApiModelProperty(value = "장르", required = true, example = "1~10")
    private Long category;

}
