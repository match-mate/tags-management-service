package com.leaseforlove.tagsmanagementservice.common.event;

import java.time.LocalDateTime;


public abstract class DomainEvent {
    public abstract LocalDateTime occurredOn();
}
