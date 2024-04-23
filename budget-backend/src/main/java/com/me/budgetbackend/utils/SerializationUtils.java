package com.me.budgetbackend.utils;
import com.me.budgetbackend.entity.DBInstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerializationUtils {

    public static <T> DBInstructor<T> deserialize(byte[] data) {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            return (DBInstructor<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization error. Cause: " + e.getMessage(), e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // log this error or handle it
                }
            }
        }
    }
}
