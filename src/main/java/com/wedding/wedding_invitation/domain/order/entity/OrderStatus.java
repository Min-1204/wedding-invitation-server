package com.wedding.wedding_invitation.domain.order.entity;

public enum OrderStatus {
    ORDER("주문완료"), // 주문완료
    CANCEL("주문취소"), // 주문취소
    DELIVERY("배송중"); // 배송중

    private final String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}

