package cn.maidaotech.smartapi.common.utils;

import com.sunnysuperman.commons.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> List<T> emptyToNull(List<T> list) {
        if (list != null && list.isEmpty()) {
            return null;
        }
        return list;
    }

    public static <T> List<T> nullToEmpty(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    public static <T> Set<T> emptyToNull(Set<T> set) {
        if (set != null && set.isEmpty()) {
            return null;
        }
        return set;
    }

    public static <T> Set<T> nullToEmpty(Set<T> set) {
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAll(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Object candidate : candidates) {
            if (!source.contains(candidate)) {
                return false;
            }
        }
        return true;
    }

    public static <T> List<T> removeDuplicate(List<T> list) {
        if (isEmpty(list)) {
            return list;
        }
        ArrayList<T> newList = new ArrayList<T>(list.size());
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    @SafeVarargs
    public static <T> Set<T> arraysAsSet(T... a) {
        Set<T> set = new HashSet<>(a.length);
        for (T item : a) {
            set.add(item);
        }
        return set;
    }

    public static Map<String, Object> arrayAsMap(Object... t) {
        if (t == null || t.length <= 0) {
            return null;
        }
        if (t.length % 2 != 0) {
            throw new RuntimeException("illegal args count");
        }
        Map<String, Object> params = new HashMap<String, Object>(t.length);
        for (int i = 0; i < t.length; i += 2) {
            if (t[i] == null || !t[i].getClass().equals(String.class)) {
                throw new RuntimeException("illegal arg: " + t[i] + "at " + i);
            }
            String key = t[i].toString();
            Object value = t[i + 1];
            params.put(key, value);
        }
        return params;
    }

    public static <K, V> Map<K, V> newMap(int capacity) {
        return new HashMap<K, V>(capacity, 1f);
    }

    public static Map<String, Object> singletonMap(String key, Object value) {
        return Collections.singletonMap(key, value);
    }

    public static List<Long> stringToLongCollections(Collection<String> items) {
        if (items == null) {
            return null;
        }
        if (items.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> list = new ArrayList<>(items.size());
        for (String item : items) {
            list.add(Long.valueOf(item));
        }
        return list;
    }

    public static class CollectionChange<T> {
        private Collection<T> adds;
        private Collection<T> keeps;
        private Collection<T> removes;

        public CollectionChange(Collection<T> adds, Collection<T> keeps, Collection<T> removes) {
            super();
            this.adds = adds;
            this.keeps = keeps;
            this.removes = removes;
        }

        public Collection<T> getAdds() {
            return adds;
        }

        public Collection<T> getKeeps() {
            return keeps;
        }

        public Collection<T> getRemoves() {
            return removes;
        }

    }

    public static <T> CollectionChange<T> compare(Collection<T> oldCollections, Collection<T> collections) {
        if (oldCollections == null) {
            oldCollections = Collections.emptyList();
        }
        if (collections == null) {
            collections = Collections.emptyList();
        }
        if (oldCollections.isEmpty()) {
            Collection<T> keeps = Collections.emptyList();
            Collection<T> removes = Collections.emptyList();
            return new CollectionChange<>(collections, keeps, removes);
        }
        Collection<T> adds = new ArrayList<T>();
        Collection<T> keeps = new ArrayList<T>();
        Collection<T> removes = new ArrayList<T>();
        for (T item : oldCollections) {
            if (collections.contains(item)) {
                keeps.add(item);
            } else {
                removes.add(item);
            }
        }
        for (T item : collections) {
            if (!oldCollections.contains(item)) {
                adds.add(item);
            }
        }
        return new CollectionChange<>(adds, keeps, removes);
    }

    public static <T> boolean isSame(Collection<T> oldCollections, Collection<T> collections) {
        CollectionChange<?> change = compare(oldCollections, collections);
        return change.getAdds().isEmpty() && change.getRemoves().isEmpty();
    }

    public static Map<String, Object> camelizeMap(Map<String, Object> item) {
        Map<String, Object> doc = new HashMap<>();
        for (Entry<String, Object> entry : item.entrySet()) {
            doc.put(StringUtil.underline2camel(entry.getKey()), entry.getValue());
        }
        return doc;
    }

    public static <ID, T> Map<ID, T> collection2map(Collection<T> items, Class<ID> clazz, String idName) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyMap();
        }
        String readMethodName = idName != null ? "get" + StringUtils.capitalize(idName) : "getId";
        Method readMethod;
        try {
            readMethod = items.iterator().next().getClass().getMethod(readMethodName);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        Map<ID, T> map = new HashMap<>();
        for (T item : items) {
            try {
                @SuppressWarnings("unchecked")
                ID key = (ID) readMethod.invoke(item);
                map.put(key, item);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    public static <ID, T> Map<ID, T> collection2map(Collection<T> items, Class<ID> clazz) {
        return collection2map(items, clazz, null);
    }
}
