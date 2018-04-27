package formula1.fetcher;

import java.io.IOException;

/**
 * Created by Oleksandr Ryzhkov on 18.04.2018.
 */
public interface DataHandler {
    void fetchDataFromServer() throws IOException;

    void handleData();
}
