package cn.cnlee.test.javamethod;

import com.google.gson.Gson;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/11
 * @Version 1.0
 */
public class TestFinalCase {
    public static void main(String[] args) {
        int aA = 123;
        checkInt(aA);
        System.err.println(aA);

        String aS = "123";
        checkString(aS);
        System.err.println(aS);

        Info info = new Info();
        info.setUsername("name");
        info.setPassword("pwd");
        System.err.println(new Gson().toJson(info));
        checkInfo(info);
        System.err.println(new Gson().toJson(info));

        StringBuffer sb = new StringBuffer("Hello ");
        System.err.println("Before change, sb = " + sb);
        changeData(sb);
        System.err.println("After changeData(n), sb = " + sb);
    }

    private static void changeData(StringBuffer strBuf) {
        StringBuffer sb2 = new StringBuffer("Hi ");
        strBuf = sb2;
        sb2.append("World!");
    }

    private static void checkInt(int i) {
        i = 100;
    }

    private static void checkString(String s) {
        s = "100";
    }

    private static void checkInfo(Info info) {
        info = new Info();
        info.setUsername("name1");
        info.setPassword("pwd1");
        System.err.println(new Gson().toJson(info));
    }

    static class Info {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
