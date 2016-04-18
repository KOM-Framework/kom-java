package core;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
 
/**
 * Cloner - a recursive, reflective, hierarchical deep-cloner for objects!
 * 
 * Typically used for cloning beans and domain model objects.
 * 
 * @author Rudolf Ingerl
 * http://mathdotrandom.blogspot.com
 */
public class Cloner {
     
    /**
     * Main deepClone method.
 
     * 
 
     * This method takes an object and produces a <i>best possible</i> deep clone of the supplied param.
 
     * 
 
     * NB: Not all supplied objects can be deep cloned... this works best for beans or domain model objects.
 
     * 
 
     * Requirements for a good clonable objects class:
 
     *  - Must have a visible no-args constructor
 
     *  - Must have public fields, or must have visible accessor methods for any private fields (get*; set*; is*) (default or protected fields might work...)
 
     *  - Must not have any immutable classes somewhere in it's hierarchy tree (those classes should be <code>final</code> anyway...)
 
     *  - Must not have any fields as anonymous classes (since they cannot have constructors)
     * 
 
     * Supported clonable object features:
     *  - Standard objects
 
     *  - Complex / deep objects
 
     *  - Primitives
 
     *  - Nulls
 
     *  - Arrays and multidimensional arrays
 
     *  - Collections
 
     *  - Enumerations
 
     *  - Annotations
 
     *  - CharSequences, except for CharBuffer
 
     *  - Multiple inheritance hierarchy levels
 
     *  - Fields of instantiated inner classes
 
     * 
 
     * Fields with the default or protected access modifier will also clone if:
 
     *  - Default: You need to place this class in the same package as the class you wish to clone
 
     *  - Protected: You need to copy these two methods into lowest class in the inheritance hierarchy which contains the protected fields you wish to clone
  
     * 
 
     * This method ignores any implementation of Object.clone() or any reference to java.lang.Clonable as these are not guaranteed to be deep-cloning...
  
     * 
 
     * @param obj    the original object to clone
     * @return    the cloned object
     * @throws Exception    thrown only if bad reflective assumptions are made - not if clone is not a 100% copy.
     *                      Please make sure your clones are  
     */
    public static <T> T deepClone(Object obj) throws Exception {
        return deepClone(null, obj, null, null);
    }
     
    /**
     * Internal deepClone method. Not to be invoked directly.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> T deepClone(T clone, Object obj, Class currentClazz, T parent) throws Exception {
        boolean firstIteration = false;
         
        /**
         * Null
         */
        if (obj == null){
            return null;
        }
 
        if (currentClazz == null){
            currentClazz = obj.getClass();
            firstIteration = true;
        }
         
        /**
         * Class
         */
        if (obj.getClass().equals(java.lang.Class.class)){
            return (T)obj;
        }
 
        /**
         * Enum
         */
        if (currentClazz.isEnum()){
            return (T)obj;
        }
         
        /**
         * Annotation
         */
        if (currentClazz.isAnnotation()){
            return (T)obj;
        }
         
        /**
         * Primitive
         */
        if (currentClazz.isPrimitive()){
            return (T)obj; //primitives are immutable
        }
         
        /**
         * Immutable objects-wrappers for one of the 8 primitives 
         */
        if (currentClazz.equals(java.lang.Boolean.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Character.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Byte.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Short.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Integer.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Long.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Float.class)){
            return (T)obj;
        } else if (currentClazz.equals(java.lang.Double.class)){
            return (T)obj;
        }
         
        /**
         * Immutable String 
         */
        if (currentClazz.equals(java.lang.String.class)){
            return (T)obj;
        }
         
        /**
         * Array
         */
        if (currentClazz.isArray()){
            int length = Array.getLength(obj);
            clone = (T)Array.newInstance(obj.getClass().getComponentType(), length);
            for (int i = 0; i < length; i++){
                Array.set(clone, i, deepClone(Array.get(obj, i))); //can't use System.arraycopy to copy across the array elements - they have to be cloned too!
            }
             
            return clone;
        }
         
        /**
         * Clone Main Object
         */
        if (clone == null){
            try {
                if (currentClazz.isMemberClass()){
                    Constructor constructor = currentClazz.getConstructor(new Class[]{currentClazz.getDeclaringClass()});
                    clone = (T)constructor.newInstance(parent);
                } else {
                    clone = (T)currentClazz.newInstance();
                }
            } catch (IllegalAccessException iae){
                iae.printStackTrace();
                throw new Exception("No visible constructor for class: " + currentClazz);
            } catch (InstantiationException ie){
                ie.printStackTrace();
                throw new Exception("No visible no-args constructor for class: " + currentClazz);
            }
        }
         
        /**
         * Mutable Char Sequences
         * StringBuffer, StringBuilder
         * (Sequence will clone OK via the non-specialized code...)
         * (CharBuffer doesn't have a no-args constructor)
         */
        if (obj instanceof CharSequence){
            /**
             * StringBuffer, StringBuilder
             */
            if (currentClazz.equals(java.lang.StringBuffer.class) || currentClazz.equals(java.lang.StringBuilder.class)){
                Method append;
                try {
                    append = currentClazz.getMethod("append", new Class[]{currentClazz});
                    append.invoke(clone, new Object[]{obj});
                } catch (Throwable t){
                    t.printStackTrace();
                    throw new Exception("Unable to clone CharSequence content on class: " + currentClazz);
                }
            }
        }
         
        /**
         * Visible Fields
         */
        Field[] fields = currentClazz.getDeclaredFields();
        for (Field field : fields){
            if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
                continue;
            }
             
            if ((field.getModifiers() & Modifier.PRIVATE) == Modifier.PRIVATE){
                continue;
            }
             
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL){
                continue;
            }
             
            //Enable this check - depending on how you want to handle transient... 
            //if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT){
            //  continue;
            //}
             
            try { //attempt to copy across accessible fields
                field.set(clone, deepClone(null, field.get(obj), null, clone));
            } catch (IllegalAccessException iae){
                //consume - can't set value on a field with the default or protected access modifier if that field is not in scope of THIS METHOD's CLASS (not the class invoking this method!1)
            } catch (Throwable t){
                t.printStackTrace();
                throw new Exception("Unable to clone field: " + field.getName() + ", of type: " + field.getType() + " on class: " + currentClazz);
            }
        }
         
        /**
         * Fields via Accessors
         */
        Method[] methods = currentClazz.getDeclaredMethods();
        for (Method method : methods){
            if ((method.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
                continue;
            }
             
            if ((method.getModifiers() & Modifier.PRIVATE) == Modifier.PRIVATE){
                continue;
            }
             
            if ((method.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT){
                continue;
            }
             
            String methodName = method.getName().toLowerCase();
            if (!method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 0){
                String fieldName = null;
                if (methodName.startsWith("get") && methodName.length() > 3){
                    fieldName = methodName.substring(3, methodName.length());
                } else if (methodName.startsWith("is") && methodName.length() > 2){
                    fieldName = methodName.substring(2, methodName.length());
                }
                 
                if (fieldName == null){
                    continue;
                }
                 
                for (Method setterMethod : methods){
                    if ((setterMethod.getModifiers() & Modifier.STATIC) == Modifier.STATIC){
                        continue;
                    }
                     
                    if ((setterMethod.getModifiers() & Modifier.PRIVATE) == Modifier.PRIVATE){
                        continue;
                    }
                     
                    if ((setterMethod.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT){
                        continue;
                    }
                     
                    if (setterMethod.getName().toLowerCase().equals("set" + fieldName) && setterMethod.getReturnType().equals(Void.TYPE) && setterMethod.getParameterTypes().length == 1 && setterMethod.getParameterTypes()[0].equals(method.getReturnType())){
                        try {
                            setterMethod.invoke(clone, deepClone(null, method.invoke(obj, new Object[]{}), null, clone));
                        } catch (Throwable t) {
                            //consume - either we don't have access to that method, or we're sending an incorrect param
                            continue; //keep searching
                        }
                         
                        break; //break only if successful
                    }
                }
            }
        }
         
        /**
         * Inheritance Class Hierarchy
         */
        Class parentClazz = currentClazz.getSuperclass();
        if (parentClazz != null && !Object.class.equals(parentClazz)){
            clone = deepClone(clone, obj, parentClazz, null);
        }
         
        /**
         * Collection
         */
        if (firstIteration && obj instanceof java.util.Collection){
            Object[] clonedItems = deepClone(((Collection)obj).toArray());
            ((Collection)clone).addAll(Arrays.asList(clonedItems));
        }
         
        return clone;
    }
     
    /**
     * Private constructor
     * 
     * This class is a static util class.
     */
    private Cloner(){
        //Don't instantiate this class!
    }
     
}