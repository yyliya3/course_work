package com.websurvey.websurvey_basko.model_basko;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Answers")
@Table(name = "answers")
public class AnswerModel_Basko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String answer;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "owner_id")
    @Getter @Setter private UserModel_Basko owner;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id")
    @Getter @Setter private QuestionModel_Basko question;
}
