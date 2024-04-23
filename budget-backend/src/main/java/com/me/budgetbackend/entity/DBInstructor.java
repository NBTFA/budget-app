package com.me.budgetbackend.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Setter
@Getter
public class DBInstructor<T> implements Serializable {
    public String dbName;
    public T content;
    public String operation;

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);  // 写入当前对象
            oos.flush();
            return bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            bos.close();
        }
    }
}
