package com.book.heejinbook.service;

import com.book.heejinbook.dto.comment.response.CommentListResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.dto.user.request.KakaoLoginRequest;
import com.book.heejinbook.dto.user.request.LoginRequest;
import com.book.heejinbook.dto.user.response.KakaoTokenResponse;
import com.book.heejinbook.dto.user.response.KakaoUserInfoResponse;
import com.book.heejinbook.dto.user.response.LoginResponse;
import com.book.heejinbook.dto.user.response.MyInfoResponse;
import com.book.heejinbook.entity.Comment;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.enums.FilePath;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.FileErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.CommentRepository;
import com.book.heejinbook.repository.ReviewRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.dto.user.request.SignupRequest;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.security.AuthHolder;
import com.book.heejinbook.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${kakao.kakaoClientId}")
    private String kakaoClientId;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Service awsS3Service;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public String signup(SignupRequest signupRequest) {
        checkDuplicateEmail(signupRequest.getEmail());
        checkConfirmPassword(signupRequest.getPassword(), signupRequest.getPasswordCheck());
        User user = User.from(signupRequest, passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
        user.setProfileUrl(uploadImageFile(signupRequest.getProfileFile(), user));
        return user.getEmail();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = validUserByEmail(loginRequest.getEmail());
        checkPassword(loginRequest.getPassword(), user.getPassword());
        String accessToken = TokenProvider.createToken(user);

        return LoginResponse.from(user, accessToken);
    }

    public LoginResponse kakaoLogin(KakaoLoginRequest kakaoLoginRequest) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String redirectUrl = "http://localhost:8080/api/user/kakao/callback";

        KakaoTokenResponse kakaoTokenResponse = restTemplate.postForObject("https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=" + kakaoClientId + "&redirect_uri=" + redirectUrl + "&code=" + kakaoLoginRequest.getCode(), null, KakaoTokenResponse.class);
        if (kakaoTokenResponse == null) throw new CustomException(UserErrorCode.FAIL_KAKAO_LOGIN);
        httpHeaders.setBearerAuth(kakaoTokenResponse.getAccess_token());

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, KakaoUserInfoResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            KakaoUserInfoResponse kakaoUserInfoResponse = response.getBody();
            Optional<User> user = userRepository.findByEmail(Objects.requireNonNull(kakaoUserInfoResponse).getKakao_account().getEmail());
            if (user.isEmpty()) {
                String password = passwordEncoder.encode(kakaoUserInfoResponse.getId());
                User saveUser = userRepository.save(User.builder()
                                .email(kakaoUserInfoResponse.getKakao_account().getEmail())
                                .nickname(kakaoUserInfoResponse.getProperties().getNickname())
                                .profileUrl(kakaoUserInfoResponse.getProperties().getProfile_image())
                                .password(password)
                                .isDeleted(false)
                                .build()
                );
                String accessToken = TokenProvider.createToken(saveUser);
                return LoginResponse.from(saveUser, accessToken);
            }
            String accessToken = TokenProvider.createToken(user.get());
            return LoginResponse.from(user.get(), accessToken);
        }
        throw new CustomException(UserErrorCode.FAIL_KAKAO_LOGIN);
    }

    public MyInfoResponse getMyInfo() {
        User user = userRepository.findById(AuthHolder.getUserId()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
        return MyInfoResponse.from(user);
    }

    public List<ReviewSwiperResponse> getMyReviews() {
        User user = userRepository.findById(AuthHolder.getUserId()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
        List<Review> reviews = reviewRepository.findAllByUser(user);
        return reviews.stream().map(ReviewSwiperResponse::from).collect(Collectors.toList());
    }

    public List<CommentListResponse> getMyComments() {
        User user = userRepository.findById(AuthHolder.getUserId()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
        List<Comment> comments = commentRepository.findAllByUser(user);
        return comments.stream().map(CommentListResponse::from).collect(Collectors.toList());
    }


    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(UserErrorCode.DUPLICATE_USER_ID);
        }
    }

    private void checkConfirmPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new CustomException(UserErrorCode.NOT_MATCH_PASSWORD_CONFIRM);
        }
    }

    private void checkPassword(String requestPassword, String password) {
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new CustomException(UserErrorCode.BAD_REQUEST_PASSWORD);
        }
    }

    private String uploadImageFile(MultipartFile multipartFile, User user) {
        String uniqueIdentifier = UUID.randomUUID().toString();
        try {
            if (multipartFile != null) {
                return awsS3Service.uploadImage(multipartFile, FilePath.USER_DIR.getPath() + user.getId() + "/" + uniqueIdentifier);
            }
        }catch (IOException e) {
            throw new CustomException(FileErrorCode.FILE_UPLOAD_FAILED);
        }
        return null;
    }

    private User validUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->new CustomException(UserErrorCode.NOT_FOUND_USER));
    }
}
