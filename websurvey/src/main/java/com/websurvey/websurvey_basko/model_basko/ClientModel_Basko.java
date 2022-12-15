package com.websurvey.websurvey_basko.model_basko;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Clients")
@Table(name = "clients")
public class ClientModel_Basko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String surname;
    @Getter @Setter private String phone;
    @Getter @Setter private String email;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Getter @Setter private UserModel_Basko user;
}
