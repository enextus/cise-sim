package eu.cise.emulator.deprecated.web.app.core;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Member implements Serializable {
    private String id;
    private String name;

    public Member() {
    }

    public Member(String name) {
        this.name = name;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (null == o || o.getClass() != this.getClass()) return false;

        Member other = (Member) o;

        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }
}
