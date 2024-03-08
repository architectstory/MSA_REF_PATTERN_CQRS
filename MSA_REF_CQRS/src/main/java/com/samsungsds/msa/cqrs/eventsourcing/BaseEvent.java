package com.samsungsds.msa.cqrs.eventsourcing;

import com.samsungsds.msa.cqrs.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEvent extends Message {
    private int version;
}
