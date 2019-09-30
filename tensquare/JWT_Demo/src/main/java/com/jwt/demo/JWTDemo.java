package com.jwt.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTDemo {

    //测试生成token
    @Test
    public void createToken() {
        //定义一个Token生成器JwtBuilder

        long l = System.currentTimeMillis();        //得到当前时间距离1970-1-1以来的毫秒数
        Date date = new Date(l + 300 * 1000);       //设置到期时间为5分钟后

        JwtBuilder jwtBuilder = Jwts.builder().setId("20190923")//设置生成器id
                .setIssuedAt(new Date())//设置加密时间
                .setExpiration(date)//设置过期时间
                .setSubject("Hello World")//设置认证主体信息
                .claim("likes","delicious")
                .claim("tag","fighting")
                .signWith(SignatureAlgorithm.HS256,"jacky");//设置Jwt签名
        //得到Jwt生成的加密字串
        String token = jwtBuilder.compact();
        System.out.println(token);

    }

    //测试解析token
    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTc2MDczODI0NTI1OTAxODI0Iiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1Njk0MTE1NTgsInJvbGVzIjoiUk9MRV9BRE1JTiIsImV4cCI6MTU2OTQxMjE1OH0.1ccnRusW7DOOHZJ8zAEzQJD5Vc4WVJLAmDZXB1HB-bU";
        Claims claims = Jwts.parser().setSigningKey("jacky").parseClaimsJws(token).getBody();
        String id = claims.getId();
        System.out.println(claims.get("roles"));
        System.out.println("token id: " + id);
        System.out.println("加密时间: " + claims.getIssuedAt());
        System.out.println("认证主体: " + claims.getSubject());
        System.out.println("过期时间: " + claims.getExpiration());
        System.out.println("爱好: " + claims.get("likes",String.class));
        System.out.println("标签: " + claims.get("tag",String.class));
    }
}
