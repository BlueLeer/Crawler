package com.lee.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2017/4/6
 * @Description: 使用Java提供的Patter和Matcher操作字符串
 */
public class Test2 {

    @Test
    public void f() {
        String s = "Java12Java34";
        boolean java = s.matches("Java"); // 精确匹配,返回regex和s串是否完全匹配,这里返回false
        boolean matches = s.regionMatches(true, 6, "Java", 0, "Java".length());
        System.out.println(java);
        System.out.println(matches);

    }

    @Test
    public void func1() {
        String regex = "Java";
        String s2 = "Java123Java456Java789";
        Pattern pattern = Pattern.compile(regex);
//        String[] split = pattern.split(s2);
//        System.out.println("split的长度为 : "+split.length);
//
//        // 当limit的值为负数或者大于实际匹配所得的子串的数量,而regex又位于开头或者结尾的时候,会得到包含空串的String数组
//        String[] split1 = pattern.split(s2, 2);
//
//        for (String s : split1) {
//            System.out.println(s);
//        }
//        System.out.println("split1的长度为 : " + split1.length);

//        String[] split2 = pattern.split(s2, -2); //该模式就是和设置limit为0时的效果一样
//        for (String s : split2) {
//            System.out.println(s);
//        }
//        System.out.println("split2的长度为 : " + split2.length);


        String[] split3 = pattern.split(s2, 7); //该模式就是pattern.split(s1)的默认模式
        for (String s : split3) {
            System.out.println(s);
        }
        System.out.println("split3的长度为 : " + split3.length);


//        // Pattern中的静态方法,方便快捷的进行匹配,返回是否有子串和它匹配
//        boolean isMatch = Pattern.matches("Java", s2); // 其中"."代表任何字符,"*"代表该模式的0或者多次,正则表达式的内容
//        System.out.println(isMatch);

    }

    /**
     * Matcher中基础的方法 find(),matches(),lookingAt()
     */
    @Test
    public void func2() {
        String regex = "Java";
        String s = "Java123Java456Java789";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(s);
        Matcher matcher2 = pattern.matcher(s);


        System.out.println(matcher1.find());//查询整串,看是否有匹配的子串,返回true
        System.out.println(matcher2.find(5));//从指定位置开始查找匹配的子串,返回true

        System.out.println(matcher1.matches());//全串匹配,返回true
        System.out.println(matcher2.matches());//全串匹配,返回false

        System.out.println(matcher2.lookingAt());//从最开头的位置匹配满足的子串

    }


    /**
     * 尝试查找与该模式匹配的输入序列的下一个子序列。
     * 此方法从匹配器区域的开头开始，
     * 如果该方法的前一次调用成功了并且从那时开始匹配器没有被重置，则从以前匹配操作没有匹配的第一个字符开始。
     */
    @Test
    public void func11() {
        String target = "Java1Java2";
        String regex = "Java.";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        // 从字符串的初始位置开始进行查找匹配
        if (matcher.find()) {
            System.out.println(matcher.group());
        }

        // 从上一次匹配的位置为 Java1Java2字符串的第二个Java处,此次匹配从此处开始
        if (matcher.find()) {
            System.out.println(matcher.group());
        }

    }

    /**
     * Matcher中的分组方法
     * 调用分组之前需要先进行匹配查找,例如需要先调用find()方法
     * 分组表示用一对圆括号()括起来的组,例如
     * regex为 "Java(123)Java(456)",
     * 执行find()方法以后,
     * group()代表返回组号为0的分组,也就是整串,如果整个regex没有括号,则0分组代表匹配结果的字符串
     * group(1)代表分组为1的的分组,也就是Java
     * group(2)代表分组为2的分组,也就是Java
     */
    @Test
    public void func3() {
        String regex = "(Java.*)(Java.*)";
        String s = "Java123Java456";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher2 = pattern.matcher(s);

        matcher2.find();
//        System.out.println(matcher1.group());
        System.out.println(matcher2.group());
        System.out.println(matcher2.group(1));
        System.out.println(matcher2.group(2));

        String ss = "Java1234Java56";
        String s1 = ss.replaceAll("[\\d]+", "-");
        System.out.println(s1);
    }

    /**
     * Matcher提供了region(int start, int end)(不包括end)方法用于设定查找范围，
     * 并提供regionStrat()和regionEnd()用于返回起始和结束查找的索引
     */
    @Test
    public void func4() {
        String s1 = "Java";
        String s2 = "Java123Java456Java789";
        Pattern pattern = Pattern.compile("Java.{4}"); // .{4}代表匹配任意字符模式4次
        Matcher matcher1 = pattern.matcher(s1);
        Matcher matcher2 = pattern.matcher(s2);

        matcher2.region(5, 15); //指定匹配查找的范围
        matcher2.find();
        System.out.println(matcher2.group());
    }

    /**
     * 重置匹配器的方法
     */
    @Test
    public void func5() {
        String s = "Java123Java456Java789";
        Pattern pattern = Pattern.compile("Java.{10}"); // .{4}代表匹配任意字符模式4次
        Matcher matcher = pattern.matcher(s);

        // 执行匹配
        matcher.find();
        System.out.println(matcher.group());

        // 重置后继续查找
        matcher.reset();
        matcher.find();
        System.out.println(matcher.group());

        // 重新设置input,对新的目标字符串进行查找
        matcher.reset("Java88935838459385934895Java8t9348569368340uteoijgl");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void func6() {
        String s = "Java55454Java99898Java950659";
        String regex = "(Java.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

//        while (matcher.find()) {
//            System.out.println(matcher.group());
//        }

        matcher.find();
        System.out.println(matcher.group());
    }

    @Test
    public void func8() {
        String s = "2018-4-10 15-45-30";
        String regex = "-";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        StringBuffer sb = new StringBuffer();

        matcher.find();
        matcher.appendReplacement(sb, ":");
        System.out.println(sb.toString());
        matcher.find();
        matcher.appendReplacement(sb, ":");
        System.out.println(sb.toString());
        matcher.find();
        matcher.appendReplacement(sb, ":");
        System.out.println(sb.toString());



    }
}
