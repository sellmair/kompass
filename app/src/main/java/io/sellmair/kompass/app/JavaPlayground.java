package io.sellmair.kompass.app;

import android.os.Bundle;

/**
 * Created by sebastiansellmair on 10.12.17.
 */

public class JavaPlayground {

    static void play() {
        ExampleDestination destination = new ExampleDestination(1, 0, "Hallo");
        Bundle bundle = new Bundle();
        io.sellmair.kompass.app.ExampleDestinationSerializer.writeToBundle(destination, bundle);
        io.sellmair.kompass.app.ExampleDestinationSerializer.createFromBundle(bundle);

    }


}

