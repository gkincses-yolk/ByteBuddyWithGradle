package digital.yolk;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.*;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.IOException;

@Slf4j
public class TransformerPlugin implements Plugin {
    @Override
    public boolean matches(TypeDescription target) {
        System.out.println("target=" + target.getName());
        return target.getName().equals("digital.yolk.MyClass");
    }

    @Override
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        return builder.modifiers(ModifierContributor.ForType.EMPTY_MASK | Opcodes.ACC_PUBLIC).method(ElementMatchers.named("toMyString")).intercept(FixedValue.value("Hello World!!!"));
    }

    @Override
    public void close() throws IOException {

    }
}

