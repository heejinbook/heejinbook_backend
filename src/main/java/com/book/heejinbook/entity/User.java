package com.book.heejinbook.entity;

import com.book.heejinbook.dto.user.request.SignupRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    @NotNull
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Size(max = 45)
    @Column(name = "nickname", length = 45)
    private String nickname;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    public static User from(SignupRequest signupRequest, String passwordEnc) {
        return User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEnc)
                .nickname(signupRequest.getNickname())
                .isDeleted(false)
                .build();
    }

}