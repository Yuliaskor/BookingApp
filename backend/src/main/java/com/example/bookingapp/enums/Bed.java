package com.example.bookingapp.enums;

import lombok.Getter;

@Getter
public enum Bed {
   SINGLE_BED(1), DOUBLE_BED(2), BUNK_BED(2);

   private final int bedSize;

    Bed(int bedSize) {
        this.bedSize = bedSize;
    }
}
