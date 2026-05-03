package ru.yandex.practicum.gym;

import java.util.Objects;

public class Group {
    private final String title;
    private final Age age;
    private final int duration;

    public Group(String title, Age age, int duration) {
        if (title == null || age == null) {
            throw new IllegalArgumentException("Title and age cannot be null");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Group))
            return false;
        Group group = (Group) o;
        return duration == group.duration && Objects.equals(title, group.title) && age == group.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, duration);
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }
}
