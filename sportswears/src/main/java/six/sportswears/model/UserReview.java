package six.sportswears.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating_value")
    private Integer rating_value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "time_created")
    private Date timeCreated;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sportswear_id")
    Sportswear sportswear;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    User user;
}
