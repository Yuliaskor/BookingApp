package com.example.bookingapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "room type")
public enum RoomType {
    ENTIRE_PLACE, PRIVATE_ROOM, SHARED_ROOM
}
