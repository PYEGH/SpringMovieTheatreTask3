package com.epam.moviemanagment.domain.entity;

import java.util.Calendar;
import java.util.Objects;


public class TicketEntity extends DomainObjectEntity implements Comparable<TicketEntity> {

    private UserEntity userEntity;

    private EventEntity eventEntity;

    private Calendar dateTime;

    private long seat;

    public TicketEntity(UserEntity userEntity, EventEntity eventEntity, Calendar dateTime, long seat , Long id) {
        this.userEntity = userEntity;
        this.eventEntity = eventEntity;
        this.dateTime = dateTime;
        this.seat = seat;
        super.setId(id);
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public EventEntity getEventEntity() {
        return eventEntity;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public long getSeat() {
        return seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, eventEntity, seat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TicketEntity other = (TicketEntity) obj;
        if (dateTime == null) {
            if (other.dateTime != null) {
                return false;
            }
        } else if (!dateTime.equals(other.dateTime)) {
            return false;
        }
        if (eventEntity == null) {
            if (other.eventEntity != null) {
                return false;
            }
        } else if (!eventEntity.equals(other.eventEntity)) {
            return false;
        }
        if (seat != other.seat) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TicketEntity other) {
        if (other == null) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = eventEntity.getName().compareTo(other.getEventEntity().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

}
