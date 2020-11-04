package Week_01.q42;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

class helloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object obj = new helloClassLoader().findClass("Hello").getDeclaredConstructor().newInstance();
            Method method = obj.getClass().getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass (String className) throws ClassNotFoundException{
        File file = new File("./Week_01/q42/"+className+".xlass");
        InputStream in = null; 
        try {
            in = new FileInputStream(file);
            int tempbyte;
            int length =  (int)file.length();
            byte[] Bytes = new byte[length];
            int i = 0;
            while ((tempbyte = in.read()) != -1) {
                // 255 - 这个字节。
                Bytes[i] =   (byte) (255 - tempbyte);
                i++;
            }
            in.close();
            return defineClass(className, Bytes, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
             throw new ClassNotFoundException(className);
        }
    }
}