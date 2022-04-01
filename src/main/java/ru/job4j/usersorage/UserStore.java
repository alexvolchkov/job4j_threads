package ru.job4j.usersorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Set<User> users = new HashSet<>();

    public synchronized boolean add(User user) {
        return users.add(user);
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (delete(user)) {
            rsl = add(user);
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user);
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
        User rsl = null;
        for (User user : users) {
            if (user.getId() == id) {
                rsl = new User(user.getId(), user.getAmount());
                break;
            }
        }
        return rsl;
    }
}
