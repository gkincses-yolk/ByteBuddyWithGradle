import digital.yolk.MyClass;
import digital.yolk.extension.ExtensionPoint1;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MyApp {

    public static abstract class InvocationStrategy {

        Class<? extends MyClass> completeClass;
        MyClass completeObject;

        public InvocationStrategy() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        }

        protected MyClass instantiate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            return completeClass.getConstructor().newInstance();
        }

        public void executeGeneratedMethod() {
            System.out.println(completeObject.toMyString());
        }
    }
    public static class RuntimeAugmentation extends InvocationStrategy {

        public RuntimeAugmentation() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            completeClass = this.buildClass();
            completeObject = this.instantiate();
        }

        private Class<? extends MyClass> buildClass() {
            DynamicType.Unloaded<? extends MyClass> unloadedType = new ByteBuddy()
                    .subclass(MyClass.class)
                    .method(named("toMyString"))
                    .intercept(MethodDelegation.to(new ExtensionPoint1()))
                    .make();
            return unloadedType.load(MyApp.class.getClassLoader()).getLoaded();
        }

    }

    public static class DynamicInvocation extends InvocationStrategy {

        public DynamicInvocation() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            completeClass = (Class<? extends MyClass>) MyApp.class.getClassLoader().loadClass("digital.yolk.MyClass");
            completeObject = this.instantiate();
        }

    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
//        InvocationStrategy strategy = new RuntimeAugmentation();
        InvocationStrategy strategy = new DynamicInvocation();
        strategy.executeGeneratedMethod();
    }

}
