package com.donovanbrun.organizr.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Event")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;
    private String name;
    private Date date;
}
