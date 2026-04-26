package com.aska.palindrom.presentation.panel.components;

import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

public class TextFileDropHandler extends TransferHandler {
    private final JTextArea inputArea;
    private final Runnable onSuccess;
    private final Consumer<String> onError;

    public TextFileDropHandler(JTextArea inputArea, Runnable onSuccess, Consumer<String> onError) {
        this.inputArea = inputArea;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            onError.accept("Only file drag and drop is supported.");
            return false;
        }

        try {
            @SuppressWarnings("unchecked")
            List<File> files =
                    (List<File>)
                            support.getTransferable()
                                    .getTransferData(DataFlavor.javaFileListFlavor);

            if (files.isEmpty()) {
                onError.accept("No file was provided.");
                return false;
            }

            File file = files.get(0);

            if (!isSupportedTextFile(file)) {
                onError.accept("Only .txt files are supported.");
                return false;
            }

            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            inputArea.setText(content);
            inputArea.setCaretPosition(0);

            onSuccess.run();
            return true;
        } catch (IOException e) {
            onError.accept("Could not read the dropped file.");
            return false;
        } catch (Exception e) {
            onError.accept("Could not import the dropped file.");
            return false;
        }
    }

    private boolean isSupportedTextFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".txt");
    }
}
