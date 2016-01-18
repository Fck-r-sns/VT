package com.vt.timedriven;

import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */

public interface TimeDrivenExecutable {
    boolean execute(); // checks Environment.gameTime
    void setValuesHistory(ValuesChangeHistory history); // for rewind
}
