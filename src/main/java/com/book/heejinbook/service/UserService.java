package com.book.heejinbook.service;

import com.book.heejinbook.dto.user.request.LoginRequest;
import com.book.heejinbook.dto.user.response.LoginResponse;
import com.book.heejinbook.dto.user.response.MyInfoResponse;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.enums.FilePath;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.FileErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.dto.user.request.SignupRequest;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.security.AuthHolder;
import com.book.heejinbook.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Service awsS3Service;



    @Transactional
    public String  signup(SignupRequest signupRequest) {
        checkDuplicateEmail(signupRequest.getEmail());
        checkConfirmPassword(signupRequest.getPassword(), signupRequest.getPasswordCheck());
        User user = User.from(signupRequest, passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
        user.setProfileUrl(uploadImageFile(signupRequest.getProfileFile(), user));
        return user.getEmail();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->new CustomException(UserErrorCode.NOT_FOUND_USER));
        checkPassword(loginRequest.getPassword(), user.getPassword());
        String accessToken = TokenProvider.createToken(user);

        return LoginResponse.from(user, accessToken);
    }

    public MyInfoResponse getMyInfo() {
        User user = userRepository.findById(AuthHolder.getUserId()).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
        return MyInfoResponse.from(user);
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
}
