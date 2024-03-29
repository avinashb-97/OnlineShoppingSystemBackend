package com.sreihaan.SreihaanFood.model.persistence;

import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String orderId;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    private Date deliveryTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isOrderedByAdmin;

    private String orderedBy;

}
