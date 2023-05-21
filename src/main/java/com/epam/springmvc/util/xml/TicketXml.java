package com.epam.springmvc.util.xml;

import com.epam.springmvc.model.Ticket;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketXml {
    private Long userId;
    private Long eventId;
    private Integer place;
    private Ticket.Category category;
}