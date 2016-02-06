package by.pvt.khudnitsky.payments.entities;

import by.pvt.khudnitsky.payments.enums.AccessLevelEnum;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 12:20
 */

@Entity
public class AccessLevel extends AbstractEntity {
    private static final long serialVersionUID = 8L;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('CLIENT', 'ADMINISTRATOR')")
    public AccessLevelEnum getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(AccessLevelEnum accessLevel) {
        this.accessLevel = accessLevel;
    }
    private AccessLevelEnum accessLevel;

    @ManyToMany(mappedBy = "accessLevels")
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    private Set<User> users;

    public AccessLevel() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessLevel)) return false;
        if (!super.equals(o)) return false;

        AccessLevel that = (AccessLevel) o;

        return accessLevel == that.accessLevel;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accessLevel != null ? accessLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccessLevel{" +
                "accessLevel=" + accessLevel +
                '}';
    }
}
