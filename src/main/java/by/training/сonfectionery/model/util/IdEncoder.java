package by.training.сonfectionery.model.util;

public class IdEncoder {
    public static int encodeId(int id) {
        id = ((id >> 16) ^ id) * 0x45d9f3b;
        id = ((id >> 16) ^ id) * 0x45d9f3b;
        return (id >> 16) ^ id;
    }

    public static int decodeId(int id) {
        id = ((id >> 16) ^ id) * 0x119de1f3;
        id = ((id >> 16) ^ id) * 0x119de1f3;
        id = (id >> 16) ^ id;
        return id;
    }
}
