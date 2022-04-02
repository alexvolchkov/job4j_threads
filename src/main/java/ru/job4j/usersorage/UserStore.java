package ru.job4j.usersorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return putIf(user, u -> !users.containsKey(u.getId()));
    }

    public synchronized boolean update(User user) {
        return putIf(user, u -> users.containsKey(u.getId()));
    }

    private synchronized boolean putIf(User user, Predicate<User> filter) {
        boolean rsl = false;
        if (filter.test(user)) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User fromUser = findById(fromId);
        User toUser = findById(toId);
        if (fromUser != null && toUser != null) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            update(fromUser);
            update(toUser);
        }
    }

    public synchronized User findById(int id) {
        User currenUser = users.get(id);
        return (currenUser == null) ? null : new User(currenUser.getId(), currenUser.getAmount());
    }
}
