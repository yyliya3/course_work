package com.websurvey.websurvey_basko.model_basko;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Users")
@Table(name = "users")
public class UserModel_Basko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String login;
    @Getter @Setter private String passwordHash;
    @Getter @Setter private int role;
    @Getter @Setter private String codePhraseHash;
    @Nullable
    @Getter @Setter private String sessionIdHash;
    @Getter @Setter private Boolean banned = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    Set<SurveyModel_Basko> completedSurveys;
}
