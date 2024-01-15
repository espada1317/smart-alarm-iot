package rest.arduino.smartalarm.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "alarm_schedule")
public class RecurringAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_monday", nullable = false)
    private boolean isMonday;

    @Column(name = "is_tuesday", nullable = false)
    private boolean isTuesday;

    @Column(name = "is_wednesday", nullable = false)
    private boolean isWednesday;

    @Column(name = "is_thursday", nullable = false)
    private boolean isThursday;

    @Column(name = "is_friday", nullable = false)
    private boolean isFriday;

    @Column(name = "is_saturday", nullable = false)
    private boolean isSaturday;

    @Column(name = "is_sunday", nullable = false)
    private boolean isSunday;

}
