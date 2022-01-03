package pl.k4mil.springcleanlogging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueExtractionUtil {

    private final String argsRegex = "^args\\[([0-9]|[1-9][0-9]|[1-9][0-9][0-9])\\]$";

    public List<String> extractValues(List<String> paths, Object[] args, Object retVal) throws NoSuchFieldException, IllegalAccessException {
        List<String> values = new ArrayList<>();
        for(var source : paths) {
            var elements = Arrays.asList(source.split("\\."));
            if(elements.get(0).equals("retVal") && retVal != null) {
                if(elements.size() == 1) {
                    values.add(retVal.toString());
                }
                else {
                    elements = removeFirstEl(elements);
                    values.add(getFieldValue(elements, retVal));
                }
            }
            else if(elements.get(0) != null && elements.get(0).matches(argsRegex) && args != null) {
                int argIndex = Integer.parseInt(substringBetween(elements.get(0), "[", "]"));
                if(argIndex <= args.length) {
                    elements = removeFirstEl(elements);
                    values.add(getFieldValue(elements, args[argIndex]));
                }
            }
        }
        return values;
    }

    private List<String> removeFirstEl(List<String> list) {
        list = new ArrayList<>(list);
        return list.subList(1, list.size());
    }

    private String substringBetween(final String str, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    private String getFieldValue(List<String> paths, Object root) throws NoSuchFieldException, IllegalAccessException {
        if(paths != null && paths.size() > 0) {
            Object next = null;
            for(var field : paths) {
                if(next == null)
                    next = root;
                next = getFieldValue(field, next);
            }
            return next.toString();
        }
        return null;
    }

    private Object getFieldValue(String fieldName, Object source) throws NoSuchFieldException, IllegalAccessException {
        Field field = source.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(source);
    }
}
