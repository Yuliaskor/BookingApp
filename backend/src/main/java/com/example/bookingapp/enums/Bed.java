package com.example.bookingapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "bed size")
public enum Bed {
   SINGLE_BED(1), DOUBLE_BED(2), BUNK_BED(2);

   private final int bedSize;

    Bed(int bedSize) {
        this.bedSize = bedSize;
    }
}
