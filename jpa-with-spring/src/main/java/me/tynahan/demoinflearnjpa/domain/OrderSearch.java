package me.tynahan.demoinflearnjpa.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String MemberName;

    private OrderStatus orderStatus;
}
