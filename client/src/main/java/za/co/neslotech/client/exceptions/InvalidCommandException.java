package za.co.neslotech.client.exceptions;

import java.io.IOException;

public class InvalidCommandException extends IOException {

    public InvalidCommandException(final String message) {
        super(message);
    }

}
