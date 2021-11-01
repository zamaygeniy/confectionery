package by.training.сonfectionery.control;

import by.training.сonfectionery.util.IdEncoder;
public class Main {
    public static void main(String[] args) {
        int id = 14;
        int hashedId = IdEncoder.encodeId(id);
        System.out.println(hashedId);
        System.out.println(IdEncoder.decodeId(hashedId));
    }
}
