// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.reflection;

import org.apache.commons.lang3.reflect.MethodUtils;
import java.util.Iterator;
import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.Field;

public class FabricReflect
{
    public static Field getField(final Class<?> cls, final String obfName, final String deobfName) {
        if (cls == null) {
            return null;
        }
        Class<?> cls2 = cls;
        Field field = null;
        while (cls2 != null) {
            try {
                field = cls2.getDeclaredField(obfName);
            }
            catch (Exception e) {
                try {
                    field = cls2.getDeclaredField(deobfName);
                }
                catch (Exception e2) {}
            }
            Label_0041: {
                break Label_0041;
                cls2 = cls2.getSuperclass();
                continue;
            }
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field;
        }
        for (final Class<?> class1 : ClassUtils.getAllInterfaces((Class)cls)) {
            try {
                field = class1.getField(obfName);
            }
            catch (Exception e3) {
                try {
                    field = class1.getField(deobfName);
                }
                catch (Exception e4) {}
            }
            return field;
        }
        throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, cls.getSimpleName()));
    }
    
    public static Object getFieldValue(final Object target, final String obfName, final String deobfName) {
        if (target == null) {
            return null;
        }
        Class<?> cls2;
        Class<?> cls;
        for (cls = (cls2 = target.getClass()); cls2 != null; cls2 = cls2.getSuperclass()) {
            Field field = null;
            try {
                field = cls2.getDeclaredField(obfName);
            }
            catch (Exception e) {
                try {
                    field = cls2.getDeclaredField(deobfName);
                }
                catch (Exception e2) {}
            }
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                return field.get(target);
            }
            catch (Exception e) {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, target.getClass().getSimpleName()));
            }
        }
        for (final Class<?> class1 : ClassUtils.getAllInterfaces((Class)cls)) {
            Field field;
            try {
                field = class1.getField(obfName);
            }
            catch (Exception e3) {
                try {
                    field = class1.getField(deobfName);
                }
                catch (Exception e4) {}
            }
            try {
                return field.get(target);
            }
            catch (Exception e3) {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, target.getClass().getSimpleName()));
            }
            break;
        }
        throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, target.getClass().getSimpleName()));
    }
    
    public static void writeField(final Object target, final Object value, final String obfName, final String deobfName) {
        if (target == null) {
            return;
        }
        final Class<?> cls = target.getClass();
        final Field field = getField(cls, obfName, deobfName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(target, value);
        }
        catch (Exception e) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, target.getClass().getSimpleName()));
        }
    }
    
    public static Object invokeMethod(final Object target, final String obfName, final String deobfName, final Object... args) {
        Object o;
        try {
            o = MethodUtils.invokeMethod(target, true, obfName, args);
        }
        catch (Exception e) {
            try {
                o = MethodUtils.invokeMethod(target, true, deobfName, args);
            }
            catch (Exception e2) {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, deobfName, obfName, target.getClass().getSimpleName()));
            }
        }
        return o;
    }
}
