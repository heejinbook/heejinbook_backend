package com.book.heejinbook.entity;

import com.book.heejinbook.dto.review.request.RegisterReviewRequest;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @JoinColumn(name = "book_id", nullable = false)
    @ManyToOne
    private Book book;

    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Size(max = 45)
    @NotNull
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotNull
    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Lob
    @Column
    private String phrase;

    @Column(name = "rating")
    private Integer rating;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;



    public Integer getLikeCount() {
        return likes.size();
    }

    public Long getCommentCount() {
        return comments.stream().filter(comment -> comment.getIsDeleted() == false).count();}

    public static Review from(RegisterReviewRequest registerReviewRequest, Book book, User user) {
        return Review.builder()
                .book(book)
                .user(user)
                .title(registerReviewRequest.getTitle())
                .contents(registerReviewRequest.getContents())
                .phrase(registerReviewRequest.getPhrase())
                .rating(registerReviewRequest.getRating())
                .isDeleted(false)
                .build();
    }

}