package com.book.heejinbook.dto.user.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignupRequest {

    @NotBlank
    @ApiModelProperty(value = "이메일 입력 필드", dataType = "String")
    private String email;
    @NotBlank
    @ApiModelProperty(value = "패스워드 입력 필드", dataType = "String")
    private String password;
    @NotBlank
    @ApiModelProperty(value = "패스워드 확인 입력 필드", dataType = "String")
    private String passwordCheck;
    @NotBlank
    @ApiModelProperty(value = "닉네임 입력 필드", dataType = "String")
    private String nickname;

    @ApiModelProperty(value = "이미지 파일 입력 필드", dataType = "MultipartFile")
    private MultipartFile profileFile;

}
