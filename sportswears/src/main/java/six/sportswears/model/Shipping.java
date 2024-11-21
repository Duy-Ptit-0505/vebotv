package six.sportswears.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shipping")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_status")
    private Integer shippingStatus;


    @Column(name = "shipping_fee")
    private Double shippingFee;


    @OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH})
    private List<Orders> ordersArrayList= new ArrayList<>();
}

