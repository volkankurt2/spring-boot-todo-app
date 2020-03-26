package com.example.todo.base;

import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseMockitoTest {

    private MockitoMocksCollector mockitoMocksCollector = new MockitoMocksCollector();

    @After
    public void after() {
        Object[] allMocks = mockitoMocksCollector.getAllMocks();
        if (allMocks.length > 0) {
            verifyNoMoreInteractions(allMocks);
        }
        mockitoMocksCollector.close();
    }

    protected InOrder inOrderVerifier() {
        return inOrder(mockitoMocksCollector.getAllMocks());
    }

}
