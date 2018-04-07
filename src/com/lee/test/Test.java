package com.lee.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2017/4/4
 * @Description:
 */
public class Test {
    @org.junit.Test
    public void f1() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.txt");
        StringBuffer sb = new StringBuffer();
        byte[] temp = new byte[1024];
        while (in.read(temp, 0, temp.length) != -1) {
            String s = new String(temp, "UTF-8");
            sb.append(s);
        }


        String regex1 = "question_link(.*)>(\\n*)(.+)(\\n*)<";
//        String regex11 = "question_link(.*)>";
        String regex11 = "question_link(.*)>(\\r)(\\n)(.*)";
//        String regex2 = "question_link.*<";
//        String regex3 = "question_link";

        Pattern pattern = Pattern.compile(regex11);
        Matcher matcher = pattern.matcher(sb.toString());

//        System.out.println(sb.toString());

        while (matcher.find()) {
//            String group = matcher.group(1)+matcher.group(2);
            String group = matcher.group(4);
            System.out.println(group);
        }
    }


    @org.junit.Test
    public void func2() {
        String s1 = "\r1234\r567";
        String s2 = "\n12345";
        System.out.println(s1);
        System.out.println(s2);
    }


    @org.junit.Test
    public void func3() {
        String s1 = "\r1234\r567";
        String regex = ".*";
        String regex1 = "\\r.*";
        Pattern pattern = Pattern.compile(regex1); // 换成regex则下面的输出为空,也就是不能匹配
        Matcher matcher = pattern.matcher(s1);
        matcher.find();
        System.out.println(matcher.group());

    }
}
