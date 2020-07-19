package com.wyh.javabase.seriallize;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

public class SerializeDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User1.setStatic_name("静态的张三");
        User1 u1 = new User1();
        u1.setName("张三");
        u1.setAge(10);
        u1.setFlag(true);
        test(u1);

        User2.setStatic_name("静态的张三");
        User2 u2 = new User2();
        u2.setName("张三");
        u2.setAge(10);
        u2.setFlag(true);
        test(u2);
    }

    private static void test(Object obj) throws IOException, ClassNotFoundException {
        File file = new File("./serialize_output");
        ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(file));
        out1.writeObject(obj);
        out1.close();

        ObjectInputStream i1 = new ObjectInputStream(new FileInputStream(file));
        obj = i1.readObject();
        System.out.println(obj);
    }

    /**
     * static和transient修饰的变量不会被序列化
     */
    static class User1 implements Serializable {
        private String name;
        private transient int age;
        private static String static_name;
        private boolean flag;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public static String getStatic_name() {
            return static_name;
        }

        public static void setStatic_name(String static_name) {
            User1.static_name = static_name;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "User1{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", flag=" + flag +
                    '}';
        }
    }

    /**
     * 指定哪些字段需要序列化
     */
    static class User2 implements Externalizable {
        private String name;
        private transient int age;
        private static String static_name;
        private boolean flag;

        public User2() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public static String getStatic_name() {
            return static_name;
        }

        public static void setStatic_name(String static_name) {
            User1.static_name = static_name;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(name);
            out.writeInt(age);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            age = in.readInt();
        }

        @Override
        public String toString() {
            return "User2{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", flag=" + flag +
                    '}';
        }
    }

}
