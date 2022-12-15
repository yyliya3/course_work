package com.websurvey.websurvey_basko.model_basko;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Surveys")
@Table(name = "surveys")
public class SurveyModel_Basko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String title;
    @Getter @Setter private String description;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "owner_id")
    @Getter @Setter private UserModel_Basko owner;
}
