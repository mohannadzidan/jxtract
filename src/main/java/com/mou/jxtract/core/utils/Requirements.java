package com.mou.jxtract.core.utils;


import com.mou.jxtract.core.StringTyped;

import java.util.ArrayList;

public final class Requirements {
    private Requirements() {
    }

    static final class Messages {
        private Messages() {
        }

        public static String missingArgument(String name, String context) {
            return String.format("%s requires a value for \"%s\"", context, name);
        }


    }

    public static <T, K extends Throwable> T requireTypeOrThrow(Object value, Class<T> type, K throwable) throws K {
        if (type.isAssignableFrom(value.getClass())) {
            throw throwable;
        }
        return type.cast(value);
    }
    public static <T, K extends Throwable> T requireType(Object value, String name, Class<T> type) throws K {
        if (value == null) {
            throw new ClassCastException(String.format("Expecting %s to be of type %s but found %s", name, type, null));
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new ClassCastException(String.format("Expecting %s to be of type %s but found %s", name, type, value.getClass()));
        }
        return type.cast(value);
    }
    public static <T, K extends Throwable> T requireOrThrow(T value, K throwable) throws K {
        if (value == null) {
            throw throwable;
        }
        return value;
    }

    public static <T> T require(T value, String msg) {
        return requireOrThrow(value, new NullPointerException(msg));
    }

    public static <T> T require(T value, String name, String context) {
        return require(value, Messages.missingArgument(name, context));
    }
    public static <T> T require(T value, String name, StringTyped context) {
        return require(value, Messages.missingArgument(name, context.getType()));
    }

    public static Requirement create(StringTyped context, Object object, String name){
        return new Requirement(context, object, name);
    }
    private interface IRequire{
        void require();
    }

    public static class Requirement implements IRequire {
        private StringTyped context;
        private ArrayList<IRequire> requirements = new ArrayList<>();
        private Requirement previous;
        public Requirement(StringTyped context, Object object, String name) {
            this.context = context;
            requirements.add(() -> Requirements.require(object, name));
        }

        private Requirement(Requirement previous, Object object, String name) {
            this.previous = previous;
            this.context = previous.context;
            requirements.add(() -> Requirements.require(object, name));
        }

        public Requirement or(Object object, String name){
            return new Requirement(context, object, name);
        }

        public Requirement and(Object object, String name){
            requirements.add(() -> Requirements.require(object, name));
            return this;
        }

        @Override
        public void require() {
            for(IRequire r : requirements){
                try{
                    r.require();
                }catch (Exception e){
                    if(previous != null){
                        previous.require();
                    }else {
                        throw e;
                    }
                }
            }
        }

    }
}
