package uk.co.mruoc.jsonapi.fake;

import lombok.Data;

import java.util.UUID;

@Data
public class FakeAttributes {

    private UUID id = UUID.fromString("94e65ed0-1334-4c78-8c8f-bfd12773d041");
    private String value1 = "fake-value-1";
    private String value2 = "fake-value-2";

}
