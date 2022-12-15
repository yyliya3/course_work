package com.websurvey.websurvey_basko.model_basko;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Admins")
@Table(name = "admins")
public class model_basko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Getter @Setter private UserModel_Basko user;
}
