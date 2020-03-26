package com.example.todo.base;

import org.mockito.Mockito;
import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.progress.ThreadSafeMockingProgress;
import org.mockito.listeners.MockCreationListener;

import java.util.LinkedList;
import java.util.List;

public final class MockitoMocksCollector {

    private final List<Object> createdMocks;

    private final MockCreationListener mockCreationListener;

    public MockitoMocksCollector() {
        createdMocks = new LinkedList<>();
        MockingProgress mockingProgress = ThreadSafeMockingProgress.mockingProgress();
        mockCreationListener = (mock, settings) -> createdMocks.add(mock);
        mockingProgress.addListener(mockCreationListener);
    }

    public void close() {
        Mockito.framework().removeListener(mockCreationListener);
    }

    public Object[] getAllMocks() {
        return createdMocks.toArray();
    }

}
