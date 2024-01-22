package rest.arduino.smartalarm.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,
            unique = true)
    private String nickname;

    @Column(nullable = false,
            unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "roles")
    private String roles;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private Set<SmartAlarmDevice> smartAlarms = new HashSet<>();

}
