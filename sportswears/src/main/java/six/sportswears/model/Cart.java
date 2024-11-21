package six.sportswears.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<CartItem> cartItemList = new ArrayList<>();
    public Double getCartAmount() {
        Double amount = 0.0;
        for(CartItem x : cartItemList) {
            amount += x.getQuantity() * x.getSportswear().getPrice();
        }
        return amount;
    }
    public Long getTotalQuantity() {
        Long total = 0L;
        for(CartItem x : cartItemList) {
            total += x.getQuantity();
        }
        return total;
    }
}

