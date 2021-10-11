import digital.yolk.MyClass;
import digital.yolk.extension.ExtensionPoint1;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.*;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MyApp {

    Class<?> completeClass;
    MyClass completeObject;

    public MyApp() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        completeClass = this.buildClass();
        completeObject = this.instantiate();
    }

    private Class<?> buildClass() {
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(MyClass.class)
                .method(named("toMyString"))
                .intercept(MethodDelegation.to(new ExtensionPoint1()))
                .make();
        Class<?> completeMyClass = unloadedType.load(MyApp.class.getClassLoader()).getLoaded();
        return completeMyClass;
    }

    private MyClass instantiate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor ctor = completeClass.getConstructor();
        MyClass completeObject = (MyClass) ctor.newInstance();
        return completeObject;
    }

    private void executeGeneratedMethod() {
        System.out.println(completeObject.toMyString());
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyApp app = new MyApp();
        app.executeGeneratedMethod();
    }
}
