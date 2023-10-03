package com.book.heejinbook.entity;

import com.book.heejinbook.dto.book.response.KakaoBookResponse;
import com.book.heejinbook.utils.DateUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.time.Instant;
import java.util.Random;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Lob
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 45)
    @NotNull
    @Column(name = "author", nullable = false, length = 45)
    private String author;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Size(max = 45)
    @Column(name = "isbn")
    private String isbn;

    @Lob
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "release_date")
    private Instant releaseDate;

    public static Book from(KakaoBookResponse.Document document) {

        Random random = new Random();
        long randomCategoryId = (long) (random.nextDouble() * 10L) + 1L; // 1부터 10까지의 랜덤 long 숫자 생성
        Category category = new Category(randomCategoryId, com.book.heejinbook.enums.Category.getCategoryById(randomCategoryId));

        return Book.builder()
                .title(document.getTitle())
                .author(document.getAuthors().get(0))
                .category(category)
                .description(document.getContents())
                .thumbnailUrl(document.getThumbnail())
                .isbn(document.getIsbn())
                .publisher(document.getPublisher())
                .releaseDate(DateUtils.convertToInstant(document.getDatetime()))
                .build();
    }

}