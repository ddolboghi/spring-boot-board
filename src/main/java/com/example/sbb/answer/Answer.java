package com.example.sbb.answer;

import com.example.sbb.question.Question;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //프로젝트의 원활한 진행을 위해 setter 추가
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne //하나의 질문에 여러 개의 답변 가능 
    private Question question;

    @ManyToOne
    private SiteUser author;
}
