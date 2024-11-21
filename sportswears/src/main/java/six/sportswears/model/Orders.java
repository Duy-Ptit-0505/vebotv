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
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
    public class Orders {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "order_total")
        private Double order_total;

        @Column(name = "order_status")
        private Integer orderStatus;

        @Column(name = "shipping_address")
        private String shipping_address;

        @Column(name = "order_date")
        private Date order_date;

        @Column(name = "contact")
        private String contact;

        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
        @JoinColumn(name = "shipping_id")
        private Shipping shipping;

        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
        @JoinColumn(name = "payment_id")
        private Payment payment;

        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
        @JoinColumn(name = "user_id")
        private User user;

        @OneToMany(mappedBy = "orders", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
        private List<OrderItem> orderItemList = new ArrayList<>();


    }

