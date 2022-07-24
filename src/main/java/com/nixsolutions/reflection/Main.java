package com.nixsolutions.reflection;

import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

interface Voice {
    void performVoice();
}

class Animal {
    protected String breed;

    public Animal(String breed) {
        this.breed = breed;
    }
}

class Dog extends Animal implements Voice {

    @Setter
    @Getter
    @Deprecated
    private String name;

    public Dog(String type) {
        super(type);
    }

    public void eat(String treat) {
        System.out.println("What a yummy " + treat.toLowerCase() + "!");
    }

    @Override
    public void performVoice() {
        System.out.println("Woof woof");
    }
}

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        Dog dog = new Dog("Labradoodle");
        dog.setName("Jack");
        dog.eat("bone");
        dog.performVoice();
        printFieldsWithValues(dog);
        Class<?> dogClass = getDogClass(dog);
        printSuperClass(dogClass);
        printConstructors(dogClass);
        printInterfaces(dogClass);
        printMethods(dogClass);
        printFields(dogClass);
    }

    private static Class<?> getDogClass(Dog dog) {
        return dog.getClass();
    }

    private static Class<?> getDogClass() throws ClassNotFoundException {
//        return Dog.class;
        return Class.forName("com.nixsolutions.reflection.Dog");
    }

    private static void printInterfaces(Class<?> dogClass) {
        System.out.println("============== Interfaces ===============");
        Class<?>[] interfaces = dogClass.getInterfaces();
        for (Class<?> implInterface : interfaces) {
            System.out.println("Interface name: " + implInterface.getName());
        }
    }

    private static void printMethods(Class<?> dogClass) {
        Method[] methods = dogClass.getDeclaredMethods();

        System.out.println("============== Methods ===============");
        for (Method method : methods) {
            System.out.println("Method name: " + method.getName());
            System.out.println("Method return type: " + method.getReturnType().getSimpleName());
            System.out.println("Method modifier : " + Modifier.toString(method.getModifiers()));
            System.out.println("Method parameters count: " + method.getParameterCount());
            printParameters(method.getParameterTypes());
            System.out.println("================================");
        }
    }

    private static void printSuperClass(Class<?> dogClass) {
        System.out.println("============== Super Class ===============");
        Class<?> superClass = dogClass.getSuperclass();
        System.out.println("Super class name: " + superClass.getSimpleName());
    }

    private static void printConstructors(Class<?> dogClass) {
        System.out.println("============== Constructors ===============");
        Constructor<?>[] constructors = dogClass.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor name: " + constructor.getName());
            System.out.println("Constructor modifier: " + Modifier.toString(constructor.getModifiers()));
            System.out.println("Constructor parameters count: " + constructor.getParameterCount());
            printParameters(constructor.getParameterTypes());
        }
    }

    private static void printFields(Class<?> dogClass) {
        System.out.println("============== Fields ===============");
        Field[] fields = dogClass.getDeclaredFields();

        for (Field field : fields) {
            printFieldAnnotations(field);
            System.out.println("Field name: " + field.getName());
            System.out.println("Field type: " + field.getType().getSimpleName());
        }
    }

    private static void printFieldsWithValues(Object object) throws IllegalAccessException {
        System.out.println("============== Fields ===============");
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            printFieldAnnotations(field);
            System.out.println("Field name: " + field.getName());
            System.out.println("Field type: " + field.getType().getSimpleName());
            field.setAccessible(true);
            System.out.println("Field value: " + field.get(object));
        }
    }

    private static void printParameters(Class<?>[] parameters) {
        for (Class<?> parameterType : parameters) {
            System.out.println("    Parameter name: " + parameterType.getSimpleName());
            System.out.println("    Parameter type: " + parameterType.getTypeName());
        }
    }

    private static void printFieldAnnotations(Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            System.out.println("Field annotation: " + annotation.annotationType().getSimpleName());
        }
    }
}
