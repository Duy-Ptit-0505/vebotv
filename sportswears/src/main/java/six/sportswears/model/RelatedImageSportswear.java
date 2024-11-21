package six.sportswears.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "related_image_sportswear")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatedImageSportswear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "related_image", columnDefinition = "MEDIUMBLOB")
    private String related_image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sportswear_id")
    private Sportswear sportswear;
}
