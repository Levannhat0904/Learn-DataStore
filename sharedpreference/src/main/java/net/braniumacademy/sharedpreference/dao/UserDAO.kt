package net.braniumacademy.sharedpreference.dao

import net.braniumacademy.sharedpreference.data.User

class UserDAO private constructor() : Dao<User> {
    private val users: MutableList<User> = ArrayList()

    init {
        users.add(User(1, "Hoa", "hoaxinhgai@xmail.com"))
        users.add(User(2, "Mai", "maimezai@xmail.com"))
        users.add(User(3, "Quang", "quanghacker@xmail.com"))
        users.add(User(4, "Thắng", "thangsuperman@xmail.com"))
    }

    override fun get(id: Int): User? {
        val index = users.indexOf(User(id, "", ""))
        return if (index < 0) null else users[index]
    }

    override fun getAll(): List<User> {
        return users
    }

    override fun delete(t: User) {
        users.remove(t)
    }

    override fun update(t: User, params: Array<String>) {
        t.name = params[0]
        t.email = params[1]
    }

    override fun save(t: User) {
        if (t.id == 0) {
            t.id = maxId() + 1
        }
        users.add(t)
    }

    override fun maxId(): Int {
        var id = 0
        for (user in users) {
            if (id < user.id) {
                id = user.id
            }
        }
        return id
    }

    companion object {
        private val userDao: UserDAO = UserDAO()

        fun getInstance(): UserDAO {
            return userDao
        }
    }
}