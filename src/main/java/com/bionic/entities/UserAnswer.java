package com.bionic.entities;

import javax.persistence.*;
/**
 * Created by rondo104 on 25.11.2015.
 */
@Entity
@Table(catalog = "quizzes")
public class UserAnswer {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        //on_Production - create DB (Problem with key)
        //@Column(name = "resultId", nullable = false, unique = true)
        @Column(name = "resultId", nullable = false)
        private long  resultId;
        @Column(name = "userAnswer", nullable = false)
        private String userAnswer;
        @Column(name = "questionId", nullable = false)
        private long questionId;


        public UserAnswer() {
        }

        public UserAnswer(long resultId, String userAnswer, long questionId) {
            this.resultId = resultId;
            this.userAnswer = userAnswer;
            this.questionId = questionId;
        }

        public long getId() {
            return id;
        }

        public long getResultId() {
            return resultId;
        }

        public void setResultId(long resultId) {
            this.resultId = resultId;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }
}
