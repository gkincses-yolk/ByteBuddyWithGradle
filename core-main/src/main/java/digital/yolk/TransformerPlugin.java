package digital.yolk;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.*;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.inline.*;

import java.io.IOException;

@Slf4j
public class TransformerPlugin implements Plugin {
    @Override
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        if (typeDescription.represents(MyClass.class)) {
            log.warn("Let's work on this class: " + String.valueOf(typeDescription));
//            return new RebaseDynamicTypeBuilder<>(
//                    new InstrumentedType.WithFlexibleName().withMethod(),
//
//            );
            return null;
        } else {
            log.warn("Ignoring class: " + String.valueOf(typeDescription));
            return null;
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean matches(TypeDescription target) {
        if (target.represents(MyClass.class)) {
            return true;
        }

        return false;
    }
}
