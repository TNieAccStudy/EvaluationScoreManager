/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.utils;

import java.util.function.Function;
import org.hibernate.query.Query;

import java.util.function.Function;
import org.hibernate.query.Query;

/**
 *
 * @author GIGABYTE
 */
public class PaginatorUtils {

    public static int pageSize = 6;
    public static <T> Function<Query<T>, Query<T>> pageQueryDefault(int startIndex, int pageSize, Class<T> type) {
        return (q) -> {
                q.setFirstResult(startIndex);
                q.setMaxResults(pageSize);
                return q;
            };
    }
}
