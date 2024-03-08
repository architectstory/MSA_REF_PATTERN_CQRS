package com.samsungsds.msa.cqrs.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public abstract class BaseCommand extends Message {
    public BaseCommand(String id) { super(id);}
}
