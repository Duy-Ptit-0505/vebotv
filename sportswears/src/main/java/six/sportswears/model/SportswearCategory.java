package six.sportswears.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sportswear_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SportswearCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    @OneToMany(mappedBy = "sportswearCategory", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Sportswear> sportswearList= new ArrayList<>();
}
