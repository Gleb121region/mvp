package ru.spbstu.mvp.exception;

public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException(String message) {
        super(message);
    }
}

