package com.example.sbb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void saveQuestionsTest() {
		Question q1 = new Question();
		q1.setSubject("what is the jpa?");
		q1.setContent("I know that a little.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("what is the inversion of dependency?");
		q2.setContent("I wannna konw that.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

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

	//findBySubject부터
}
