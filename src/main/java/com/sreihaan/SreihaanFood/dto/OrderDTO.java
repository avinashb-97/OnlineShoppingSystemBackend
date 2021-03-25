package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private long id;

    private String orderId;

    private List<OrderItemDTO> orderItems;

    private BigDecimal total;

    private Status status;

    private AddressDTO address;

    private Date createdTime;

    private Date lastModifiedTime;

    private String orderedBy;

    public static OrderDTO convertEntityToOrderDTO(Order order)
    {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        List<OrderItemDTO> orderItemDTOS = OrderItemDTO.convertEntityListToDTOList(order.getOrderItems());
        orderDTO.setOrderItems(orderItemDTOS);
        orderDTO.setAddress(AddressDTO.convertEntityToAddressDTO(order.getAddress()));
        orderDTO.setOrderedBy(order.getUser().getEmail());
        return orderDTO;
    }

    public static Page<OrderDTO> convertEntityListToPage(Page<Order> orders)
    {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order : orders)
        {
            orderDTOList.add(convertEntityToOrderDTO(order));
        }
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, orders.getPageable(), orders.getTotalElements());
        return orderDTOPage;
    }

}
