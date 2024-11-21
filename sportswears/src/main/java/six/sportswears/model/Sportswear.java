package six.sportswears.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sportswear")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sportswear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "main_image", columnDefinition = "MEDIUMBLOB")
    private String main_image;


    @Column(name = "status")
    private Integer status;



    @OneToMany(mappedBy = "sportswear", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<CartItem> cartItemList= new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "sportswear_category_id")
    SportswearCategory sportswearCategory;

    @OneToMany(mappedBy = "sportswear", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    private List<RelatedImageSportswear> relatedImageSportswearList= new ArrayList<>();

    @OneToMany(mappedBy = "sportswear", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    private List<OrderItem> orderItemList= new ArrayList<>();

    @OneToMany(mappedBy = "sportswear", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH})
    private List<UserReview> userReviewList= new ArrayList<>();
}

