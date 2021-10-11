package digital.yolk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MyClass {

    @Override
    public String toString() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        MyClass clazz = null;
        System.out.println(clazz.toString());
        System.out.println(clazz.toMyString());
    }

    public abstract String toMyString();

}
