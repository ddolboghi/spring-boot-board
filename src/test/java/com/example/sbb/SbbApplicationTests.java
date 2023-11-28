package com.example.sbb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.sbb.answer.Answer;
import com.example.sbb.answer.AnswerRepository;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    //hibernate 테이블 생성 규칙이 update여서 서버 실행할때마다 생성됨
//	@Test
//	void saveQuestionsTest() {
//		Question q1 = new Question();
//		q1.setSubject("what is the jpa?");
//		q1.setContent("I know that a little.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//
//		Question q2 = new Question();
//		q2.setSubject("what is the inversion of dependency?");
//		q2.setContent("I wannna konw that.");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);
//	}

    @Test
    void findAllQuestionsTest() {
        List<Question> all = this.questionRepository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void findQuestionTest() {
        Optional<Question> question = this.questionRepository.findById(1);
        question.ifPresent(value -> assertThat(value.getSubject())
                .isEqualTo("what is the jpa?"));
    }

    @Test
    void findBySubjectTest() {
        Question question = this.questionRepository.findBySubject("what is the jpa?");
        assertEquals(1, question.getId());
    }

    @Test
    void findBySubjectAndContentTest() {
        Question question = this.questionRepository.findBySubjectAndContent("what is the inversion of dependency?",
                "I wannna konw that.");
        assertEquals(2, question.getId());
    }

    @Test
    void findBySubjectLikeTest() {
        List<Question> questions = this.questionRepository.findBySubjectLike("%jpa%");
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("what is the jpa?");
    }

    @Test
    void updateTest() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        q.setSubject("수정된 제목");
        questionRepository.save(q);

        Optional<Question> updatedOq = this.questionRepository.findById(1);
        assertTrue(updatedOq.isPresent());
        Question updatedQ = updatedOq.get();
        assertThat(updatedQ.getSubject()).isEqualTo("수정된 제목");
    }

    @Test
    void deleteTest() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        this.questionRepository.delete(q);

        assertThat(this.questionRepository.count()).isEqualTo(1);
    }

//	@Test
//	void createAnswerTest() {
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//
//		Answer a = new Answer();
//		a.setContent("read book.");
//		a.setQuestion(q);
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);
//	}

    @Test
    void findAnswerTest() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();

        assertThat(a.getQuestion().getId()).isEqualTo(2);
    }

    @Transactional
    @Test
    void answersFromQuestionTest() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answers = q.getAnswerList();

        assertThat(answers.size()).isEqualTo(1);
    }
}
