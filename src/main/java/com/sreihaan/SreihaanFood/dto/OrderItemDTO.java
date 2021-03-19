package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderItemDTO {

    private String itemName;

    private long quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    public static OrderItemDTO convertEntityToOrderItemsDTO(OrderItem orderItem)
    {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        BeanUtils.copyProperties(orderItem, orderItemDTO);
        return orderItemDTO;
    }

    public static List<OrderItemDTO> convertEntityListToDTOList(List<OrderItem> orderItems)
    {
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem : orderItems)
        {
            orderItemDTOS.add(convertEntityToOrderItemsDTO(orderItem));
        }
        return orderItemDTOS;
    }


}
