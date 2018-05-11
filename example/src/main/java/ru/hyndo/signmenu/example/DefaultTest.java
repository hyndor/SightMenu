package ru.hyndo.signmenu.example;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class DefaultTest {

    public static void main(String[] args) {
        String singleLookupStr = "Player = ${player}, ${name}, lyooxa lox ?= ${lox}";
        Map<String, Object> values = new HashMap<>();
        values.put("player", "SomePlaceHolder");
        values.put("name", "Lesha");
        values.put("lox", "True");
        StrSubstitutor singleLookup = new StrSubstitutor(StrLookup.mapLookup(values));
        String replaced = singleLookup.replace(singleLookupStr);
        System.out.println(replaced);
    }


}
