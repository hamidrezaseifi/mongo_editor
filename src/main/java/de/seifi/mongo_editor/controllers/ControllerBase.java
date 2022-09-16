package de.seifi.mongo_editor.controllers;

public interface ControllerBase {
    boolean isDirty();

    String getDirtyMessage();
}
