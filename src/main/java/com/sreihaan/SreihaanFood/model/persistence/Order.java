//package com.sreihaan.SreihaanFood.model.persistence;
//
//
//import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.domain.Persistable;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Setter
//@Getter
//@Entity
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    @ManyToOne()
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;
//
////    private Map<Long, Long> contents;
//
//    private BigDecimal total;
//
//    private Status status;
//
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_time")
//    private Date createdTime;
//
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "last_modified_time")
//    private Date lastModifiedTime;
//
//}
