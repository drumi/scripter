package com.andreyprodromov.scripter.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HelpCommandTest {

    OutputStream outputStream = mock(OutputStream.class);

    @Test
    void listsHelp() throws IOException {
        var command = new HelpCommand(outputStream);

        command.execute();

        ArgumentCaptor<byte[]> byteCaptor = ArgumentCaptor.forClass(byte[].class);
        ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(outputStream, atLeast(0)).write(byteCaptor.capture(), anyInt(), anyInt());
        verify(outputStream, atLeast(0)).write(byteCaptor.capture());
        verify(outputStream, atLeast(0)).write(intCaptor.capture());

        Assertions.assertFalse(
            byteCaptor.getAllValues().isEmpty() && intCaptor.getAllValues().isEmpty(),
            "Help command should print help menu"
        );
    }
}